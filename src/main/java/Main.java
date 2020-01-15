import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception{

        String inputFilePath = "C:\\Users\\moral\\Documents\\programming\\.net und Csharp\\instagramBotCore\\InstagramBotCore\\InstagramBotCore\\bin\\Debug\\netcoreapp2.1\\images\\1826523511368410613_7408347087.jpg";

        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Users\\moral\\Downloads\\"); //path to language file
        //tesseract.setLanguage("deu");
        String text = tesseract.doOCR(new File((inputFilePath)));

        System.out.println(text);



    }

}
