/*
 * The MIT License
 *
 * Copyright 2015 Impulse Labs.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package adam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


import java.util.Arrays;
import org.encog.ml.data.MLData;

import org.encog.ml.data.basic.BasicMLData;

import org.encog.neural.networks.BasicNetwork;

/**
 *
 * @author HAZEM
 */
public class Output {
   
 File directory = new File(".");
String input_path =".\\input\\"; 
String output_path =".\\output\\"; 
BasicNetwork Neural  ; 

 public Output(BasicNetwork Network, String filename){
   
     Neural = Network ; 
     input_path= input_path+filename + ".in" ; 
     output_path= output_path+filename + ".out" ;
 }
 private MLData CreateMLDataInput() throws FileNotFoundException, IOException {
       FileInputStream fstream = new FileInputStream(input_path);
 MLData input = new BasicMLData(TrainRNN.icons_number*3);
BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
String strLine;
int i=0 ; 
while ((strLine = br.readLine()) != null)   {
     String [] s = strLine.split("\\|");

   
    input.add(i, Double.parseDouble(s[0]));
    input.add(i+1,Double.parseDouble(s[1])); 
     input.add(i+2,Double.parseDouble(s[2])); 
     
    i+=3 ; 

}
return input;
 }
private  double[] compute() throws IOException{
    return(Neural.compute(CreateMLDataInput()).getData());
}
   private int[] SortOutput(double[] output){
       double[] output_copy = new double [TrainRNN.icons_number]; //for sorting 
         double[] output_copy_copy = new double [TrainRNN.icons_number]; //don't destroy  output[] array
       Data.CopyArray(output_copy, output); 
       Data.CopyArray(output_copy_copy, output); 
       Arrays.sort(output_copy); 
       int[] indices = new int[TrainRNN.icons_number];
         for (int i = 0; i < output_copy.length; ++i) {
     int index = Data.SearchArray(output_copy, output_copy_copy[i]);
        indices[i]= index ;
             
      output_copy[index] = -1 ; 
           
        }
         return indices ; 
   } 
   public int[] getOutput(double[] HitsNo , double[] Weights, double[] Durations) throws IOException {
       Input In = new Input(input_path); 
       In.InputCompute(HitsNo, Weights, Durations);
     int [] final_out = SortOutput(compute());
    BufferedWriter output = null;
        try {
            File file = new File(output_path);
            output = new BufferedWriter(new FileWriter(file));
            for(int i=0; i<   TrainRNN.icons_number; i++){
                 output.write(String.valueOf(final_out[i]));
                 output.newLine();
            }
           
        } catch ( IOException e ) {
          //  e.printStackTrace();
        } finally {
            if ( output != null ) output.close();
        }
        return final_out ; 
   }
}
