import java.util.LinkedList;
import java.util.List;

// TODO: make this extend Iterator to allow the nextTranslation() method.
/**
 * A Translator takes segments to be translated, i.e. "inputs" from the input
 * channel, decodes them via the decoder, and routes the decoded outputs on
 * through the output sink.
 * 
 * @author Luke Orland
 * 
 */
public class Translator extends Thread {

  private final Decoder decoderRunner;
  private final String[] inputs;
  private List<String> translations = new LinkedList<String>();
  private final int threadNum;

  public Translator(Decoder decoderRunner, String[] inputs,
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
