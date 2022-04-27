package data;

import exceptions.UnableToReadFileException;
import utils.CommandLine;

import java.io.*;

public class FileManager {
    private final CommandLine commandLine;

    public FileManager(CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    public void canRead(String fileName) throws UnableToReadFileException {
        File file = new File(fileName);
        if (file.canRead()) return;
        throw new UnableToReadFileException(String.format("Can't read file {%s}!", fileName));
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