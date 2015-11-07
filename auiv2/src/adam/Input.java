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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Input {
    String input_path ; 
    public Input(String InputPath) {
    input_path = InputPath; 
    }
    
    
    public void InputCompute (double[] HitsNo , double[] Weights, double[] Durations) throws IOException {
        double SumHits = Data.Sum(HitsNo); 
        BufferedWriter output = null;
        try {
            File file = new File(input_path);
            output = new BufferedWriter(new FileWriter(file));
        for(int i=0; i<TrainRNN.icons_number;i++){
            Data.calculatePalpha(i, Weights,HitsNo);
                      output.write(HitsNo[i]+"|"+Data.apps_palpha_s[i]+"|"+Durations[i]/10.0);
                      output.newLine();
                    
        }
        
        } catch ( IOException e ) {
          //  e.printStackTrace();
        } finally {
            if ( output != null ) output.close();
        }
    }
  
}
