

public class Function {
    private double slope;
    private int y;
    private double yIntercept;
    
    public Function(){
        yIntercept = 0;

    }
    public Function(double slope, int y, double yIntercept){
        this.slope = slope;
        this.y = y;
        this.yIntercept = yIntercept;
    }
    public double getSlope(){
        return this.slope;
    }
    public int getY(){
        return this.y;
    }
    public double getYIntercept(){
        return this.yIntercept;
    }
    public void setSlope(double slope){
        this.slope = slope;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setYIntercept(double yIntercept){
        this.yIntercept = yIntercept;
    }

    public double getYPoint(int x){
        return ( x*slope + yIntercept);
    }
    public double getXPoint(int YPoint){
        return ((YPoint *this.y)-yIntercept)/slope;
    }
}
