package org.example.model;

import org.example.Validation.MatcheException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

public class Polynomial {
    private List<Monomial> monomialList;

    public List<Monomial> getMonomialList() {
        return monomialList;
    }

    public Polynomial(List<Monomial> monomialList) {
        this.monomialList = monomialList;
    }

    public Polynomial(String polynomial) throws MatcheException {
        if (!Objects.equals(polynomial, " ")) {
            monomialList = new ArrayList<>();
            List<MonomSplit> monomSplits = splitPolynomial(polynomial);
            for (MonomSplit monomSplit : monomSplits) {
                monomialList.add(new Monomial(monomSplit.getMonomial(), monomSplit.getX()));
            }
        }
    }

    public void test(String match) throws MatcheException {
        Pattern pattern = Pattern.compile("((\\d*?)[*]{0,1}X{0,1}(\\^{0,1}\\d*?)[\\+-]{0,1}){1,10000}");
        Matcher matcher = pattern.matcher(match);
        if (!matcher.matches()) {
            throw new MatcheException("Format gresit");
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

    public Polynomial multiplicate(Polynomial secondPolynomial){
        List<Monomial> multipicatedList = new ArrayList<>();
      for( Monomial firstMonomial : monomialList) {
          for (Monomial secondMonomial : secondPolynomial.monomialList) {
              Monomial monomial = new Monomial(firstMonomial.getExp() + secondMonomial.getExp(), firstMonomial.getCoef() * secondMonomial.getCoef());
              multipicatedList.add(monomial);
          }
          System.out.println(toString());
      }
      monomialList = compressPolynomial(multipicatedList);
       monomialList.sort(new Monomial.SortByExp());
      return this;
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

            if (z != 0 && z != 1) {
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
                i++;
            }
        }
        return monomialList;
    }
}
