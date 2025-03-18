
import edu.princeton.cs.algs4.*;

import java.util.ArrayList;

public class Cell {
    private double width;
    private double mass;
    private double x;
    private double y;
    private double cx;
    private double cy;
    Cell[] subCells;
    boolean hasSubCells;
    private Particle particle;
    private Cell parent;

    public ArrayList<Particle> getParticles() {
        return particles;
    }

    private ArrayList<Particle> particles;
//    int index;

    public double getWidth() {
        return width;
    }

    public double getMass() {
        return mass;
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

    public void BH_set_location_subcells(double width){
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


    public void BH_generate_subcells(){
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
        BH_set_location_subcells(width);
    }

    /*
     * Locates the subcell to which the particle must be added
     *
     */

    //这里原本用的是particle的index---改成直接调用particle了
    public int BH_locate_subcell(Particle particle){
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

    /*
     * Added a particle to the cell. If a particle already
     * exists, the cube/cell is sub-divided adding the existing
     * and new particle to the sub cells
     *
     */

    //假设一个particle 被加到了一个 cell里，且这个cell至多包含一个particle
    //想要做到的是：如果这个cell 里面只有一个particle 则这个cell.particle (particle)
//    public void BH_add_to_cell(Cell cell, Particle particle) {
//
//        //if cell contains no particle
//        //equal to the condition: no_subcell=true && cell.particle==null
//        // then cell.particle=particle
//        cell.particles.add(particle);
//        if (!cell.hasSubCells && cell.particle == null) {
//            cell.particle = particle;
//            particle.setCell(cell);
//            return;
//        }
//        //cell contain particle?
//        //生成 4个 子cell
//        if (!cell.hasSubCells) BH_generate_subcells(cell);
//
//        // The current cell's body must now be re-added to one of its subcells
//        int sc1 = BH_locate_subcell(cell, cell.particle);
//
////        cell.subcells[sc1].particle = cell.particle;
////
////        cell.particle=null;
//        // Locate subcell for new body
//        int sc2 = BH_locate_subcell(cell, particle);
//        BH_add_to_cell(cell.subCells[sc1], cell.particle);
//        BH_add_to_cell(cell.subCells[sc2], particle);
////        if (sc1 == sc2) {
////
////            BH_add_to_cell(cell.subCells[sc1], particle);
////        }
////        else {
////            cell.subCells[sc2].particle = particle;
////        }
//        cell.particle=null;
//    }

    /*
     * Generates the octtree for the entire system of
     * particles
     *
     */

    //生成树的代码，但是还没有达到"不是只包含一个particle就为null"
//    void BH_generate_octTree(Particle[] particles, int size) {
//
//        // Initialize root of octTree
//        //这里的size指的是坐标轴width
//        root_cell = new Cell(size);
//        root_cell.particle = null;
//        root_cell.x = 0;
//        root_cell.y = 0;
//
//
//        for (int i = 0; i < particles.length; i++) {
//            Cell cell = root_cell;
//
//            // Find which node to add the body to
//            while (!cell.no_subcells) {
//                int sc = BH_locate_subcell(cell, particles[i]);
//                cell = cell.subcells[sc];
//            }
//            //这里的cell是 没有子cell的情况！！！
//            BH_add_to_cell(cell, particles[i]);
//        }
//    }

    public void addParticle(Particle particle) {
        this.particles.add(particle);
        if (this.hasSubCells) {
            int sc = this.BH_locate_subcell(particle);
            this.subCells[sc].addParticle(particle);
            return;
        }
        if (this.particle == null) {
            this.particle = particle;
            particle.setCell(this);
            return;
        }
        this.BH_generate_subcells();
        int sc1 = this.BH_locate_subcell(this.particle);
        int sc2 = this.BH_locate_subcell(particle);
        this.subCells[sc1].addParticle(this.particle);
        this.subCells[sc2].addParticle(particle);
        this.particle = null;

//        while (cell.hasSubCells) {
//            cell.particles.add(particle);
//            int sc = cell.BH_locate_subcell(cell, particle);
//            cell = cell.subCells[sc];
//        }
//        //这里的cell是 没有子cell的情况！！！
//        cell.BH_add_to_cell(cell, particle);
    }

//    public void BH_add_to_cell(Cell cell, Particle particle) {
//        cell.particles.add(particle);
//        if (!cell.hasSubCells && cell.particle == null) {
//            cell.particle = particle;
//            particle.setCell(cell);
//            return;
//        }
//        if (!cell.hasSubCells) BH_generate_subcells(cell);
//        int sc1 = BH_locate_subcell(cell, cell.particle);
//        int sc2 = BH_locate_subcell(cell, particle);
//        BH_add_to_cell(cell.subCells[sc1], cell.particle);
//        BH_add_to_cell(cell.subCells[sc2], particle);
//        cell.particle=null;
//    }
}