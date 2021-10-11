import java.awt.Color;

// Evan Strohman
// 9/17/21
// Algorithms H
// Homework #4
public class Homework_4 {
    
    // This class uses an object of the class Canvas to draw
    // images of the mandelbrot set. The size and bounds of the image,
    // as well as different color schemes can be specified through the command line
    
    // I reccomened the following command line input, so far it's been the most interesting
    // image that I have been able to produce:
    // -width 600 -height 600 -limit 3333 -xmin -0.75 -xmax -0.7 -ymin 0.225 -ymax 0.275 -gradient
    // (a larger limit may make the image clearer, but it yields diminishing returns)
    
    // I ran into a peculiar error when testing this program with the command line
    // (i use WSL on my laptop), called headlessException. I was able to get it to
    // work using project properties on netbeans, and the bug seemed outside the scope
    // of this project. It happened when calling canvas.display(), and seemed related
    // to the graphics object. Hopefully it works on Mac (i assume it will), but it
    // just seemed strange that it didn't work for me
    
    public static void main(String[] args) {
        int height = 600, width = 600, limit = 1000;
        double xmin = -2.0, ymin = -1.5, xmax = 1.0, ymax = 1.5;
        Hue in = Hue.BLACK ,out = Hue.WHITE;
        boolean createGradient = false;
        String expecting = "";
        for(String arg : args){
            switch(arg){
                case "-width":
                    expecting = "width";
                    continue;
                case "-height":
                    expecting = "height";
                    continue;
                case "-limit":
                    expecting = "limit";
                    continue;
                case "-xmin":
                    expecting = "xmin";
                    continue;
                case "-xmax":
                    expecting = "xmax";
                    continue;
                case "-ymin":
                    expecting = "ymin";
                    continue;
                case "-ymax":
                    expecting = "ymax";
                    continue;
                case "-in":
                    expecting = "in";
                    continue;
                case "-out":
                    expecting = "out";
                    continue;   
                case "-gradient": //adds a cool gradient effect (doesn't use enums)
                    createGradient = true;
                    expecting = "";
                    continue;
            }
            switch(expecting){
                case "width":
                    expecting = "";
                    try{
                        width = Integer.parseInt(arg);
                    }catch(NumberFormatException e){
                        System.err.println("Invalid integer for width: " + arg);
                    }
                    break;
                case "height":
                    expecting = "";
                    try{
                       height = Integer.parseInt(arg);
                    }catch(NumberFormatException e){
                        System.err.println("Invalid integer for height: " + arg);
                    }
                    break;
                case "limit":
                    expecting = "";
                    try{
                        limit = Integer.parseInt(arg);
                    }catch(NumberFormatException e){
                        System.err.println("Invalid integer for limit: " + arg);
                    }
                    break;
                case "xmin":
                    expecting = "";
                    try{
                        xmin = Double.parseDouble(arg);
                    }catch(NumberFormatException e){
                        System.err.println("Invalid double for xmin: " + arg);
                    }
                    break;
                case "xmax":
                    expecting = "";
                    try{
                        xmax = Double.parseDouble(arg);
                    }catch(NumberFormatException e){
                        System.err.println("Invalid double for xmax: " + arg);
                    }
                    break;
                case "ymin":
                    expecting = "";
                    try{
                        ymin = Double.parseDouble(arg);
                    }catch(NumberFormatException e){
                        System.err.println("Invalid double for ymin: " + arg);
                    }
                    break;
                case "ymax":
                    expecting = "";
                    try{
                        ymax = Double.parseDouble(arg);
                    }catch(NumberFormatException e){
                        System.err.println("Invalid integer for ymax: " + arg);
                    }
                    break;
                case "in":
                    expecting = "";
                    try{
                        in = Hue.parseHue(arg);
                    }catch(IllegalArgumentException e){
                        System.err.println("Invalid Hue for in: " + arg);
                    }
                    break;
                case "out":
                    expecting = "";
                    try{
                        out = Hue.parseHue(arg);
                    }catch(IllegalArgumentException e){
                        System.err.println("Invalid Hue for out: " + arg);
                    }
                    break;
            }  
        }
        
        Mandelbrot m = new Mandelbrot(limit);
        Canvas canvas = new Canvas(height, width);
        canvas.setXRange(xmin, xmax);
        canvas.setYRange(ymin, ymax);
        double xstep = (xmax - xmin) / width;
        double ystep = (ymax - ymin) / height;
        for (double x = xmin; x < xmax; x += xstep) {
            for (double y = ymin; y < ymax; y += ystep) {
            // set the point x, y to be the desired color
                Complex c = new Complex(x,y);
                if(m.isInMandelbrotSet(c)) canvas.set(x, y, in.getColor());
                //fades from blue to white, then from white to orange based on escape time
                else if(createGradient){
                    int escapeTime = m.escapeTime(c);
                    if(escapeTime < 2) canvas.set(x, y, Hue.LIGHT_BLUE.getColor());
                    else{
                        int r = 0;
                        int g = 165;
                        int b = 255;
                        boolean isWhite = false;
                        for (int i = 0; i < escapeTime/2; i++) {
                            if(isWhite){
                                if(r > 220) r--;
                                if(g > 165) g--;
                                if(b > 1) b -= 2;
                            }else{
                                if (r < 254) r += 2;
                                if (g < 255) g++;
                                if (b < 255) b++;
                                if (r + g + b >= 762) isWhite = true;
                            }
                        canvas.set(x, y, new Color(r, g, b));
                        }
                    }
                }else{
                    canvas.set(x, y,out.getColor());
                }   
            }
        }
        canvas.display("Mandelbrot Set. Bounds: X( " + xmin + ", " + xmax + ") , Y( " + ymin + ", " + ymax + ")"); 
    }
}
