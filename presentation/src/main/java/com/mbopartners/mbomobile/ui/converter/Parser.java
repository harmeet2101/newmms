package com.mbopartners.mbomobile.ui.converter;

/**
 *
 */
public class Parser {
    public  static Double safeParseDouble(String s) {
        Double value = null;
        try {
            value = Double.parseDouble(s.toString());
        } catch (Exception e) {
            value = 0.0d;
        }
        return value;
    }



}
