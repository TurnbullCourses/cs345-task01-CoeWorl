package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @throws InsufficientFundsException if amount is negative or greater than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        if (email.equals("")){
            return false;
        }
        int atChar = email.indexOf('@');
        if (atChar == -1){
            return false;
        }
        /*if (email.substring(0,0).equals(".") || email.substring(0,0).equals("-") || email.substring(0,0).equals("_")){
            return false;
        }
        for (int i=1; i<atChar; i++){
            if (!Character.isLetterOrDigit(email.charAt(i-1)) && email.charAt(i) != '.' && email.charAt(i) != '-' && email.charAt(i) != '_'){
                return false;
            }
        }
        for (int i = atChar + 1; i<email.length(); i++){
            if (!Character.isLetterOrDigit(email.charAt(i-1)) && email.charAt(i) != '.' && email.charAt(i) != '-' && email.charAt(i) != '_'){
                return false;
            }
        }
        for (int i = 1; i <email.length(); i++){
            if (!(Character.isLetterOrDigit(email.charAt(i)) || email.charAt(i) == '.' || email.charAt(i) == '-' || (email.charAt(i) == '_' && i < atChar) || (email.charAt(i) == '@' && i == atChar) || (email.charAt(i) == '.' && (email.length()-i >= 2)))){
                return false;
            }
        }*/
        return true;
    }
    }