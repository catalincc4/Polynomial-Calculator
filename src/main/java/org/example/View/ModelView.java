package org.example.View;

import org.example.Exception.FormatException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModelView {

    public void test(String match) throws FormatException {
        Pattern pattern = Pattern.compile("((\\d*?)[*]{0,1}X{0,1}(\\^{0,1}\\d*?)[\\+-]{0,1}){1,10000}");
        Matcher matcher = pattern.matcher(match);
        if (!matcher.matches()) {
            throw new FormatException("Format gresit");
        }
    }
}
