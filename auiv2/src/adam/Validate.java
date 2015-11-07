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

import java.util.Arrays;
public class Validate {
 private   int [] output = new int [3] ; 
 private   double [] apps_hits = new double [3]; 
 private   double [] apps_weights = new double[3]; 
 private   double [] apps_dur = new double[3]; 
 private  double validationSum = 0.0 ; 
 private final double validationWeight = 1/3.0 ; 
 private double missedaccp = 0 ; 
 private double missedacch = 0 ; 
  private double missedaccd = 0 ; 
    public Validate(int []out, double []hits, double []weights,double []dur) {
        output = out ; 
       apps_hits =hits ; 
       apps_weights = weights ; 
       apps_dur = dur ; 
    }

    public void ValidateOutput () {
     EquallySortedPalpha();
         EquallySortedAppsHits();
          EquallySortedAppsDur();
         if(missedaccp==3) {
          // validationSum += validationWeight * missedaccp;   
        validationSum =3 ; 
       // return ; 
         }
         else {
        if(missedacch==3) {
            validationSum += validationWeight * missedacch;  
      
         }
   
         if(missedaccd==3){
              validationSum += validationWeight * missedaccd;
        
        }
       
     // System.out.println(missedaccp+"\t"+missedacch+"\t"+missedaccd);
                
                
         } 
       
     
    }
    private boolean EquallySortedPalpha() {
        boolean sorted = true ; 
        
        for(int i=0; i<3; i++){
            if(output[i]!=SortPalpha()[i]){
                  sorted = false ; 
             
        
              
            }
            else {
               missedaccp++ ; 
            }
           
        }
        return sorted ; 
    }
        private boolean EquallySortedAppsHits() {
        boolean sorted = true ; 
        int [] hits = SortHits();
        for(int i=0; i<3; i++){
            if(output[i]!=hits[i]){
                  sorted = false ; 
                
                  
              
            }
            else 
            {
                   missedacch++ ; 
            }
           
             
        }
        return sorted ; 
    }
              private boolean EquallySortedAppsDur() {
        boolean sorted = true ; 
        int [] dur = SortDur();
        for(int i=0; i<3; i++){
            if(output[i]!=dur[i]){
                  sorted = false ; 
               
              
            }
            else 
            {
               missedaccd++ ;  
            }
            
        }
        return sorted ; 
    }
    private int []SortHits () {
        double [] apps_hits_sorted = new double[3] ; 
         double [] apps_hits_new = new double[3] ; 
        int[] sorting = new int[3]; 
         Data.CopyArray(apps_hits_sorted,apps_hits);
         Data.CopyArray(apps_hits_new,apps_hits);
  
        Arrays.sort(apps_hits_sorted);
       for(int i=0 ; i<3;i++){
         sorting[i] = Data.SearchArray(apps_hits_new, apps_hits_sorted[i]); 
         apps_hits_new [sorting[i]] =-1 ; 
       }
       int temp = sorting[0] ; 
       sorting[0] = sorting[2] ; 
       sorting[2] = temp; 
       return sorting ; 
    } 
      private int []SortDur () {
        double [] apps_dur_sorted = new double[3] ; 
         double [] apps_dur_new = new double[3] ; 
        int[] sorting = new int[3]; 
       Data.CopyArray(apps_dur_sorted,apps_dur);
         Data.CopyArray(apps_dur_new,apps_dur);
        Arrays.sort(apps_dur_sorted);
       for(int i=0 ; i<3;i++){
         sorting[i] = Data.SearchArray(apps_dur_new, apps_dur_sorted[i]); 
     
  
    
                     apps_dur_new[sorting[i]]=-1 ; 
       }
           int temp = sorting[0] ; 
       sorting[0] = sorting[2] ; 
       sorting[2] = temp; 
        
       return sorting ; 
    } 
      private int [] SortPalpha() {
          for(int i=0 ; i<3;i++){
              Data.calculatePalpha(i, apps_hits, apps_weights);
              
          }
     
           for(int i=0 ; i<3;i++){
           
              
          }
        return  (Data.SortPalpha()); 
      }
      public double GetValidationSum() {
          return (validationSum); 
      }
}
