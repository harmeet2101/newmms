package com.mbopartners.mbomobile.ui.util;

import java.text.DecimalFormat;

public class NumberFormatUtils {


    public static DecimalFormat decimalFormat;


    public static String getAmountWithCommas(String value){

        decimalFormat=new DecimalFormat("###,###.###");
        String temp=decimalFormat.format(Double.parseDouble(value));

        return TwoDecimalPlacesUtil.getAmount_uptoTwoDecimalPlaces(temp);
    }
}
