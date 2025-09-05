import java.util.*;
import javax.swing.*;

import java.awt.*;



public class Perceptron {
    private double[] weights; //theoritically this should match up with the amount of input per perceptron

    private double output;
    private double activation;
    
    private final int INDEX;
    private double bias;
    private final int NUMBEROFPERCEPTRONS;
    private double value;
    private final double LEARNINGRATE = 0.00001111111111111119;
    public Perceptron(int numberOfPerceptrons, int index){
        this.NUMBEROFPERCEPTRONS = numberOfPerceptrons;
        this.INDEX = index;
        weights = new double[numberOfPerceptrons];
        for(int i = 0; i < numberOfPerceptrons; i++){
            weights[i] = Math.random() ;

        } 
        bias = 1.0;

    }
    public Perceptron(int numberOfWeights, int INDEX, String test){
        weights = new double[numberOfWeights];
    
        this.INDEX = INDEX;
        this.NUMBEROFPERCEPTRONS = numberOfWeights;
        for(int i = 0; i < numberOfWeights; i++){
            weights[i] = Math.random() ;

        } 
        bias = 1.0;

    }
    

    //FEED FORWARD PARTS
    public double collect(Hiddenlayer prevlayer){//collects weights and previous layers activation value
        double sum = 0;
        for( Perceptron perceptron: prevlayer.getPerceptrons()){
            double weight = perceptron.getWeight(INDEX);
            double prevActivation = perceptron.getActivation();
            sum+= weight * prevActivation;
        }

        return sum + bias;
    }
    public double relu(double weightedSum){//applies activation function
        if(weightedSum > 0)
            return weightedSum;
        else 
            return 0;
    }
    // BACKPROPAGATION PART
    

    //update
    public void updateWeight(double weightGradient, int index){
         weights[index] = weights[index] - LEARNINGRATE*weightGradient;

    }
    public void updateBias(double biasGradient){
        this.bias = bias - LEARNINGRATE*biasGradient;

    }
    public void setActivtion(double value){
        this.activation = value;

    }
    public void presetWeights(double[] weights){
        this.weights = weights;
    }
    //getter methods
    
    public double getWeight(int index){

        return weights[index];

    }
    public double[] getWeights(){
        return this.weights;
    }
    public int weightSize(){
        return this.weights.length;
    }
    public double getActivation(){
        return activation;
    }
    public double getBias(){
        return bias;
    }

}
