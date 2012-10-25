
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;



public final class DecoderRunner implements Decoder {

  protected synchronized String translateInput(String input) {
    return input + "t,";
  }

  public class DecoderCallable implements Callable<String> {

    private String input;

    public DecoderCallable(String input) {
      this.setInput(input);
    }

    @Override
    public synchronized String call() throws Exception {
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      return translateInput(getInput());
    }

    public synchronized String getInput() {
      return input;
    }

    public synchronized void setInput(String input) {
      this.input = input;
    }

  }

  /* (non-Javadoc)
   * @see Decoder#decode(java.lang.String)
   */
  @Override
  public synchronized String decode(String input) {
    String result = new String();

    Future<String> translFuture = pool.submit(new DecoderCallable(input));
    try {
      result = translFuture.get();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ExecutionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result;
  }

  /* (non-Javadoc)
   * @see Decoder#decodeAll(java.lang.String[], int)
   */
  @Override
  public List<String> decodeAll(String[] inputs, int threadNum) {
    String[] lInputs = inputs;
    int thN = threadNum;

    List<String> results = new LinkedList<String>();
    Queue<Future<String>> futureResults = new LinkedList<Future<String>>();

    for (String nextInput : lInputs) {
      // This submit only goes through when a thread is available, and blocks
      // otherwise.
      System.out.print(thN + "." + nextInput + "i ");;
      futureResults.add(pool.submit(new DecoderCallable(nextInput)));
    }

    for (Future<String> futureResult : futureResults) {
      try {
        results.add(futureResult.get());
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (ExecutionException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return results;

  }
}

