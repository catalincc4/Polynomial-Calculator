package org.example.model;


import org.example.Validation.MatcheException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Monomial {
    private float exp;
    private float coef;

    public Monomial(float exp, float coef) {
        this.exp = exp;
        this.coef = coef;
    }

    static class SortByExp implements Comparator<Monomial> {

        @Override
        public int compare(Monomial o1, Monomial o2) {
            return (int) (o2.exp - o1.exp);
        }
    }

    public float getExp() {
        return exp;
    }

    public void setExp(float exp) {
        this.exp = exp;
    }

    public float getCoef() {
        return coef;
    }

    public void setCoef(float coef) {
        this.coef = coef;
    }

    Monomial(int exp, int coef) {
        this.exp = exp;
        this.coef = coef;
    }

    public Monomial(String monom, int x) throws MatcheException {
        int n = monomialPatternChooser(monom);
        if (n == -1)
            throw new MatcheException("Format gresit");
        if (n == 0) {
            exp = 0;
            coef = x * Integer.parseInt(monom);
        } else if (n == 1) {
            exp = 1;
            coef = x * Integer.parseInt(getCoefNumber(monom));
        } else if (n == 2) {
            exp = 1;
            coef = x * 1;
        } else if (n == 3) {
            exp = Integer.parseInt(getExpNumber(monom));
            coef = x * 1;
        } else if (n == 4) {
            exp = Integer.parseInt(getExpNumber(monom));
            coef = x * Integer.parseInt(getCoefNumber(monom));
        } else if (n == 5) {
            exp = Integer.parseInt(getExpNumber(monom));
            coef = x * Integer.parseInt(getCoefNumber(monom));
        }
    }

    public int monomialPatternChooser(String monom) {
        Pattern[] patterns = new Pattern[6];
        patterns[0] = Pattern.compile("\\+{0,1}(\\d{1,100})");
        patterns[1] = Pattern.compile("(\\d{1,100})X");
        patterns[2] = Pattern.compile("X");
        patterns[3] = Pattern.compile("X(\\^{1,1})(\\d{1,100})");
        patterns[4] = Pattern.compile("(\\d{1,100})X(\\^{1,1})(\\d{1,100})");
        patterns[5] = Pattern.compile("(\\d{1,100})(\\*{1,100})X(\\^{1,1})(\\d{1,100})");
        int i = 0;
        while (i < 6) {
            Matcher matcher = patterns[i].matcher(monom);
            if (matcher.matches())
                return i;
            i++;
        }
        return -1;
    }


    public String getCoefNumber(String monom) {
        String stringCoef = new String();
        int i = 0;
        while (monom.charAt(i) >= '0' && monom.charAt(i) <= '9')
            stringCoef += monom.charAt(i++);
        return stringCoef;
    }


    public String getExpNumber(String monom) {
        String stringExp = new String();
        int i = 0;
        int flag = 0;
        int n = monom.length();
        while (i < n) {
            if (flag == 1)
                stringExp += monom.charAt(i++);
            else if (monom.charAt(i++) == '^') {
                flag = 1;
            }
        }
        return stringExp;
    }


}

