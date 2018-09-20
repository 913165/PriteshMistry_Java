
package com.abcbank.gl;

public class EndOfDayPosition {
    private Position position;
    private long delta;
    public Position getPosition() {
        return this.position;
    }
    public void setPosition(Position p) {
        this.position = p;
    }
    public String getInstrumentSymbol() {
        return this.getPosition().getInstrumentSymbol();
    }
    public void applyTransaction(Transaction t) {
        delta += this.position.applyTransaction(t);
    }
    public long getDelta() {
        return this.delta;
    }
    public String toString() {
        return this.position.toString()+", "+this.delta;
    }
    public boolean equals(Object obj) {
        return this.position.equals(obj);
    }
    public int hashCode() {
        return this.position.hashCode();
    }
    public String toCSV() {
        return this.position.toCSV()+","+delta;
    }
}

