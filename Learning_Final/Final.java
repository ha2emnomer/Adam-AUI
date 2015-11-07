package impulselabs.adam2;

/**
 * Created by HAZEM on 25/06/2015.
 */
import org.encog.neural.networks.BasicNetwork;
// the method  Start sorts 9 apps it returns int array with numbers in range of 0-8
public class Final {
    private final  BasicNetwork Neural= elman.LoadNetwork(".\\NNStruct_Weight_group3100.eg"); // 3yzeen n3rf el files deeh n7otha feen
    private final  BasicNetwork NeuralStacked= elman.LoadNetwork(".\\NNStruct_Weight_stack3neuralannelv2.eg"); 
    private double apps_hits[] ;
    private double apps_dur[] ;
    private double [] apps_weights = new double [9] ;
    public Final () {
 // do nothing
    }
    public Final(double[] hits , double[] dur) {
        this.apps_hits = hits ;
        this.apps_dur = dur ;
    }
    public int[] Start()  {
        Test test = new Test() ;
        apps_weights = test.GetAppsWeights();


        int r=0;
        int f_outr[] = new int [9] ;
        for(int icons=0 ; icons<3; icons++){
            int outr [] = new int [3];

            Output out = new   Output(Neural,Data.CopyArrayInRange(apps_hits, r, r+2),Data.CopyArrayInRange(apps_weights, r, r+2), Data.CopyArrayInRange(apps_dur, r, r+2));
            outr =   out.getOutput();
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
        Output outstack = new   Output(NeuralStacked,stacked_apps_hits, stacked_apps_weights,stacked_apps_dur);
        outrs=   outstack.getOutput();
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
}
