package com.mbopartners.mbomobile.ui.util;

import java.text.DecimalFormat;

public class MboFormatter {
    public static class Hours {
        public static String formatHours(Double hours){
            DecimalFormat df = new DecimalFormat("#0.00");
            return df.format(hours);
        }

        public static String formatHours01(Double hours){
            DecimalFormat df = new DecimalFormat("#0.00");
            double v=hours.doubleValue();
            int i=(int)v;
            return String.valueOf(i);
            //return df.format(hours);
        }
    }


    public static class Money {
        public static String formatMoney(Double hours){
            DecimalFormat df = new DecimalFormat("#0.00");
            return df.format(hours);
        }

        public static String formatMoney01(Double hours){
            double v=hours.doubleValue();
            int i=(int)v;
            return String.valueOf(i);
        }
    }
}
