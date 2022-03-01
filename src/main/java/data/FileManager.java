package data;

import utils.CommandLine;

import java.io.*;

public class FileManager {
    public static boolean canRead(String fileName) {
        try {
            File file = new File(fileName);
            return file.canRead();
        } catch (Exception e) {
            CommandLine.errorOut(String.format("Unable to read file %s!", fileName));
        }
        return false;
    }

    public static boolean canWrite(String fileName) {
        try {
            File file = new File(fileName);
            return file.canWrite();
        } catch (Exception e) {
            CommandLine.errorOut(String.format("Unable to write data to file %s!", fileName));
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
            CommandLine.errorOut(String.format("Problems reading the file %s!", fileName));
            return null;
        }
    }


}
