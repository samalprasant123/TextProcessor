package io.jb.textprocessor.util;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextProcessorUtil {
    private static final String banner = """
            ######## ######## ##     ## ########    ########  ########   #######   ######  ########  ######   ######   #######  ########
               ##    ##        ##   ##     ##       ##     ## ##     ## ##     ## ##    ## ##       ##    ## ##    ## ##     ## ##     ##
               ##    ##         ## ##      ##       ##     ## ##     ## ##     ## ##       ##       ##       ##       ##     ## ##     ##
               ##    ######      ###       ##       ########  ########  ##     ## ##       ######    ######   ######  ##     ## ########
               ##    ##         ## ##      ##       ##        ##   ##   ##     ## ##       ##             ##       ## ##     ## ##   ##
               ##    ##        ##   ##     ##       ##        ##    ##  ##     ## ##    ## ##       ##    ## ##    ## ##     ## ##    ##
               ##    ######## ##     ##    ##       ##        ##     ##  #######   ######  ########  ######   ######   #######  ##     ##
            """;
    private static final String menu = """
            +---------------------------------------------------------------------+
            |                                 MENU                                |
            +---------------------------------------------------------------------+
            |Press:                                                               |
            |      -> 1 or s: to sort alphabetically (Ascending/Descending)       |
            |      -> 2 or r: to remove duplicate                                 |
            |      -> 3 or f: to find text and indicate line number in the file   |
            |      -> 4 or p: to replace a specific text with new text            |
            |      -> 5 or m: to merge files into a single file                   |
            |      -> 6 or e: to exit the program                                 |
            +---------------------------------------------------------------------+
            """;
    public static void printBanner() {
        System.out.println(banner);
    }
    public static void printMenu() {
        System.out.println(menu);
    }
    public static void printExitMessage() {
        System.out.println("\nBye! Exiting text processing application.\n");
    }
    public static void sortLines(String filePathWithExtension, String sortingOrder) {
        BufferedReader reader;
        BufferedWriter writer;
        List<String> listOfLines = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(filePathWithExtension));
            String line = reader.readLine();
            while (line != null) {
                listOfLines.add(line);
                line = reader.readLine();
            }
            if (sortingOrder.equalsIgnoreCase("asc")) {
                Collections.sort(listOfLines);

            }
            if (sortingOrder.equalsIgnoreCase("desc")) {
                Collections.sort(listOfLines, Collections.reverseOrder());
            }
            writer = new BufferedWriter(new FileWriter(filePathWithExtension));
            for (String listElement : listOfLines) {
                writer.write(listElement + "\n");
            }
            writer.close();

        } catch (IOException e) {
            System.out.println(filePathWithExtension + " file is not found. " + e.getMessage());
            System.out.println(e.getMessage());

        }
    }
    public static void removeDuplicates(String path) {
        try {
            //removeDuplicatesIO(path);
            removeDuplicatesNIO(path);
            System.out.println("Removed duplicates in files successfully.");
        } catch (IOException e) {
            System.out.println("Unable to remove duplicates.");
            System.out.println(e.getMessage());
        }
    }
    public static void searchAndShowLineNumber(String fileName, String searchText) {
        BufferedReader reader;
        BufferedWriter writer;
        List<String> listOfLines = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            int lineNumber = 0;
            while (line != null) {
                lineNumber++;
                if (line.contains(searchText)) {
                    listOfLines.add(lineNumber + "->" + searchText + "\n");
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found." + e.getMessage());

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            for (String st : listOfLines) {
                System.out.println(st);
                writer.write(st);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("File not found.");
            System.out.println(e.getMessage());
        }

    }
    public static void searchAndReplace(String fileName, String searchText, String newText) {
        BufferedReader reader;
        BufferedWriter writer;
        List<String> listOfLines = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            writer = new BufferedWriter(new FileWriter(fileName));
            while (line != null) {
                if (line.contains(searchText)) {
                    String newLine = line.replace(searchText, newText);
                    System.out.println(newLine);
                    listOfLines.add(newLine);
                } else {
                    listOfLines.add(line);
                }
                line = reader.readLine();
            }
            for (String l : listOfLines) {
                writer.write(l + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("File not found. ");
            System.out.println(e.getMessage());
        }
    }
    public static void mergeFiles(String path) {
        try {
            mergeFilesNIO(path);
            System.out.println("Files merged successfully.");
        } catch (IOException e) {
            System.out.println("Unable to merge files.");
            System.out.println(e.getMessage());
        }
    }
    private static void removeDuplicatesIO(String path) throws IOException {
        Set<String> lines = readUniqueLines(path);
        if (!Objects.isNull(lines)) {
            removeDuplicatesInFile(lines, path);
        }
    }
    private static void removeDuplicatesNIO(String path) throws IOException {
        Path filePath = FileSystems.getDefault().getPath(path);
        Set<String> lines = readUniqueLinesNIO(filePath);
        if (!Objects.isNull(lines)) {
            removeDuplicatesInFileNIO(lines, filePath);
        }
    }
    private static Set<String> readUniqueLines(String path) throws IOException {
        Set<String> lines;
        try (FileReader fr = new FileReader(path)) {
            BufferedReader br = new BufferedReader(fr);
            Scanner sc = new Scanner(br);
            lines = new LinkedHashSet<>();
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }
        }
        return lines;
    }
    private static Set<String> readUniqueLinesNIO(Path path) throws IOException {
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
    private static void removeDuplicatesInFile(Set<String> lines, String path) throws IOException {
        try (FileWriter fw = new FileWriter(path);
             BufferedWriter bw = new BufferedWriter(fw)
        ) {
            bw.flush();
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }
    private static void removeDuplicatesInFileNIO(Set<String> lines, Path path) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            bw.flush();
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }
    private static void mergeFilesNIO(String dirPath) throws IOException {
        Set<String> files = listFiles(dirPath);
        String mergedPathFile = dirPath + "MergedFile.txt";
        Path mergedPath = FileSystems.getDefault().getPath(mergedPathFile);
        List<String> allLines = new ArrayList<>();
        for (String file : files) {
            Path path = FileSystems.getDefault().getPath(dirPath + file);
            List<String> lines = readFileNIO(path);
            allLines.addAll(lines);
        }
        writeInMergedFileNIO(allLines, mergedPath);
    }
    private static Set<String> listFiles(String dirPath) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dirPath))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }
    private static List<String> readFileNIO(Path path) throws IOException {
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
    private static void writeInMergedFileNIO(List<String> lines, Path path) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            bw.flush();
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }
/*public static void sortLinesNIO(String filePathWithExtension, String sortingOrder) throws IOException {
        // using stream
        writer = new BufferedWriter(new FileWriter(filePathWithExtension));
        try (Stream<String> lines = Files.lines(Path.of(filePathWithExtension))) {
            if (sortingOrder.equalsIgnoreCase("asc")) {
                lines.sorted().forEach(
                        input -> {
                              //  writer.write(input.concat("\n"));
                                System.out.println(input);
                                lines.
                        });
            }
            if (sortingOrder.equalsIgnoreCase("desc")) {
                lines.sorted(Collections.reverseOrder()).forEach(
                        input -> {
                            //  writer.write(input.concat("\n"));
                            System.out.println(input);
                        });
            }
        }
        writer.close();
    }
    public static void searchAndShowLineNumberNIO(String fileName, String searchText) {
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            int lineNumber = 0;
            while (line != null) {
                listOfLines.add(line);
                lineNumber++;
                if (line.contains(searchText)) {
                    System.out.println(lineNumber + "->" + searchText);
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public static void searchAndReplaceNIO(String fileName, String searchText, String newText) throws IOException {
        try (Stream<String> lines = Files.lines(Path.of(fileName))) {
            //lines.m.filter(s -> s.contains(searchText));
        }
    }
*/
}