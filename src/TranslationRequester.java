import java.util.LinkedList;
import java.util.List;

// TODO: make this extend Iterator to allow the nextTranslation() method.
public class TranslationRequester extends Thread {

  private final Decoder decoderRunner;
  private final String[] inputs;
  private List<String> translations = new LinkedList<String>();
  private final int threadNum;

  public TranslationRequester(Decoder decoderRunner, String[] inputs,
                              int threadNum) {
    this.decoderRunner = decoderRunner;
    this.inputs = inputs;
    this.threadNum = threadNum;
  }


  @Override
  public void run() {
    setTranslations(decoderRunner.decodeAll(inputs, getThreadNum()));
    // All the translations happened, so print done.
    System.out.print("[" + getThreadNum() + "done]");
  }

  public synchronized List<String> getTranslations() {
    return translations;
  }

  public synchronized void setTranslations(List<String> translations) {
    this.translations = translations;
  }

  public synchronized int getThreadNum() {
    return threadNum;
  }

}
