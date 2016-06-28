package com.mbopartners.mbomobile.rest.model.response;

import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

import ua.com.mobidev.android.mdrest.web.model.validation.Validatable;
import ua.com.mobidev.android.mdrest.web.model.validation.ValidationHelper;

public class Receipt implements Serializable, Validatable {
    private static final String TAG = Receipt.class.getSimpleName();
    public static final double VERSION = 1.0d;

    @SerializedName("filename")
    private String filename = null;
    @SerializedName("expenseId")
    private String mboExpenseId = null;
    @SerializedName("thumbnailPath")
    private String thumbnailPath = null;
    @SerializedName("creationDate")
    private Date creationDate = null;
    @SerializedName("version")
    private Double version = VERSION;

    public Receipt(String filename, String mboExpenseId, String thumbnailPath, Date creationDate, Double version) {
        this.filename = filename;
        this.mboExpenseId = mboExpenseId;
        this.thumbnailPath = thumbnailPath;
        this.creationDate = creationDate;
        this.version = version;
    }

    @Override
    public boolean isValid() {
        boolean result =
                filename != null &&
                mboExpenseId != null &&
//                thumbnailPath!= null &&
                creationDate!= null;

        if (! result) {
            Log.e(TAG, "NOT VALID. filename = " + filename);
            ValidationHelper.Screamer screamer = new ValidationHelper.Screamer(TAG, "");
            screamer.sayIfIsNull("filename", filename);
            screamer.sayIfIsNull("mboExpenseId", mboExpenseId);
            screamer.sayIfIsNull("creationDate", creationDate);
        }
        return result;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMboExpenseId() {
        return mboExpenseId;
    }

    public void setMboExpenseId(String mboExpenseId) {
        this.mboExpenseId = mboExpenseId;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    @Override
    public String toString() {

        String receipt="Receipt{" +
                "filename='" + filename + '\'' +
                ", mboExpenseId='" + mboExpenseId + '\'' +
                ", thumbnailPath='" + thumbnailPath + '\'' +
                ", creationDate=" + creationDate +
                ", version=" + version +
                '}';
        Log.d("receipt",receipt);
        return receipt;
    }
}
