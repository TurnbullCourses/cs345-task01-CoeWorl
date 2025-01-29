package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
        assertTrue(BankAccount.isAmountValid(bankAccount.getBalance()));

    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);
        assertTrue(BankAccount.isAmountValid(bankAccount.getBalance()));

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-100));
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertFalse( BankAccount.isEmailValid(""));         // empty string
        assertFalse( BankAccount.isEmailValid("ab.com"));   // missing @
        assertFalse( BankAccount.isEmailValid("a@b"));      // missing .com
        assertFalse( BankAccount.isEmailValid(".a@b.com")); // starts with .
        assertFalse( BankAccount.isEmailValid("a#b@c.com")); // invalid character #
        assertFalse(BankAccount.isEmailValid("a..b@c.com")); // double .
        assertTrue(BankAccount.isEmailValid("a-@b.com")); // invalid character - //this isn't actually invalid

        
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertTrue(BankAccount.isAmountValid(bankAccount.getBalance()));

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 0.0001));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 0));
    }

    @Test
    void isAmountValidTest(){
        assertTrue(BankAccount.isAmountValid(100)); //valid amount
        assertFalse(BankAccount.isAmountValid(-100)); //negative amount
        assertTrue(BankAccount.isAmountValid(0)); //zero amount
        assertTrue(BankAccount.isAmountValid(0.01)); //valid amount
        assertFalse(BankAccount.isAmountValid(0.0001)); //more than 2 decimals
        assertFalse(BankAccount.isAmountValid(-0.01)); //negative amount
    }

    @Test
    void depositTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.deposit(100);
        assertEquals(300, bankAccount.getBalance(), 0.001);
        bankAccount.deposit(0);
        assertEquals(300, bankAccount.getBalance(), 0.001);
        bankAccount.deposit(0.01);
        assertEquals(300.01, bankAccount.getBalance(), 0.001);

        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(0.0001));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-100));
    }

    @Test
    void transferTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 500);
        BankAccount other = new BankAccount("c@d.com", 500);

        bankAccount.transfer(other, bankAccount, 0);
        assertEquals(500, bankAccount.getBalance(), 0.001);
        bankAccount.transfer(other, bankAccount, 100);
        assertEquals(600, bankAccount.getBalance(), 0.001);
        bankAccount.transfer(other, bankAccount, 0.01);
        assertEquals(600.01, bankAccount.getBalance(), 0.001);

        assertThrows(InsufficientFundsException.class, () -> bankAccount.transfer(other, bankAccount, 700));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(other, bankAccount, -100));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(other, bankAccount, 0.0001));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(bankAccount, bankAccount, 100));
    }
}