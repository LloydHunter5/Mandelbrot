// Evan Strohman
// Algorithms
// 9/2/21
// Homework #4

// This class contains methods to represent the mandelbrot set, 
// a set of complex numbers based on an equation
public class Mandelbrot {
    private final int limit;
    public Mandelbrot(){
        this.limit = 1000;
    }
    public Mandelbrot(int limit){
        if(limit > 0) this.limit = limit;
        else this.limit = 1000;
    }
    // checks if a value is within the mandelbrot set, 
    // checking iterations up to the specified limit
    public boolean isInMandelbrotSet(Complex c){
        Complex z = Complex.ZERO;
        for (int i = 0; i < this.limit; i++) {
            z = z.square().add(c);
            if (z.modulus() > 2)return false;
        }
        return true;
    }
    // returns the amount of iterations needed for a complex number to escape the set
    // if the number is in the set, return -1
    public int escapeTime(Complex c){
        Complex z = Complex.ZERO;
        for (int i = 0; i < this.limit; i++) {
            z = z.square().add(c);
            if (z.modulus() > 2) return i;
        }
        return -1;
    }
    
}
