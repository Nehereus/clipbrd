// 
// Decompiled by Procyon v0.5.36
// 

package com.yikecited.clipbrd;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference$OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;

public class SettingsActivity extends PreferenceActivity
{
    private void _updateRingtoneSummary(final RingtonePreference ringtonePreference, final Uri uri) {
        if (uri == null) {
            ringtonePreference.setSummary(2131361876);
            return;
        }
        final Ringtone ringtone = RingtoneManager.getRingtone((Context)this, uri);
        if (ringtone == null) {
            ringtonePreference.setSummary((CharSequence)"");
            return;
        }
        ringtonePreference.setSummary((CharSequence)ringtone.getTitle((Context)this));
    }
    
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.addPreferencesFromResource(2131558400);
        final RingtonePreference ringtonePreference = (RingtonePreference)this.findPreference((CharSequence)"notification_ringtone");
        ringtonePreference.setOnPreferenceChangeListener((Preference$OnPreferenceChangeListener)new Preference$OnPreferenceChangeListener() {
            public boolean onPreferenceChange(final Preference preference, final Object o) {
                final Uri uri = null;
                final String s = (String)o;
                Uri parse = uri;
                if (!s.equals("")) {
                    parse = Uri.parse(s);
                }
                SettingsActivity.this._updateRingtoneSummary((RingtonePreference)preference, parse);
                return true;
            }
        });
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)this);
        Uri parse = null;
        final String string = defaultSharedPreferences.getString("notification_ringtone", "content://settings/system/notification_sound");
        if (!string.equals("")) {
            parse = Uri.parse(string);
        }
        this._updateRingtoneSummary(ringtonePreference, parse);
    }
}
