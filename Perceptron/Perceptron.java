import java.util.*;
import javax.swing.*;

import java.awt.*;



public class Perceptron {
    private ArrayList<Point> inputBlue;
    private ArrayList<Point> inputRed;
    private ArrayList<Integer> outputRed;
    private ArrayList<Integer> outputBlue;
    private ArrayList<Integer>  outputs;
    private ArrayList<Point> input;
    // private int output;
    private double[] weights;
    private final double n = 10; // this represents its learning curve
    // weight 1 is weight for x, weight 2 is weight for y, weight 3 is for the constant
    private double error;
    private double target;
    private Function func;


    public Perceptron(){
        weights = new double[3];
        for(int i = 0; i < 3; i++){
            weights[i] = Math.random();
            
        }
        outputs = new ArrayList<Integer>();
        func = new Function();
        
    }
    public void input(ArrayList<Point> blue, ArrayList<Point> red){
        inputBlue = (ArrayList<Point>) blue.clone(); 
        inputRed = (ArrayList<Point>)red.clone();
        input = (ArrayList<Point>) inputBlue.clone();
        for(Point point: red){
            input.add(point);
        }
    }
    public void printPoints(){//just to test
        for(Point point: input){
            System.out.println("x: " + point.getX());
            System.out.println("y: " + point.getY());
        }
    }

    public void calculate(){//x1 is x cord x2 is y x0 is 1
        outputs.clear();//1 is red and 0 is blue
        int sum = 0;
        double m = 0;// slope
        int y = 0;
        double yIntercept = func.getYIntercept();
        for(Point point: input){
            sum+= weights[0] *1;
            sum+= weights[1] * point.getX();
            sum+= weights[2] * point.getY();
            

            if( sum >= 0){ //step function
                outputs.add(1);
            }
            else
                outputs.add(0);
            
            sum = 0;
            
        }
        
        m = -weights[1]/weights[2];
        yIntercept = -weights[0]/ weights[2];
        func.setSlope(m);                  
        func.setYIntercept(yIntercept);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             this.func = func;

    }
    public Function getFunction(){
        return this.func;
    }
    public double updateWeights(double weight, double input, int d){ //d is either 1 or -1 depedning on which classification it should be
        double newWeight = weight + n * d* input;
        return newWeight;
    }
    public void fixWeights(int output, Point point, int target){
        

        int error =  target - output;

        if(error != 0){
            weights[0] =updateWeights(weights[0], 10000, error);
            weights[1] = updateWeights(weights[1], point.getX(), error);
            weights[2] = updateWeights(weights[2], point.getY(), error);
        }
        

    }
    public void check(){//this checks and then corrects the weight
        //all of the blues come first
        //blue is 0 
        //red is 1
        int pointer = 0;
        int mistakes = 0;
        while(pointer < inputBlue.size()){
            int output = outputs.get(pointer);
            Point point = inputBlue.get(pointer);
            
            fixWeights(output, point, 0);
            pointer++;
        }
        int redPointer =0;
        while(redPointer < inputRed.size() ){
            int output = outputs.get(pointer);
            Point point = inputRed.get(redPointer);
            fixWeights(output, point, 1);
            pointer++;
            redPointer++;
        }
        calculate();
    }
    public double[] getWeights(){
        return weights;
    }

}
