package kn.tcg.client;
import kn.tcg.controller.engine.Engine;

import java.util.Scanner;

public class Client{
    public static void main(String[] args){
        Engine engine = new Engine();
        Scanner scanner = new Scanner(System.in);
        String message;
        System.out.println("## enter '(s)tart' to start game or '(q)uit)' to cancel game ##");
        while(true) {
            System.out.print("|::>> ");
            message = engine.performCommand(scanner.next());
            System.out.println(message);

            if(message.contains("canceled") || message.contains("winner")){
                break;
            }
        }
    }
}