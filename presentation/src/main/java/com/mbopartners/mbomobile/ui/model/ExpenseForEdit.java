package com.mbopartners.mbomobile.ui.model;

import android.app.Application;
import android.util.Log;

import com.mbopartners.mbomobile.data.db.generated.model.TableExpense;
import com.mbopartners.mbomobile.rest.model.Converter;
import com.mbopartners.mbomobile.rest.model.response.Expense;
import com.mbopartners.mbomobile.rest.model.response.ExpenseData;
import com.mbopartners.mbomobile.rest.model.response.ExpenseField;
import com.mbopartners.mbomobile.rest.model.response.ExpenseType;
import com.mbopartners.mbomobile.rest.model.response.Receipt;
import com.mbopartners.mbomobile.ui.activity.logexpense.ExpenseFieldGovernor_Menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ua.com.mobidev.android.framework.util.CollectionsUtils;

public class ExpenseForEdit implements Serializable {
    private static final String TAG = ExpenseForEdit.class.getSimpleName();

    private static final String KEY__EXPENSE_DATA_FIELD__AMOUNT = "amount";
    private static final String KEY__EXPENSE_DATA_FIELD__PURPOSE_MENU = "purposeId";
    private static final String KEY__EXPENSE_DATA_FIELD__PURPOSE_TEXT = "purpose";

    /** MBO Expense Id */
    public String mboId = null;
    public String mboWorkOrderId = null;
    public String description = null;
    public String mboAssociateId = null;
    public String mboExpenseTypeId = null;
    public Double amount = null;
    public Boolean editable = null;
    public Boolean billable = null;
    public Double version = null;

    public Map<String, String> expenseDataMap = new HashMap<>();
    public Map<String, String> expenseDataMap_Original = new HashMap<>();
    public List<Receipt> receipts = new ArrayList<Receipt>();

    public String companyName = null;
    public String workOrderName = null;
    public ExpenseType expenseType = null;
    public String lastChangedDate;
    public void fillWithExisting(Application application, TableExpense expense) {
        expense.resetReceipts();
        expense.resetExpenseData();
        this.mboWorkOrderId = expense.getMboWorkOrderId();
        this.description = expense.getDescription();
        this.mboAssociateId = expense.getMboAssociateId();
        this.mboExpenseTypeId = expense.getMboExpenseTypeId();
        this.amount = expense.getAmount();
        this.editable = expense.getEditable();
        this.billable = expense.getBillable();
        this.version = expense.getVersion();
        this.expenseType = Converter.toWeb(Dao.loadTableExpenseType(application, this.mboExpenseTypeId));
        this.setExpenseDataMap(Converter.to_ExpenseData_Map(expense.getExpenseData()));
        this.receipts = Converter.toWeb_Receipts(expense.getReceipts());
        this.lastChangedDate=expense.getLastChangedDate();
    }

    public void fillWithNew(Application application, String mboAssociateId) {
        this.description = "";
        this.editable = true;
        this.billable = (mboWorkOrderId != null);
        this.version = 1.0d;
        this.mboAssociateId = mboAssociateId;
        this.expenseType = Converter.toWeb(Dao.loadTableExpenseType(application, this.mboExpenseTypeId));
        this.setExpenseDataMap(fillDataWithDefaultValues(this.expenseType));
    }

    public void setExpenseDataMap(Map<String, String> expenseDataMap) {
        this.expenseDataMap = expenseDataMap;
        for (String key : expenseDataMap.keySet()) {
            String clonedValue = expenseDataMap.get(key);
            this.expenseDataMap_Original.put(key, clonedValue);
        }
    }

    public String putFieldValue(String key, String value) {
        return expenseDataMap.put(key, value);
    }

    public String getFieldValue(String key) {
        return expenseDataMap.get(key);
    }

    public boolean isChanged() {
        return ! CollectionsUtils.equalMaps(expenseDataMap_Original, expenseDataMap);
    }

    public boolean isAllMandatoryFieldsFilled() {
        Set<String> mandatoryFields = getMandatoryFields();
        Set<String> filledFields = getFilledFields();
        return filledFields.containsAll(mandatoryFields);
    }

    public Set<String> getMandatoryFields() {
        Set<String> mandatoryFields = new HashSet<>();
        for (ExpenseField field : expenseType.getFields()) {
            if (field.getRequired()) {
                mandatoryFields.add(field.getMboId());
            }
        }
        return mandatoryFields;
    }

    private Set<String> getFilledFields() {
        Set<String> filledFields = new HashSet<>();
        for (String key : expenseDataMap.keySet()) {
            if (! expenseDataMap.get(key).isEmpty()) {
                filledFields.add(key);
            }
        }

        Iterator iterator=filledFields.iterator();
        while(iterator.hasNext())
        {
            Log.d("filled",(String)iterator.next());
        }
        return filledFields;
    }
    public ArrayList<String> getMandatoryFilledValues()
    {
        ArrayList<String> filledFieldsValues = new ArrayList<>();
        for (String key : expenseDataMap.keySet()) {
            if (! expenseDataMap.get(key).isEmpty()) {
                filledFieldsValues.add(expenseDataMap.get(key));
            }
        }
        return filledFieldsValues;
    }

    private List<ExpenseData> expenseValueDataMapToList() {
        List<ExpenseData> expenseData = new ArrayList<>();
        for (String key : expenseDataMap.keySet()) {
            expenseData.add(new ExpenseData(key, expenseDataMap.get(key)));
        }
        return expenseData;
    }

