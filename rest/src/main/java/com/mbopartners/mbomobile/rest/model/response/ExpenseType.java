package com.mbopartners.mbomobile.rest.model.response;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;


public class ExpenseType implements Serializable, Validatable {
  private static final String TAG = ExpenseType.class.getSimpleName();
  
  @SerializedName("id")
  private String mboId = null;
  @SerializedName("name")
  private String name = null;
  @SerializedName("fields")
  private List<ExpenseField> fields = null;

    public ExpenseType(String mboId, String name, List<ExpenseField> fields) {
        this.mboId = mboId;
        this.name = name;
        this.fields = fields;
    }

    @Override
  public boolean isValid() {
    boolean result =
            mboId != null &&
            name != null &&
            fields != null && !fields.isEmpty() && ValidationHelper.validAll(fields);

    if (! result) {
      Log.e(TAG, "NOT VALID. id = " + mboId);
    }
    return result;
  }

  public String getMboId() {
    return mboId;
  }

  public String getName() {
    return name;
  }

  public List<ExpenseField> getFields() {
    return fields;
  }

  @Override
  public String toString() {
    return "ExpenseType{" +
            "mboId='" + mboId + '\'' +
            ", name='" + name + '\'' +
            ", fields=" + fields +
            '}';
  }
}
