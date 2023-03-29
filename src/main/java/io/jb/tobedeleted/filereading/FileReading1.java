package io.jb.tobedeleted.filereading;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReading1 {
    public static void main(String[] args) {
        System.out.println("\nStarting program\n");
        File file = new File("src/main/resources/files/test.txt");
        printContentsOfFile(file);
        System.out.println("\nEnding program\n");
    }

    private static void printContentsOfFile(File file) {
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                System.out.println(line);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found. " + e.getMessage());
            //throw new RuntimeException(e);
        }
    }
}