package data;

import utils.CommandLine;

import java.io.*;

public class FileManager {
    private final CommandLine commandLine;

    public FileManager(CommandLine commandLine){
        this.commandLine = commandLine;
    }

    public boolean canRead(String fileName) {
        try {
            File file = new File(fileName);
            return file.canRead();
        } catch (Exception e) {
            commandLine.errorOut(String.format("Unable to read file %s!", fileName));
        }
        return false;
    }

    public boolean canWrite(String fileName) {
        try {
            File file = new File(fileName);
            return file.canWrite();
        } catch (Exception e) {
            commandLine.errorOut(String.format("Unable to write data to file %s!", fileName));
        }
        return false;
    }

    public String read(String fileName){
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
            commandLine.errorOut(String.format("Problems reading the file %s!", fileName));
            return null;
        }
    }


}
