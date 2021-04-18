/*
 * Copyright (C) 2018 The Asus-SDM660 Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package org.lineageos.device.DeviceSettings.kcal;

import android.os.Bundle;
import android.provider.Settings;
import android.content.Context;
import androidx.preference.PreferenceFragment;
import androidx.preference.Preference;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;
import android.content.SharedPreferences;
import org.lineageos.device.DeviceSettings.R;
import org.lineageos.device.DeviceSettings.CustomSeekBarPreference;
import org.lineageos.device.DeviceSettings.SecureSettingListPreference;


public class KCalSettings extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener, Utils {

    private final FileUtils mFileUtils = new FileUtils();
    private SwitchPreference mEnabled;
    private CustomSeekBarPreference mRed;
    private CustomSeekBarPreference mGreen;
    private CustomSeekBarPreference mBlue;
    private CustomSeekBarPreference mSaturation;
    private CustomSeekBarPreference mValue;
    private CustomSeekBarPreference mContrast;
    private CustomSeekBarPreference mHue;
    private SharedPreferences mPrefs;    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        setPreferencesFromResource(R.xml.preferences_kcal, rootKey);

        mEnabled =  findPreference(PREF_ENABLED);
        mEnabled.setChecked(mPrefs.getBoolean(KCalSettings.PREF_ENABLED, false));        
        mEnabled.setOnPreferenceChangeListener(this);

        mRed = (CustomSeekBarPreference) findPreference(PREF_RED);
        mRed.setValue(mPrefs.getInt(PREF_RED, mRed.getValue()));        
        mRed.setOnPreferenceChangeListener(this);

        mGreen = (CustomSeekBarPreference) findPreference(PREF_GREEN);
        mGreen.setValue(mPrefs.getInt(PREF_GREEN, mGreen.getValue()));                
        mGreen.setOnPreferenceChangeListener(this);

        mBlue = (CustomSeekBarPreference) findPreference(PREF_BLUE);
        mBlue.setValue(mPrefs.getInt(PREF_BLUE, mBlue.getValue()));                
        mBlue.setOnPreferenceChangeListener(this);

        mSaturation = (CustomSeekBarPreference) findPreference(PREF_SATURATION);
        mSaturation.setValue(mPrefs.getInt(PREF_SATURATION, mSaturation.getValue()));                   
        mSaturation.setOnPreferenceChangeListener(this);

        mValue = (CustomSeekBarPreference) findPreference(PREF_VALUE);
        mValue.setValue(mPrefs.getInt(PREF_VALUE, mValue.getValue()));                
        mValue.setOnPreferenceChangeListener(this);

        mContrast = (CustomSeekBarPreference) findPreference(PREF_CONTRAST);
        mContrast.setValue(mPrefs.getInt(PREF_CONTRAST, mContrast.getValue()));                
        mContrast.setOnPreferenceChangeListener(this);

        mHue = (CustomSeekBarPreference) findPreference(PREF_HUE);
        mHue.setValue(mPrefs.getInt(PREF_HUE, mHue.getValue()));                
        mHue.setOnPreferenceChangeListener(this);
    }                                                            

    public static void restore(Context context) {
       boolean storeEnabled = PreferenceManager
                .getDefaultSharedPreferences(context).getBoolean(KCalSettings.PREF_ENABLED, false);
      if (storeEnabled) {
           int storedRed = PreferenceManager
                   .getDefaultSharedPreferences(context).getInt(KCalSettings.PREF_RED, 256);
           int storedGreen = PreferenceManager
                   .getDefaultSharedPreferences(context).getInt(KCalSettings.PREF_GREEN, 256);
           int storedBlue = PreferenceManager
                   .getDefaultSharedPreferences(context).getInt(KCalSettings.PREF_BLUE, 256);
           int storedSaturation = PreferenceManager
                   .getDefaultSharedPreferences(context).getInt(KCalSettings.PREF_SATURATION, 127);
           int storedContrast = PreferenceManager
                   .getDefaultSharedPreferences(context).getInt(KCalSettings.PREF_CONTRAST, 127);
           int storedValue = PreferenceManager
                   .getDefaultSharedPreferences(context).getInt(KCalSettings.PREF_VALUE, 127);
           int storedHue = PreferenceManager
                   .getDefaultSharedPreferences(context).getInt(KCalSettings.PREF_HUE, 0);
           FileUtils.WriteValue(COLOR_FILE_RED, storedRed);
           FileUtils.WriteValue(COLOR_FILE_GREEN, storedGreen);
           FileUtils.WriteValue(COLOR_FILE_BLUE, storedBlue);
           FileUtils.WriteValue(COLOR_FILE_SAT, storedSaturation + SATURATION_OFFSET);
           FileUtils.WriteValue(COLOR_FILE_CONT, storedContrast + CONTRAST_OFFSET);
           FileUtils.WriteValue(COLOR_FILE_VAL, storedValue + VALUE_OFFSET);
           FileUtils.WriteValue(COLOR_FILE_HUE, storedHue);
       }
    }                 

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        final String key = preference.getKey();

        switch (key) {
            case PREF_ENABLED:
                Boolean enabled = (Boolean) value;
                mPrefs.edit().putBoolean(PREF_ENABLED, enabled).apply();            
                String Red = String.valueOf(mPrefs.getInt(PREF_RED, 256));
                String Blue = String.valueOf(mPrefs.getInt(PREF_BLUE, 256));
                String Green = String.valueOf(mPrefs.getInt(PREF_GREEN, 256));
                String Sat = String.valueOf(mPrefs.getInt(PREF_SATURATION, 127));
                String Value = String.valueOf(mPrefs.getInt(PREF_VALUE, 127));
                String Cont = String.valueOf(mPrefs.getInt(PREF_CONTRAST, 127));
                String hue = String.valueOf(mPrefs.getInt(PREF_HUE, 0));
                FileUtils.WriteValue(COLOR_FILE_RED, Integer.parseInt(Red));
                FileUtils.WriteValue(COLOR_FILE_GREEN, Integer.parseInt(Green));
                FileUtils.WriteValue(COLOR_FILE_BLUE, Integer.parseInt(Blue));
                FileUtils.WriteValue(COLOR_FILE_SAT, Integer.parseInt(Sat) + SATURATION_OFFSET);
                FileUtils.WriteValue(COLOR_FILE_CONT, Integer.parseInt(Cont) + CONTRAST_OFFSET);
                FileUtils.WriteValue(COLOR_FILE_VAL, Integer.parseInt(Value) + VALUE_OFFSET);
                FileUtils.WriteValue(COLOR_FILE_HUE, Integer.parseInt(hue));                                                         
                break;

            case PREF_RED:
                mPrefs.edit().putInt(PREF_RED, (int) value).apply();            
                mFileUtils.setValue(COLOR_FILE_RED, (int) value);
                break;

            case PREF_GREEN:
                mPrefs.edit().putInt(PREF_GREEN, (int) value).apply();          
                mFileUtils.setValue(COLOR_FILE_GREEN, (int) value);
                break;

            case PREF_BLUE:
                mPrefs.edit().putInt(PREF_BLUE, (int) value).apply();            
                mFileUtils.setValue(COLOR_FILE_BLUE, (int) value);
                break;

            case PREF_SATURATION:
                mPrefs.edit().putInt(PREF_SATURATION, (int) value).apply();            
                mFileUtils.setValue(COLOR_FILE_SAT, (int) value + SATURATION_OFFSET);
                break;

            case PREF_VALUE:
                mPrefs.edit().putInt(PREF_VALUE, (int) value).apply();             
                mFileUtils.setValue(COLOR_FILE_VAL, (int) value + VALUE_OFFSET);
                 break;

            case PREF_CONTRAST:
                mPrefs.edit().putInt(PREF_CONTRAST, (int) value).apply();              
                mFileUtils.setValue(COLOR_FILE_CONT, (int) value + CONTRAST_OFFSET);
                break;

            case PREF_HUE:
                mPrefs.edit().putInt(PREF_HUE, (int) value).apply();            
                mFileUtils.setValue(COLOR_FILE_HUE, (int) value);
                break;

            default:
                break;
        }
        return true;
    }

    void applyValues(String preset) {
        String[] values = preset.split(" ");
        int red = Integer.parseInt(values[0]);
        int green = Integer.parseInt(values[1]);
        int blue = Integer.parseInt(values[2]);
        int sat = Integer.parseInt(values[3]);
        int value = Integer.parseInt(values[4]);
        int contrast = Integer.parseInt(values[5]);
        int hue = Integer.parseInt(values[6]);

        mRed.refresh(red);
        mGreen.refresh(green);
        mBlue.refresh(blue);
        mSaturation.refresh(sat);
        mValue.refresh(value);
        mContrast.refresh(contrast);
        mHue.refresh(hue);
    }   
}
