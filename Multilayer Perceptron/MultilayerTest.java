import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class MultilayerTest {

    
    private double[][] layer1Weights = {
        {-0.8984903595944664, 0.20235781623377802, 0.6232184290274567, -0.04028914952037477, -0.5401364460383391},
        {-0.8858025128159814, -0.5095378408412252, -0.2745364588063688, 0.15906602035530537, -0.3702217835266841},
        {0.306221605770542, 0.5112933463141145, 0.21978008323801523, -0.2145333040215731, 0.4161034737093794},
        {-0.3261529679847235, 0.7323376053508808, 0.8999694404009357, -0.8145116719606145, -0.7849776988772255},
        {-0.012736520590489242, -0.606625995532053, -0.643292567261325, 0.2327363406502876, -0.4960756837232039}
    };

    private double[][] layer2Weights = {{-0.009806527786735408, -0.5665213862058192, 0.4200493747383658, 0.7043996629799489, 0.9263322990178575},
    {-0.6174063481685184, 0.6720173943356009, -0.40638696576669364, 0.46973044214956117, -0.9125029203489401},
    {-0.6966388750321815, 0.9982108646495822, -0.33024031035204215, 0.1981343786348786, 0.5168184455879952},
    {-0.44131993230426003, -0.4222855819048521, -0.8108121621331705, -0.001177071607906921, -0.6033224781076221},
    {-0.21899167779668005, -0.91797080064805, -0.8146420220930053, -0.3686275854584169, 0.9073810980806007}};

    private double[][] layer3Weights = 
    {{-0.27751668709784494},
    {-0.6726794729615193},
    {-0.7834171275729627},
    {-0.9539862223320486},
    {0.1525691927686892}};
    private double[][] layer4Weights = {{.5}};
    private double[][][]layerWeights = {layer1Weights,  layer2Weights, layer3Weights, layer4Weights};


   
    
    

    
    public Multilayer helperMLP(){
        try {
            // Create a File object
            File file = new File("stockdata.txt");

            // Create a Scanner object
            Scanner scanner = new Scanner(file);
            Multilayer multilayer = new Multilayer(scanner, 6);
            return multilayer;
            

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return null;
    }
    @Test
    public void presetWeights() {
        Multilayer mlp = helperMLP();
        Hiddenlayer[] layers = mlp.getLayers();
        for(int r = 0; r < layers.length; r++){
            layers[r].setLayerWeights(layerWeights[r]);
        }
        for(int r = 0; r < layers.length; r++){
            double[][] expectedOutput = layerWeights[r];
            double[][] output = layers[r].getAllWeights();
            assertArrayEquals(expectedOutput, output);  
           
        }
         
        
        


        // Test forward pass of your MLP
    }

    @Test
    public void outputGradient() {
        Multilayer mlp = helperMLP();
        Hiddenlayer layer = mlp.getLayers()[0];

        double[][] weightsPlusOne = {
            {0.5, -0.2, 0.1},
            {0.3, 0.8, -0.5},
            {-0.7, 0.6, 0.2}
        };
        int[] activationDerivative = {1, 1, 1};  // All activations are positive, so derivatives are 1.
        double[] deltaNextLayer = {0.1, -0.3, 0.5};  // Example deltas from next layer.
    
        // Call the method to test
        double[] outputGradient = layer.outputGradient(weightsPlusOne, activationDerivative, deltaNextLayer);
    
        // Expected output gradient
        double[] expectedGradient = {-0.39, 0.04, 0.26};
    
        // Test if the output matches the expected values
        for (int i = 2; i < outputGradient.length; i++) {
            assert Math.abs(outputGradient[i] - expectedGradient[i]) < 1e-6 : 
                "Test failed at index " + i + ": Expected " + expectedGradient[i] + " but got " + outputGradient[i];
        }
    
        System.out.println("Test passed!");
        // Test the backpropagation
    }
    @Test
    public void testForwardPass() {
        Multilayer mlp = helperMLP();

        double[] ans =  mlp.feedForward();
        double[] output = {ans[1]};
        double[] expectedOutput = {-0.12854055188117025};
        // idk if this even the right expected
        assertArrayEquals(expectedOutput, output, .0001);  

        // Test forward pass of your MLP
    }

    @Test
    public void testBackwardPass() {
        // Test the backpropagation
    }

    @Test
    public void testWeightUpdates() {
        // Test if weights are updated after training
    }

    @Test
    public void testLayer2NegativeWeights() {
        // Test Layer 2 weights to ensure they're not excessively negative
    }
}