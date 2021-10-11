import java.awt.Color;

// Evan Strohman
// 9/17/21
// Algorithms H
// Homework #4

public enum Hue {
    BLACK(0,0,0),
    BLUE(0,0,255),
    LIGHT_BLUE(0,165,255),
    RED(255,0,0),
    ORANGE(255,165,0),
    GREEN(0,255,0),
    WHITE(255,255,255);
    
    private final Color color;
    private Hue(int r, int g, int b){
        this.color = new Color(r,g,b);
    }
    
    //is there a different/better way to do this?
    public static Hue parseHue(String name) throws IllegalArgumentException{
        for(Hue hue : Hue.values()){
            if(hue.toString().equals(name.toUpperCase())) return hue;
        }
        throw new IllegalArgumentException();
    }
    
    public Color getColor(){
        return this.color;
    };
}
