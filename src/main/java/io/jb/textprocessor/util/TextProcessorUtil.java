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

    public static void sortLines(String filePath, String sortingOrder) {
        BufferedReader reader;
        List<String> listOfLines;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            Scanner sc = new Scanner(reader);
            listOfLines = new ArrayList<>();
            while (sc.hasNextLine()) {
                listOfLines.add(sc.nextLine());
            }
            sortByPreference(sortingOrder, listOfLines);
            writeSortedLinesToFile(filePath, listOfLines);
            System.out.println("File content is sorted in " + sortingOrder + " order & \nFile  path :" + filePath + "\n");

        } catch (IOException e) {
            System.out.println(filePath + " file is not found. " + e.getMessage());
            System.out.println(e.getMessage());

        }
    }

    private static void writeSortedLinesToFile(String filePath, List<String> listOfLines) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (String listElement : listOfLines) {
                bufferedWriter.write(listElement + "\n");
            }
        }
    }

    private static void sortByPreference(String sortingOrder, List<String> listOfLines) {
        if (sortingOrder.equalsIgnoreCase("asc")) {
            Collections.sort(listOfLines);
        } else if (sortingOrder.equalsIgnoreCase("desc")) {
            Collections.sort(listOfLines, Collections.reverseOrder());
        } else System.out.println(sortingOrder + " is not asc or desc");
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
        Scanner sc;
        ArrayList<Integer> listOfLineNumbers;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            sc = new Scanner(reader);
            int lineNumber = 0;
            listOfLineNumbers = new ArrayList<>();
            while (sc.hasNextLine()) {
                lineNumber++;
                if (sc.nextLine().toLowerCase().contains(searchText.toLowerCase())) {
                    listOfLineNumbers.add(lineNumber);
                }
            }
            printLineNumbers(searchText, listOfLineNumbers);
        } catch (IOException e) {
            System.out.println("File not found.");
            System.out.println(e.getMessage());
        }
    }

    private static void printLineNumbers(String searchText, ArrayList<Integer> listOfLineNumbers) {
        int numberOfLines = listOfLineNumbers.size();
        if (numberOfLines > 0)
            System.out.println("\"" + searchText + "\" appeared in " + numberOfLines + "lines :" + listOfLineNumbers);
        else System.out.println("\"" + searchText + "\" did not appeared in the file");
    }

    public static void searchAndReplace(String fileName, String searchText, String newText) {
        BufferedReader reader;
        List<String> listOfLines;
        Scanner scanner;
        int replacementCounts = 0;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            listOfLines = new ArrayList<>();
            scanner = new Scanner(reader);
            String currentLine = "";
            while (scanner.hasNextLine()) {
                currentLine = scanner.nextLine();
                if (currentLine.toLowerCase().contains(searchText.toLowerCase())) {
                    replacementCounts = replacementCounts + 1;
                    String updatedLine = currentLine.replace(searchText, newText);
                    listOfLines.add(updatedLine);
                } else {
                    listOfLines.add(currentLine);
                }
            }
            writeSortedLinesToFile(fileName, listOfLines);
            if (replacementCounts > 0) {
                System.out.println("There were " + replacementCounts + " replacements.");
            } else
                System.out.println(searchText + " was not found in file ;hence no replacement happened.");
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
        try (FileWriter fw = new FileWriter(path); BufferedWriter bw = new BufferedWriter(fw)) {
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
            return stream.filter(file -> !Files.isDirectory(file))
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

}