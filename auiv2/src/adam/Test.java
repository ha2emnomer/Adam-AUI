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
import java.text.DecimalFormat;
import java.util.Random;
//Test generates Random test cases
public class Test {
    final int MAX_NO_HITS_CEIL = 25 ; 
     final int MAX_NO_HITS_FLOOR = 12 ; 
     final static int TESTCASES = 10; 
    final int [] APPS_DUR_LIMTS = new  int []{15,25,40,120,190,200,300 } ; 
     double apps_hits_ss[]= new double[9]; 
       double [] apps_weights = new double[9] ; 
       double [] apps_dur = new double [9] ; 
  public void CreateTestCases (boolean Floor) {
      //Create possible combintation of TestCases 
     //Initialize apps_weights randomly 
     RandomAppsWeights() ; 
     SetAppsHits(Floor) ; 
     SetAppsDur () ; 
     
    }
private int randInt(int min, int max) {
Random rand = new Random();
int randomNum = rand.nextInt((max - min) + 1) + min;
return randomNum;
}
public double[] GetAppsHits() {
    return apps_hits_ss;
}
public double[] GetAppsWeights() {
    return apps_weights;
}
public double[] GetAppsDur() {
    return apps_dur;
}
private void SetAppsDur () {
     for(int i=0; i< 9; i++){
         if(apps_hits_ss[i] ==0){
             apps_dur[i]=0.0 ; 
         }
         else {
         if(apps_hits_ss[i] <=3  ){
             apps_dur[i]= randInt(3,APPS_DUR_LIMTS[0]) ; 
         }
         else if (apps_hits_ss[i] >= 3 && apps_hits_ss[i] <=5 ){
               apps_dur[i]= randInt(APPS_DUR_LIMTS[0],APPS_DUR_LIMTS[1]) ; 
         }
           else if (apps_hits_ss[i] >= 5 && apps_hits_ss[i] <=8 ){
               apps_dur[i]= randInt(APPS_DUR_LIMTS[0],APPS_DUR_LIMTS[2]) ; 
         }
           else if (apps_hits_ss[i] >= 8 && apps_hits_ss[i] <=11 ){
               apps_dur[i]= randInt(APPS_DUR_LIMTS[0],APPS_DUR_LIMTS[3]) ; 
         }
          else if (apps_hits_ss[i] >= 11 && apps_hits_ss[i] <=13 ){
               apps_dur[i]= randInt(APPS_DUR_LIMTS[1],APPS_DUR_LIMTS[4]) ; 
         }
          else {
               apps_dur[i]= randInt(APPS_DUR_LIMTS[1],APPS_DUR_LIMTS[5]) ;
          }
     }
     }
}
private void SetAppsHits (boolean Floor) {
    if(Floor){
      for(int i=0; i< 9; i++){
         apps_hits_ss[i]=randInt(1,MAX_NO_HITS_FLOOR); 
     }   
    }
    else {
     for(int i=0; i< 9; i++){
         apps_hits_ss[i]=randInt(1,MAX_NO_HITS_CEIL); 
     }
    }
}
        private void RandomAppsWeights() {
          for(int i=0; i< 9; i++){
        DecimalFormat df = new DecimalFormat("#0.00"); 
        Random R = new Random() ; 
        
        Double weight = R.nextDouble();
        while (weight ==0 || weight==1) {
         weight = R.nextDouble();
        }
        apps_weights[i] = Double.parseDouble(df.format(weight));
        }
          
       
        }
}
