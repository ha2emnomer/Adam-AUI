/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impulselabs.adam2;

import java.io.File;
import org.encog.engine.network.activation.ActivationBiPolar;
import org.encog.engine.network.activation.ActivationLOG;
import org.encog.engine.network.activation.ActivationLinear;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationSoftMax;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.CalculateScore;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.ml.train.strategy.Greedy;
import org.encog.ml.train.strategy.HybridStrategy;
import org.encog.ml.train.strategy.StopTrainingStrategy;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.TrainingSetScore;
import org.encog.neural.networks.training.anneal.NeuralSimulatedAnnealing;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.neural.pattern.ElmanPattern;
import org.encog.persist.EncogDirectoryPersistence;

/**
 *
 * @author HAZEM
 */
public class elman {
    static BasicNetwork main = new BasicNetwork(); 
   public static BasicNetwork createElmanNetwork(int input, int output, int hidden) {
		// construct an Elman type network
		ElmanPattern pattern = new ElmanPattern();
		pattern.setActivationFunction(new ActivationSoftMax());
		pattern.setInputNeurons(input);
		pattern.addHiddenLayer(hidden);
           
		pattern.setOutputNeurons(output);
                main = (BasicNetwork)pattern.generate();
               
              main.addLayer(new BasicLayer(0));
              main.addLayer(new BasicLayer(0)); 
              
		return main;
	}
    	public static double trainNetwork( final MLDataSet trainingSet) {
		// train the neural network
		CalculateScore score = new TrainingSetScore(trainingSet);
		final MLTrain trainAlt = new NeuralSimulatedAnnealing(
		main, score, 5000, 10, 100);

		final MLTrain trainMain = new Backpropagation(main, trainingSet,0.0, 0.0); 
              
           

		final StopTrainingStrategy stop = new StopTrainingStrategy();
                
		//trainMain.addStrategy(new Greedy());
   trainMain.addStrategy(new HybridStrategy(trainAlt));
	 trainMain.addStrategy(stop);
		int epoch = 0;
     
		while (!stop.shouldStop() ) {
                  
		trainMain.iteration();
                  
			System.out.println("Training " + ", Epoch #" + epoch
					+ " Error:" + trainMain.getError());
			epoch++;
		}
		return trainMain.getError();
	}
        public static void SaveNetwork(String Filename){
           
            EncogDirectoryPersistence.saveObject (new File (Filename) , main ) ;
        
        }
         public static void SaveNetwork(String Filename, BasicNetwork Net){
           
            EncogDirectoryPersistence.saveObject (new File (Filename) , Net ) ;
        
        }
        public static BasicNetwork LoadNetwork(String Filename) {
            BasicNetwork network = ( BasicNetwork ) EncogDirectoryPersistence.loadObject (new File (Filename) ) ;
            return network ; 
        }
}
