/*
* Copyright (C) 2013 The OmniROM Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package org.msm.xtended.device.DeviceSettings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.preference.PreferenceManager;

import org.msm.xtended.device.DeviceSettings.FileUtils;

public class Startup extends BroadcastReceiver {

    private boolean mHBM = false;
    private Context settingsContext = null;
    private Context mContext;

    @Override
    public void onReceive(final Context context, final Intent bootintent) {

        mContext = context;

        VibratorStrengthPreference.restore(context);
        VibratorCallStrengthPreference.restore(context);
        VibratorNotifStrengthPreference.restore(context);

        boolean enabled = false;
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_SRGB_SWITCH, false);
        if (enabled) {
        mHBM = false;
        restore(SRGBModeSwitch.getFile(), enabled);
 	       }
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_HBM_SWITCH, false);
        if (enabled) {
        mHBM = true;
        restore(HBMModeSwitch.getFile(), enabled);
               }
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_DC_SWITCH, false);
        if (enabled) {
        mHBM = false;
        restore(DCModeSwitch.getFile(), enabled);
               }
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_DCI_SWITCH, false);
        if (enabled) {
        mHBM = false;
        restore(DCIModeSwitch.getFile(), enabled);
               }
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_WIDE_SWITCH, false);
        if (enabled) {
        mHBM = false;
        restore(WideModeSwitch.getFile(), enabled);
               }

        FileUtils.setValue(DeviceSettings.MICROPHONE_GAIN_PATH, Settings.Secure.getInt(context.getContentResolver(),
                DeviceSettings.PREF_MICROPHONE_GAIN, 0));
        FileUtils.setValue(DeviceSettings.EARPIECE_GAIN_PATH, Settings.Secure.getInt(context.getContentResolver(),
                DeviceSettings.PREF_EARPIECE_GAIN, 0));
        FileUtils.setValue(DeviceSettings.SPEAKER_GAIN_PATH, Settings.Secure.getInt(context.getContentResolver(),
                DeviceSettings.PREF_SPEAKER_GAIN, 0));

        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_FPS_INFO, false);
        if (enabled) {
            context.startService(new Intent(context, FPSInfoService.class));
       }

        Utils.enableService(context);
    }

    private void restore(String file, boolean enabled) {
        if (file == null) {
            return;
        }
        if (enabled) {
            Utils.writeValue(file, mHBM ? "5" : "1");
        }
    }

    private void restore(String file, String value) {
        if (file == null) {
            return;
        }
        Utils.writeValue(file, value);
    }
}
