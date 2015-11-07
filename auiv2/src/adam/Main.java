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
import java.io.IOException;
import org.encog.neural.networks.BasicNetwork;
//This the Main class provides an example to train or use the already trained RNNs 
public class Main {
    public static void main(String[] args) throws IOException {
       double apps_hits_ss[]= new double[9]; 
       double [] apps_weights = new double[9] ; 
       double [] apps_dur = new double [9] ;
       Test test = new Test() ; 
   //  test.CreateTestCases(false); 
       TrainRNN T = new TrainRNN () ; 
 //    T.StartTrain(100); // appplications 
   //  T.StartTrain(3, 30); // stack of application ( each three) 
  
       BasicNetwork Neural= elman.LoadNetwork(".\\RNNS\\NNStruct_Apps.eg");// the neural network trained to sort three actions
       BasicNetwork NeuralStacked= elman.LoadNetwork(".\\RNNS\\NNStruct_Stacks.eg"); //Stacked
   
      int sumcorrectOutput = 0 ;  int sumcorrectOutputfull = 0 ;  int stacksumcorrectOutput = 0 ; int stacksumcorrectOutputfull = 0 ;
     // after training we test the two RNNs 100 times 
     for(int runs=1 ; runs<=100; runs++){
            int correctOutput = 0 ; int correctOutputfull = 0 ; int sco = 0 ;int scof=0; 
     for(int i=0 ; i<Test.TESTCASES; i++){
          test.CreateTestCases(true); 
          apps_hits_ss = test.GetAppsHits(); 
          apps_weights = test.GetAppsWeights() ; 
          apps_dur = test.GetAppsDur() ; 
    
     int r=0; 
    for(int icons=0 ; icons<3; icons++){
        int outr [] = new int [3]; 
        
          Output out = new   Output(Neural,String.valueOf(i+"-"+icons)); 
      outr =   out.getOutput(Data.CopyArrayInRange(apps_hits_ss, r, r+2),Data.CopyArrayInRange(apps_weights, r, r+2), Data.CopyArrayInRange(apps_dur, r, r+2));
   Validate val = new Validate(outr,Data.CopyArrayInRange(apps_hits_ss, r, r+2),Data.CopyArrayInRange(apps_weights, r, r+2), Data.CopyArrayInRange(apps_dur, r, r+2) ) ; 
    val.ValidateOutput(); 
   if(val.GetValidationSum()>= 1){
       correctOutput+=1 ; 
      // System.out.println(i + "\t"+val.GetValidationSum());
   }
      if(val.GetValidationSum() == 3) {
       correctOutputfull+=1 ; 
       //System.out.println(val.GetValidationSum());
   }
   //  System.out.println(i + "\t"+ val.GetValidationSum());
   r+=3 ; 
    }
     //paramters for stacked 
    double stacked_apps_hits_ss[] = new double [3] ; 
  double stacked_apps_weights[] = new double [3] ;
    double stacked_apps_dur[] = new double [3] ; 
   int rs=0 ; 
    for(int j=0 ; j<3; j++){
        stacked_apps_hits_ss[j]= Data.Sum(Data.CopyArrayInRange(apps_hits_ss, rs, rs+2))/10.0;
        stacked_apps_weights = test.GetAppsWeights() ; 
        stacked_apps_dur[j]= Data.Sum(Data.CopyArrayInRange(apps_dur, rs, rs+2));
     
        rs+=3 ; 
    }
    int outrs []  = new int[3]; 
           Output outstack = new   Output(NeuralStacked,i+"-stack");
           outrs=   outstack.getOutput(stacked_apps_hits_ss, stacked_apps_weights,stacked_apps_dur);
           Validate val = new Validate(outrs,stacked_apps_hits_ss, stacked_apps_weights,stacked_apps_dur);
           val.ValidateOutput(); 
           if(val.GetValidationSum()>= 1){
              sco+=1 ; 
      
   }
          if(val.GetValidationSum() == 3) {
             scof+=1 ; 
       
   }
     }

          System.out.println(runs +": "+ correctOutput);
            System.out.println(runs +": "+correctOutputfull);
             System.out.println(runs +": "+ sco);
            System.out.println(runs +": "+scof);
        //  System.out.println(Test.TESTCASES*3);
            sumcorrectOutput+= correctOutput; 
            sumcorrectOutputfull +=correctOutputfull; 
                stacksumcorrectOutput+= sco; 
            stacksumcorrectOutputfull +=scof;
      }
          System.out.println(sumcorrectOutput);
         // System.out.println(sumcorrectOutputfull/100.0);
              System.out.println(stacksumcorrectOutput);
        // System.out.println(stacksumcorrectOutputfull/100.0);
          
      }
}
