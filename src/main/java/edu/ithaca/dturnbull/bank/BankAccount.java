package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    /**
     * @throws IllegalArgumentException if balance is <= 0
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email) && isAmountValid(startingBalance)){
            if (isAmountValid(startingBalance)){
                if (startingBalance >= 0){
                this.email = email;
                this.balance = startingBalance;
                }
                else {
                    throw new IllegalArgumentException("Starting balance must be greater than 0");
                }
            }

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
        if (isAmountValid(amount)){
            if (amount < 0){
                throw new IllegalArgumentException("Cannot withdraw a negative amount");
            }
            else if (amount <= balance){
                balance -= amount;
            }
            else {
                throw new InsufficientFundsException("Not enough money");
            }
        }
    }


    public static boolean isEmailValid(String email){
        if (email.isEmpty()){
            return false;
        }
        boolean hasPeriod = false;
        int atChar = email.indexOf('@');
        if (atChar == -1){
            return false;
        }
        if (email.substring(0,1).equals(".") || email.substring(0,1).equals("-") || email.substring(0,1).equals("_")){
            return false;
        }
        for (int i=1; i<atChar; i++){
            if (!Character.isLetterOrDigit(email.charAt(i-1)) && email.charAt(i) != '.' && email.charAt(i) != '-' && email.charAt(i) != '_'){
                return false;
            }
        }
        for (int i = atChar + 2; i<email.length(); i++){
            if (!Character.isLetterOrDigit(email.charAt(i-1)) && (email.charAt(i) == '.' || email.charAt(i) == '-' || email.charAt(i) == '_')){
                return false;
            }
            if (email.charAt(i) == '.'){
                hasPeriod = true;
            }
        }
        if (!hasPeriod){
            return false;
        }

        for (int i = 1; i <email.length(); i++){
            if (!(Character.isLetterOrDigit(email.charAt(i)) || email.charAt(i) == '.' || email.charAt(i) == '-' || (email.charAt(i) == '_' && i < atChar) || (email.charAt(i) == '@' && i == atChar) || (email.charAt(i) == '.' && (email.length()-i >= 2)))){
                return false;
            }
        }
        return true;
    }

    /**
     * @post returns true if amount is non-negative
     * @throws IllegalArgumentException if amount is negative and decimals > 2
     */
    public static boolean isAmountValid(double amount){
        if (amount < 0){
            return false;
        }
        else if (amount % 1 != 0 && amount % 0.1 != 0 && amount % 0.01 != 0){
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * @post increases the balance by amount if amount is non-negative
     * @throws IllegalArgumentException if amount is negative
     */
    public void deposit(double amount){
        if (isAmountValid(amount)){
            if (amount < 0) {
                throw new IllegalArgumentException("Cannot deposit a negative amount");
            }
            else {
            balance += amount;
            }
        }
        else {
            throw new IllegalArgumentException("Amount must be valid");
        }
    }

    /**
         * @post transfers amount from from to to if from has enough money
         * @throws InsufficientFundsException if from does not have enough money 
         * 
         */
        public void transfer(BankAccount from, BankAccount to, double amount) throws InsufficientFundsException {
        if (from.getBalance() >= amount){
            from.withdraw(amount);
            to.deposit(amount);
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }
}
