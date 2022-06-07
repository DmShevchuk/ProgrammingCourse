package data;

import java.io.*;

public class FileManager {

    public FileManager() {
    }

    public boolean canRead(String fileName) {
        File file = new File(fileName);
        return file.canRead();
    }

    public String read(String fileName) throws IOException {
        StringBuilder fileContent = new StringBuilder();
        InputStream inputStream = new FileInputStream(fileName);
        Reader inputStreamReader = new InputStreamReader(inputStream);

        int data = inputStreamReader.read();

        while (data != -1) {
            char theChar = (char) data;
            fileContent.append(theChar);
            data = inputStreamReader.read();
        }

        inputStreamReader.close();
        return fileContent.toString();

    }


}
