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
import java.util.Arrays;
import org.encog.neural.networks.BasicNetwork;
public class Final {
    //This class has one static method start 
    //The method sorts 9 icons in a view 
   private final  BasicNetwork Neural= elman.LoadNetwork(".\\RNNS\\NNStruct_Apps.eg");
   private final  BasicNetwork NeuralStacked= elman.LoadNetwork(".\\RNNS\\NNStruct_Stacks.eg");
   private double apps_hits[] ; 
   private double apps_dur[] ;
  double [] apps_weights = new double [9] ; 
     double [] apps_palpha = new double[9] ; 
        int [] output = new int[9] ; 
        public Final () {
            
        }
   public Final(double[] hits , double[] dur) {
       this.apps_hits = hits ; 
       this.apps_dur = dur ; 
   }
  
    private int[] Start() throws IOException {
          Test test = new Test() ; 
          apps_hits = test.GetAppsHits(); 
         
          apps_weights = test.GetAppsWeights();
          
          apps_dur = test.GetAppsDur() ; 
         int r=0; 
         int f_outr[] = new int [9] ;  
    for(int icons=0 ; icons<3; icons++){
        int outr [] = new int [3]; 
        
          Output out = new   Output(Neural,String.valueOf(icons)); 
      outr =   out.getOutput(Data.CopyArrayInRange(apps_hits, r, r+2),Data.CopyArrayInRange(apps_weights, r, r+2), Data.CopyArrayInRange(apps_dur, r, r+2));
      MergeArray(f_outr , outr, r, r+3); 
      r+=3;
     
    }
  double stacked_apps_hits[] = new double [3] ; 
  double stacked_apps_weights[] = new double [3] ;
    double stacked_apps_dur[] = new double [3] ; 
   int rs=0 ; 
   stacked_apps_weights = test.GetAppsWeights() ; 
    for(int j=0 ; j<3; j++){
        stacked_apps_hits[j]= Data.Sum(Data.CopyArrayInRange(apps_hits, rs, rs+2))/10.0;
        
        stacked_apps_dur[j]= Data.Sum(Data.CopyArrayInRange(apps_dur, rs, rs+2));
     
        rs+=3 ; 
    }
     int outrs []  = new int[3]; 
           Output outstack = new   Output(NeuralStacked,"stack");
           outrs=   outstack.getOutput(stacked_apps_hits, stacked_apps_weights,stacked_apps_dur);
           int rx =0; 
           for(int i=0 ; i<outrs.length;i++){
               if(outrs[i]==1){
                   for(int j=rx ; j<rx+3; j++){
                     f_outr[j] +=3 ; 
                   }
               }
                 if(outrs[i]==2){
                   for(int j=rx ; j<rx+3; j++){
                     f_outr[j] +=6 ; 
                   }
               }
               rx+=3 ;
           }
        return f_outr ;    
    }
    final  void MergeArray(int[] mainArray , int[] smallArray, int from , int to){
        int s =0 ; 
        for(int i=from; i< to ; i++){
            mainArray[i] = smallArray[s++]; 
        }
    }
    public int ValidateOutput() throws IOException 
    {
        int validationSum =0 ;
      
     output=   Start() ; 
     // sort according to apps_hits 
     SortHits() ; 
     SortDur(); 
     SortPalpha() ; 
    if(EquallySortedAppsHits())
    {
        validationSum++ ; 
        
    }   
      if(EquallySortedAppsDur())
    {
        validationSum++ ; 
        
    } 
      if(EquallySortedPalpha()) {
            validationSum =3 ; 
      }
      return validationSum ; 
       
    }
     private int []SortHits () {
        double [] apps_hits_sorted = new double[9] ; 
         double [] apps_hits_new = new double[9] ; 
        int[] sorting = new int[9]; 
         Data.CopyArray(apps_hits_sorted,apps_hits);
         Data.CopyArray(apps_hits_new,apps_hits);
  
        Arrays.sort(apps_hits_sorted);
       for(int i=0 ; i<9;i++){
         sorting[i] = Data.SearchArray(apps_hits_new, apps_hits_sorted[i]); 
         apps_hits_new [sorting[i]] =-1 ; 
       }
           for(int i=8; i>=4;i--){
             int temp = sorting[8-i];
          sorting[8-i] = sorting[i]; 
          sorting[i] = temp; 
      }
       return sorting ; 
    } 
      private int []SortDur () {
        double [] apps_dur_sorted = new double[9] ; 
         double [] apps_dur_new = new double[9] ; 
        int[] sorting = new int[9]; 
       Data.CopyArray(apps_dur_sorted,apps_dur);
         Data.CopyArray(apps_dur_new,apps_dur);
        Arrays.sort(apps_dur_sorted);
       for(int i=0 ; i<9;i++){
         sorting[i] = Data.SearchArray(apps_dur_new, apps_dur_sorted[i]); 
     
  
    
                     apps_dur_new[sorting[i]]=-1 ; 
       }
      
           for(int i=8; i>=4;i--){
             int temp = sorting[8-i];
          sorting[8-i] = sorting[i]; 
          sorting[i] = temp; 
      }
       return sorting ; 
    } 
         private int []SortPalpha () 
         {
           
        double [] apps_palpha_sorted = new double[9] ; 
         double [] apps_palpha_new = new double[9] ; 
        int[] sorting = new int[9]; 
           for(int i=0 ; i<9;i++){
               apps_palpha[i] = apps_hits[i]* apps_weights[i] ; 
           }
           
           
       Data.CopyArray(apps_palpha_sorted,apps_palpha);
       Data.CopyArray(apps_palpha_new,apps_palpha);
        Arrays.sort(apps_palpha_sorted);
       for(int i=0 ; i<9;i++){
         sorting[i] = Data.SearchArray(apps_palpha_new, apps_palpha_sorted[i]); 
     
  
    
                     apps_palpha_new[sorting[i]]=-1 ; 
       }
      
         for(int i=8; i>=4;i--){
             int temp = sorting[8-i];
          sorting[8-i] = sorting[i]; 
          sorting[i] = temp; 
      }
       return sorting ; 
    } 
        private boolean EquallySortedAppsHits() {
        boolean sorted = true ; 
        int [] hits = SortHits();
        for(int i=0; i<9; i++){
         
      
            if(output[i]!=hits[i]){
                  sorted = false ;       
            }
          
        }
        return sorted ; 
    }
          private boolean EquallySortedPalpha() {
        boolean sorted = true ; 
        
        for(int i=0; i<9; i++){
            if(output[i]!=SortPalpha()[i]){
                  sorted = false ;  
            } 
              System.out.println(output[i]+ " "+SortPalpha()[i]);
        }
        return sorted ; 
    }
          private boolean EquallySortedAppsDur() {
        boolean sorted = true ; 
        int [] dur = SortDur();
        for(int i=0; i<9; i++){
            if(output[i]!=dur[i]){
                  sorted = false ;             
            }
        }
        return sorted ; 
    }
                public static void main(String[] args) throws IOException {
                    int correct=0; int full_correct = 0; 
                    Final F = new Final() ; 
                    for(int i=0 ; i<1; i++){
                        //no testcase = 100 
                        if(F.ValidateOutput()==3)
                        {
                          full_correct++;
                        }
                        else if(F.ValidateOutput()>=1){
                            correct++ ; 
                        }
                        
                    }
                  //  F.ValidateOutput() ; 
                  //  System.out.println(correct);
                  //  System.out.println(full_correct);
                }
}

