import java.awt.*;
import java.util.concurrent.ArrayBlockingQueue;

public class PrimePrinterThread implements Runnable {

    int[] primes;
    boolean[][] primesChunks;
    ArrayBlockingQueue<Integer> done;
    int numThreads;

    public PrimePrinterThread(int[] p, boolean[][] c, ArrayBlockingQueue<Integer> d, int n){
        primes = p;
        primesChunks = c;
        done = d;
        numThreads = n;

        //Test
        System.out.println("Created PrimePrinterThread");
    }

    public void run(){
        for(int i=0; i<numThreads; i=i+1){
            while(!done.contains(i)){/*wait and do nothing*/}

            System.out.println("Found thread "+ i + " in queue");
            // Once the thread finishes the chunk, write its section in 
            // the 2D array to the 
        }
    }
}