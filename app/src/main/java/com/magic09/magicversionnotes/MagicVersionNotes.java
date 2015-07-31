package com.magic09.magicversionnotes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
			Log.e(TAG, "App name, version or version notes not set before calling showVersionNotes()?");
			throw new IllegalArgumentException("App name, version or version notes not set before calling showVersionNotes()?");
		}
		
        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
		if (appVersion != myPrefs.getString(NOTES_VERSION_KEY, "NONE")) {
			
			// Display version notes dialog fragment
            MagicVersionNotesDialogFragment dialogFragment = new MagicVersionNotesDialogFragment();
            Bundle fragBundle = new Bundle();
            fragBundle.putString(MagicVersionNotesDialogFragment.TITLE, appName);
            fragBundle.putString(MagicVersionNotesDialogFragment.VERSION, appVersion);
            fragBundle.putString(MagicVersionNotesDialogFragment.NOTES, versionNotes);
            dialogFragment.setArguments(fragBundle);
            dialogFragment.show(mActivity.getFragmentManager(), "MAGICVERSIONNOTES");
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
	 *  Method sets the version notes to display using the argument notes.
	 * @param notes
	 */
	public void setNotes(String notes) {
		versionNotes = notes;
	}
	
	/**
	 * Method sets the version notes using the passed text file resource
	 * in the argument resId.  Requires context.
	 * @param context
	 * @param resId
	 */
	public void setNotesFromTextFile(Context context, int resId) {
		versionNotes = readNotesFromRawHtmlTextFile(context, resId);
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
	
	/**
	 * Method reads the text file (raw) resource specified in the argument
	 * resId and returns a String with its contents.
	 * @param context
	 * @param resId
	 * @return
	 */
	private String readNotesFromRawHtmlTextFile(Context context, int resId) {
		String result = null;
		
		try {
			InputStream inputStream = context.getResources().openRawResource(resId);
			InputStreamReader inputreader = new InputStreamReader(inputStream);
		    BufferedReader buffreader =
		    		new BufferedReader(inputreader);
		    String line;
		    StringBuilder text = new StringBuilder();
		    try {
		        while ((line = buffreader.readLine()) != null) {
		        	if (text.length() > 0) {
			            text.append('\n');
		        	}
		            text.append(line);
		        }
		        result = text.toString();
		    } catch (IOException e) {
		        result = null;
		    }
		} catch (NotFoundException e) {
			Log.e(TAG, "Cannot find text file resource to display!");
			e.printStackTrace();
		}
		
		// Ensure we display any HTML encoded characters correctly
		result = Html.fromHtml(result).toString();
	    return result;
	}
	
}
