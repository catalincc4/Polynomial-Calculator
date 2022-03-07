package org.example.Model;

import org.example.Controller.Controller;
import org.example.Exception.ArithmeticallyException;
import org.example.Exception.FormatException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

public class Polynomial {

    private List<Monomial> monomialList;

    public Polynomial(List<Monomial> monomialList) {
        this.monomialList = monomialList;
    }

    public Polynomial(String polynomial) throws FormatException {
        if (!Objects.equals(polynomial, " ")) {
            monomialList = new ArrayList<>();
            List<MonomialSplit> monomSplits = splitPolynomial(polynomial);
            for (MonomialSplit monomSplit : monomSplits) {
                monomialList.add(new Monomial(monomSplit.getMonomial(), monomSplit.getTermSign()));
            }
        }
    }


    public List<MonomialSplit> splitPolynomial(String polynomial) {
        int i = 0;
        int termSign = 1;
        List<MonomialSplit> monomialList = new ArrayList<>();
        String a = "";
        while (i < polynomial.length()) {
            if (((polynomial.charAt(i) == '-' || polynomial.charAt(i) == '+') && i > 0)) {
                monomialList.add(new MonomialSplit(a, termSign));
                a = "";
            } else if (polynomial.charAt(i) != '-')
                a += polynomial.charAt(i);

            if (i == polynomial.length() - 1) {
                monomialList.add(new MonomialSplit(a, termSign));
            }
            if (polynomial.charAt(i) == '-') {
                termSign = -1;
            } else if (polynomial.charAt(i) == '+')
                termSign = 1;

            i++;
        }
        return monomialList;
    }

    public Polynomial add(Polynomial secondPolynomial) {
        Polynomial resultPolynomial = new Polynomial(new ArrayList<>());
        monomialList = compressPolynomial(monomialList);
        secondPolynomial.monomialList = compressPolynomial(secondPolynomial.monomialList);
        for (Monomial firstMonomial : monomialList) {
            int flag = 0;
            for (Monomial secondMonomial : secondPolynomial.monomialList) {
                if (firstMonomial.getExponent() == (secondMonomial.getExponent())) {
                    firstMonomial.setCoefficient(firstMonomial.getCoefficient() + secondMonomial.getCoefficient());
                    flag = 1;
                    resultPolynomial.monomialList.add(firstMonomial);
                }
            }
            if (flag == 0) {
                resultPolynomial.monomialList.add(firstMonomial);
            }
        }
        for (Monomial secondMonomial : secondPolynomial.monomialList) {
            int flag = 0;
            for (Monomial firstMonomial : monomialList) {
                if (firstMonomial.getExponent() == (secondMonomial.getExponent()))
                    flag = 1;
            }
            if (flag == 0) {
                resultPolynomial.monomialList.add(secondMonomial);
            }
        }
        resultPolynomial.monomialList.sort(new Monomial.SortByExponent());
        resultPolynomial.monomialList = compressPolynomial(resultPolynomial.monomialList);
        return resultPolynomial;
    }

    public Polynomial subtract(Polynomial secondPolynomial) {
        Polynomial resultPolynomial = new Polynomial(new ArrayList<>());
        monomialList = compressPolynomial(monomialList);
        secondPolynomial.monomialList = compressPolynomial(secondPolynomial.monomialList);
        for (Monomial firstMonomial : monomialList) {
            int flag = 0;
            for (Monomial secondMonomial : secondPolynomial.monomialList) {
                if (firstMonomial.getExponent() == (secondMonomial.getExponent())) {
                    firstMonomial.setCoefficient(firstMonomial.getCoefficient() - secondMonomial.getCoefficient());
                    flag = 1;
                    resultPolynomial.monomialList.add(firstMonomial);
                }
            }
            if (flag == 0) {
                resultPolynomial.monomialList.add(firstMonomial);
            }
        }
        for (Monomial secondMonomial : secondPolynomial.monomialList) {
            int flag = 0;
            for (Monomial firstMonomial : monomialList) {
                if (firstMonomial.getExponent() == (secondMonomial.getExponent()))
                    flag = 1;
            }
            if (flag == 0) {
                secondMonomial.setCoefficient(-secondMonomial.getCoefficient());
                resultPolynomial.monomialList.add(secondMonomial);
            }
        }
        resultPolynomial.monomialList.sort(new Monomial.SortByExponent());
        resultPolynomial.monomialList = compressPolynomial(resultPolynomial.monomialList);
        return resultPolynomial;
    }

    public void derivation() {
        monomialList = compressPolynomial(monomialList);
        int i = 0;
        while (i < monomialList.size()) {
            float n = monomialList.get(i).getExponent();
            float m = monomialList.get(i).getCoefficient();
            monomialList.get(i).setCoefficient(n * m);
            monomialList.get(i).setExponent(n - 1);
            i++;
        }
        monomialList.sort(new Monomial.SortByExponent());
    }

