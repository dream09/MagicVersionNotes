package com.magic09.magicversionnotes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * MagicVersionNotesDialogFragment provides a DialogFragment to display the
 * version notes.
 * @author dream09
 *
 */
public class MagicVersionNotesDialogFragment extends DialogFragment {

    /* Variables */
    public static final String TITLE = "title";
    public static final String VERSION = "version";
    public static final String NOTES = "NOTES";

    private String version;


    /* Constructor */
    public MagicVersionNotesDialogFragment() {
        // Empty constructor required for a DialogFragment
    }



    /* Overridden methods */

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Get arguments
        String title = getArguments().getString(TITLE);
        version = getArguments().getString(VERSION);
        String notes = getArguments().getString(NOTES);

        // Setup view
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View notesView = inflater.inflate(R.layout.notes_view, null);
        TextView notesAppTitle = (TextView) notesView.findViewById(R.id.notes_app_title);
        if (title != null && notesAppTitle != null) {
            notesAppTitle.setText(title);
        }
        TextView notesAppVersion = (TextView) notesView.findViewById(R.id.notes_app_version);
        if (version != null && notesAppVersion != null) {
            notesAppVersion.setText(getActivity().getString(R.string.version_prefix) + " " + version);
        }
        TextView notesText = (TextView) notesView.findViewById(R.id.notes_message);
        if (notes != null && notesText != null) {
            notesText.setText(notes);
        }

        // Create notes dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(notesView)
                .setPositiveButton(getActivity().getString(R.string.button_dimiss), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notesDismissed(version);
                    }
                });

        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);

        notesDismissed(version);
    }



    /* Methods */

    /**
     * Method stores the version of the app in shared preferences denoting
     * the latest version notes the user has seen.
     */
    private void notesDismissed(String version) {
        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                .putString(MagicVersionNotes.NOTES_VERSION_KEY, version).commit();
    }
}
