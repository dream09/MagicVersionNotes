package com.magic09.magicversionnotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * MagicEula provides a simple way of presenting what has been updated
 * when a user installs or updates an app.
 * @author dream09
 *
 */
public class MagicVersionNotes {
	
	
	static final String TAG = "MagicVersionNotes";
	public static final String NOTES_VERSION_KEY = "com.magic09.magicversionnotes.notesversion";
	
	/* Variables */
	private Activity mActivity;
	private String appName;
	private String appVersion;
	private String versionNotes;
	
	
	
	/**
	 * Constructor
	 */
	public MagicVersionNotes(Activity context) {
		mActivity = context;
		appName = "";
		appVersion = "";
		versionNotes = "";
	}
	
	
	
	/* Methods */
	
	/**
	 * Method only shows the version notes on first run
	 * or update.
	 */
	public void showVersionNotes() {
		
		if (appName == "" || appVersion == "" || versionNotes == "") {
			Log.d(TAG, "App name, version or version notes not passed?");
			return;
		}
		
		final SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
		if (appVersion != myPrefs.getString(NOTES_VERSION_KEY, "NONE")) {
			
			// Inflate and setup the view.
			LayoutInflater inflater = (LayoutInflater) mActivity.getLayoutInflater();
			View notesView = inflater.inflate(R.layout.notes_view, null);
			TextView notesAppTitle = (TextView) notesView.findViewById(R.id.notes_app_title);
			notesAppTitle.setText(appName);
			TextView notesAppVersion = (TextView) notesView.findViewById(R.id.notes_app_version);
			notesAppVersion.setText(mActivity.getString(R.string.version_prefix) + " " + appVersion);
			TextView notesText = (TextView) notesView.findViewById(R.id.notes_message);
			notesText.setText(versionNotes);
			
			AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
    		builder.setView(notesView)
    			.setPositiveButton(mActivity.getString(R.string.button_dimiss), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						SharedPreferences.Editor editor = myPrefs.edit();
						editor.putString(NOTES_VERSION_KEY, appVersion);
						editor.commit();
						dialog.dismiss();
					}
				})
				.setOnCancelListener(new DialogInterface.OnCancelListener() {
					
					@Override
					public void onCancel(DialogInterface dialog) {
						SharedPreferences.Editor editor = myPrefs.edit();
						editor.putString(NOTES_VERSION_KEY, appVersion);
						editor.commit();
						dialog.dismiss();
					}
				});
    		
    		AlertDialog dialog = builder.create();
    		dialog.show();
		}
	}
	
	
	/**
	 * Method sets the app name to display.
	 * @param name
	 */
	public void setAppName(String name) {
		appName = name;
	}
	
	/**
	 * Method sets the version information to display.
	 * @param version
	 */
	public void setAppVersion(String version) {
		appVersion = version;
	}
	
	/**
	 *  Method sets the version notes to display.
	 * @param message
	 */
	public void setNotes(String notes) {
		versionNotes = notes;
	}
	
	/**
	 * Method checks the version of notes the user has seen
	 * against the argument version and returns true if they
	 * match otherwise false.
	 * @return
	 */
	public boolean versionCheck(String version) {
		SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
		return (version.equals(myPrefs.getString(NOTES_VERSION_KEY, "NONE")));
	}
}
