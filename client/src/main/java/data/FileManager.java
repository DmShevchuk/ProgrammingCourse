package data;
import utils.CommandLine;

import java.io.*;

public class FileManager {
    private final CommandLine commandLine;

    public FileManager(CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    public boolean canRead(String fileName) {
        File file = new File(fileName);
        return file.canRead();
    }

    public String read(String fileName) {
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
        } catch (IOException e) {
            commandLine.errorOut(String.format("Problems reading the file %s!", fileName));
            return null;
        }
    }


}
