package io.jb.textprocessor.util;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;
public class TextProcessorUtil {
    private static List<String> listOfLines = new ArrayList<>();
    private static BufferedReader reader;
    private static BufferedWriter writer;
    public static void printMenu() {
        System.out.println();
        System.out.println("\t+----------------------------------------------------------+");
        System.out.println("\t|            MENU-Text Processor                           |");
        System.out.println("\t+----------------------------------------------------------+");
        System.out.println("\t|press |1 or s: sort alphabetically (Ascending/Descending       |");
        System.out.println("\t|      |2 or r: remove duplicate                               |");
        System.out.println("\t|      |3 or f: finding text and indicate line number in a file |");
        System.out.println("\t|      |4 or p: replace a specific text with new text           |");
        System.out.println("\t|      |5 or m: merge files into a single file                  |");
        System.out.println("\t|------|6 or e: exit the program--------------------------------|");
    }
    public static void sortLines(String filePathWithExtension, String sortingOrder) throws IOException {
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

        } catch (FileNotFoundException e) {
            System.out.println(filePathWithExtension + " file is not found. " + e.getMessage());

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public static void searchAndShowLineNumber(String fileName, String searchText) {
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
        } catch (FileNotFoundException e) {
            System.out.println("File not found." + e.getMessage());

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public static void searchAndReplace(String fileName, String searchText, String newText) {
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            writer = new BufferedWriter(new FileWriter(fileName));
            while (line != null) {
                //listOfLines.add(line);
                if (line.contains(searchText)) {
                    String newLine = line.replace(searchText, newText);
                    System.out.println(newLine);
                    listOfLines.add(newLine);
                } else {
                    listOfLines.add(line);
                }
                line = reader.readLine();
            }
            //reader.close();
            for (String l : listOfLines) {
                writer.write(l + "\n");
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found. " + e.getMessage());

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
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