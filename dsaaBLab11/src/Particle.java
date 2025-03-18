public class Particle {
    public double X;
    public double Y;

    public Particle (double x, double y){
        X = x;
        Y = y;
    }

    public int hashCode(){
        return (int)(X+Y);
    }


}
