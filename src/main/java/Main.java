import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import javax.swing.*;
import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.console;

public class Main {

    public static void main(String[] args) throws Exception{

        Tesseract tesseract = new Tesseract();

        //choose folder with datafiles
        System.out.println("Select folder for language files");
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select folder for language files");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            if(chooser.getSelectedFile().isDirectory()){
                File[] listOfFiles = chooser.getSelectedFile().listFiles();
                boolean hasTraineddata = false;
                for (File file : listOfFiles) {
                    if(file.getName().endsWith(".traineddata")){
                        hasTraineddata = true;
                        tesseract.setDatapath(""+chooser.getSelectedFile());
                        break;
                    }
                }

                if(!hasTraineddata){
                    System.out.println("No \".trainedata\" files in selected folder");
                    System.exit(1);
                }
            }
            else{
                File[] listOfFiles = chooser.getSelectedFile().listFiles();
                boolean hasTraineddata = false;
                for (File file : listOfFiles) {
                    if(file.getName().endsWith(".traineddata")){
                        hasTraineddata = true;
                        tesseract.setDatapath(""+chooser.getCurrentDirectory());
                        break;
                    }
                }

                if(!hasTraineddata){
                    System.out.println("No \".trainedata\" files in selected folder");
                    System.exit(1);
                }
            }
        } else {
            System.out.println("No folder selected");
            System.exit(1);
        }

        //select language
        System.out.println("Select language, default is eng(lish)");
        Scanner scanner = new Scanner(System.in);
        String languageIn = scanner.nextLine();
        try{
            tesseract.setLanguage(languageIn);
        }
        catch(Exception e){
            tesseract.setLanguage("eng");
        }

        //select image path
        String imagePath = null;

        System.out.println("Select image location");
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Image location");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            if(chooser.getSelectedFile().isDirectory()){
                System.out.println(chooser.getSelectedFile());
                imagePath =""+chooser.getSelectedFile();
            }
            else{
                System.out.println(chooser.getCurrentDirectory());
                imagePath = ""+chooser.getCurrentDirectory();
            }
        } else {
            System.out.println("No folder selected");
            return;
        }

        File folder = new File(imagePath);
        File[] listOfFiles = folder.listFiles();
        String text = "";

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {

                try{
                    text = text + tesseract.doOCR((listOfFiles[i])) + "\n\n";
                }
                catch(Exception e){
                    System.err.println(e);
                }

            } else if (listOfFiles[i].isDirectory()) {

                System.out.println("Directory " + listOfFiles[i].getName());

            }
        }

        try {
            File file = new File(imagePath + "\\output.txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(text);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(text + "Texts were saved to " + imagePath + "\\output.txt");



    }

}
