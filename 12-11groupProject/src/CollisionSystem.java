import edu.princeton.cs.algs4.*;
import java.awt.Color;
import java.util.PriorityQueue;

public class CollisionSystem {
    private static final double HZ = 5;    // number of redraw events per clock tick
    private double t  = 0.0;          // simulation clock time
    private final Particle[] particles;     // the array of particles
    private final int size;
    private final int n = 800;
    private Cell root;
    private MinPQ<Event> pq;
    private double maxD;

    /**
     * Initializes a system with the specified collection of particles.
     * The individual particles will be mutated during the simulation.
     *
     * @param  particles the array of particles
     */

    public CollisionSystem(Particle[] particles, int size) {
        this.particles = particles.clone();   // defensive copy
        this.size = size;
        for (Particle particle: particles) {
            if (particle.getRadius() * 2 >maxD) maxD = particle.getRadius();
        }
    }

    public boolean hasCollision (Particle[] particles){
        int mult = 1;

        for (Particle particle: particles) {
            if(particle.collision(particle)) mult = 0;
            else mult=1;
            mult = mult * mult;
        }

        return mult == 0;
    }
    //*-----------------------------------------------------

    //*------------------------------------------------------
    private void predict(Particle a, double limit) {
        if (a == null) return;

        // predict 经过以HZ为频率redraw，期间若所有粒子没有碰撞,存入PQ
        if(t + 1.0 / HZ <= limit && !hasCollision(particles)){
            for (int i = 0; i < particles.length; i++) {
                pq.insert(new Event(t + 1.0/HZ, a, particles[i]));
            }
        }

        // particle-particle collisions
        for (int i = 0; i < particles.length; i++) {
            double dt = a.timeToHit(particles[i]);
            if (t + dt <= limit)
                pq.insert(new Event(t + dt, a, particles[i]));
        }

        // particle-wall collisions
        double dtX = a.timeToHitVerticalWall();
        double dtY = a.timeToHitHorizontalWall();
        if (t + dtX <= limit) pq.insert(new Event(t + dtX, a, null));
        if (t + dtY <= limit) pq.insert(new Event(t + dtY, null, a));
    }

    // redraw all particles
    private void redraw() {
        StdDraw.clear();
        for (Particle particle : particles) {
            particle.draw();
        }
        StdDraw.show();
        StdDraw.pause(1);
    }

    /**
     * Simulates the system of particles for the specified amount of time.
     *
     * @param  limit the amount of time
     */

    public void simulate(double limit) {
//        pq = new MinPQ<Event>();
//        for (int i = 0; i < particles.length; i++) {
//            predict(particles[i], limit);
//        }
//        pq.insert(new Event(0, null, null));
//
//        while (!pq.isEmpty() && hasCollision(particles)) {
//
//            // get impending event, discard if invalidated
//            Event e = pq.delMin();
//            if (!e.isValid()) continue;
//            Particle a = e.a;
//            Particle b = e.b;
//
//            // physical collision, so update positions, and then simulation clock
//            for (Particle particle: particles) particle.prepare(1.0 / HZ / n);
//
//            moveAndCollision(e.time-t,n);
//            t = e.time;//e.time:从0时刻到现在，每隔一段事件就要例行predict一下，碰撞后也要predict
//            redraw();
//
//            // update the priority queue with new collisions involving a or b
//            for (int i = 0; i < particles.length; i++) {
//                predict(particles[i], limit);//再对所有粒子重新predict
//            }
//        }
        for (Particle particle: particles) particle.prepare(1.0 / HZ / n);
        // initialize PQ with collision events and redraw event
        while (t < limit) {
            moveAndCollision(1.0 / HZ, n);
            redraw();
        }
    }

    public void printInformation(double[] times, int[] index) {
        double dt = 1.0 / HZ / n;
        int test = 0;
        for (Particle particle: particles) particle.prepare(dt);
        // the main event-driven simulation loop
        while (test < times.length) {
            Particle particle = particles[index[test]];
            moveAndCollision(1.0 / HZ, n);
            if (t > times[test]) {
                for (Particle particle0: particles) particle0.complete(dt);
                StdOut.print(particle.getRx() + " " + particle.getRy() + " " + particle.getVx() + " " + particle.getVy() + "\n");
                for (Particle particle0: particles) particle0.prepare(dt);
                test++;
            }
        }
    }

    private void updateAcc() {
        for (Particle particle: particles) {
            particle.setAx(0);
            particle.setAy(0);
        }
        for (Particle particle: particles) {
            particle.updateAcc(root);
        }
    }

    private void update_acc() {
        for (Particle particle: particles) {
            particle.setAx(0);
            particle.setAy(0);
        }
        for(int i = 0; i < particles.length; i++){
            for (int j = i + 1; j < particles.length; j++) {
                particles[i].acceleration(particles[j]);
            }
        }
    }

