package ua.com.mobidev.android.mdrest.web.model.parameters;

import java.io.Serializable;

public class PreferencesParam implements Serializable {
    public String name;
    public String email;
    public String phoneNumber;
    public String defaultAddressId;
    public String currentPassword;
    public String newPassword;
}
