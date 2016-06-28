package com.mbopartners.mbomobile.ui.activity.helper;

import java.io.Serializable;
import java.util.Date;

public class TimePeriodParameters implements Serializable{
    public final String mboWorkOrderId;
    public final String mboTimePeriodId;
    public final Date chosenDate;

    public TimePeriodParameters(String mboWorkOrderId, String mboTimePeriodId, Date chosenDate) {
        this.mboWorkOrderId = mboWorkOrderId;
        this.mboTimePeriodId = mboTimePeriodId;
        this.chosenDate = chosenDate;
    }
}
