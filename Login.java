/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poepractice;

/**
 *
 * @author Student
 */
public class Login {
    private String name;
    private String username;
    private String password;
    private String cellNumber;

    
    private String registeredUsername;
    private String registeredPassword;
    private boolean registered = false;
    private boolean lastLoginSuccessful = false;

    public Login(String name, String username, String password) {
        this.name = name == null ? "" : name;
        this.username = username;
        this.password = password;
    }

    public Login() {
        this("", "", "");
    }

    
    public boolean checkUserName() {
        if (username == null) return false;
        return username.contains("_") && username.length() <= 5;
    }

    
    public boolean checkPasswordComplexity() {
        if (password == null) return false;
        return password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*")
                && password.matches(".*\\d.*")
                && password.matches(".*[^a-zA-Z0-9].*");
    }

    
    public boolean checkCellPhoneNumber() {
        if (cellNumber == null) return false;
        
        return cellNumber.matches("^\\+[0-9]{10,14}$");
    }

    
    public String registerUser() {
        if (!checkUserName()) {
            return "The username is incorrectly formatted.";
        }
        if (!checkPasswordComplexity()) {
            return "The password does not meet the complexity requirements.";
        }
        
        this.registeredUsername = this.username;
        this.registeredPassword = this.password;
        this.registered = true;
        return "User has been registered successfully.";
    }

    
    public boolean loginUser() {
        if (!registered) {
            lastLoginSuccessful = false;
            return false;
        }
        lastLoginSuccessful = registeredUsername.equals(this.username) && registeredPassword.equals(this.password);
        return lastLoginSuccessful;
    }

    
    public boolean loginUser(String usernameToCheck, String passwordToCheck) {
        if (!registered) {
            lastLoginSuccessful = false;
            return false;
        }
        lastLoginSuccessful = registeredUsername.equals(usernameToCheck) && registeredPassword.equals(passwordToCheck);
        return lastLoginSuccessful;
    }

    
    public String returnLoginStatus() {
        String[] parts = name.split("\\s+");
        String first = parts.length > 0 ? parts[0] : "";
        String last = parts.length > 1 ? parts[parts.length - 1] : "";
        if (lastLoginSuccessful) {
            if (last.isEmpty()) {
                return "Welcome " + first + " it is great to see you again.";
            }
            return "Welcome " + first + ", " + last + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }

    
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setCellNumber(String cellNumber) { this.cellNumber = cellNumber; }
    public void setName(String name) { this.name = name == null ? "" : name; }
    public boolean isRegistered() { return registered; }
    public boolean getLastLoginSuccessful() { return lastLoginSuccessful; }
}
