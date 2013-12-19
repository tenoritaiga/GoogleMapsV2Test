package com.smartcity.redux.fragments;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import com.smartcity.redux.R;

/**
 * Fragment that controls a dialog used for picking a date when entering a new gas 
 * consumption record in the My Gas Consumption functionality.
 * @author Class2013
 *
 */
public class DatePickerFragment extends DialogFragment implements
		OnDateSetListener {

	/**
	 * Function called when the date picker dialog is created - sets the current date as
	 * the initial values of the dialog.
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	
	/**
	 * Called when the Set button in the dialog is clicked - sets the chosen date as the 
	 * value of the date field in the "Enter Tank Refill" tab of the My Gas Consumption component 
	 * of the app.
	 */
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// TODO Auto-generated method stub
		TextView dateText = (TextView) getActivity().findViewById(R.id.view_date);
		dateText.setText((month + 1) + "/" + day + "/" + year);
	}

}
