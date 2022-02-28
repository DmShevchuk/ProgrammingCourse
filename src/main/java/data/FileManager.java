package data;

import utils.CommandLine;

import java.io.*;

public class FileManager {
    public static boolean canRead(String fileName) {
        try {
            File file = new File(fileName);
            return file.canRead();
        } catch (Exception e) {
            CommandLine.outLn(String.format("Невозможно прочитать файл %s!", fileName));
        }
        return false;
    }

    public static String read(String fileName){
        String fileContent = "";
        try {
            InputStream inputStream = new FileInputStream(fileName);
            Reader inputStreamReader = new InputStreamReader(inputStream);

            int data = inputStreamReader.read();

            while (data != -1) {
                char theChar = (char) data;
                fileContent += theChar;
                data = inputStreamReader.read();
            }

            inputStreamReader.close();
            return fileContent;
        }catch (IOException e){
            CommandLine.outLn(String.format("Проблемы с прочтением файла %s!", fileName));
            return null;
        }
    }


}
