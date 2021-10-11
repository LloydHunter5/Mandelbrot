import static java.lang.String.valueOf;
// Evan Strohman
// Algorithms
// 9/2/21
// Homework #4

// This class represents a complex number, 
// as well as providing basic mathematical operations and comparisons
// relating to it.

public class Complex implements Comparable<Complex>{
    private final double re;
    private final double im;
    
    public static final Complex ZERO = new Complex(0.0,0.0);
    public static final Complex ONE = new Complex(1.0,0.0);
    public static final Complex I = new Complex(0.0, 1.0);
    
    public Complex(double imaginary){
        this.re = 0.0;
        this.im = imaginary;
    }
    public Complex(double real, double imaginary){
        this.re = real;
        this.im = imaginary;
    }
    
    //Mathematical Operations
    
    public Complex add(Complex other){
        double r = this.re + other.re;
        double i = this.im + other.im;
        return new Complex(r,i);
    }
    public Complex subtract(Complex other){
        double r = this.re - other.re;
        double i = this.im - other.im;
        return new Complex(r,i);
    }
    public Complex multiply(Complex other){
        double r = (this.re*other.re - this.im*other.im);
        double i = (this.re*other.im + this.im*other.re);
        return new Complex(r,i);
    }
    public Complex divide(Complex other){
        double r = (this.re*other.re + this.im*other.im) / 
                   (other.re*other.re + other.im*other.im);
        double i = (this.im*other.re - this.re*other.im) / 
                   (other.re*other.re + other.im*other.im);
        return new Complex(r,i);
    }   
    public Complex negate(){
        return new Complex(-this.re,-this.im);
    }
    public Complex conjugate(){
        return new Complex(re, -im);
    }
    public double modulus(){
        return Math.sqrt(re*re + im*im);
    }
    public Complex square(){
        return this.multiply(this);
    }
    public Complex exponentiate(int exponent){
        Complex result = this;
        if(exponent == 0) return Complex.ONE;
        else if(exponent > 0){
            for (int i = 0; i < exponent-1; i++) {
                result = result.multiply(this);
            }
        }else{
            Complex invThis = Complex.ONE.divide(this);
            result = Complex.ONE.divide(this);
            for (int i = 0; i > exponent+1; i--){
                result = result.multiply(invThis);
            }
        }
        return result;
    }
    public double arg(){
        return Math.atan2(im, re);
    }
    
    public double getReal(){
        return re;
    }
    public double getImaginary(){
        return im;
    }
    @Override
    public String toString(){
        if(re == 0) return im + "i";
        else if(im == 0) return valueOf(re);
        else if(im < 0) return re + " - " + -im + "i";
        else return re + " + " + im + "i";
    }
    @Override
    public int compareTo(Complex other){
        //Uses if statements instead of subtraction to prevent lossy conversions from doubles
        if(this.equals(other)) return 0;
        if(this.modulus() == other.modulus()){
            if (this.arg() - other.arg() < 0) return 1;
            else return -1;
        }
        if (this.modulus() - other.modulus() < 0) return 1;
        else return -1;
    }
    @Override
    public boolean equals(Object other){
        return other instanceof Complex && this.equals((Complex) other);
    }
    public boolean equals(Complex other){
        return other != null && this.re == other.re && this.im == other.im;
    }
}
