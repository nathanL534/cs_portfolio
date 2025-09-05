import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;


public class Multilayer {
    private int numberOfLayers;
    private int numberOfPerceptrons;
    private Hiddenlayer[] layers;
    private double target;
    private boolean isTarget = false;


    public Multilayer(Scanner scanner, double target){
        this.target = target;
        this.isTarget = true;
        ArrayList<Double> data = new ArrayList<Double>();
        while(scanner.hasNext()){
            data.add(scanner.nextDouble());
        }
        
        numberOfLayers = 4;//needs to be changed based on compelxity of problem
        numberOfPerceptrons = 5; //depends on number of datapoints.
        layers = new Hiddenlayer[numberOfLayers];
        for(int i = 0; i < numberOfLayers - 2; i++){
            Hiddenlayer layer = new Hiddenlayer(numberOfPerceptrons);
            layers[i] = layer;
        }
        inputLayer(data);

        //second to last layer 
        Hiddenlayer layer = new Hiddenlayer(numberOfPerceptrons, 1);
        layers[numberOfLayers -2] = layer;
        // List<Perceptron> l = layer.getPerceptrons();
        // for(Perceptron p: l){
        //     double[] w= p.getWeights();


        // }
        


        //output layer 
        Hiddenlayer outputLayer = new Hiddenlayer(1);
        layers[numberOfLayers -1] = outputLayer;
    }
    public Multilayer(Scanner scanner){
        ArrayList<Double> data = new ArrayList<Double>();
        while(scanner.hasNext()){
            data.add(scanner.nextDouble());
            
        }
        
        numberOfLayers = 4;//needs to be changed based on compelxity of problem
        numberOfPerceptrons = 5; //depends on number of datapoints.
        layers = new Hiddenlayer[numberOfLayers];
        for(int i = 0; i < numberOfLayers - 1; i++){
            Hiddenlayer layer = new Hiddenlayer(numberOfPerceptrons);
            layers[i] = layer;
        }
        inputLayer(data);
        //second to last layer only needs one weight
        Hiddenlayer layer = new Hiddenlayer(numberOfPerceptrons, 1);
        layers[numberOfLayers -2] = layer;
        

        Hiddenlayer outputLayer = new Hiddenlayer(1);
        layers[numberOfLayers -1] = outputLayer;
        System.out.println("number of layers, expected 4, actual: " + layers.length);
        
    
    }
    
    
    //input layer

    /**
     * Callibrates input layer 
     * by setting activation function 
     * with input data
     * @param data
     */
    public void inputLayer(ArrayList<Double> data){//eventually going to have to combine with the feed forward i think or something because backprop needs to go back to here\
        //but maybe not because all this does is set activation fo rfirst lyaer because collect goes back a lauer 
        Hiddenlayer layer = layers[0];
        // CHANGED THIS FROM 1 TO ) RECENTLY TO FIX ACITVATION PRBOLEM of AL BEIG 0
        List<Perceptron> list = layer.getPerceptrons();
        for(int i =0; i < numberOfPerceptrons; i++){
            Perceptron p = list.get(i);
            // change 0 to i i think should it be fixed now
            double value = data.get(i);
            p.setActivtion(value);
        }
        layer.updateActivationOutputs();
        




    }


    public double MSE(double predictedValue){//should be right but wher is it getting the target i never intilzied this 
        // System.out.println(("targe: " + target));
        return (Math.pow(target-predictedValue, 2)*.5);
    }


