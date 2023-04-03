package io.jb.tobedeleted.nioexamples;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class NIOTest {

    public static void main(String[] args)  {
        Path path = FileSystems.getDefault().getPath("src/main/resources/files/nio/SortedAndDuplicatesRemoved.txt");
        try {
            List<String> linesList = readFileForSorting(path);
            Collections.sort(linesList);
            createSortedFile(linesList, path);

            Set<String> lines = readFileForRemovingDuplicates(path);
            removeDuplicatesFile(lines, path);
        } catch (IOException e) {
            System.out.println("Exception occurred. " + e.getMessage());
        }
    }

    private static List<String> readFileForSorting(Path path) throws IOException {
        List<String> lines;
        try (BufferedReader br = Files.newBufferedReader(path)) {
            Scanner sc = new Scanner(br);
            lines = new ArrayList<>();
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }
        }
        return lines;
    }

    private static Set<String> readFileForRemovingDuplicates(Path path) throws IOException {
        Set<String> lines;
        try (BufferedReader br = Files.newBufferedReader(path)) {
            Scanner sc = new Scanner(br);
            lines = new LinkedHashSet<>();
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }
        }
        return lines;
    }

    private static void createSortedFile(List<String> lines, Path path) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            bw.flush();
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }

    private static void removeDuplicatesFile(Set<String> lines, Path path) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            bw.flush();
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }

}
