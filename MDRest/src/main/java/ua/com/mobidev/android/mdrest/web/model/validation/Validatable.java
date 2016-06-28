package ua.com.mobidev.android.mdrest.web.model.validation;

/**
 *
 */
public interface Validatable {
    /**
     * Validate all mandatory fields.
     * @return true value if object is valid.
     */
    boolean isValid();
}
