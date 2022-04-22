package data;
import exceptions.UnableToWorkWithFileException;

import java.io.*;

public class FileManager {

    public FileManager() {
    }

    public void canRead(String fileName) throws UnableToWorkWithFileException {
        File file = new File(fileName);
        if (file.canRead()) return;
        throw new UnableToWorkWithFileException(String.format("Can't read file %s!", fileName));
    }

    public boolean canWrite(String fileName) {
        File file = new File(fileName);
        return file.canWrite();
    }

    public String read(String fileName) throws IOException {
        String fileContent = "";

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
    }


}
