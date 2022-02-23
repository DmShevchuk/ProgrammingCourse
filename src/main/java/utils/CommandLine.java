package utils;

import collection.CollectionManager;

import java.util.Scanner;

public class CommandLine {
    private final static String USER_COMMAND_PREFIX = ">>";
    private final static String SCRIPT_EXECUTE_PREFIX = "$ ";
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final CollectionManager MANAGER = new CollectionManager();

    public void run(){
        String INPUT_COMMAND;

        do {
            INPUT_COMMAND = SCANNER.nextLine();
            System.out.println("$" + INPUT_COMMAND);
            if(INPUT_COMMAND.equals("show")){
                MANAGER.collectionToString();
            }
        } while (!INPUT_COMMAND.equals("exit"));

    }

    public static void outLn(String text){
        System.out.println(text);
    }

}
