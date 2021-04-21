import java.awt.*;
import java.util.concurrent.ArrayBlockingQueue;

public class PrimeFinderThread implements Runnable {

    int id;
    boolean[] smallPrimes;
    boolean[] primesChunk;
    ArrayBlockingQueue<Integer> done;
    int rangeLow;
    int rangeHigh;



    public PrimeFinderThread(int i, boolean[] s, boolean[] p, ArrayBlockingQueue<Integer> d, int l, int h){
        id = i;
        smallPrimes = s;
        primesChunk = p;
        done = d;
        rangeLow = l;
        rangeHigh = h;

        //Test
        System.out.println("Created Thread "+id+" with low "+ l + ", high "+ h);
    }
    
    public void run(){  
        // Use SoE to mark multiples of smallPrimes as false (Composite)
        for(int i=0; i<smallPrimes.length; i=i+1){
            
            if(smallPrimes[i]){
                // Mark rangeLow to rangeHigh multiples of i
                
                // First, find first multiple of i from rangeLow
                boolean found = false;
                int j = rangeLow;
                int counter = 0;

                while(!found){
                    if(j%i==0){
                        found = true;
                    }else{
                        j=j+1;
                        counter = counter+1;
                    }
                }

                // Mark all multiples of i until rangeMax as false
                while (true) {
                    if ((counter) >= (primesChunk.length)) {
                        break;
                    }
                    primesChunk[counter] = false;
                    counter += i;
                } 
            }
        }  

        // If its Thread 0, need to copy smallPrimes to the chunk
        if(id==0){
            for(int i=0; i<smallPrimes.length; i=i+1){
                primesChunk[i] = smallPrimes[i];
            }
        }

        // After all is done, set done[id] as true, so that 
        // PrimePrinterThread can get to work
        done.add(id);
        
        //Test  
        System.out.println("Thread "+ id +" finished");
    }
}