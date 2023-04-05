package io.jb.textprocessor.util;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class TextProcessorUtil {

    public static void printBanner() {
        System.out.println("\n\n\n######## ######## ##     ## ########    ########  ########   #######   ######  ########  ######   ######   #######  ########  \n" +
                "   ##    ##        ##   ##     ##       ##     ## ##     ## ##     ## ##    ## ##       ##    ## ##    ## ##     ## ##     ## \n" +
                "   ##    ##         ## ##      ##       ##     ## ##     ## ##     ## ##       ##       ##       ##       ##     ## ##     ## \n" +
                "   ##    ######      ###       ##       ########  ########  ##     ## ##       ######    ######   ######  ##     ## ########  \n" +
                "   ##    ##         ## ##      ##       ##        ##   ##   ##     ## ##       ##             ##       ## ##     ## ##   ##   \n" +
                "   ##    ##        ##   ##     ##       ##        ##    ##  ##     ## ##    ## ##       ##    ## ##    ## ##     ## ##    ##  \n" +
                "   ##    ######## ##     ##    ##       ##        ##     ##  #######   ######  ########  ######   ######   #######  ##     ## \n\n\n");
    }

    public static void printMenu() {
        System.out.println();
        System.out.println("+---------------------------------------------------------------------+");
        System.out.println("|                                 MENU                                |");
        System.out.println("+---------------------------------------------------------------------+");
        System.out.println("|Press:                                                               |");
        System.out.println("|      -> 1 or s: to sort alphabetically (Ascending/Descending)       |");
        System.out.println("|      -> 2 or r: to remove duplicate                                 |");
        System.out.println("|      -> 3 or f: to find text and indicate line number in the file   |");
        System.out.println("|      -> 4 or p: to replace a specific text with new text            |");
        System.out.println("|      -> 5 or m: to merge files into a single file                   |");
        System.out.println("|      -> 6 or e: to exit the program                                 |");
        System.out.println("+---------------------------------------------------------------------+");
    }

    public static void printExitMessage() {
        System.out.println("\nBye! Exiting text processing application.\n");
    }

    public static void sort(String filePath, String order ){
        //TODO Bili

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

    private static void removeDuplicatesIO(String path) throws IOException {
        Set<String> lines = readFile(path);
        if (!Objects.isNull(lines)) {
            removeDuplicatesInFile(lines, path);
        }
    }

    private static void removeDuplicatesNIO(String path) throws IOException {
        Path filePath = FileSystems.getDefault().getPath(path);
        Set<String> lines = readFileNIO(filePath);
        if (!Objects.isNull(lines)) {
            removeDuplicatesInFileNIO(lines, filePath);
        }
    }

    private static Set<String> readFile(String path) throws IOException {
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

    private static Set<String> readFileNIO(Path path) throws IOException {
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
}