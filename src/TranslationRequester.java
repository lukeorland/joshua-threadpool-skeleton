import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class TranslationRequester extends Thread {

  ExecutorService translationsExecutor;
  public List<String> inputs = new LinkedList<String>();
  public List<String> results = new LinkedList<String>();
  public int threadNum;

  public TranslationRequester(ExecutorService pool, String[] inputs, int threadNum) {
    this.threadNum = threadNum;
    translationsExecutor = pool;
    for (String input : inputs) {
      this.inputs.add(threadNum + input);
    }
  }

  @Override
  public void run() {

    final Translator translator = new SimpleTranslator();
    Queue<Future<String>> translations = new LinkedList<Future<String>>();

    // Initiate translations, store them in a Queue
    for (final String input : inputs) {
      System.out.print(input + " ");
      Future<String> translFuture =
          translationsExecutor.submit(new Callable<String>() {
            @Override
            public String call() {
              return translator.translate(input);
            }
          });
      translations.add(translFuture);
    }
    // Get translation back.
    while (!translations.isEmpty()) {
      Future<String> translation = translations.peek();
      if (translation.isDone()) {
        try {
          String result = translation.get();
          System.out.print(result);
          results.add(result);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (ExecutionException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } // use
        translations.remove();
      }
    }
  }

}
