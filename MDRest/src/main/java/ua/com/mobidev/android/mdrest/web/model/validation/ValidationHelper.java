package ua.com.mobidev.android.mdrest.web.model.validation;


import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ValidationHelper {

    public static <T extends Validatable> boolean validAll(List<T> listToValidate) {
        if (listToValidate != null) {
            boolean result = true;
            for (T item : listToValidate) {
                if( ! item.isValid()) {
                    result =  false;
                }
            }
            return result;
        } else {
            return false;
        }
    }

    public static <T extends Validatable> boolean validAll(T[] listToValidate) {
        return validAll(Arrays.asList(listToValidate));
    }

    public static class Screamer {
        private String logTag;
        private String objectName;

        public Screamer(String logTag, String objectName) {
            this.logTag = logTag;
            this.objectName = objectName;
        }

        public void sayIfIsNull(String fieldName, Object field) {
            if (field == null) {
                Log.e(logTag, "___" + fieldName + " is null");
            }
        }

        public void sayIfIsFalse(String expressionStr, boolean expression) {
            if (!expression) {
                Log.e(logTag, "___" + expressionStr + " is [false]");
            }

        }

        public void sayIfIsTrue(String expressionStr, boolean expression) {
            if (expression) {
                Log.e(logTag, "___" + expressionStr + " is [true]");
            }

        }
    }
}
