package com.example.encryp_decryp;

import android.provider.BaseColumns;
public final class RegistrationContract {
    private RegistrationContract() {};
    public static class RegistrationEntry implements BaseColumns{
        public static final String TABLE_NAME = "registration";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_MOBILE = "mobile";
        public static final String COLUMN_PASSWORD = "password";
    }
}
