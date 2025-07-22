package ui;

import com.formdev.flatlaf.IntelliJTheme;
import data.DataManagement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import translator.TranslatorBeta;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UITest {

    @BeforeEach
    public void beforeEach(){

    }

    @Test
    public void testApp(){
        UIMainWindow.getInstance().startWindow();

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //UI TESTS
    //@Test
    public void testChangeWindow(){
        UIMiscWindow.getInstance().customAlert("Gib test ein");
        assertEquals("test", UIMiscWindow.getInstance().changeWindow("GIB TEST EIN").toLowerCase());
    }

    //@Test
    public void testChangeTimeWindow(){
        UIMiscWindow.getInstance().customAlert("Gib 12. Dezember 2012 um 12:12 Uhr ein");
        assertEquals("2012-12-12T12:12", UIMiscWindow.getInstance().changeWindowWithDateTime("GIB 12.12.2012 um 12:12 EIN").toString());
    }


}
