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
import java.util.Arrays;
import java.util.Random;
public class Data {
    static  String [] apps_view_1 = new String [] {"com.apple.mobilephone","com.facebook.Facebook","com.yelp.yelpiphone"}; 
    static  String [] apps_view_G = new String [] {"com.weather.TWC","com.apple.mobilenotes","com.groupon.grouponapp","com.apple.mobiletimer"
          ,"com.apple.mobilecal","com.yelp.yelpiphone","com.apple.AppStore","com.apple.Maps","com.apple.mobilephone"};  
   static double [] apps_hits = new double[] {0,0,0,0,0,0,0,0,0} ; 
    static double TotalNoHits  = 0 ; 
    static double [] apps_palpha = new double[] {0.1,0.1,0.1,0.1,0.3,0.8,0.5,0.2,0.9} ;
     static double [] apps_palpha_G= new double[] {0.7,0.1,0.2} ;
     static double [] apps_palpha_s_G= new double[] {0,0,0} ;
    static double [] apps_palpha_s = new double[] {0,0,0,0,0,0,0,0,0} ; 
    static double [] apps_hits_ss = new double [] {0,0,0,0,0,0,0,0,0};
     static double [] apps_dur = new double [] {0,0,0,0,0,0,0,0,0};
    static  int SessionTime =  3600; 
        static  int SessionTimeG = 1800; 
    static int CurrentSessionTime = 0 ; 
    static int flag=1 ; 
    public static void randapps_palpha() {
    for(int i=0; i< 3; i++){
        DecimalFormat df = new DecimalFormat("#0.00"); 
        Random R = new Random() ; 
        Double weight = R.nextDouble();
        while (weight ==0|| weight==1) {
         weight = R.nextDouble();
        }
        apps_palpha[i] = Double.parseDouble(df.format(weight));
    }
    
    }
    public static void randapps_palphaG() {
    for(int i=0; i< 3; i++){
        DecimalFormat df = new DecimalFormat("#0.00"); 
        Random R = new Random() ; 
        Double weight = R.nextDouble();
        while (weight ==0 || weight==1) {
         weight = R.nextDouble();
        }
        apps_palpha_G[i] = Double.parseDouble(df.format(weight));
    }  
}
    public static void Switcharrya() {
        if(flag==0){
            String[] new_apps_view = new String[]{"com.facebook.Facebook","com.yelp.yelpiphone","com.apple.MobileSMS" };
          apps_view_1= new_apps_view ; 
         
          flag++ ; 
        }
        else if (flag==1){
               String[] new_apps_view = new String[]{"com.apple.mobiletimer"
          ,"com.apple.mobilecal","com.yelp.yelpiphone"};
          apps_view_1= new_apps_view ; 
          flag++ ; 
        }
        else if (flag==2)
        {
                String[] new_apps_view = new String[]{ "com.apple.Maps","com.yelp.yelpiphone","com.apple.MobileSMS"};
          apps_view_1= new_apps_view ; 
          flag++ ; 
        }
        else if(flag==3){
                 String[] new_apps_view = new String[]{ "com.apple.AppStore","com.apple.Maps","com.apple.mobilephone"};
          apps_view_1= new_apps_view ; 
          flag++ ; 
        }
         else if(flag==4){
                 String[] new_apps_view = new String[]{"com.weather.TWC","com.apple.mobilenotes","com.apple.MobileSMS"};
          apps_view_1= new_apps_view ; 
          flag++ ; 
        }
          else if(flag==5){
                 String[] new_apps_view = new String[]{"com.weather.TWC","com.apple.MobileSMS","com.apple.mobiletimer"};
          apps_view_1= new_apps_view ; 
          flag=0 ; 
        }
       randapps_palpha(); 
        }
    
