PARALLELPRIMES OPTIMISATIONS

For this project, I took a multithreading approach to optimise the search for 
and the printing of prime numbers. To do this, I first found primes up to the
square root of MAX, and then used SoE to find the rest of the primes. In order 
to use multithreading, I split the large array into (n-1) threads, n being the number of 
cores, and reserved 1 thread. In other words, I made n-1 ParallelFinderThreads and 
1 PrimePrinterThread. 

- ParallelFinderThread: finds primes using SoE for its assigned chunk of the large
  array of primes up to MAX
- ParallelPrinterThread: prints primes into the final array of primes in sequence,
  as the threads finish. In order to keep the list of primes sequential, the one 
  ParallelPrinterThread waits for each sequential thread to finish before printing
  its results.

Using these optimisations, the tests I performed were first using a FileWriter, because
in the beginning, I was printing correct primes only for a fraction of the values that
it was producing. 

After sorting out this issue, I deleted the FileWriter and increased the value of MAX
to test performance:

RESULTS:
MAX = 1_000_000;    Computed 78498 primes, the largest being 999983.       That took 1001ms.
MAX = 10_000_000;   Computed 664579 primes, the largest being 9999991.     That took 1000ms.
MAX = 100_000_000;  Computed 5761455 primes, the largest being 99999989.   That took 1020ms.
MAX = 200_000_000;  Computed 11078937 primes, the largest being 199999991. That took 1004ms.
MAX = 225_000_000;  Computed 12382875 primes, the largest being 224999989. That took 1009ms.
MAX = 230_000_000;  Computed 12642573 primes, the largest being 229999981. That took 1011ms.
MAX = 240_000_000;  Computed 13161544 primes, the largest being 239999987. That took 1019ms.
MAX = 245_000_000;  Computed 13420660 primes, the largest being 244999991. That took 1010ms.

After MAX = 250_000_000, I got the following error:

Exception in thread "main" java.lang.IllegalArgumentException: timeout value is negative
        at java.base/java.lang.Thread.sleep(Native Method)
        at ParallelPrimes.main(ParallelPrimes.java:85)

and at MAX = 1_000_000_000, I got the following error:

Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at ParallelPrimes.main(ParallelPrimes.java:50)

I believe that after a certain value, Java was not able to handle the sheer size of the array. Ultimately
I believe that these optimisations were successful. 

