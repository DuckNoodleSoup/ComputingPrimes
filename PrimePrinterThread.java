import java.awt.*;
import java.util.concurrent.ArrayBlockingQueue;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.StringBuilder;
import java.io.BufferedWriter;


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
        //TEST
        //StringBuilder builder = new StringBuilder();

        int count = 0;
        int current = 0;
        for(int i=0; i<numThreads; i=i+1){
            while(!done.contains(i)){/*wait and do nothing*/}

            System.out.println("Found thread "+ i + " in queue");
            // Once the thread finishes the chunk, write its section in 
            // the 2D array to the main array
            int chunkLength = primesChunks[0].length;
            for (int j = 0; j < chunkLength; ++j) {
                if (primesChunks[i][j]) {
                    //TEST
                    //builder.append("Thread "+i+" found "+current+" \n");
                    primes[count] = current;
                    count = count+1;
                }
                current = current+1;
            }
        }

        //TEST
        /*
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("TestResults.txt"))) {
            writer.write(builder.toString());
        } catch (IOException e){
            System.err.format("IOException: %s%n", e);
        }*/
    } 
}