  public static void Switcharry () {
 
      if(flag==0){
          String[] new_apps_view_G = new String[]{ "com.weather.TWC","com.apple.mobilenotes","com.groupon.grouponapp","com.apple.mobiletimer"
          ,"com.apple.mobilecal","com.yelp.yelpiphone","com.apple.AppStore","com.apple.Maps","com.apple.mobilephone"};
          apps_view_G = new_apps_view_G ; 
          flag++ ; 
      }
      else if (flag==1){
                 String[] new_apps_view_G = new String[]{"com.apple.AppStore","com.apple.Maps","com.apple.mobilephone","com.apple.mobiletimer"
          ,"com.apple.mobilecal","com.yelp.yelpiphone","com.weather.TWC","com.apple.mobilenotes","com.groupon.grouponapp"};
          apps_view_G = new_apps_view_G ; 
          flag++ ; 
      }
      else if(flag==2)
      {
                       String[] new_apps_view_G = new String[]{"com.apple.AppStore","com.apple.Maps","com.apple.mobilephone","com.weather.TWC","com.apple.mobilenotes","com.groupon.grouponapp","com.apple.mobiletimer"
          ,"com.apple.mobilecal","com.yelp.yelpiphone"};
          apps_view_G = new_apps_view_G ; 
          flag++;
      }
      else if(flag==3)
      {
                         String[] new_apps_view_G = new String[]{"com.apple.mobiletimer","com.apple.mobilecal","com.yelp.yelpiphone","com.weather.TWC","com.apple.mobilenotes","com.groupon.grouponapp","com.apple.AppStore","com.apple.Maps","com.apple.mobilephone"};
          apps_view_G = new_apps_view_G ; 
          flag++;
      }
      else if( flag==4)
      {
             String[] new_apps_view_G = new String[]{"com.apple.mobiletimer","com.apple.mobilecal","com.yelp.yelpiphone","com.apple.AppStore","com.apple.Maps","com.apple.mobilephone","com.weather.TWC","com.apple.mobilenotes","com.groupon.grouponapp"};
          apps_view_G = new_apps_view_G ; 
          flag++ ; 
      }
        else if( flag==5)
      {
             String[] new_apps_view_G = new String[]{"com.weather.TWC","com.apple.mobilenotes","com.groupon.grouponapp","com.apple.AppStore","com.apple.Maps","com.apple.mobilephone","com.apple.mobiletimer"
          ,"com.apple.mobilecal","com.yelp.yelpiphone"};
          apps_view_G = new_apps_view_G ; 
          flag=0 ; 
      }
  randapps_palphaG();
  }
    public static double Sum(double [] array) {
        double Sum =0.0 ;
        for(int i=0; i<array.length; i++){
           Sum += array[i]; 
        }
        return Sum;
    }
    public static int[] SortPalpha () {
   double [] newPalpha  = new double [3] ; 
   double [] newPalphax = new double [3]; 
      
   int x [] = new int[3] ; 
  Data.CopyArray(newPalpha, apps_palpha_s);
   Data.CopyArray(newPalphax, apps_palpha_s);
  Arrays.sort(newPalpha);
 
   
     
    for (int i = 0; i < newPalpha.length; ++i) {
     int index = Data.SearchArray(newPalphax, newPalpha[i]);
     x[i]= index ; 
     newPalphax[index] = -1 ; 
      
        
        
    }
   int temp =  x[0]; 
    x[0] = x[2]; 
    x[2]= temp;

       return x;  
    }
        public static int[] SortPalphaG () {
   double [] newPalpha  = new double [3] ; 
   double [] newPalphax = new double [3]; 
      
   int x [] = new int[3] ; 
  Data.CopyArray(newPalpha, apps_palpha_s_G);
   Data.CopyArray(newPalphax, apps_palpha_s_G);
  Arrays.sort(newPalpha);
 
   
     
    for (int i = 0; i < newPalpha.length; ++i) {
     int index = Data.SearchArray(newPalphax, newPalpha[i]);
     x[i]= index ; 
     newPalphax[index] = -1 ; 
      
        
        
    }
 int temp =  x[0]; 
    x[0] = x[2]; 
    x[2]= temp;
       return x;  
    }
    public static void calculatePalpha(int i ) {
       apps_palpha_s[i]= apps_palpha[i]*(apps_hits_ss[i]/10.0); 
     
    }
    public static void calculatePalphaG(int i ) {
       apps_palpha_s_G[i]= apps_palpha_G[i]*apps_hits_ss[i]; 
     
    }
     public static void calculatePalpha(int i , double[] appspalpha , double[] appshitss) {
      
     apps_palpha_s[i]= appspalpha[i]*(appshitss[i]/10.0); 
    }
  
      public static int SearchArray(double []a , double key){
         for(int i=0 ; i< a.length; i++){
             if(a[i] == key){ return i ;} 
         }
        return -1;
     }
     public static void CopyArray(double []a , double [] b){
         for(int i=0 ; i< a.length; i++){
             a[i]=b[i];
         }
     }
     public static double[] CopyArrayInRange(double []a ,int from , int to){
         double[] newArray = new double[to-from+1]; 
         int r=0; 
        for(int i=from; i<=to;i++){
            newArray[r]= a[i];
            r++ ;
        }
         return newArray ; 
     }
    public static int[] CopyArrayInRange(int []a ,int from , int to){
         int[] newArray = new int[to-from+1]; 
         int r=0; 
        for(int i=from; i<=to;i++){
            newArray[r]= a[i];
            r++ ;
        }
         return newArray ; 
     }
}
