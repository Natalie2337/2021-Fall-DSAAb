import edu.princeton.cs.algs4.*;
import java.awt.Color;

public class CollisionSystem {
    private static final double HZ = 10;    // number of redraw events per clock tick
    private double t  = 0.0;          // simulation clock time
    private final Particle[] particles;     // the array of particles
    private final int size;
    private final int n;
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
            if (particle.getRadius() * 2 > maxD) maxD = particle.getRadius();
        }
        this.n = 100000 / particles.length;
    }

    private void predict(Particle a, double deltaT) {
        if (a == null) return;

        // particle-particle collisions
        for (Particle particle : particles) {
            double dt = a.timeToHit(particle);
            if (dt < 0 && a.collision(particle)) {
                a.bounceOff(particle);
                continue;
            }
            if (dt < deltaT && dt >= 0)
                pq.insert(new Event(t + dt, a, particle));
        }

        // particle-wall collisions
        double dtX = a.timeToHitVerticalWall(size);
        double dtY = a.timeToHitHorizontalWall(size);
        if (dtX < 0 && a.collisionToVerticalWall(size)) a.bounceOffVerticalWall();
        else if (dtX < deltaT && dtX >= 0) pq.insert(new Event(t + dtX, a, null));
        if (dtY < 0 && a.collisionToHorizontalWall(size)) a.bounceOffHorizontalWall();
        else if (dtY < deltaT && dtY >= 0) pq.insert(new Event(t + dtY, null, a));
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

    private void redrawPQ() {
        StdDraw.clear();
        for (Particle particle : particles) {
            particle.draw();
        }
        StdDraw.show();
        StdDraw.pause(1);
        Event e = new Event(t + 1.0 / HZ, null, null);
        e.redraw = true;
        pq.insert(e);
    }

    /**
     * Simulates the system of particles for the specified amount of time.
     *
     * @param  limit the amount of time
     */

    public void simulatePQ(double limit) {
        double deltaT = 1.0 / HZ / n;
        pq = new MinPQ<>();
        for (Particle value : particles) {
            predict(value,deltaT);
        }
        Event e0 = new Event(0, null, null);
        e0.redraw = true;
        pq.insert(e0);
        pq.insert(new Event(0, null, null));
        for (Particle particle: particles) particle.prepare(deltaT);
        while (!pq.isEmpty() && t < limit) {
            Event e = pq.delMin();
            Particle a = e.a;
            Particle b = e.b;
            double dt = e.time - t;
            move(dt);
            t += dt;
            if (a != null && b != null) a.bounceOff(b);
            else if (a != null) a.bounceOffVerticalWall();
            else if (b != null) b.bounceOffHorizontalWall();
            else if (e.redraw) redrawPQ();
            else update();
        }
    }

    public void simulate(double limit) {
        double time = 1.0 / HZ;
        double dt = time / n;
        for (Particle particle: particles) particle.prepare(dt);
        // initialize PQ with collision events and redraw event
        while (t < limit) {
            for (int i = 0; i < n; i++) {
                operate(dt);
            }
            redraw();
        }
    }

    public void printInformationPQ(double[] times, int[] index) {
        double deltaT = 1.0 / HZ / n;
        pq = new MinPQ<>();
        for (Particle value : particles) {
            predict(value,deltaT);
        }
        pq.insert(new Event(0, null, null));
        for (int i = 0; i < times.length; i++) {
            Event e0 = new Event(times[i], particles[index[i]], null);
            e0.print = true;
            pq.insert(e0);
        }
        int test = 0;
        for (Particle particle: particles) particle.prepare(deltaT);
        while (!pq.isEmpty() && test < times.length) {

            // get impending event, discard if invalidated
            Event e = pq.delMin();
            Particle a = e.a;
            Particle b = e.b;
            double dt = e.time - t;
            move(dt);
            t += dt;
            if (e.print) {
                for (Particle particle0: particles) particle0.complete(dt);
                StdOut.print(a.getRx() + " " + a.getRy() + " " + a.getVx() + " " + a.getVy() + "\n");
                for (Particle particle0: particles) particle0.prepare(dt);
                test++;
            }
            else if (a != null && b != null) a.bounceOff(b);
            else if (a != null) a.bounceOffVerticalWall();
            else if (b != null) b.bounceOffHorizontalWall();
            else if (e.redraw) redraw();
            else update();
        }
    }

    public void printInformation(double[] times, int[] index) {
        double time = 1.0 / HZ;
        double dt = time / n;
        int test = 0;
        for (Particle particle: particles) particle.prepare(dt);
        // the main event-driven simulation loop
        while (test < times.length) {
            Particle particle = particles[index[test]];
            for (int i = 0; i < n; i++) {
                operate(dt);
                if (test < times.length && t > times[test]) {
                    for (Particle particle0: particles) particle0.complete(dt);
                    StdOut.print(particle.getRx() + " " + particle.getRy() + " " + particle.getVx() + " " + particle.getVy() + "\n");
                    for (Particle particle0: particles) particle0.prepare(dt);
                    test++;
                }
            }
        }
    }

    private void operate(double dt) {
        collision(dt);
//        generateBHTree();
        updateAcc();
//        updateAccBH();
        accelerate(dt);
        move(dt);
        t += dt;
    }

    private void updateAccBH() {
        for (Particle particle: particles) {
            particle.setAx(0);
            particle.setAy(0);
        }
        for (Particle particle: particles) {
            particle.updateAcc(root);
        }
    }

    private void updateAcc() {
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

    private void collisionBH(double dt) {
        for (Particle particle: particles) {
            particle.complete(dt);
            particle.setChecked(false);
        }
        for (Particle value : particles) {
            value.collision(value.getCell(), maxD, particles, size);
        }
        for (Particle particle: particles) particle.prepare(dt);
    }

    private void collision(double dt) {
        for (Particle particle: particles) particle.complete(dt);
        for (int i = 0; i < particles.length; i++) {
            if (particles[i].collisionToHorizontalWall(size)) {
                particles[i].bounceOffHorizontalWall();
            }
            if (particles[i].collisionToVerticalWall(size)) {
                particles[i].bounceOffVerticalWall();
            }
            for (int j = i + 1; j < particles.length; j++) {
                if (particles[i].collision(particles[j])) {
                    particles[i].bounceOff(particles[j]);
                }
            }
        }
        for (Particle particle: particles) particle.prepare(dt);
    }

    private void move(double dt) {
        for (Particle particle : particles) particle.move(dt);
    }

    private void accelerate(double dt) {
        for (Particle particle : particles) particle.accelerate(dt);
    }

    private void update() {
        double dt = 1.0 / HZ / n;
//        generateBHTree();
        updateAcc();
//        updateAccBH();
        accelerate(dt);
        for (Particle particle : particles) predict(particle,dt);
        pq.insert(new Event(t + 1.0 / n, null, null));
    }

    private void kineticEnergy() {
        double kineticEnergy = 0;
        for (Particle particle : particles) {
            kineticEnergy += particle.kineticEnergy();
        }
        StdOut.print(kineticEnergy + "\n");
    }

    public void generateBHTree() {
        root = new Cell(size);
        for (Particle particle : particles) {
            root.addParticle(particle);
        }
        root.COM();
    }

    private static class Event implements Comparable<Event> {
        private final double time;         // time that event is scheduled to occur
        private final Particle a, b;       // particles involved in event, possibly null
        private final int countA, countB;  // collision counts at event creation
        private boolean redraw;
        private boolean print;


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
        }
        long startTime = System.currentTimeMillis();
        if (line.equals("gui")) {
            StdDraw.setCanvasSize(600, 600);
            StdDraw.setXscale(0,squareSize);
            StdDraw.setYscale(0,squareSize);

            // enable double buffering
            StdDraw.enableDoubleBuffering();

            CollisionSystem system = new CollisionSystem(particles, squareSize);
            system.simulate(10000);
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
            system.printInformationPQ(time,index);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
    }
}