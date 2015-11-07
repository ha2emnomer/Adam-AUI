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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;

public class TrainRNN {
   private int datasetSize =0 ; 
  private final  String filepath = ".\\Data\\appusage.txt"; 
  private final MLDataSet result = new BasicMLDataSet();
 static int   icons_number  =3;
  static int   icons_number_G  =3;
  private MLDataSet generateDataGrouped () throws IOException {
      FileInputStream fstream = new FileInputStream(filepath);
      BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
      String strLine;
      while ((strLine = br.readLine()) != null)   {
          MLData inputData = new BasicMLData(icons_number_G*3);
          MLData idealData = new BasicMLData(icons_number_G);
          String [] s = strLine.split("\\|");
          int index =-1 ; // the application doesn't exist on the text file 
          for(int i=0; i<9; i++){
            //search for the application
            if(Data.apps_view_G[i].equals(s[3])){
                index = i ; // add the index of the application 
                
                break ; 
            }
        }
          if(index != -1 && index <=2) {
              index =0 ; 
          } 
          else if (index >=2 && index <=5) {
              index =1 ; 
          }
          else {
              index=2 ;
          }
   // System.out.println(index);
          if(s[3].equals("SpringBoard")){
              Data.CurrentSessionTime += Integer.parseInt(s[5]) ; //if it is spring board add to seesion time 
          }
          if(index >= 0){
              Data.TotalNoHits += 1; 
              int count_1 = 0 ; int count_2 =1; int count_3=2; 
              if(Data.CurrentSessionTime >= Data.SessionTimeG) {
              //if session time is finshed reset current session time & add apps hit as input data
                  for(int i=0 ; i <icons_number_G  ; i++) {
                      inputData.add(count_1,Data.apps_hits_ss[i]/10.0 ); count_1+=3; 
                  }
                  for(int i=0 ; i <icons_number_G  ; i++) {
                  //palpha = NHS * P[i] 
                      Data.calculatePalphaG(i);
                      inputData.add(count_2,Data.apps_palpha_G[i] ); count_2+=3; 
                  }
                  for(int i=0 ; i < icons_number_G ; i++) {
                       //duration the app is used in a session  
                      inputData.add(count_3,Data.apps_dur[i]/10.0); count_3+=3; 
  }
                  int[] x =  Data.SortPalphaG(); 
                  for(int i=0 ; i <icons_number_G  ; i++) {
                      idealData.add(i,x[i]/10.0); // output is the number of icon (sorted asc.)
                  }
                  for(int i=0 ; i < icons_number_G ; i++) {
                      System.out.print(i + ": "+Data.apps_palpha_s_G[i]+"\t");
                      System.out.print(Data.apps_dur[i]/10.0+"\t");
                      System.out.print(x[i]/10.0);
                      System.out.println();
                  }
                  result.add(inputData,idealData); // combine data item 
                  Arrays.fill(Data.apps_hits_ss, 0);
                  Arrays.fill(Data.apps_dur ,0);
                  Arrays.fill(Data.apps_palpha_s_G,0);
                  Data.CurrentSessionTime =0  ; datasetSize++ ; 
                  Data.TotalNoHits=0;
                  Data.Switcharry();
              }
              else {
                  Data. apps_hits_ss[index]++ ;
                  Data.CurrentSessionTime += Integer.parseInt(s[5]) ; 
                  Data.apps_dur[index] += Integer.parseInt(s[5]);
              }
          }
      }
      br.close();
      return result;
  }
  private MLDataSet generateData () throws IOException {
      FileInputStream fstream = new FileInputStream(filepath);
      BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
      String strLine;
      while ((strLine = br.readLine()) != null)  {
          MLData inputData = new BasicMLData(icons_number*3);
          MLData idealData = new BasicMLData(icons_number);
          String [] s = strLine.split("\\|");
          int index =-1 ; // the application doesn't exist on the text file 
          for(int i=0; i<icons_number; i++){
            //search for the application
              if(Data.apps_view_1[i].equals(s[3])){
                  index = i ; // add the index of the application 
                  break ; 
              }
          }
          if(s[3].equals("SpringBoard")){
              Data.CurrentSessionTime += Integer.parseInt(s[5]) ; //if it is spring board add to seesion time 
          }
          if(index >= 0){
              Data.TotalNoHits += 1; 
              int count_1 = 0 ; int count_2 =1;  int count_3=2; 
              if(Data.CurrentSessionTime >= Data.SessionTime) {
               //if session time is finshed reset current session time & add apps hit as input data
                  for(int i=0 ; i <icons_number  ; i++) {
                      inputData.add(count_1,Data.apps_hits_ss[i]); count_1+=3; 
                  }
                  for(int i=0 ; i <icons_number  ; i++) {
                    //palpha = NHS * P[i] 
                      Data.calculatePalpha(i);
                      inputData.add(count_2,Data.apps_palpha_s[i] ); count_2+=3; 
  }
    for(int i=0 ; i < icons_number ; i++) {
      //duration the app is used in a session 
        inputData.add(count_3,Data.apps_dur[i]/10.0); count_3+=3; 
    }
    int[] x =  Data.SortPalpha(); 
      for(int i=0 ; i < icons_number  ; i++) {
          idealData.add(i,x[i]/10.0); // output is the number of icon (sorted asc.)
      }
     //System.out.println(datasetSize);
      for(int i=0 ; i < icons_number ; i++) {
          System.out.print(i + ": "+Data.apps_palpha_s[i]+"\t");
          System.out.print(Data.apps_dur[i]/10.0+"\t");
          System.out.print(x[i]/10.0);
          System.out.println();
          
         
  }
        System.out.println("-------------------------------");
                result.add(inputData,idealData); // combine data item 
                Arrays.fill(Data.apps_hits_ss, 0);
                Arrays.fill(Data.apps_dur ,0);
                Arrays.fill(Data.apps_palpha_s,0);
                Data.CurrentSessionTime =0  ; datasetSize++ ; 
                Data.TotalNoHits=0;
                Data.Switcharrya(); 
              }
              else {
                  Data. apps_hits_ss[index]++ ;
                  Data.CurrentSessionTime += Integer.parseInt(s[5]) ; 
                  Data.apps_dur[index] += Integer.parseInt(s[5]);
              }
          }
      }
      br.close();
      return result;
  }
    public BasicNetwork StartTrain (int hidden) throws IOException{
     
      elman.createElmanNetwork(icons_number*3, icons_number, hidden);
      elman.trainNetwork(generateData()) ;
       BasicNetwork FinalNetwork = (BasicNetwork) elman.main.clone();
       elman.SaveNetwork(".\\RNNS\\NNStruct_Weight_group"+icons_number+"120dash.eg");
      return FinalNetwork ;

}
  public BasicNetwork StartTrain (int icons_number ,int hidden) throws IOException{
     
      elman.createElmanNetwork(icons_number*3, icons_number, hidden);
      elman.trainNetwork(generateDataGrouped()) ;
       BasicNetwork FinalNetwork = (BasicNetwork) elman.main.clone();
       elman.SaveNetwork(".\\RNNS\\NNStruct_Weight_stack"+icons_number+"neuralannelv3.eg");
      return FinalNetwork ;

}
}

