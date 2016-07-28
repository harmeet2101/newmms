package com.mbopartners.mbomobile.ui.util;

/**
 * Created by MboAdil on 28/7/16.
 */
public class TwoDecimalPlacesUtil {

    public static String getAmount_uptoTwoDecimalPlaces(String amount)
    {


        int dotPos = -1;

        for (int i = 0; i < amount.length(); i++) {
            char c = amount.charAt(i);
            if (c == '.') {
                dotPos = i;
            }
        }



        /*Making Money  get trailing values after the decimal*/

        if (dotPos == -1&& !amount.equals("")){
            amount=amount + ".00";
        }else if(dotPos == -1&& amount.equals(""))
            amount=amount + "0.00";
        else if(dotPos==amount.length()-1 && !amount.equals(""))
            amount="0"+amount + "00";
        else if(dotPos==amount.length()-2 && !amount.equals(""))
            amount=amount + "0";

        return amount;
    }
}
