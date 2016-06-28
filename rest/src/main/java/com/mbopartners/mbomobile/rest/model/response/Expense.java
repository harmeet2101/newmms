package com.mbopartners.mbomobile.rest.model.response;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

public class Expense implements Serializable, Validatable {
    private static final String TAG = Expense.class.getSimpleName();
    public static final double VERSION = 1.0d;

    @SerializedName("id")
    private String mboId = null;
    @SerializedName("workOrderId")
    private String mboWorkOrderId = null;
    @SerializedName("description")
    private String description = null;
    @SerializedName("associateId")
    private String mboAssociateId = null;
    @SerializedName("expenseTypeId")
    private String mboExpenseTypeId = null;
    @SerializedName("amount")
    private Double amount = null;
    @SerializedName("editable")
    private Boolean editable = null;
    @SerializedName("billable")
    private Boolean billable = null;
    @SerializedName("version")
    private Double version = VERSION;
    @SerializedName("expenseData")
    private List<ExpenseData> expenseData = new ArrayList<ExpenseData>();
    @SerializedName("receipts")
    private List<Receipt> receipts = new ArrayList<Receipt>();
    private String lastChangedDate;

    @Override
    public boolean isValid() {
        boolean result =
                mboId != null &&
                //description != null &&
                mboAssociateId != null &&
                mboExpenseTypeId != null &&
                amount != null &&
                editable != null &&
                        (billable == true && mboWorkOrderId != null ||
                         billable == false) &&
//                version != null &&
                expenseData != null && ! expenseData.isEmpty() && ValidationHelper.validAll(expenseData) &&
                receipts != null && ValidationHelper.validAll(receipts);

        if (! result) {
            Log.e(TAG, "NOT VALID. id = " + mboId);
            ValidationHelper.Screamer screamer = new ValidationHelper.Screamer(TAG, "");
            screamer.sayIfIsNull("mboId", mboId);
            screamer.sayIfIsNull("mboAssociateId", mboAssociateId);
            screamer.sayIfIsNull("mboExpenseTypeId", mboExpenseTypeId);
            screamer.sayIfIsNull("amount", amount);
            screamer.sayIfIsNull("editable", editable);
            screamer.sayIfIsNull("lastChangedDate", lastChangedDate);
            screamer.sayIfIsFalse("(billable == true && mboWorkOrderId != null || billable == false)", (billable == true && mboWorkOrderId != null || billable == false));
            screamer.sayIfIsNull("expenseData", expenseData);
            screamer.sayIfIsTrue("expenseData is empty", expenseData.isEmpty());
            //todo   && ValidationHelper.validAll(expenseData) &&
            screamer.sayIfIsNull("receipts", receipts);
            //todo   && ValidationHelper.validAll(receipts);
        }
        return result;
    }

    public Expense(String mboIid, String mboWorkOrderId, String description, String mboAssociateId,
                   String mboExpenseTypeId, Double amount, Boolean editable, Boolean billable,
                   Double version, List<ExpenseData> expenseData, List<Receipt> receipts,String lastChangedDate) {
        this.mboId = mboIid;
        this.mboWorkOrderId = mboWorkOrderId;
        this.description = description;
        this.mboAssociateId = mboAssociateId;
        this.mboExpenseTypeId = mboExpenseTypeId;
        this.amount = amount;
        this.editable = editable;
        this.billable = billable;
        this.version = version;
        this.expenseData = expenseData;
        this.receipts = receipts;
        this.lastChangedDate=lastChangedDate;
    }

    /*public Expense(String mboIid, String mboWorkOrderId, String description, String mboAssociateId,
                   String mboExpenseTypeId, Double amount, Boolean editable, Boolean billable,
                   Double version, List<ExpenseData> expenseData, List<Receipt> receipts) {
        this.mboId = mboIid;
        this.mboWorkOrderId = mboWorkOrderId;
        this.description = description;
        this.mboAssociateId = mboAssociateId;
        this.mboExpenseTypeId = mboExpenseTypeId;
        this.amount = amount;
        this.editable = editable;
        this.billable = billable;
        this.version = version;
        this.expenseData = expenseData;
        this.receipts = receipts;
    }*/
    public String getMboId() {
        return mboId;
    }

    public void setMboId(String mboId) {
        this.mboId = mboId;
    }

    public String getMboWorkOrderId() {
        return mboWorkOrderId;
    }

    public void setMboWorkOrderId(String mboWorkOrderId) {
        this.mboWorkOrderId = mboWorkOrderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMboAssociateId() {
        return mboAssociateId;
    }

    public void setMboAssociateId(String mboAssociateId) {
        this.mboAssociateId = mboAssociateId;
    }

    public String getMboExpenseTypeId() {
        return mboExpenseTypeId;
    }

    public void setMboExpenseTypeId(String mboExpenseTypeId) {
        this.mboExpenseTypeId = mboExpenseTypeId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Boolean getBillable() {
        return billable;
    }

    public void setBillable(Boolean billable) {
        this.billable = billable;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public List<ExpenseData> getExpenseData() {
        return expenseData;
    }

    public void setExpenseData(List<ExpenseData> expenseData) {
        this.expenseData = expenseData;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "mboId='" + mboId + '\'' +
                ", mboWorkOrderId='" + mboWorkOrderId + '\'' +
                ", description='" + description + '\'' +
                ", mboAssociateId='" + mboAssociateId + '\'' +
                ", mboExpenseTypeId='" + mboExpenseTypeId + '\'' +
                ", amount=" + amount +
                ", editable=" + editable +
                ", billable=" + billable +
                ", version=" + version +
                ", expenseData=" + expenseData +
                ", receipts=" + receipts +'}';
    }

    public String getLastChangedDate() {
        return lastChangedDate;
    }

    public void setLastChangedDate(String lastChangedDate) {
        this.lastChangedDate = lastChangedDate;
    }
}
