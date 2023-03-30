package io.jb.textprocessor;
import io.jb.textprocessor.util.TextProcessorUtil;

import java.util.Scanner;
public class TextProcessor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char wantToContinue;
        do {
            TextProcessorUtil.printMenu();
            System.out.println("\t Enter your choice >>>");
            String userInput = scanner.nextLine();
            char choose = userInput.toLowerCase().charAt(0);
            switch (choose) {
                case 's':
                case '1':

                    System.out.println("filePathname?");
                    System.out.println("enter your order (asc/des)");
                    String fileName="";
                    String order="";
                    TextProcessorUtil.sort(fileName, order);
                    System.out.println("sort");
                    break;
                case 'r':
                case '2':
                    System.out.println("remove");
                    break;
                case 'f':
                case '3':
                    System.out.println("find");
                    break;
                case 'p':
                case '4':
                    System.out.println("replace");
                    break;
                case 'm':
                case '5':
                    System.out.println("merge");
                    break;
                case 'e':
                case '6':
                    System.out.println("Bye, you are exiting");
                default:
                    System.out.println("unknown choice");
            }
            System.out.println("press (6 or e) to exit or (c) to continue the program");
            wantToContinue = scanner.nextLine().charAt(0);

        } while (wantToContinue != 'e' || wantToContinue != '6');
        System.out.println("Bye! Exiting text processing application");

    }

}