    public void integration() {
        monomialList = compressPolynomial(monomialList);
        int i = 0;
        while (i < monomialList.size()) {
            float n = monomialList.get(i).getExponent();
            float m = monomialList.get(i).getCoefficient();
            monomialList.get(i).setCoefficient(m / (n + 1));
            monomialList.get(i).setExponent(n + 1);
            i++;
        }
        monomialList.sort(new Monomial.SortByExponent());
    }

    public Polynomial multiplicate(Polynomial secondPolynomial) {
        List<Monomial> multipicatedList = new ArrayList<>();
        for (Monomial firstMonomial : monomialList) {
            for (Monomial secondMonomial : secondPolynomial.monomialList) {
                Monomial monomial = new Monomial(firstMonomial.getExponent() + secondMonomial.getExponent(), firstMonomial.getCoefficient() * secondMonomial.getCoefficient());
                multipicatedList.add(monomial);
            }
        }
        monomialList = compressPolynomial(multipicatedList);
        monomialList.sort(new Monomial.SortByExponent());
        return this;
    }

    public List<Polynomial> divide(Polynomial secondPolynomial) throws ArithmeticallyException, FormatException {
        List<Polynomial> polynomialList = new ArrayList<>();
        secondPolynomial.monomialList.sort(new Monomial.SortByExponent());
        secondPolynomial.monomialList = compressPolynomial(secondPolynomial.monomialList);
        if (secondPolynomial.monomialList.size() == 0 || secondPolynomial.monomialList.get(0).getCoefficient() == 0) {
            throw new ArithmeticallyException("Impartitorul trebuie sa fie diferit de 0");
        } else {
            monomialList.sort(new Monomial.SortByExponent());
            Polynomial quotient = new Polynomial("0");
            Polynomial remainder = this;
            float secondPolynomialExp = secondPolynomial.monomialList.get(0).getExponent();
            float secondPolynomialCoef = secondPolynomial.monomialList.get(0).getCoefficient();
            while (remainder.monomialList.get(0).getCoefficient() != 0 && remainder.monomialList.get(0).getExponent() >= secondPolynomial.monomialList.get(0).getExponent()) {
                float remainderExp = remainder.monomialList.get(0).getExponent();
                float remainderCoef = remainder.monomialList.get(0).getCoefficient();
                Polynomial t = new Polynomial(new ArrayList<>());
                t.monomialList.add(new Monomial(remainderExp - secondPolynomialExp, remainderCoef / secondPolynomialCoef));
                quotient = quotient.add(t);
                remainder = remainder.subtract(t.multiplicate(secondPolynomial));
            }
            polynomialList.add(quotient);
            polynomialList.add(remainder);
        }
        return polynomialList;
    }

    public String toString() {
        String polynomialString = "";
        int i = 0;
        int listSize = monomialList.size();

        for (Monomial monomial : monomialList) {
            float x = monomial.getExponent();
            float z = monomial.getCoefficient();

            if (i > 0 && i < listSize && z > 0) {
                polynomialString += "+";
            }

            if (z != 0 && z != 1 && x > 0) {
                if (z - (int) z > 0.0001 || (int) z - z > 0.0001)
                    polynomialString += format("%.2f", z);
                else if (z == -1)
                    polynomialString += "-";
                else
                    polynomialString += (int) z;
            }
            if (x > 1 && z != 0) {
                polynomialString += "X^" + (int) x;
            } else if (monomialList.size() == 1 && z == 0)
                polynomialString += 0;

            if (x == 1 && z != 0) {
                polynomialString += "X";
            }
            if (x == 0 && z != 0) {
                if (z - (int) z > 0.0001 || (int) z - z > 0.0001)
                    polynomialString += format("%.2f", z);
                else
                    polynomialString += (int) z;
            }
            i++;
        }
        return polynomialString;
    }

    public List<Monomial> compressPolynomial(List<Monomial> monomialList) {
        int i = 0;
        if (monomialList.size() > 1) {
            while (i < monomialList.size()) {
                Monomial monomial1 = monomialList.get(i);
                int j = 0;
                while (j < monomialList.size()) {
                    Monomial monomial = monomialList.get(j);
                    if (monomial1.getExponent() == monomial.getExponent() && !monomial.equals(monomial1)) {
                        monomialList.get(i).setCoefficient(monomial1.getCoefficient() + monomial.getCoefficient());
                        monomialList.remove(j);
                    } else
                        j++;

                }
                if (monomialList.get(i).getCoefficient() == 0)

                    monomialList.remove(i);
                else
                    i++;
            }
        }

        return monomialList;
    }
}
