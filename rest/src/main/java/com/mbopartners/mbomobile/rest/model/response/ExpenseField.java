package com.mbopartners.mbomobile.rest.model.response;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

public class ExpenseField implements Serializable, Validatable {
    private static final String TAG = ExpenseField.class.getSimpleName();

    public static final String ID__EXPENSE_DATE__DATA_FIELD = "startDate";
    public static final String ID_EXPENSE_DATE_DATA_FIELD_END="endDate";
    public static final String note="note";

    @SerializedName("id")
    private String mboId = null;
    @SerializedName("type")
    private String type = null;
    @SerializedName("name")
    private String name = null;
    @SerializedName("required")
    private Boolean required = null;
    @SerializedName("maxLength")
    private Integer maxLength = null;
    @SerializedName("values")
    private List<ExpenseFieldValue> values = null;

    public ExpenseField(String mboId, String type, String name, Boolean required, Integer maxLength, List<ExpenseFieldValue> values) {
        this.mboId = mboId;
        this.type = type;
        this.name = name;
        this.required = required;
        this.maxLength = maxLength;
        this.values = values;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override

    public boolean isValid() {
        boolean result =
        mboId != null &&
        type != null &&
        name != null &&
        required != null &&
        //maxLength = null;
        ((type.equals("menu") && values != null && !values.isEmpty() && ValidationHelper.validAll(values)) || (!type.equals("menu") && values == null));

        if (! result) {
            Log.e(TAG, "NOT VALID. id = " + mboId);
        }
        return result;
    }

    public String getMboId() {
        return mboId;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public SpannableStringBuilder getSpannableTitle() {
        SpannableStringBuilder builder = new SpannableStringBuilder(name);
        if (getRequired()) {
            builder.append(" *");
//            builder.setSpan(new ForegroundColorSpan(Color.RED), builder.length() - 1, builder.length(), Spanned
//		            .SPAN_INCLUSIVE_INCLUSIVE);
        }
        return builder;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public List<ExpenseFieldValue> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "ExpenseField{" +
                "mboId='" + mboId + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", required=" + required +
                ", maxLength=" + maxLength +
                ", values=" + values +
                '}';
    }
}
