
package com.abcbank.gl;

import java.util.Scanner;

public class Position {
    private String instrumentSymbol;
    private int ledgerNumber;
    private AccountType accountType;
    private long quantity;

    public String getInstrumentSymbol() {
        return this.instrumentSymbol;
    }
    public int getLedgerNumber() {
        return this.ledgerNumber;
    }
    public AccountType getAccountType() {
        return this.accountType;
    }
    public long getQuantity() {
        return this.quantity;
    }
    public String toString() {
        return "Position:"+this.getInstrumentSymbol()+", "+this.getLedgerNumber()+", "+this.getAccountType().getName()+", "+this.getQuantity();
    }
    public String toCSV() {
        return this.getInstrumentSymbol()+","+this.getLedgerNumber()+","+this.getAccountType()+","+this.getQuantity();
    }
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Position p = (Position)obj;
        return this.instrumentSymbol.equals(p.instrumentSymbol) && this.ledgerNumber == p.ledgerNumber;
    }
    public int hashCode() {
        return (this.instrumentSymbol+this.ledgerNumber).hashCode();
    }
    public long applyTransaction(Transaction t) {
        long netQuantity = getAccountType().getSign() * t.getSign() * t.getQuantity();
        this.quantity += netQuantity;
        return netQuantity;
    }
    public static Position parsePosition(String s) {
        Scanner scanner = new Scanner(s).useDelimiter(",");
        Position position = new Position();
        position.instrumentSymbol = scanner.next();
        position.ledgerNumber = scanner.nextInt();
        String accountTypeStr = scanner.next().toUpperCase();
        AccountType actType = AccountType.valueOf(accountTypeStr);
        position.accountType = actType;
        position.quantity = scanner.nextLong();
        return position;
    }

    public enum AccountType {
        E("External", 1),
        I("Internal", -1),
        ;
        private String name;
        private int sign;
        private AccountType(String name, int sign) {
            this.name = name;
            this.sign = sign;
        }
        public String getName() {
            return this.name;
        }
        public int getSign() {
            return this.sign;
        }
    }
}


