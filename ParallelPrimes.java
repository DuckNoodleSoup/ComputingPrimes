import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Arrays;

public class ParallelPrimes {
	

    // the maximum prime number considered
    public static final int MAX = 245_000_000;
    //public static final int MAX = Integer.MAX_VALUE - 2;
    
    public static final int ROOT_MAX = (int) Math.sqrt(MAX);

    // the maximum number of primes less than MAX
    public static final int N_PRIMES = (int) (1.2 * MAX / Math.log(MAX));

    // time to run experiment
    public static final int TIMEOUT_MS = 1000;	

    private static int countPrimes (int[] primes) {
		int[] truePrimes = Primes.getPrimesUpTo(MAX);
	
		for (int i = 0; i < truePrimes.length; ++i) {
	   		if (primes[i] != truePrimes[i]) {
			return i;
	    	}
		}

		return truePrimes.length;
    }
    
    public static void main (String[] args) {

		// create a thread pool
		int nThreads = Runtime.getRuntime().availableProcessors();
		ExecutorService pool = Executors.newFixedThreadPool(nThreads);
	
		long start = System.currentTimeMillis();

		/* PARALLEL IMPLEMENTATION*/

		// 1. Compute smallPrimes[]
		boolean[] smallPrimes = getIsPrime(ROOT_MAX);

		// 2. Writing primes[]
		int[] primes = new int[MAX];
		ArrayBlockingQueue<Integer> done = new ArrayBlockingQueue<Integer>(nThreads-1);
		int chunk = MAX/(nThreads-1);
		boolean[][] primesChunks = new boolean[nThreads-1][chunk];
    

		// Create PrimeFinderThreads to find primes in parallel
		// chunk: range that each thread is responsible for
		int low = 0;
		int high = chunk-1;

		// Create and execute printer thread
		Thread w = new Thread(new PrimePrinterThread(primes, primesChunks, done, nThreads-1));
		pool.execute(w);

		// Create and execute finder threads
		for(int i = 0; i<(nThreads-1); i=i+1){
			// Create array for chunk, assign it to the array of arrays (primesChunk)
			boolean[] c = new boolean[chunk];
			Arrays.fill(c, Boolean.TRUE);
			primesChunks[i] = c;

			//Create thread and execute it
			Thread t = new Thread(new PrimeFinderThread(i, smallPrimes, c, done, low, high));
			pool.execute(t);

			// Update high and low for next thread
			low = low+chunk;
			high = high+chunk;
		}

		/* END PARALLEL IMPLEMENTATION*/
	
		// Don't modify the code below here
	
		long current = System.currentTimeMillis();

		try {
			Thread.sleep(TIMEOUT_MS - (current - start));
		} catch(InterruptedException e) {
	    
		}

		pool.shutdownNow();

		long stop = System.currentTimeMillis();

		int count = countPrimes(primes);

		if (count > 0) {
			System.out.println("Computed " + count + " primes, the largest being " + primes[count - 1] + ".");
		} else {
	    	System.out.println("Failed to produce any primes!");
		}

		System.out.println("That took " + (stop - start) + "ms.");
    }

	/* getIsPrime ported from Primes.java */
	public static boolean[] getIsPrime (int max) {
		int rootMax = (int) Math.sqrt(max);
		boolean[] isPrime = new boolean[max];
	
		// initialize isPrime[i] to true for i >= 2
		for (int i = 2; i < max; ++i) {
	    	isPrime[i] = true;
		}

		// use sieve of Eratosthenes to compute isPrime[i]
		// after calling this, isPrime[i] evaluates to true
		// if and only if i is prime
		for (int i = 0; i < max; ++i) {
	    	if (isPrime[i]) {

				// mark multiples of i as composite
				if (i <= rootMax) {
		    
		    		int j = i * i;

		    		while (true) {
			
						isPrime[j] = false;
			
						if (j >= max - i) {
			    			break;
						}
			
						j += i;
					}
				} 
	    	}
		}

		return isPrime;
    }
}
