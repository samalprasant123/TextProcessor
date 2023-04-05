package io.jb.textprocessor;
import io.jb.textprocessor.util.TextProcessorUtil;
import java.io.IOException;
import java.util.Scanner;
public class TextProcessor {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String filePathWithExtension = "";
        String sortingOrder = "";
        String searchText = "";
        String replacementText = "";
        char wantToContinue;
        do {
            TextProcessorUtil.printMenu();
            System.out.println("\t Enter your choice >>>");
            String userInput = scanner.nextLine();
            char choose = userInput.toLowerCase().charAt(0);
            switch (choose) {
                case 's':
                case '1':
                    System.out.println("Enter filename with extension?");
                    filePathWithExtension = scanner.nextLine();
                    System.out.println("Enter sorting order (asc/desc)");
                    sortingOrder = scanner.nextLine();
                    TextProcessorUtil.sortLines(filePathWithExtension, sortingOrder);
                    //TextProcessorUtil.sortLinesNIO(filePathWithExtension,sortingOrder);
                    System.out.println("File content is sorted in " + sortingOrder + " order & \nFill path :" + filePathWithExtension + "\n");
                    break;
                case 'r':
                case '2':
                    System.out.println("remove");
                    break;
                case 'f':
                case '3':
                    System.out.println("Enter filename with extension?");
                    filePathWithExtension = scanner.nextLine();
                    System.out.println("Enter search text");
                    searchText = scanner.nextLine();
                    TextProcessorUtil.searchAndShowLineNumber(filePathWithExtension, searchText);
                    //TextProcessorUtil.searchAndShowLineNumberNIO(filePathWithExtension,searchText);
                    System.out.println("Result for \"" + searchText + "\" :");
                    break;
                case 'p':
                case '4':
                    System.out.println("Enter filename with extension?");
                    filePathWithExtension = scanner.nextLine();
                    System.out.println("Enter search text");
                    searchText = scanner.nextLine();
                    System.out.println("Enter replacement text");
                    replacementText = scanner.nextLine();
                    TextProcessorUtil.searchAndReplace(filePathWithExtension, searchText, replacementText);
                    //TextProcessorUtil.searchAndReplaceNIO(filePathWithExtension,searchText,replacementText);
                    System.out.println(searchText + " was replaced by " + replacementText);
                    break;
                case 'm':
                case '5':
                    System.out.println("merge");
                    break;
                case 'e':
                case '6':
                    System.out.println("Bye, you are exiting");
                    break;
                default:
                    System.out.println("unknown choice");
            }
            System.out.println("press (6 or e) to exit or (c) to continue the program");
            wantToContinue = scanner.nextLine().charAt(0);

        } while (wantToContinue != 'e' || wantToContinue != '6');
        System.out.println("Bye! Exiting text processing application");

    }

}