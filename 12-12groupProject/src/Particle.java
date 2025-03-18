import edu.princeton.cs.algs4.*;
import java.awt.Color;

public class Particle {
    private static final double INFINITY = Double.POSITIVE_INFINITY;

    public double getRx() {
        return rx;
    }

    public double getRy() {
        return ry;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public double getMass() {
        return mass;
    }

    public double getAx() {
        return ax;
    }

    public void setAx(double ax) {
        this.ax = ax;
    }

    public double getAy() {
        return ay;
    }

    public void setAy(double ay) {
        this.ay = ay;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    private double rx, ry;        // position
    private double vx, vy;        // velocity
    private double ax, ay;        // acceleration
    private int count;            // number of collisions so far

    public double getRadius() {
        return radius;
    }

    private final double radius;  // radius
    private final double mass;    // mass
    private final Color color;    // color
    private static final double G = 6.67259 * 10e-11;
    private Cell cell;
    private boolean checked;


    /**
     * Initializes a particle with the specified position, velocity, radius, mass, and color.
     *
     * @param  rx <em>x</em>-coordinate of position
     * @param  ry <em>y</em>-coordinate of position
     * @param  vx <em>x</em>-coordinate of velocity
     * @param  vy <em>y</em>-coordinate of velocity
     * @param  radius the radius
     * @param  mass the mass
     * @param  color the color
     */
    public Particle(double rx, double ry, double vx, double vy, double radius, double mass, Color color) {
        this.vx = vx;
        this.vy = vy;
        this.rx = rx;
        this.ry = ry;

//        this.ay = -0.0005;

        this.radius = radius;
        this.mass   = mass;
        this.color  = color;
    }

    /**
     * Initializes a particle with a random position and velocity.
     * The position is uniform in the unit box; the velocity in
     * either direciton is chosen uniformly at random.
     */
    public Particle() {
        rx     = StdRandom.uniform(0.0, 1.0);
        ry     = StdRandom.uniform(0.0, 1.0);
        vx     = StdRandom.uniform(-0.005, 0.005);
        vy     = StdRandom.uniform(-0.005, 0.005);
        radius = 0.02;
        mass   = 0.5;
        color  = Color.BLACK;
    }

    /**
     * Moves this particle in a straight line (based on its velocity)
     * for the specified amount of time.
     *
     * @param  dt the amount of time
     */

    /**
     * Warning: the velocity is stored half a time step ahead of the position.
     * * @param p before the update: x(t), v(t+dt/2), a(t);
     * *          after the update: x(t+dt), v(t+3*dt/2), a(t+dt)
     */
    public void move(double dt) {
        rx += vx * dt;
        ry += vy * dt;
    }

    public void accelerate(double dt) {
        vx += ax * dt;
        vy += ay * dt;
    }

    /**
     * prepare method for bringing the velocity in the desired half step
     * * @param p before the update: v(t);
     *            after the update: v(t+dt/2)
     */
    public void prepare(double dt){
        vx += ax * 0.5 * dt;
        vy += ay * 0.5 * dt;
    }

    /**
     * complete method for bringing the velocity in the desired half step
     * before the update: v(t+dt/2);
     * after the update: v(t)
     */
    public void complete(double dt){
        vx -= ax * 0.5 * dt;
        vy -= ay * 0.5 * dt;
    }

    /**
     * Draws this particle to standard draw.
     */
    public void draw() {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(rx, ry, radius);
    }

    /**
     * Returns the number of collisions involving this particle with
     * vertical walls, horizontal walls, or other particles.
     * This is equal to the number of calls to {@link #bounceOff},
     * {@link #bounceOffVerticalWall}, and
     * {@link #bounceOffHorizontalWall}.
     *
     * @return the number of collisions involving this particle with
     *         vertical walls, horizontal walls, or other particles
     */
    public int count() {
        return count;
    }

    /**
     * Returns the amount of time for this particle to collide with the specified
     * particle, assuming no interening collisions.
     *
     * @param  that the other particle
     * @return the amount of time for this particle to collide with the specified
     *         particle, assuming no interening collisions;
     *         {@code Double.POSITIVE_INFINITY} if the particles will not collide
     */
    public double timeToHit(Particle that) {
        if (this == that) return INFINITY;
        double dx  = that.rx - this.rx;
        double dy  = that.ry - this.ry;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        double dvdr = dx*dvx + dy*dvy;
        if (dvdr > 0) return INFINITY;
        double dvdv = dvx*dvx + dvy*dvy;
        if (dvdv == 0) return INFINITY;
        double drdr = dx*dx + dy*dy;
        double sigma = this.radius + that.radius;
        double d = (dvdr*dvdr) - dvdv * (drdr - sigma*sigma);
        // if (drdr < sigma*sigma) StdOut.println("overlapping particles");
        if (d < 0) return INFINITY;
        return -(dvdr + Math.sqrt(d)) / dvdv;
    }


    /**
     * Returns the amount of time for this particle to collide with a vertical
     * wall, assuming no interening collisions.
     *
     * @return the amount of time for this particle to collide with a vertical wall,
     *         assuming no interening collisions;
     *         {@code Double.POSITIVE_INFINITY} if the particle will not collide
     *         with a vertical wall
     */
    public double timeToHitVerticalWall() {
        if      (vx > 0) return (1.0 - rx - radius) / vx;
        else if (vx < 0) return (radius - rx) / vx;
        else             return INFINITY;
    }

    /**
     * Returns the amount of time for this particle to collide with a horizontal
     * wall, assuming no interening collisions.
     *
     * @return the amount of time for this particle to collide with a horizontal wall,
     *         assuming no interening collisions;
     *         {@code Double.POSITIVE_INFINITY} if the particle will not collide
     *         with a horizontal wall
     */
    public double timeToHitHorizontalWall() {
        if      (vy > 0) return (1.0 - ry - radius) / vy;
        else if (vy < 0) return (radius - ry) / vy;
        else             return INFINITY;
    }

    public void collision(Cell cell, double d, Particle[] particles, int size) {
        if (cell == null) {
            if (this.collisionToVerticalWall(size)) this.bounceOffVerticalWall();
            if (this.collisionToHorizontalWall(size)) this.bounceOffHorizontalWall();
            for (Particle particle1: particles) {
                if (particle1.isChecked()) continue;
                if (this.collision(particle1)) this.bounceOff(particle1);
            }
            this.checked = true;
            return;
        }
        if (cell.getWidth() <= 2 * d) {
            this.collision(cell.getParent(), d, particles, size);
            return;
        }
        if (this.rx > cell.getX() + d && this.ry > cell.getY() + d && this.rx < cell.getX() + cell.getWidth() - d && this.rx < cell.getY() + cell.getWidth() - d) {
            for (Particle particle: cell.getParticles()) {
                if (particle.checked) continue;
                if (this.collision(particle)) this.bounceOff(particle);
            }
            this.checked = true;
        }
        this.collision(cell.getParent(), d, particles, size);
    }

    public boolean collision(Particle particle) {
        if (this == particle) return false;
        double distanceSquare;
        double rx = particle.rx - this.rx;
        double ry = particle.ry - this.ry;
        double vx = particle.vx - this.vx;
        double vy = particle.vy - this.vy;
        distanceSquare = rx * rx + ry * ry;
        if (distanceSquare > (this.radius + particle.radius) * (this.radius + particle.radius)) {
            return false;
        }
        return !(rx * vx + ry * vy >= 0);
    }

    public boolean collisionToVerticalWall(int size) {
        if (this.rx < this.radius) {
            return this.vx < 0;
        }
        else if (size < this.radius +this.rx) {
            return this.vx > 0;
        }
        else {
            return false;
        }
    }

    public boolean collisionToHorizontalWall(int size) {
        if (this.ry < this.radius) {
            return this.vy < 0;
        }
        else if (size < this.radius +this.ry) {
            return this.vy > 0;
        }
        else {
            return false;
        }
    }

    public double dis(Particle that) {
        if (this == that) return 0;

        double dx  = that.rx - this.rx;
        double dy  = that.ry - this.ry;

//        double dxy = Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double dis(Cell cell) {
        double dx  = cell.getCx() - this.rx;
        double dy  = cell.getCy() - this.ry;

//        double dxy = Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void acceleration(Particle that) {
        if(this == that) return;
        double d = dis(that);
        double force = G * that.mass * this.mass / (d * d) / 10;
        double fX = force * (that.rx - this.rx) / d;
        double fY = force * (that.ry - this.ry) / d;
        this.ax += fX / this.mass;
        that.ax -= fX / that.mass;
        this.ay += fY / this.mass;
        that.ay -= fY / that.mass;
    }

    public void acceleration(Cell cell) {
        double d = dis(cell);
        if (d == 0) return;
        double force = G * cell.getMass() * this.mass / (d * d) / 10;
        double fX = force * (cell.getCx() - this.rx) / d;
        double fY = force * (cell.getCy() - this.ry) / d;
        this.ax += fX / this.mass;
        this.ay += fY / this.mass;
    }

    public boolean checkAndAcc(Cell cell) {
        if (cell.getParticle() != null) {
            this.acceleration(cell);
            return true;
        }
        if (cell.getWidth() / this.dis(cell) < 0.5 ) {
            this.acceleration(cell);
            return true;
        }
        return false;
    }

    public void updateAcc(Cell cell) {
        if (this.checkAndAcc(cell)) return;
        if (!cell.isHasSubCells()) return;
        for (Cell cell0: cell.subCells) {
            this.updateAcc(cell0);
        }
    }

    /**
     * Updates the velocities of this particle and the specified particle according
     * to the laws of elastic collision. Assumes that the particles are colliding
     * at this instant.
     *
     * @param  that the other particle
     */
    public void bounceOff(Particle that) {
        double dx  = that.rx - this.rx;
        double dy  = that.ry - this.ry;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        double dvdr = dx*dvx + dy*dvy;             // dv dot dr
        double dist = this.radius + that.radius;   // distance between particle centers at collison

        // magnitude of normal force
        double magnitude = 2 * this.mass * that.mass * dvdr / ((this.mass + that.mass) * dist);

        // normal force, and in x and y directions
        double fx = magnitude * dx / dist;
        double fy = magnitude * dy / dist;

        // update velocities according to normal force
        this.vx += fx / this.mass;
        this.vy += fy / this.mass;
        that.vx -= fx / that.mass;
        that.vy -= fy / that.mass;

        // update collision counts
        this.count++;
        that.count++;
    }

    /**
     * Updates the velocity of this particle upon collision with a vertical
     * wall (by reflecting the velocity in the <em>x</em>-direction).
     * Assumes that the particle is colliding with a vertical wall at this instant.
     */
    public void bounceOffVerticalWall() {
        vx = -vx;
        count++;
    }

    /**
     * Updates the velocity of this particle upon collision with a horizontal
     * wall (by reflecting the velocity in the <em>y</em>-direction).
     * Assumes that the particle is colliding with a horizontal wall at this instant.
     */
    public void bounceOffHorizontalWall() {
        vy = -vy;
        count++;
    }

    /**
     * Returns the kinetic energy of this particle.
     * The kinetic energy is given by the formula 1/2 <em>m</em> <em>v</em><sup>2</sup>,
     * where <em>m</em> is the mass of this particle and <em>v</em> is its velocity.
     *
     * @return the kinetic energy of this particle
     */
    public double kineticEnergy() {
        return 0.5 * mass * (vx * vx + vy * vy);
    }
}