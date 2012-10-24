import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public interface Decoder {

  static final int NUM_PROCS = 8;
  static final int numThreads = NUM_PROCS;
  /**
   * queueCapacity should be at least numThreads, so all the available threads
   * can be filled at once.
   */
  static final int queueCapacity = numThreads;


  final static BlockingQueue<Runnable> blockingQueue =
      new ArrayBlockingQueue<Runnable>(queueCapacity);
  final static RejectedExecutionHandler rejectedExecutionHandler =
      new ThreadPoolExecutor.CallerRunsPolicy();
  final static ExecutorService pool = new ThreadPoolExecutor(
    numThreads, numThreads, 0L, TimeUnit.MILLISECONDS, blockingQueue,
    rejectedExecutionHandler);

  public abstract String decode(String input);

  public abstract List<String> decodeAll(String[] inputs, int threadNum);

}
