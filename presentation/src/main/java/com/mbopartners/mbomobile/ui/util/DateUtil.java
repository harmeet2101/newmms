package com.mbopartners.mbomobile.ui.util;

import com.mbopartners.mbomobile.ui.activity.dashboard.DashboardActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 */
public class DateUtil {
    public static final int DAYS_IN_FOUR_WEEKS = 27;

    android.text.format.DateUtils dateUtils;

    // http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html

    /**
     * Example : "01/12/2015"
     */
    private static final String FORMAT_PERIOD__DD_MM_YYYY = "%1$td/%1$tm/%1$tY - %2$td/%2$tm/%2$tY";

    /**
     * Example : "Jul 31,2015" or "Aug 1,2015"
     */
    private static final String FORMAT_PERIOD__Mon_DD_YYYY = "%1$tb %1$te, %1$tY - %2$tb %2$te, %2$tY";


    private static final String FORMAT_DATE_Mon_DD_YYYY="%1$tb %1$te, %1$tY";

    /**
     * Example : "Jul 31" or "Aug 1"
     */
    private static final String FORMAT_PERIOD__Mon_DD = "%1$tb %1$te - %2$tb %2$te";

    /**
     * Example : "Jul 31" or "Aug 1"
     */
    private static final String FORMAT_DATE__Mon_DD = "%1$tb %1$te";
    private static final String FORMAT_DATE_MM__Mon_DD = "%1$tA, %1$tb %1$te";
    private static final String FORMAT_DATE__DD_MM_YYYY = "%1$td/%1$tm/%1$tY";

    private static final String FORMAT_DATE__YYYY_MM_DD = "%1$tY-%1$tm-%1$td";
    private static final String FORMAT_DATE__MM_DD_YYYY = "%1$tm-%1$td-%1$tY";
    private static final String FORMAT_DATE__MM_DD_YYYY_01 = "%1$tm/%1$td/%1$tY";

    private static final String SIMPLE_DATE_FORMAT_DATE__YYYY_MM_DD = "yyyy-MM-dd";

    public static Date getCurrentDate() {
        Calendar rightNow = getCurrentCalendar();
        rightNow.set(Calendar.HOUR_OF_DAY, 0);
        rightNow.set(Calendar.MINUTE, 0);
        rightNow.set(Calendar.SECOND, 0);
        rightNow.set(Calendar.MILLISECOND, 0);
        return rightNow.getTime();
    }
    public static Date getCurrentDateForExpenseSort() {
        Calendar rightNow = getCurrentCalendar();
        return rightNow.getTime();
    }

    public static Calendar getCurrentCalendar() {
//        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        TimeZone timeZone = TimeZone.getDefault();
        return Calendar.getInstance(timeZone);
//        return Calendar.getInstance();
    }

    public static String getFullFormattedPeriodAsString(Calendar beginDate, Calendar endDate) {
        return String.format(FORMAT_PERIOD__Mon_DD_YYYY, beginDate, endDate);
    }

    public static String getFullFormattedPeriodAsString(Date beginDate, Date endDate) {
        return String.format(FORMAT_PERIOD__Mon_DD_YYYY, beginDate, endDate);
    }

    public static String getFullFormattedAsString(Date beginDate) {
        return String.format(FORMAT_DATE_Mon_DD_YYYY, beginDate);
    }

    public static String getShortFormattedPeriodAsString(Calendar beginDate, Calendar endDate) {
        return String.format(FORMAT_PERIOD__Mon_DD, beginDate, endDate);
    }

    public static String getShortFormattedPeriodAsString(Date beginDate, Date endDate) {
        return String.format(FORMAT_PERIOD__Mon_DD, beginDate, endDate);
    }

    public static String getDayOfWeek(Date date) {
        return String.format("%1$ta", date);
    }

    public static String getShortDate(Date date) {
        return String.format(FORMAT_DATE__Mon_DD, date);
    }
    public static String getShortDate01(Date date) {
        return String.format(FORMAT_DATE_MM__Mon_DD, date);
    }

    public static Date parseFrom_yyyymmdd(String dateInputString) {
        Date date = null;
        if (dateInputString != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(SIMPLE_DATE_FORMAT_DATE__YYYY_MM_DD);
            try {
                date = formatter.parse(dateInputString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public static String parseFrom_yyyymmdd01(String dateInputString) {
        Date date = null;
        String formatedDate=null;
        if (dateInputString != null) {
            Locale locale=new Locale(DashboardActivity.SYSTEM_LOCALE);
            SimpleDateFormat formatter = new SimpleDateFormat(SIMPLE_DATE_FORMAT_DATE__YYYY_MM_DD);
            try {
                date = formatter.parse(dateInputString);
                DateFormat formatter01 = DateFormat.getDateInstance(DateFormat.SHORT, locale);
                String pattern       = ((SimpleDateFormat)formatter01).toPattern();
                if(pattern.equals("M/d/yy"))
                {
                    //formatedDate=String.format(FORMAT_DATE__MM_DD_YYYY_01,date);
                    formatedDate=String.format(FORMAT_DATE_Mon_DD_YYYY,date);
                }else
                {
                    //formatedDate=String.format(FORMAT_DATE__DD_MM_YYYY,date);
                    formatedDate=String.format(FORMAT_DATE_Mon_DD_YYYY,date);
                }
                //Log.d("str",pattern+" : "+locale.getDisplayLanguage()+" : "+formatedDate);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return formatedDate;
    }

    public static String getDateFormatted_yyyymmdd(Date date) {
        return String.format(FORMAT_DATE__YYYY_MM_DD, date);
    }

    public static String getDateFormatted_mmddyyyy(Date date) {
        return String.format(FORMAT_DATE__MM_DD_YYYY, date);
    }
    public static String getDateFormatted_mmddyyyy_01(Date date) {
        String formatedDate=null;
        Locale locale=new Locale(DashboardActivity.SYSTEM_LOCALE);
        DateFormat formatter01 = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        String pattern       = ((SimpleDateFormat)formatter01).toPattern();
        if(pattern.equals("M/d/yy"))
        {
            formatedDate=String.format(FORMAT_DATE__MM_DD_YYYY_01,date);
        }else
        {
            formatedDate=String.format(FORMAT_DATE__DD_MM_YYYY,date);
        }

        return formatedDate;
    }

    public static String getDateFormatted_payroll(Date date)
    {
        return String.format(FORMAT_DATE__DD_MM_YYYY,date);
    }

    public static long daysBetween(Date d1, Date d2)
    {
        return ( (d2.getTime() - d1.getTime()) / (1000L * 60L * 60L * 24L));
    }

    public static Date addDays(Date date, int number) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, number);
        return calendar.getTime();
    }

    public static boolean isDateInsidePeriod(Date dateInside, Date periodStart, Date periodEnd) {

        Calendar dateInsideCalendar = Calendar.getInstance();
        Calendar periodStartCalendar = Calendar.getInstance();
        Calendar periodEndCalendar = Calendar.getInstance();

        dateInsideCalendar.setTime(dateInside);
        periodStartCalendar.setTime(periodStart);
        periodEndCalendar.setTime(periodEnd);

        return (periodStartCalendar.compareTo(dateInsideCalendar) <= 0 && periodEndCalendar.compareTo(dateInsideCalendar) >=0 );
    }

    public static String get4weekPeriod() {
        Calendar beginDate = new GregorianCalendar();
        Calendar endDate = new GregorianCalendar();
        beginDate.add(Calendar.DAY_OF_MONTH, (-DAYS_IN_FOUR_WEEKS));
        return getFullFormattedPeriodAsString(beginDate, endDate);
    }

}
