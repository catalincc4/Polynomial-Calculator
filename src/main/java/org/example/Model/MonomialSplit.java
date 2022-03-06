package org.example.Model;

public class MonomialSplit {
    private String monomial;
    private int termSign;

    public MonomialSplit(String monomial, int termSign) {
        this.monomial = monomial;
        this.termSign = termSign;
    }

    public String getMonomial() {
        return monomial;
    }

    public void setMonomial(String monomial) {
        this.monomial = monomial;
    }

    public int getTermSign() {
        return termSign;
    }

    public void setTermSign(int termSign) {
        this.termSign = termSign;
    }

}
