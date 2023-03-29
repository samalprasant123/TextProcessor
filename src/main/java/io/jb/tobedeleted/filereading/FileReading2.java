package io.jb.tobedeleted.filereading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class FileReading2 {
    public static void main(String[] args) {
        String path = "src/main/resources/files/writer/WriteTest.txt";
        try {
            readFileAndPrintContents(path);
        } catch (IOException e) {
            System.out.println("Exception occurred. " + e.getMessage());
        }
    }

    private static void readFileAndPrintContents(String path) throws IOException {
        try (FileReader fr = new FileReader(path)) {
            BufferedReader br = new BufferedReader(fr);
            //Scanner sc = new Scanner(fr);
            String line = br.readLine();
            System.out.println();
            while (!Objects.isNull(line)) {
                System.out.println(line);
                line = br.readLine();
            }
            System.out.println();
        }
    }

}
