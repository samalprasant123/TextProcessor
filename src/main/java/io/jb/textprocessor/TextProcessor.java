package io.jb.textprocessor;

import io.jb.textprocessor.util.TextProcessorUtil;

import java.util.Scanner;

public class TextProcessor {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        TextProcessorUtil.printBanner();
        runApplication();
        TextProcessorUtil.printExitMessage();
    }

    private static void runApplication() {
        char exitOrContinue;
        do {
            TextProcessorUtil.printMenu();
            System.out.println("Enter your choice: ");
            String userInput = scanner.nextLine();
            char input = userInput.toLowerCase().charAt(0);
            switch (input) {
                case 's', '1' -> {

                    System.out.println("Enter filename with extension?");
                    String filePathWithExtension = scanner.nextLine();
                    System.out.println("Enter sorting order (asc/desc)");
                    String sortingOrder = scanner.nextLine();
                    TextProcessorUtil.sortLines(filePathWithExtension, sortingOrder);
                    //TextProcessorUtil.sortLinesNIO(filePathWithExtension,sortingOrder);
                    System.out.println("File content is sorted in " + sortingOrder + " order & \nFill path :" + filePathWithExtension + "\n");

                }
                case 'r', '2' -> {
                    System.out.println("Enter file path: ");
                    String path = scanner.nextLine();
                    TextProcessorUtil.removeDuplicates(path);
                }
                case 'f', '3' -> {
                    System.out.println("Enter filename with extension?");
                    String filePathWithExtension = scanner.nextLine();
                    System.out.println("Enter search text");
                    String searchText = scanner.nextLine();
                    TextProcessorUtil.searchAndShowLineNumber(filePathWithExtension, searchText);
                    //TextProcessorUtil.searchAndShowLineNumberNIO(filePathWithExtension,searchText);
                    System.out.println("Result for \"" + searchText + "\" :");
                }
                case 'p', '4' -> {
                    System.out.println("Enter filename with extension?");
                    String filePathWithExtension = scanner.nextLine();
                    System.out.println("Enter search text");
                    String searchText = scanner.nextLine();
                    System.out.println("Enter replacement text");
                    String replacementText = scanner.nextLine();
                    TextProcessorUtil.searchAndReplace(filePathWithExtension, searchText, replacementText);
                    //TextProcessorUtil.searchAndReplaceNIO(filePathWithExtension,searchText,replacementText);
                    System.out.println(searchText + " was replaced by " + replacementText);
                }
                case 'm', '5' -> {
                    System.out.println("Enter the directory path where your files are present (max 5 files): ");
                    String path = scanner.nextLine();
                    TextProcessorUtil.mergeFiles(path);
                }
                case 'e', '6' -> {
                    TextProcessorUtil.printExitMessage();
                    System.exit(0);
                }
                default -> System.out.println("Unknown choice. Please enter proper choices from the menu.");
            }
            System.out.println("\nPress (6 or e) to exit or (c) to continue the program.");
            exitOrContinue = scanner.nextLine().charAt(0);
        } while (keepRunning(exitOrContinue));
    }

    private static boolean keepRunning(char flag) {
        return !(flag == 'e' || flag == '6');
    }

}