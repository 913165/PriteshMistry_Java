
package com.abcbank.gl;

public class Transaction {
    private int id;
    private String instrumentSymbol;
    private Type type;
    public enum Type {
        B("Buy", 1),
        S("Sell", -1),
        ;
        private String name;
        private int sign;
        private Type(String name, int sign) {
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
    private long quantity;
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getInstrumentSymbol() {
        return this.instrumentSymbol;
    }
    public void setInstrumentSymbol(String is) {
        this.instrumentSymbol = is;
    }
    public Type getType() {
        return this.type;
    }
    public void setType(Type t) {
        this.type = t;
    }
    public int getSign() {
        return getType().getSign();
    }
    public long getQuantity() {
        return this.quantity;
    }
    public void setQuantity(long qty) {
        this.quantity = qty;
    }
    public String toString() {
        return "Transaction :"+this.getId()+", "+this.getInstrumentSymbol()+", "+this.getType().getName()+", "+this.getQuantity();
    }
}

