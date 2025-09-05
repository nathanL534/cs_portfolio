import java.util.*;

public class Hiddenlayer {
    private ArrayList<Perceptron> perceptrons;
    private int numberOfPerceptrons;
    private double[] activationOutputs;
    private double[][] weights;
  
    public Hiddenlayer(int numberOfPerceptrons){
        this.numberOfPerceptrons = numberOfPerceptrons;
        perceptrons = new ArrayList<Perceptron>();
        for(int i = 0; i < numberOfPerceptrons; i++){
            Perceptron perceptron = new Perceptron(numberOfPerceptrons, i);
            perceptrons.add(perceptron);
        }
        // i need to rename or something or fix that the second to last layers each perceptorn only has one weight
        this.activationOutputs = new double[numberOfPerceptrons];
        //this should make u worried that its a square matrix
        this.weights = new double[numberOfPerceptrons][numberOfPerceptrons];

    }
    public Hiddenlayer(int numberOfPerceptrons, int numberOfWeights){
        this.numberOfPerceptrons = numberOfPerceptrons;
        perceptrons = new ArrayList<Perceptron>();
        for(int i = 0; i < numberOfPerceptrons; i++){
            Perceptron perceptron = new Perceptron(numberOfWeights, i);
            perceptrons.add(perceptron);
        }
        // i need to rename or something or fix that the second to last layers each perceptorn only has one weight
        this.activationOutputs = new double[numberOfPerceptrons];
        this.weights = new double[numberOfPerceptrons][numberOfWeights];

    }
    

    //BACK PROPAGATION
    //Calculating gradients

    /**
     * Calculates the output gradient for a lyer
     * @param weightsPlusOne
     * @param activationDerivative
     * @param deltaNextLayer
     * @return 1d Array of gradient of the output
     */
    public double[] outputGradient(double[][] weightsPlusOne, int[] activationDerivative, double[] deltaNextLayer){//theortically should work now
        double[] arr = new double[deltaNextLayer.length];
        for(int r = 0; r < weightsPlusOne.length; r++){
            double sumPerceptron = 0; 
            for (int c = 0; c < weightsPlusOne[0].length; c++){
                sumPerceptron += deltaNextLayer[c] * weightsPlusOne[r][c];
            }
            arr[r] = sumPerceptron * activationDerivative[r];

        }
        return arr;

    }

    /**
     * Calculates the graident of a layer
     * @param outputGradient
     * @return a 2d Array weight gradient for a layer
     */
    public double[][] weightGradients( double[] outputGradient){//theprtically should work after the parameters are fixed 
        //NOTE i think activationpreviouslayer should be acitvationcurrentlayer
        // this might be okay because my weights are in each hidden instead of the following layer
        double[][] weightGradients = new double[numberOfPerceptrons][numberOfPerceptrons];
        updateActivationOutputs();
        for(int n = 0; n < numberOfPerceptrons; n++){
            for(int a =0; a < numberOfPerceptrons; a++){
                // NOTES ACTIVATION OUTPUTS SHOULD BE i - 1 (one closer to input)
                weightGradients[n][a] =outputGradient[n] * activationOutputs[a];
            }
        }
        return weightGradients;
    }

    /**
     * @return 1d Array of the derivate of relu functions being 
     * 1 or 0
     */
    public int[] activationDerivateHelper(double[] activation){//should work
        int size = activation.length;
        int[] ret = new int[size];
        for(int i = 0; i < size; i++){
            if(activation[i] > 0){
                ret[i] = 1;
            }
            else{
                ret[i] = 0;
            }
        }
        return ret;

    }


    //GET METHODS

    /**
     * 
     * @return 2d Array of layer weights
     */
    public double[][] getAllWeights(){

        for(int i = 0; i < perceptrons.size(); i++){
            Perceptron perceptron = perceptrons.get(i);
            weights[i] = perceptron.getWeights();

        }

        return weights;


    }

    /**
     * Updates each perceptrons activation output 
     * for the current layer
     */
    public void updateActivationOutputs(){//do we want to change this to reutnr double
        for(int i = 0; i < perceptrons.size(); i++){
            activationOutputs[i] = perceptrons.get(i).getActivation();
        }
    }
    public double[] getActivationOutputs(){
        updateActivationOutputs();
        return activationOutputs;
    }
    
    //getters and setters
    public List<Perceptron> getPerceptrons(){
        return perceptrons;
    }
    public Perceptron getPerceptron(int index){
        return perceptrons.get(index);
    }

    public void printActivation(){
        for (int i = 0; i <perceptrons.size(); i++){
            System.out.print(perceptrons.get(i).getActivation() + ", ");
        }
    }
    public void setLayerWeights(double[][] setWeights){
        for(int r = 0; r < perceptrons.size(); r++){
            Perceptron p = perceptrons.get(r);
            p.presetWeights(setWeights[r]);

        }
    }

    
}
