package com.mycompany.poepractice;

import java.util.Random;
import java.util.Scanner;

public class MessageApp {

    public static void start(Scanner input) {
        System.out.println("Welcome to QuickChat.");
        boolean running = true;
        while (running) {
            
            System.out.println("\nPlease choose an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Enter option (1-3): ");
            String opt = input.nextLine();
            switch (opt) {
                case "1":
                    // go to send messages flow
                    handleSendMessages(input);
                    break;
                case "2":
                    // not finished yet
                    System.out.println("Coming Soon.");
                    break;
                case "3":
                    // user wants to quit
                    System.out.println("Thank you for using QuickChat. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // handle entering multiple messages using a for loop
    private static void handleSendMessages(Scanner input) {
        System.out.print("How many messages would you like to enter? ");
        int num = 0;
        try {
            num = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number, returning to menu.");
            return;
        }
        // loop through the number of messages
        for (int i = 0; i < num; i++) {
            System.out.println("\n--- Message " + (i+1) + " of " + num + " ---");
            String recipient = null;
            while (true) {
                // ask for recipient until it is valid
                System.out.print("Enter recipient number: ");
                recipient = input.nextLine();
                if (Message.checkRecipientCell(recipient)) {
                    System.out.println("Cell phone number successfully captured.");
                    break;
                } else {
                    System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                }
            }

            String messageText = null;
            while (true) {
                // ask for text until it is less than 250 chars
                System.out.print("Enter message text: ");
                messageText = input.nextLine();
                String lengthCheck = Message.checkMessageLength(messageText);
                if ("Message ready to send.".equals(lengthCheck)) {
                    System.out.println("Message ready to send.");
                    break;
                } else {
                    System.out.println(lengthCheck);
                }
            }

            // create an id and message object
            String id = generateRandomId();
            if (!Message.checkMessageID(id)) {
                id = id.substring(0, Math.min(10, id.length()));
            }
            Message m = new Message(id, recipient, messageText, i);

            // show hash and ask what to do with the message
            System.out.println("Message Hash: " + m.getHash());
            System.out.println("Choose action: 1-Send, 2-Disregard (Press 0 to delete), 3-Store");
            System.out.print("Enter choice: ");
            String choice = input.nextLine();
            switch (choice) {
                case "1":
                    // send: increment total and show full details
                    m.markAsSent();
                    System.out.println("Message successfully sent.");
                    displayFullMessage(m);
                    break;
                case "2":
                    // disregard message
                    System.out.println("Press 0 to delete the message");
                    break;
                case "3":
                    // store message to JSON file
                    Message.storeMessage(m);
                    System.out.println("Message successfully stored");
                    break;
                default:
                    System.out.println("Invalid action. Message discarded.");
            }
        }
        // after loop show how many were sent
        System.out.println("\nTotal messages sent: " + Message.returnTotalMessages());
    }

    // print all message fields in a simple format
    private static void displayFullMessage(Message m) {
        System.out.println("Full message details:");
        System.out.println("Message ID: " + m.getId());
        System.out.println("Message Hash: " + m.getHash());
        System.out.println("Recipient: " + m.getRecipient());
        System.out.println("Message: " + m.getText());
    }

    // helper to make a random 10 digit id
    private static String generateRandomId() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) sb.append(r.nextInt(10));
        return sb.toString();
    }
}
