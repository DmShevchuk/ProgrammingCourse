package data;

import java.io.*;

public class FileManager {
    public boolean canRead(String fileName) {
        File file = new File(fileName);
        return file.canRead();
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
