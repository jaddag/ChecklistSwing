package ui;

import org.junit.jupiter.api.Test;
import translator.TranslatorBeta;

import java.io.IOException;

public class UITest {

    @Test
    public void testUI(){
        UIMainWindow.getInstance().startWindow();

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void testLanguage(){
        try {
            System.out.println(TranslatorBeta.getInstance().translate("Hallo", "de", "en"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
