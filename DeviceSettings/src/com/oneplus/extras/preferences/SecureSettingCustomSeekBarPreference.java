package com.oneplus.extras.preferences;

import android.content.Context;
import android.util.AttributeSet;

import com.oneplus.extras.preferences.SecureSettingsStore;

public class SecureSettingCustomSeekBarPreference extends CustomSeekBarPreference {

    public SecureSettingCustomSeekBarPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setPreferenceDataStore(new SecureSettingsStore(context.getContentResolver()));
    }

    public SecureSettingCustomSeekBarPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setPreferenceDataStore(new SecureSettingsStore(context.getContentResolver()));
    }

    public SecureSettingCustomSeekBarPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPreferenceDataStore(new SecureSettingsStore(context.getContentResolver()));
    }

    public SecureSettingCustomSeekBarPreference(Context context) {
        super(context);
        setPreferenceDataStore(new SecureSettingsStore(context.getContentResolver()));
    }
}
