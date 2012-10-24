interface Translator {
  String translate(String input);
}


public class SimpleTranslator implements Translator {

  @Override
  public String translate(String input) {
    try {
      TranslationRequester.sleep(1000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return input + "t,";
  }
}