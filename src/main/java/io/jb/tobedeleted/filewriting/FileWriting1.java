package io.jb.tobedeleted.filewriting;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FileWriting1 {

    private static Map<Integer, String> lines = new HashMap<>();

    static {
        lines.put(1, "This is line One.");
        lines.put(2, "This is line Two.");
        lines.put(3, "This is line Three.");
        lines.put(4, "This is line Four.");
    }
    public static void main(String[] args) {
        FileWriter fw = null;
        try {
            fw = new FileWriter("src/main/resources/files/writer/WriteTest.txt");
            for (String element : lines.values()) {
                fw.write(element + "\n");
            }
        } catch (IOException e) {
            System.out.println("Exception occurred. " + e.getMessage());
        } finally {
            try {
                if (!Objects.isNull(fw)) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
