package com.example.bigfootone.mobilepoebuddy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.Toast;


/**
 * Created by Bigfootone on 03/12/2016.
 */

public class aboutDialogue extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder aboutDialog = new AlertDialog.Builder(getActivity());
        aboutDialog.setMessage(R.string.aboutText).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
            }
        });

        aboutDialog.setTitle("About");
        return  aboutDialog.create();
    }
}
