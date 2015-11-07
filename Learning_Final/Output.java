/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impulselabs.adam2;

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

import javax.xml.datatype.Duration;

/**
 *
 * @author HAZEM
 */
public class Output {
   

BasicNetwork Neural  ;
    double[] HitsNo ; double[] Weights; double[] Durations;
 public Output(BasicNetwork Network,double[] HitsNo , double[] Weights, double[] Durations){
   
     Neural = Network ;
     this.HitsNo = HitsNo ;
     this.Weights = Weights ;
     this.Durations = Durations ;

 }
 private MLData CreateMLDataInput()  {

 MLData input = new BasicMLData(9);

int i=0 ; 
 for(int x=0; x<3; x++) {


     input.add(i, HitsNo[x]);
     input.add(i + 1, HitsNo[x]*Weights[x]);
     input.add(i + 2, Durations[x]/10.0);

     i += 3;

 }
return input;
 }
private  double[] compute() {
    return(Neural.compute(CreateMLDataInput()).getData());
}
   private int[] SortOutput(double[] output){
       double[] output_copy = new double [3]; //for sorting
         double[] output_copy_copy = new double [3]; //don't destroy  output[] array
       Data.CopyArray(output_copy, output); 
       Data.CopyArray(output_copy_copy, output); 
       Arrays.sort(output_copy); 
       int[] indices = new int[3];
         for (int i = 0; i < output_copy.length; ++i) {
     int index = Data.SearchArray(output_copy, output_copy_copy[i]);
        indices[i]= index ;
             
      output_copy[index] = -1 ; 
           
        }
         return indices ; 
   } 
   public int[] getOutput() {

     int [] final_out = SortOutput(compute());

       return final_out ;
   }
}
