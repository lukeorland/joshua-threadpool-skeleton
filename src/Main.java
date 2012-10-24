import java.util.concurrent.ExecutorService;


public class Main {

  static ExecutorService pool = java.util.concurrent.Executors
      .newFixedThreadPool(2);

  public static void main(String[] args) {
    String[] inputs = new String[] {"1", "2", "3", "4", "5"};
    // Create a few Translation Requester threads
    TranslationRequester tr1 = new TranslationRequester(pool, inputs, 1);
    TranslationRequester tr2 = new TranslationRequester(pool, inputs, 2);

    tr1.start();
    tr2.start();

    try {
      tr1.join();
      tr2.join();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    System.out.println();
    for (String line : tr1.results) {
      System.out.print(line);
    }

    System.out.println();
    for (String line : tr1.results) {
      System.out.print(line);
    }
  }

}