    public ArrayList<String> getMandatoryEmptyFileds()
    {
        ArrayList<String> EmptyFields = new ArrayList<>();
        for (String key : expenseDataMap.keySet()) {
            if ( expenseDataMap.get(key).isEmpty()) {
                EmptyFields.add(expenseDataMap.get(key));
            }
        }
        return EmptyFields;
    }
    private Map<String, String> fillDataWithDefaultValues(ExpenseType expenseType) {
        Map<String, String> expenseDataMapDefault = new HashMap<>();
        for (ExpenseField field : expenseType.getFields()) {
            String key = field.getMboId();
            String value = getDefaultFieldValue(field);
            expenseDataMapDefault.put(key, value);
        }
        return expenseDataMapDefault;
    }

    private String getDefaultFieldValue(ExpenseField field) {

        String defaultValue = "";
        switch (ExpenseFieldType.getValueOf(field.getType())) {
            case TEXT : {
                defaultValue = "";
                break;
            }
            case BIG_TEXT : {
                defaultValue = "";
                break;
            }
            case DATE : {
                //defaultValue = DateUtil.getDateFormatted_yyyymmdd(DateUtil.getCurrentDate());
                break;
            }
            case MONEY : {
                defaultValue = "";
                break;
            }
            case MENU : {
                defaultValue = ExpenseFieldGovernor_Menu.getMboIdVariants(field.getValues()).get(0);
                break;
            }
            case CITY_STATE : {
                defaultValue = "";
                break;
            }
            case NUMBER : {
                defaultValue = "0";
                break;
            }
            default : {
                defaultValue = "";
            }
        }
        return defaultValue;
    }

    public void setPurposeFieldAsMandatoryField() {
        for (ExpenseField field : expenseType.getFields()) {
            if (
                    field.getMboId().equals(KEY__EXPENSE_DATA_FIELD__PURPOSE_MENU) ||
                            field.getMboId().equals(KEY__EXPENSE_DATA_FIELD__PURPOSE_TEXT)
                    ) {
                field.setRequired(true);
            }
        }
    }

    public void removePurposeFieldFromExpenseData() {
        expenseDataMap.remove(KEY__EXPENSE_DATA_FIELD__PURPOSE_MENU);
        expenseDataMap.remove(KEY__EXPENSE_DATA_FIELD__PURPOSE_TEXT);

        expenseDataMap_Original.remove(KEY__EXPENSE_DATA_FIELD__PURPOSE_MENU);
        expenseDataMap_Original.remove(KEY__EXPENSE_DATA_FIELD__PURPOSE_TEXT);
    }

    public void removePurposeFieldFromExpenseTypeFields() {
        ExpenseField expenseFieldToRemove = null;
        for (ExpenseField expenseField :  expenseType.getFields()) {
            if (expenseField.getMboId().equals(KEY__EXPENSE_DATA_FIELD__PURPOSE_MENU)){
                expenseFieldToRemove = expenseField;
            }
        }
        if (expenseFieldToRemove != null) {
            expenseType.getFields().remove(expenseFieldToRemove);
        }


        expenseFieldToRemove = null;
        for (ExpenseField expenseField :  expenseType.getFields()) {
            if (expenseField.getMboId().equals(KEY__EXPENSE_DATA_FIELD__PURPOSE_TEXT)){
                expenseFieldToRemove = expenseField;
            }
        }
        if (expenseFieldToRemove != null) {
            expenseType.getFields().remove(expenseFieldToRemove);
        }
    }

    /**
     *
     * @return
     */
    public boolean isItNewExpense() {
        return this.mboId == null;
    }

    /**
     *
     * @return
     */
    public boolean isItBillableExpense() {
        return this.billable;
    }

    /**
     *
     * @param field
     * @return
     */
    public boolean isItFixedPurpose(ExpenseField field) {
        return (field.getMboId().equals(KEY__EXPENSE_DATA_FIELD__PURPOSE_MENU) ||
                field.getMboId().equals(KEY__EXPENSE_DATA_FIELD__PURPOSE_TEXT)) &&
                ! isItNewExpense();
    }

    public Expense toExpenseForSave() {
        String amountStringValue = getFieldValue(KEY__EXPENSE_DATA_FIELD__AMOUNT);
        Double amount = 0.00d;
        if (amountStringValue != null) {
            try {
                amount =  Double.parseDouble(amountStringValue.toString());
            } catch (NumberFormatException e) {
                Log.e(TAG, "Parsing Error", e);
            }
        }

        Expense expenseForSave =
                new Expense(
                        mboId,
                        mboWorkOrderId,
                        description,
                        mboAssociateId,
                        expenseType.getMboId(),
                        amount,
                        editable,
                        billable,
                        version,
                        expenseValueDataMapToList(),
                        receipts,lastChangedDate);

        // TODO handle Description ??
        // TODO handle Purpose

        return expenseForSave;
    }

    public void update(ExpenseForEdit newObj) {
        this.mboId = newObj.mboId;
        this.mboWorkOrderId = newObj.mboWorkOrderId;
        this.description = newObj.description;
        this.mboAssociateId = newObj.mboAssociateId;
        this.mboExpenseTypeId = newObj.mboExpenseTypeId;
        this.amount =newObj.amount;
        this.editable = newObj.editable;
        this.billable = newObj.billable;
        this.version = newObj.version;

        this.expenseDataMap.clear();
        this.expenseDataMap = newObj.expenseDataMap;

        this.expenseDataMap_Original.clear();
        this.expenseDataMap_Original = newObj.expenseDataMap_Original;

        updateReceipts(newObj.receipts);

        this.companyName = newObj.companyName;
        this.workOrderName = newObj.workOrderName;

        this.expenseType = newObj.expenseType;
        this.lastChangedDate=newObj.lastChangedDate;
    }

    public void updateReceipts(List<Receipt> receipts) {
        this.receipts.clear();
        this.receipts.addAll(receipts);
    }
}
