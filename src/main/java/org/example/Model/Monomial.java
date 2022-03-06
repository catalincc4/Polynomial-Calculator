package org.example.Model;


import org.example.Exception.FormatException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Monomial {
    private float exponent;
    private float coefficient;

    public Monomial(float exp, float coef) {
        this.exponent = exp;
        this.coefficient = coef;
    }

    static class SortByExponent implements Comparator<Monomial> {

        @Override
        public int compare(Monomial o1, Monomial o2) {
            return (int) (o2.exponent - o1.exponent);
        }
    }

    public float getExponent() {
        return exponent;
    }

    public void setExponent(float exponent) {
        this.exponent = exponent;
    }

    public float getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(float coefficient) {
        this.coefficient = coefficient;
    }

    Monomial(int exp, int coef) {
        this.exponent = exp;
        this.coefficient = coef;
    }

    public Monomial(String monomial, int termSign) throws FormatException {
        int n = monomialPatternChooser(monomial);
        if (n == -1)
            throw new FormatException("Format gresit");
        if (n == 0) {
            exponent = 0;
            coefficient = termSign * Integer.parseInt(monomial);
        } else if (n == 1) {
            exponent = 1;
            coefficient = termSign * Integer.parseInt(getCoefficentNumber(monomial));
        } else if (n == 2) {
            exponent = 1;
            coefficient = termSign * 1;
        } else if (n == 3) {
            exponent = Integer.parseInt(getExponentNumber(monomial));
            coefficient = termSign * 1;
        } else if (n == 4) {
            exponent = Integer.parseInt(getExponentNumber(monomial));
            coefficient = termSign * Integer.parseInt(getCoefficentNumber(monomial));
        } else if (n == 5) {
            exponent = Integer.parseInt(getExponentNumber(monomial));
            coefficient = termSign * Integer.parseInt(getCoefficentNumber(monomial));
        }
    }

    public int monomialPatternChooser(String monomial) {
        Pattern[] patterns = new Pattern[6];
        patterns[0] = Pattern.compile("\\+{0,1}(\\d{1,100})");
        patterns[1] = Pattern.compile("(\\d{1,100})X");
        patterns[2] = Pattern.compile("X");
        patterns[3] = Pattern.compile("X(\\^{1,1})(\\d{1,100})");
        patterns[4] = Pattern.compile("(\\d{1,100}X(\\^{1,1})(\\d{1,100}))");
        patterns[5] = Pattern.compile("(\\d{1,100})(\\*{1,100})X(\\^{1,1})(\\d{1,100})");
        int i = 0;
        while (i < 6) {
            Matcher matcher = patterns[i].matcher(monomial);
            if (matcher.matches())
                return i;
            i++;
        }
        return -1;
    }


    public String getCoefficentNumber(String monomial) {
        String stringCoefficent = new String();
        int i = 0;
        while (monomial.charAt(i) >= '0' && monomial.charAt(i) <= '9' || monomial.charAt(i) == '.')
            stringCoefficent += monomial.charAt(i++);
        return stringCoefficent;
    }


    public String getExponentNumber(String monomial) {
        String stringExponent = new String();
        int i = 0;
        int flag = 0;
        int n = monomial.length();
        while (i < n) {
            if (flag == 1)
                stringExponent += monomial.charAt(i++);
            else if (monomial.charAt(i++) == '^') {
                flag = 1;
            }
        }
        return stringExponent;
    }


}