    private void collision(double dt) {
        for (Particle particle: particles) {
            particle.complete(dt);
            particle.setChecked(false);
        }
        for (int i = 0; i < particles.length; i++) {
//            if (particles[i].collisionToHorizontalWall(size)) {
//                particles[i].bounceOffHorizontalWall();
//            }
//            if (particles[i].collisionToVerticalWall(size)) {
//                particles[i].bounceOffVerticalWall();
//            }
//            for (int j = i + 1; j < particles.length; j++) {
//                if (particles[i].collision(particles[j])) {
//                    particles[i].bounceOff(particles[j]);
//                }
//            }
        particles[i].collision(particles[i].getCell(), maxD, particles, size);
        }
        for (Particle particle: particles) particle.prepare(dt);
    }

    private void move(double dt) {
        updateAcc();
        for (Particle particle : particles) {
            particle.accelerate(dt);
            particle.move(dt);
        }
    }

    private void moveAndCollision(double time, int n) {
        for (int i = 0; i < n; i++) {
            BH_generate_octTree();
            root.COM();
            collision(time / n);
            move(time / n);
        }
        t += time;
    }

    private void printKineticEnergy() {
        double kineticEnergy = 0;
        for (Particle particle : particles) {
            kineticEnergy += particle.kineticEnergy();
        }
        StdOut.print(kineticEnergy + "\n");
    }

    public void BH_generate_octTree() {

        // Initialize root of octTree
        //这里的size指的是坐标轴width
        root = new Cell(size);

        for (Particle particle : particles) {
            root.addParticle(particle);
        }
    }

    private static class Event implements Comparable<Event> {
        private final double time;         // time that event is scheduled to occur
        private final Particle a, b;       // particles involved in event, possibly null
        private final int countA, countB;  // collision counts at event creation


        // create a new event to occur at time t involving a and b
        public Event(double t, Particle a, Particle b) {
            this.time = t;
            this.a    = a;
            this.b    = b;
            if (a != null) countA = a.count();
            else           countA = -1;
            if (b != null) countB = b.count();
            else           countB = -1;
        }

        // compare times when two events will occur
        public int compareTo(Event that) {
            return Double.compare(this.time, that.time);
        }

        // has any collision occurred between when event was created and now?
        public boolean isValid() {
            if (a != null && a.count() != countA) return false;
            if (b != null && b.count() != countB) return false;
            return true;
        }
    }

    /***************************************************************************
     *  An event during a particle collision simulation. Each event contains
     *  the time at which it will occur (assuming no supervening actions)
     *  and the particles a and b involved.
     *
     *    -  a and b both null:      redraw event
     *    -  a null, b not null:     collision with vertical wall
     *    -  a not null, b null:     collision with horizontal wall
     *    -  a and b both not null:  binary collision between a and b
     *
     ***************************************************************************/

    /**
     * Unit tests the {@code CollisionSystem} data type.
     * Reads in the particle collision system from a standard input
     * (or generates {@code N} random particles if a command-line integer
     * is specified); simulates the system.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String line = StdIn.readLine();
        if (!line.equals("gui") && !line.equals("terminal")) {
            StdOut.print("Illegal input. ");
            return;
        }
        int squareSize;
        Particle[] particles;
        squareSize = StdIn.readInt();//区域大小
        int n = StdIn.readInt();
        particles = new Particle[n];
        for (int i = 0; i < n; i++) {
            double rx = StdIn.readDouble();
            double ry = StdIn.readDouble();
            double vx = StdIn.readDouble();
            double vy = StdIn.readDouble();
            double radius = StdIn.readDouble();
            double mass = StdIn.readDouble();
            int r = StdIn.readInt();
            int g = StdIn.readInt();
            int b = StdIn.readInt();
            Color color = new Color(r, g, b);
            particles[i] = new Particle(rx, ry, vx, vy, radius, mass, color);
            double maxD = 0;
        }
        if (line.equals("gui")) {
            StdDraw.setCanvasSize(600, 600);
            StdDraw.setXscale(0,squareSize);
            StdDraw.setYscale(0,squareSize);

            // enable double buffering
            StdDraw.enableDoubleBuffering();

            CollisionSystem system = new CollisionSystem(particles, squareSize);
            system.simulate(1000);
        }
        else {
            int num_test = StdIn.readInt();
            double[] time = new double[num_test];
            int[] index = new int[num_test];
            for (int i = 0; i < num_test; i++) {
                time[i] = StdIn.readDouble();//第一个数字表示秒数
                index[i] = StdIn.readInt();//第二个数字表示index
            }
            CollisionSystem system = new CollisionSystem(particles, squareSize);
            system.BH_generate_octTree();
            system.printInformation(time,index);
        }
    }
}