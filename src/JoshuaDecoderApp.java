import java.util.LinkedList;
import java.util.List;




public class JoshuaDecoderApp {

  public static void main(String[] args) {
    DecoderRunner decoder = new DecoderRunner();

    int numInputs = 100;
    String[] inputs = new String[numInputs];
    for (int i = 0; i < numInputs; i++) {
      inputs[i] = (new Integer(i + 1)).toString();
    }


    // Create a few Translation Requester threads
    int numRequests = 4;
    List<Translator> requests =
        new LinkedList<Translator>();

    for (int i = 0; i < numRequests; i++) {
      Translator tr = new Translator(decoder, inputs, i);
      requests.add(tr);
    }
    for (Translator tr : requests) {
      tr.start();
    }
    for (Translator tr : requests) {
      try {
        tr.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    for (Translator tr : requests) {

      // All the translations happened, so print them together.
      System.out.println();
      System.out.print("thread " + tr.getThreadNum() + ":{");
      for (String line : tr.getTranslations()) {
        System.out.print(line);
      }
      System.out.print("}");
    }
  }

}