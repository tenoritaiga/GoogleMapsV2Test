package com.smartcity.redux.adapters;

import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.smartcity.redux.MyGasActivity;
import com.smartcity.redux.R;
import com.smartcity.redux.R.string;
import com.smartcity.redux.fragments.EnterGasConsumptionFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class GasPagerAdapter extends FragmentPagerAdapter {

	/**
	 * 
	 */
	private MyGasActivity myGasActivity;

	public GasPagerAdapter(MyGasActivity myGasActivity, FragmentManager fm) {
		super(fm);
		this.myGasActivity = myGasActivity;
	}

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		// Return a DummySectionFragment (defined as a static inner class
		// below) with the page number as its lone argument.
		Fragment fragment = new EnterGasConsumptionFragment();
		Bundle args = new Bundle();
		args.putInt(EnterGasConsumptionFragment.ARG_SECTION_NUMBER, position + 1);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		// Show 3 total pages.
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return this.myGasActivity.getString(R.string.title_section1).toUpperCase(l);
		case 1:
			return this.myGasActivity.getString(R.string.title_section2).toUpperCase(l);
		}
		return null;
	}
}