import java.util.ArrayList;

public class Cell {
    private final double width;
    private double mass;
    private double x;
    private double y;
    private double cx;
    private double cy;
    Cell[] subCells;
    boolean hasSubCells;
    private Particle particle;
    private Cell parent;
    private final ArrayList<Particle> particles;

    public double getMass() {
        return mass;
    }

    public double getWidth() {
        return width;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getCx() {
        return cx;
    }

    public double getCy() {
        return cy;
    }

    public Particle getParticle() {
        return particle;
    }

    public boolean isHasSubCells() {
        return hasSubCells;
    }

    public Cell[] getSubCells() {
        return subCells;
    }

    public Cell getParent() {
        return parent;
    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public Cell(double width, double x, double y) {
        this.width = width;
        this.x = x;
        this.y = y;
        this.particles = new ArrayList<>();
    }

    public Cell(double width) {
        this(width, 0, 0);
    }

    public void COM() {
        if (this.particle != null) {
            this.mass = this.particle.getMass();
            this.cx = this.particle.getRx();
            this.cy = this.particle.getRy();
            return;
        }
        if (!this.hasSubCells) return;
        for (Cell cell: subCells) {
            cell.COM();
            this.mass += cell.mass;
            this.cx += cell.cx * cell.mass;
            this.cy += cell.cy * cell.mass;
        }
        this.cx /= this.mass;
        this.cy /= this.mass;
    }

    public void setLocation(double width){
        // Set location of new cells
        subCells[0].x = x;
        subCells[0].y = y;

        subCells[1].x = x + width;
        subCells[1].y = y;

        subCells[2].x = x;
        subCells[2].y = y + width;

        subCells[3].x = x + width;
        subCells[3].y = y + width;
    }


    public void generateSubCells(){
        // Calculate subcell dimensions
        double width  = this.width / 2.0;
        this.subCells = new Cell[4];

        // Cell no longer a leaf
        this.hasSubCells = true;

        // Create and initialize new subcells
        for (int i = 0; i < 4; i++) {
            this.subCells[i] = new Cell(width);
            subCells[i].parent = this;
        }
        setLocation(width);
    }

    public int locateSubCell(Particle particle){
        if (particle.getRy() > this.subCells[3].y){
            if (particle.getRx() > this.subCells[3].x){
                return 3;
            }
            else return 2;
        }
        else {
            if (particle.getRx() > this.subCells[1].x){
                return 1;
            }
            else return 0;
        }
    }

    public void addParticle(Particle particle) {
        this.particles.add(particle);
        if (this.hasSubCells) {
            int sc = this.locateSubCell(particle);
            this.subCells[sc].addParticle(particle);
            return;
        }
        if (this.particle == null) {
            this.particle = particle;
            particle.setCell(this);
            return;
        }
        this.generateSubCells();
        int sc1 = this.locateSubCell(this.particle);
        int sc2 = this.locateSubCell(particle);
        this.subCells[sc1].addParticle(this.particle);
        this.subCells[sc2].addParticle(particle);
        this.particle = null;
    }
}