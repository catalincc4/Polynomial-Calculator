package org.example.Model;

import org.example.Exception.ArithmeticallyException;
import org.example.Exception.FormatException;
import org.example.Model.Polynomial;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
public class OperationTest {
   @Test
    public void addTest() throws FormatException{
            Polynomial firstPolynomial = new Polynomial("X^2+2X+1");
            Polynomial secondPolynomial = new Polynomial("2X+3");
            Polynomial resultPolynomila = new Polynomial("X^2+4X+4");
            assertTrue(firstPolynomial.add(secondPolynomial).toString().equals(resultPolynomila.toString()),
                    "The result of adding X^2+2X+1 and 2X+3 is X^2+4X+4");

    }
    @Test
    public void subtractTest() throws FormatException{
        Polynomial firstPolynomial = new Polynomial("X^2+2X+1");
        Polynomial secondPolynomial = new Polynomial("2X+3");
        Polynomial resultPolynomila = new Polynomial("X^2-2");
        assertTrue(firstPolynomial.subtract(secondPolynomial).toString().equals(resultPolynomila.toString()),
                "The result of subtracting X^2+2X+1 and 2X+3 is X^2-2");

    }
    @Test
    public void multiplicateTest() throws FormatException{
        Polynomial firstPolynomial = new Polynomial("X^2+2X+1");
        Polynomial secondPolynomial = new Polynomial("2X+3");
        Polynomial resultPolynomila = new Polynomial("2X^3+7X^2+8X+3");
        assertTrue(firstPolynomial.multiplicate(secondPolynomial).toString().equals(resultPolynomila.toString()),
                "The result of multiplicate X^2+2X+1 and 2X+3 is 2X^3+7X^2+8X+3");

    }
    @Test
    public void divideTest() throws FormatException, ArithmeticallyException{
        Polynomial firstPolynomial = new Polynomial("X^3-2X^2+3X-1");
        Polynomial secondPolynomial = new Polynomial("X+2");
        Polynomial quotient = new Polynomial("X^2-4X+11");
        Polynomial remainder = new Polynomial("-23");
        assertTrue(firstPolynomial.divide(secondPolynomial).get(0).toString().equals(quotient.toString()) &&
                        firstPolynomial.divide(secondPolynomial).get(1).toString().equals(remainder.toString()),
                "The result of divide X^3-2X^2+3X-1 and X+2 is Q=X^2-4X+11 and R=-23");

    }

    @Test
    public void derivationTest() throws FormatException{
        Polynomial firstPolynomial = new Polynomial("X^3-2X^2+3X-1");
        Polynomial resulPolynomial = new Polynomial("3X^2-4X+3");
        firstPolynomial.derivation();
        assertTrue(firstPolynomial.toString().equals(resulPolynomial.toString()),
                "The result of derivation X^3-2X^2+3X-1 is 3X^2-4X+3");

    }
    @Test
    public void integrationTest() throws FormatException{
        Polynomial firstPolynomial = new Polynomial("4X^3-3X^2+2X-1");
        Polynomial resulPolynomial = new Polynomial("X^4-X^3+X^2-X");
        firstPolynomial.integration();
        assertTrue(firstPolynomial.toString().equals(resulPolynomial.toString()),
                "The result of integration 4X^3-3X^2+2X-1 is X^4-X^3+X^2-X");

    }

}
