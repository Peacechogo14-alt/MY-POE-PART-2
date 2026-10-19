/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poepractice;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class PoePractice {
    
    public static void main(String[] args) {
    
    Scanner input = new Scanner(System.in);
    
    System.out.println("\nRegister");
    System.out.println("Please enter your name");
    System.out.print("Please enter your first name: ");
    String firstName = input.nextLine();

    System.out.print("Please enter your last name: ");
    String lastName = input.nextLine();

    String name = firstName + " " + lastName;

    System.out.print("Please enter your username: ");
    String username = input.nextLine();

    System.out.print("Please enter your password: ");
    String password = input.nextLine();
    Login obj = new Login(name, username, password);

    if(obj.checkUserName()){
        System.out.println("Username has been successfuly captured");
    }else{
        System.out.println("Username is incorrect");
    }

    if(obj.checkPasswordComplexity()){
        System.out.println("Password successfuly captured");
    }else{
        System.out.println("Password is incorrect");
    }

    System.out.print("Please enter your cell phone number (e.g. +27817123163): ");
    String cellNumber = input.nextLine();
    obj.setCellNumber(cellNumber);

    if(obj.checkCellPhoneNumber()){
        System.out.println("Cell number successfully captured.");
    } else {
        System.out.println("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
    }

    System.out.println("\nPlease login using your username and password");
    System.out.print("Username: ");
    String loginUsername = input.nextLine();
    System.out.print("Password: ");
    String loginPassword = input.nextLine();

    if(loginUsername.equals(username) && loginPassword.equals(password)){
        if(lastName.isEmpty()){
            // if no last name, show first name only
            System.out.println("Welcome " + firstName + " it is great to see you again.");
        } else {
            // show full name
            System.out.println("Welcome " + firstName + ", " + lastName + " it is great to see you again.");
        }
        MessageApp.start(input);
    } else {
        System.out.println("Username or password incorrect, please try again.");
    }
    }
}
