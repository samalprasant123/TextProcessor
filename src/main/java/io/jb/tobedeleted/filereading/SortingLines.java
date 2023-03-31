package io.jb.tobedeleted.filereading;

import java.io.*;
import java.util.*;

public class SortingLines {

    public static void main(String[] args) {
        String path = "src/main/resources/files/sorted/Sorted.txt";

        try {
            List<String> lines = readFile(path);
            Collections.sort(lines);

            createSortedFile(lines, path);
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

    private static List<String> readFile(String path) throws IOException {
        List<String> lines;
        try (FileReader fr = new FileReader(path)) {
            BufferedReader br = new BufferedReader(fr);
            Scanner sc = new Scanner(br);
            lines = new ArrayList<>();
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }
        }
        return lines;
    }

    private static void createSortedFile(List<String> lines, String path) throws IOException {
        try (FileWriter fw = new FileWriter(path);
             BufferedWriter bw = new BufferedWriter(fw);
        ) {
            bw.flush();
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }

}
