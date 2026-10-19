package com.mycompany.poepractice;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

// Message class: holds message data
public class Message {
    private String id;
    private String hash;
    private String recipient;
    private String text;
    private int number;
    private static int totalSent = 0;

    // constructor: make a new message object
    public Message(String id, String recipient, String text, int number) {
        this.id = id;
        this.recipient = recipient;
        this.text = text;
        this.number = number;
        // make the hash when we create the message
        this.hash = createMessageHash(id, number, text);
    }

    // simple getters to read fields
    public String getId() { return id; }
    public String getHash() { return hash; }
    public String getRecipient() { return recipient; }
    public String getText() { return text; }

    // check the id is not longer than 10 chars
    public static boolean checkMessageID(String id) {
        return id != null && id.length() <= 10;
    }

    // check recipient number format accepts numbers that start with + (international) or 0 (local)
    public static boolean checkRecipientCell(String r) {
        if (r == null) return false;
        r = r.trim();
        if (r.startsWith("+")) {
            String digits = r.substring(1);
            return digits.matches("\\d{9,12}");
        } else if (r.startsWith("0")) {
            return r.matches("0\\d{8,10}");
        }
        return false;
    }

    // create a simple hash from id, number and first+last words output is in upper case like: 00:0:HITHANKS
    public static String createMessageHash(String id, int num, String text) {
        String firstTwo = id != null && id.length() >= 2 ? id.substring(0,2) : id;
        String count = String.valueOf(num);
        String cleaned = text == null ? "" : text.trim();
        String[] words = cleaned.split("\\s+");
        String first = "";
        String last = "";
        if (words.length > 0) first = words[0].replaceAll("[^a-zA-Z]", "");
        if (words.length > 0) last = words[words.length-1].replaceAll("[^a-zA-Z]", "");
        return (firstTwo + ":" + count + ":" + (first + last)).toUpperCase();
    }

    // get how many messages were sent
    public static int returnTotalMessages() {
        return totalSent;
    }

    // mark message as sent (increment total)
    public void markAsSent() {
        totalSent++;
    }

    // check message length and return a simple message
    public static String checkMessageLength(String message) {
        if (message == null) return "Please enter a message of less than 250 characters.";
        int len = message.length();
        if (len <= 250) return "Message ready to send.";
        int excess = len - 250;
        return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
    }

    // store message to a JSON file called messages.json this is a simple approach for learning, not production ready
    public static synchronized void storeMessage(Message m) {
        Path file = Paths.get("messages.json");
        String obj = String.format("{\n  \"messageId\": \"%s\",\n  \"messageHash\": \"%s\",\n  \"recipient\": \"%s\",\n  \"message\": \"%s\"\n}",
                escape(m.getId()), escape(m.getHash()), escape(m.getRecipient()), escape(m.getText()));
        try {
            // try: attempt to write the JSON object to the messages file if the file does not exist we create a new array with one object
            if (!Files.exists(file)) {
                String arr = "[\n" + obj + "\n]";
                Files.write(file, arr.getBytes(StandardCharsets.UTF_8));
            } else {
                // if file exists we read, add the new object and write back
                List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
                String content = String.join("\n", lines).trim();
                if (content.endsWith("]")) {
                    content = content.substring(0, content.length()-1).trim();
                    content = content + ",\n" + obj + "\n]";
                } else {
                    content = "[\n" + obj + "\n]";
                }
                Files.write(file, content.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            // catch: this runs if there is a problem writing the file for this simple assignment we do not stop the program, but in a real application we would log the error or show a message to the user or this simple assignment we do not stop the program, but in a real application we would log the error or show a message to the user
        }
    }

    // escape simple JSON characters
    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }
}
