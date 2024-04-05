package se2.alpha.riskapp.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SecurePreferencesService {

    private static final String FILE_NAME = "secure_prefs";
    private static final String KEY_SESSION_TOKEN = "KEY_SESSION_TOKEN";
    private SharedPreferences sharedPreferences;

    public SecurePreferencesService(Context context) {
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            sharedPreferences = EncryptedSharedPreferences.create(
                    FILE_NAME,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (GeneralSecurityException | IOException e) {
            Log.e(this.getClass().getSimpleName(), "Something went terribly wrong in saving Preferences");
        }
    }

    public void saveSessionToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SESSION_TOKEN, token);
        editor.apply();
    }

    public String getSessionToken() {
        return sharedPreferences.getString(KEY_SESSION_TOKEN, null);
    }
}
