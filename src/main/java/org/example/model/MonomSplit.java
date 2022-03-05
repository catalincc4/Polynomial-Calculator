package org.example.model;

public class MonomSplit {
    private String monomial;
    private int x;

    public MonomSplit(String monomial, int x) {
        this.monomial = monomial;
        this.x = x;
    }

    public String getMonomial() {
        return monomial;
    }

    public void setMonomial(String monomial) {
        this.monomial = monomial;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

}
