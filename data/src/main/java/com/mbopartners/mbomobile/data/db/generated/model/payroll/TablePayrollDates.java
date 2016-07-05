package com.mbopartners.mbomobile.data.db.generated.model.payroll;

import java.util.Date;

/**
 * Created by MboAdil on 5/7/16.
 */
public class TablePayrollDates {


    private Long id;
    /** Not-null value. */
    private Date date;


    public TablePayrollDates(){}

    public TablePayrollDates(Long id,Date date)
    {
        this.id=id;
        this.date=date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