    //feed forward
    /**
     * Feeds all data forward to calculate
     * the expected output 
     * @return An array where index 0 is the error 
     * and index 1 is the guess
     */
    public double[] feedForward(){
        

        for(int i = 1; i < numberOfLayers - 1; i++){
        //changes this to numberoflayers from numberoflayers -1
            Hiddenlayer layer = layers[i];
            List<Perceptron>  perceptrons = layer.getPerceptrons();
            for(Perceptron p: perceptrons){
                double weightedSum = p.collect(layers[i-1]);
                double newActivation = p.relu(weightedSum);

                p.setActivtion(newActivation);

            }
            layer.updateActivationOutputs();
            
        }

        // PLEASE NOTE OUTPUT LAYER HANDLED DIFFERENTLY BECAUSE NO ACTIVATION FUNCTION
        Hiddenlayer layer = layers[numberOfLayers- 1]; //gets output layer
        List<Perceptron>  perceptrons  = layer.getPerceptrons();
        
        //TODO FIX WEIGHTS FOR LAYER BEFORE OUTPUT LAYER
        Perceptron p = perceptrons.get(0);
        double output = p.collect(layers[numberOfLayers-2]);
        p.setActivtion(output);
        layer.updateActivationOutputs();
        double error = MSE(output); 
        double [] ans = {error, output};
        return ans;


    }
    //backpropagation 
    /** Backpropagates through all layers to
     * determine the gradients of each layer.
     * Also applies gradient descent to correct the
     * weights and bias of each layer
     * 
     * @param outputError
     */ 
    public void backPropagation(double[] outputError){
        //START HERE make each hidden layer hold its gradients instead of array maybe? or just assign them after

        Hiddenlayer layer = layers[numberOfLayers-2];


        //back propagating output lauer 
        ArrayList<double[][]> arrWeightGradients = new ArrayList<double[][]>();
        double[][] weightGradientOutputLayer = layer.weightGradients(outputError);
        arrWeightGradients.add(weightGradientOutputLayer);
        ArrayList<double[]> arrBiasGradients = new ArrayList<double[]>();
        arrBiasGradients.add(outputError); //START HERE need to make more so it is same size all of the time
        
        //Back propagating the hidden layers 
        // TODO on second iteration the last two layers of acitvatio are 0
        for(int i = numberOfLayers-2; i >= 0; i--){
            

            layer = layers[i];
            
            //setup for calcualting gradients
            double[][] weights = layer.getAllWeights();
            double[] activation = layer.getActivationOutputs();
            
            int[]activationDerivative = layer.activationDerivateHelper(activation);
            double[] delta = arrBiasGradients.get(0);

            
            double[] outputGradient = layer.outputGradient(weights, activationDerivative, delta);
            arrBiasGradients.add(0,outputGradient);

            double[][]weightGradient = layer.weightGradients(outputGradient);
            arrWeightGradients.add(0, weightGradient);
            

        }
        
        for(int i = numberOfLayers -3; i > 0; i--){
            double[][] currentWeighGradient = arrWeightGradients.remove(i);
            Hiddenlayer curLayer = this.layers[i];
            // System.out.println(i);
            for(int index = 0; index< currentWeighGradient.length; index++){

                Perceptron p = curLayer.getPerceptron(index);

                for(int wIndex = 0; wIndex < currentWeighGradient[index].length; wIndex++){
                    double weightGradient = currentWeighGradient[index][wIndex];
                    
                    p.updateWeight(weightGradient, wIndex);
                }
                double[] biasGrad = arrBiasGradients.get(i);
                p.updateBias(biasGrad[index]);


            }
        }
            
        // second to last layer different strucutre
        double[][] currentWeighGradient = arrWeightGradients.remove(0);
        Hiddenlayer curLayer = this.layers[numberOfLayers -2];
        for(int i = 0; i < curLayer.getPerceptrons().size()  ; i++){
            Perceptron p = curLayer.getPerceptron(i);
            double weightGradient = currentWeighGradient[i][0];
            p.updateWeight(weightGradient, 0);
            double[] biasGrad = arrBiasGradients.get(0);
            p.updateBias(biasGrad[i]);
        }
        
        currentWeighGradient = arrWeightGradients.remove(0);
        curLayer = this.layers[numberOfLayers -2];
        Perceptron p = curLayer.getPerceptron(0);
        p.updateWeight(currentWeighGradient[0][0], 0);
        double[] biasGrad = arrBiasGradients.get(0);
        p.updateBias(biasGrad[0]);
        
    }
    /**
     * Iterates through feedforward, backpropagation 
     * and gradient descent to calibrate the weights of
     * a multilayer perceptron
     */
    public void iterate(){
        

        // RIGHT NOW LAYER 1 HAS WEIGHTS IS THIS SUPPOSE TOE BE LIKE TAHT?????

        for(int i = 0; i < 80000; i++){
            double[] ans =  feedForward();
            if(i  == 1){
                System.out.println("here is guess" + ans[1]);
                printAllActivation();
            }
            double error = ans[0];
            if(i % 10 ==0){
                // ERROR IS NOT UPDATING WHY? ACTIVATION ONLY HAVE SECOND ITERATION IS 0
                System.out.println("here is error" + ans[0]);
            }
            double[] deltaLayer = new double[numberOfPerceptrons];
            for(int r = 0; r< numberOfPerceptrons; r++){
                deltaLayer[r] = error;
            }
            backPropagation(deltaLayer);
            if(i  == 0){
                System.out.println("here is guess" + ans[1]);
                printAllActivation();
            }
            

        }
        printWeights();
        double[] ans = feedForward();
        System.out.println("here is the final guess"  + ans[1]);
        System.out.println(ans[0]);
        // saveWeights(getMLPWeights());
        
        

    }
    public Hiddenlayer[] getLayers(){
        return layers;
    }

