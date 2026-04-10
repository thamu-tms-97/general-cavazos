package com.cavazos;

import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class CavazosExample {

    public static void main(String[] args) {
        String[] commandArray = JSONFile.readCommandsFromResource("/commands.json");

        if (commandArray == null || commandArray.length == 0) {
            System.out.println("ERROR: Could not load commands. Check your resources folder.");
            return;
        }

        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        
        Stack<String> undoStack = new Stack<>();
        Stack<String> redoStack = new Stack<>();

        char userChoice = '_';

        while (userChoice != 'q') {
            printMenu();
            System.out.print("Enter a command: ");

            String input = scan.nextLine().trim().toLowerCase();

            if (input.isEmpty()) {
                System.out.println("ERROR: Please enter a command.");
                continue;
            }

            userChoice = input.charAt(0);
            System.out.println();

            switch (userChoice) {
                case 'i':
                    String issued = commandArray[rand.nextInt(commandArray.length)];
                    // Exact match for screenshot prompt
                    System.out.println("[COMMAND ISSUED]: General Cavazos orders the troops to do: " + issued);
                    undoStack.push(issued);
                    redoStack.clear();
                    break;

                case 'l':
                    // Exact match for screenshot list header
                    System.out.println("Number  Command");
                    System.out.println("------  --------------------");
                    for (int i = 0; i < commandArray.length; i++) {
                        System.out.printf("%02d      %s%n", (i + 1), commandArray[i]);
                    }
                    break;

                case 'u':
                    if (undoStack.isEmpty()) {
                        // Exact match for screenshot error
                        System.out.println("ERROR: There are no commands to undo. Please issue or redo a command");
                    } else {
                        String undone = undoStack.pop();
                        redoStack.push(undone);
                        // Exact match for screenshot prompt
                        System.out.println("[UNDO COMMAND ISSUED]: General Cavazos orders the troops to undo: " + undone);
                    }
                    break;

                case 'r':
                    if (redoStack.isEmpty()) {
                        System.out.println("ERROR: There are no commands to redo.");
                    } else {
                        String redone = redoStack.pop();
                        undoStack.push(redone);
                        // Exact match for screenshot prompt
                        System.out.println("[REDO COMMAND ISSUED]: General Cavazos orders the troops to redo: " + redone);
                    }
                    break;

                case 'q':
                    System.out.println("Thank You General Cavazos");
                    break;

                default:
                    System.out.println("ERROR: Invalid selection.");
                    break;
            }
        }
        scan.close();
    }

    public static void printMenu() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("General Cavazos Commander App");
        System.out.println("----------------------------------------------------------------");
        System.out.println("i    Issue a command");
        System.out.println("l    List all of the commands");
        System.out.println("u    Undo the last command that was issued");
        System.out.println("r    Redo the last command that was issued");
        System.out.println("q    Quit");
        System.out.println("----------------------------------------------------------------");
    }
}