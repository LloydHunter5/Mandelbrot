import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Canvas {

    // A class that represents a 2D drawing surface.
    // Surface is diviced into 'height' rows of pixels
    // and 'width' columns of pixels; each pixels is
    // is a 3-byte RGB (red, green, blue) color.

    // Real world coordinates ranging from xmin to xmax
    // on the x-axis and from ymin to ymax on the y-axis
    // are linearly mapped onto the array of pixels.

    // The origin (row 0, column 0) is the lower left
    // hand cornder of the canvas.  The canvas is in
    // quadrant I.

    // Any attempt to draw off the canvas is 'clipped'
    // (i.e., ignored) and WHITE is returned for any
    // attempt to inquire about a pixel off the canvas.

    private Color[][] pixels;
    private int height;
    private int width;
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;

    public Canvas(int height, int width) {
        this.height = height;
        this.width = width;
        this.xMin = 0.0;
        this.xMax = width;
        this.yMin = 0.0;
        this.yMax = height;

        // Initialize the canvas to be all white.

        this.pixels = new Color[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                this.pixels[row][col] = Color.WHITE;
            }
        }
    }

    // Selector/setter methods.

    public int height() {
        return this.height;
    }

    public int width() {
        return this.width;
    }

    public boolean isOnCanvas(int row, int col) {
        return row >= 0 && row < this.height && col >= 0 && col < this.width;
    }

    public Color get(int row, int col) {
        if (isOnCanvas(row, col)) {
            return this.pixels[row][col];
        } else {
            return Color.WHITE;
        }
    }

    public boolean isSet(int row, int col) {
        if (isOnCanvas(row, col)) {
            return !this.pixels[row][col].equals(Color.WHITE);
        } else {
            return false;
        }
    }

    public void set(int row, int col, Color color) {
        if (isOnCanvas(row, col)) {
            this.pixels[row][col] = color;
        }
    }

    public void set(int row, int col) {
        set(row, col, Color.BLACK);
    }

    public void setXRange(double min, double max) {
        this.xMin = min;
        this.xMax = max;
    }

    public void setYRange(double min, double max) {
        this.yMin = min;
        this.yMax = max;
    }

    public int row(double y) {
        double result = height * (y - yMin) / (yMax - yMin);
        return (int) (result + 0.5);
    }

    public int column(double x) {
        double result = width * (x - xMin) / (xMax - xMin);
        return (int) (result + 0.5);
    }

    public Color get(double x, double y) {
        return get(row(y), column(x));
    }

    public void set(double x, double y, Color color) {
        set(row(y), column(x), color);
    }

    public void set(double x, double y) {
        set(row(y), column(x));
    }

    public void print() {

        // Displays the canvas on an output print stream.
        // Any non-white pixel is displayed as an asterisk.

        // Notice that the row zero (at the bottom of the 
        // canvas needs to be printed last).

        for (int i = 0; i < this.height; i++) {
            int row = this.height - i - 1;
            for (int col = 0; col < this.width; col++) {
                char c = isSet(row, col) ? '*' : ' ';
                System.out.print(c);
            }
            System.out.println();
        }
    }

    private void draw(Graphics g) {
        for (int i = 0; i < this.height; i++) {
            int row = this.height - i - 1;
            for (int col = 0; col < this.width; col++) {
                g.setColor(get(row, col));
                g.fillRect(col, row, 1, 1);
            }
        }
    }

    public void display(String title) {

        // Creates a GUI graphics window and displays the canvas.

        Canvas canvas = Canvas.this;
        JFrame frame = new JFrame(title);
        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                canvas.draw(g);
            }
        };

        frame.setSize(canvas.width(), canvas.height());
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