    public static void saveWeights(double[][][] weights) {
        String fileName = "weights.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (double[][] layerWeights : weights) {
                for (double[] neuronWeights : layerWeights) {
                    writer.write(String.join(",", 
                        Arrays.stream(neuronWeights)
                              .mapToObj(Double::toString)
                              .toArray(String[]::new)) + "\n");
                }
            }
            System.out.println("Weights saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving weights: " + e.getMessage());
        }
    }

    //PRINT METHODS
    public void printWeights(){
        System.out.println("PRINGTING ALL WEIGHTS...");
        for(int i = 0; i< layers.length; i++){
            double[][] layerWeights = layers[i].getAllWeights();
            System.out.println("LAYER WEIGHT " + (i + 1) + ":");

            for(int r = 0; r < layerWeights.length; r++){
                for(double c: layerWeights[r]){
                    System.out.print(c + ", ");
                }
                System.out.println("");

            }

        }

    }
    public void printAllActivation(){
        System.out.println("ACTIVATION LAYERS: " );
        for(int i = 0; i<layers.length; i++){
            System.out.println("Layer " + (i+ 1) + ": ");
            layers[i].printActivation();
        }
        System.out.println();

    }
    public double[][][] getMLPWeights(){
        double [][][] arr = {
            layers[0].getAllWeights(),
            layers[1].getAllWeights(),
            layers[2].getAllWeights(),
            layers[3].getAllWeights(),
            
        };
        return arr;

    }
    public void loadWeights(double [][][] allWeights){
        for(int  i = 0; i < layers.length; i++){
            layers[i].setLayerWeights(allWeights[i]);
        }
    }
    public double[][][] readWeightsFromFile(Scanner scanner) {
        double[][][] weights = new double[4][][]; // 4 layers
        ArrayList<double[]> allRows = new ArrayList<>();
        
        // Read all rows first
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                String[] parts = line.split(",");
                double[] rowWeights = new double[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    rowWeights[i] = Double.parseDouble(parts[i].trim());
                }
                allRows.add(rowWeights);
            }
        }
        
        // Now organize into layers based on your network structure
        // Layer 0: 5x5 weights (rows 0-4)
        weights[0] = new double[5][];
        for (int i = 0; i < 5; i++) {
            weights[0][i] = allRows.get(i);
        }
        
        // Layer 1: 5x5 weights (rows 5-9)  
        weights[1] = new double[5][];
        for (int i = 0; i < 5; i++) {
            weights[1][i] = allRows.get(5 + i);
        }
        
        // Layer 2: 5x1 weights (rows 10-14)
        weights[2] = new double[5][];
        for (int i = 0; i < 5; i++) {
            weights[2][i] = new double[]{allRows.get(10 + i)[0]};
        }
        
        // Layer 3: 1x1 weight (row 15)
        weights[3] = new double[1][];
        weights[3][0] = new double[]{allRows.get(15)[0]};
        
        return weights;
    }
    
    
   
    

    public static void main(String[] args) {
        // ===== CONFIGURATION =====
        String dataFile = "stockdata.txt";      // Change this to switch datasets
        String weightFile = "weights.txt";      // Weight file to load/save
        boolean trainMode = false;              // Set to true for training, false for prediction
        boolean trainFromScratch = true;       // true = fresh random weights, false = load existing weights first
        double targetValue = 16.0;              // Target value for training
        // ========================
        
        try {
            // Create a File object for data
            File file = new File(dataFile);
            Scanner scanner = new Scanner(file);
            
            Multilayer multilayer;
            
            if (trainMode) {
                // TRAINING MODE - use constructor with target
                System.out.println("=== TRAINING MODE ===");
                System.out.println("Data: " + dataFile + ", Target: " + targetValue);
                multilayer = new Multilayer(scanner, targetValue);
                
                if (!trainFromScratch) {
                    // Load existing weights before training (fine-tuning)
                    System.out.println("Loading existing weights for fine-tuning...");
                    try {
                        File weightFileObj = new File(weightFile);
                        Scanner weightScanner = new Scanner(weightFileObj);
                        double[][][] loadedWeights = multilayer.readWeightsFromFile(weightScanner);
                        multilayer.loadWeights(loadedWeights);
                        System.out.println("Existing weights loaded from " + weightFile);
                        weightScanner.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("No existing weights found, starting with random weights...");
                    }
                } else {
                    System.out.println("Training from scratch with random weights...");
                }
                
                // Train the model
                multilayer.iterate();
                
                // Save the trained weights
                Multilayer.saveWeights(multilayer.getMLPWeights());
                System.out.println("Training complete! Weights saved to " + weightFile);
                
            } else {
                // PREDICTION MODE - use constructor without target
                System.out.println("=== PREDICTION MODE ===");
                System.out.println("Data: " + dataFile);
                multilayer = new Multilayer(scanner);
                
                // Try to load weights from file first
                try {
                    File weightFileObj = new File(weightFile);
                    Scanner weightScanner = new Scanner(weightFileObj);
                    double[][][] loadedWeights = multilayer.readWeightsFromFile(weightScanner);
                    multilayer.loadWeights(loadedWeights);
                    System.out.println("Weights loaded from " + weightFile);
                    weightScanner.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Weights file not found, using hardcoded weights...");
                    // Fallback to hardcoded weights
                    double[][][] allllweights = {
                        {
                            {0.138437883781532, 0.8144865738519029, 0.40924047863411106, 0.30828497605297767, 0.0634065616031183},
                            {0.6967654674201815, 0.4276268955396788, 0.22680451341919117, 0.3655931669580692, 0.9486145108310394},
                            {0.6148012626565906, 0.33325863518894505, 0.3605665469870921, 0.15441833491944512, 0.8862218413747572},
                            {0.9905895309843024, 0.010176413681613372, 0.8054812787860328, 0.7760720994217636, 0.6264973349604661},
                            {0.8776579846034213, 0.3413376415485484, 0.7168171925421148, 0.4391262376225241, 0.9686475928804746}
                        },
                        {
                            {-0.6753647000338673, 0.13614837484232786, 0.09629249913566724, -0.2619370078723103, 0.015083815824437581},
                            {0.055387462561409924, -0.052164762129429824, 0.1604090007073745, 0.37084411320245353, -0.24869426582123325},
                            {-0.1839328253515662, 0.09946184823334958, 0.16994737441730548, -0.0517199710623829, -0.21330502243081997},
                            {-0.35526875621594106, -0.16036694854457995, 0.1981564500846107, 0.13466070953534787, -0.2408488649638045},
                            {-0.5762299794275588, 0.4160855066342154, -0.02235041436562741, -0.38533440110932704, 0.1160951512327683}
                        },
                        {
                            {0.10469375122884973},
                            {0.17908204092625096},
                            {0.6399264760539777},
                            {0.13219997064680789},
                            {0.44501296038014115}
                        },
                        {
                            {0.09688053086689696}
                        }
                    };
                    multilayer.loadWeights(allllweights);
                }
                
                // Make prediction
                double prediction = multilayer.feedForward()[1];
                System.out.println("Prediction: " + prediction);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Data file not found: " + e.getMessage());
        }
    }
}




        

