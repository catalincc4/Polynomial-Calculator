package org.example.Model;

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
            List<MonomSplit> monomSplits = splitPolynomial(polynomial);
            for (MonomSplit monomSplit : monomSplits) {
                monomialList.add(new Monomial(monomSplit.getMonomial(), monomSplit.getX()));
            }
        }
    }


    public List<MonomSplit> splitPolynomial(String polynomial) {
        int i = 0;
        int s = 1;
        List<MonomSplit> monomialList = new ArrayList<>();
        String a = "";
        while (i < polynomial.length()) {
            if (((polynomial.charAt(i) == '-' || polynomial.charAt(i) == '+') && i > 0)) {
                monomialList.add(new MonomSplit(a, s));
                a = "";
            } else if (polynomial.charAt(i) != '-')
                a += polynomial.charAt(i);

            if (i == polynomial.length() - 1) {
                monomialList.add(new MonomSplit(a, s));
            }
            if (polynomial.charAt(i) == '-') {
                s = -1;
            } else if (polynomial.charAt(i) == '+')
                s = 1;

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
                if (firstMonomial.getExp() == (secondMonomial.getExp())) {
                    firstMonomial.setCoef(firstMonomial.getCoef() + secondMonomial.getCoef());
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
                if (firstMonomial.getExp() == (secondMonomial.getExp()))
                    flag = 1;
            }
            if (flag == 0) {
                resultPolynomial.monomialList.add(secondMonomial);
            }
        }
        resultPolynomial.monomialList.sort(new Monomial.SortByExp());
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
                if (firstMonomial.getExp() == (secondMonomial.getExp())) {
                    firstMonomial.setCoef(firstMonomial.getCoef() - secondMonomial.getCoef());
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
                if (firstMonomial.getExp() == (secondMonomial.getExp()))
                    flag = 1;
            }
            if (flag == 0) {
                secondMonomial.setCoef(-secondMonomial.getCoef());
                resultPolynomial.monomialList.add(secondMonomial);
            }
        }
        resultPolynomial.monomialList.sort(new Monomial.SortByExp());
        resultPolynomial.monomialList = compressPolynomial(resultPolynomial.monomialList);
        return resultPolynomial;
    }

    public void derivation() {
        monomialList = compressPolynomial(monomialList);
        int i = 0;
        while (i < monomialList.size()) {
            float n = monomialList.get(i).getExp();
            float m = monomialList.get(i).getCoef();
            monomialList.get(i).setCoef(n * m);
            monomialList.get(i).setExp(n - 1);
            i++;
        }
        monomialList.sort(new Monomial.SortByExp());
    }

    public void integration() {
        monomialList = compressPolynomial(monomialList);
        int i = 0;
        while (i < monomialList.size()) {
            float n = monomialList.get(i).getExp();
            float m = monomialList.get(i).getCoef();
            monomialList.get(i).setCoef(m / (n + 1));
            monomialList.get(i).setExp(n + 1);
            i++;
        }
        monomialList.sort(new Monomial.SortByExp());
    }

    public Polynomial multiplicate(Polynomial secondPolynomial) {
        List<Monomial> multipicatedList = new ArrayList<>();
        for (Monomial firstMonomial : monomialList) {
            for (Monomial secondMonomial : secondPolynomial.monomialList) {
                Monomial monomial = new Monomial(firstMonomial.getExp() + secondMonomial.getExp(), firstMonomial.getCoef() * secondMonomial.getCoef());
                multipicatedList.add(monomial);
            }
        }
        monomialList = compressPolynomial(multipicatedList);
        monomialList.sort(new Monomial.SortByExp());
        return this;
    }

    public List<Polynomial> divide(Polynomial secondPolynomial) throws ArithmeticallyException {
        List<Polynomial> polynomialList = new ArrayList<>();
        secondPolynomial.monomialList.sort(new Monomial.SortByExp());
        secondPolynomial.monomialList = compressPolynomial(secondPolynomial.monomialList);
        if (secondPolynomial.monomialList.size() == 0 || secondPolynomial.monomialList.get(0).getCoef() == 0) {
            throw new ArithmeticallyException("Impartitorul trebuie sa fie diferit de 0");
        } else {
            monomialList.sort(new Monomial.SortByExp());
            Polynomial quotient = new Polynomial(new ArrayList<>());
            Polynomial remainder = this;
            float secondPolynomialExp = secondPolynomial.monomialList.get(0).getExp();
            float secondPolynomialCoef = secondPolynomial.monomialList.get(0).getCoef();
            while (remainder.monomialList.get(0).getCoef() != 0 && remainder.monomialList.get(0).getExp() >= secondPolynomial.monomialList.get(0).getExp()) {
                float remainderExp = remainder.monomialList.get(0).getExp();
                float remainderCoef = remainder.monomialList.get(0).getCoef();
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
        int n = monomialList.size();

        for (Monomial monomial : monomialList) {
            float x = monomial.getExp();
            float z = monomial.getCoef();

            if (i > 0 && i < n && z > 0) {
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
                    if (monomial1.getExp() == monomial.getExp() && !monomial.equals(monomial1)) {
                        monomialList.get(i).setCoef(monomial1.getCoef() + monomial.getCoef());
                        monomialList.remove(j);
                    } else
                        j++;

                }
                if (monomialList.get(i).getCoef() == 0)

                    monomialList.remove(i);
                else
                    i++;
            }
        }

        return monomialList;
    }
}
