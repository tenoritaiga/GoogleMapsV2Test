package com.smartcity.redux.adapters;

import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.smartcity.redux.fragments.MyGasFragment;
import com.smartcity.redux.R;
import com.smartcity.redux.fragments.EnterGasConsumptionFragment;
import com.smartcity.redux.fragments.ViewGasConsumptionFragment;

/**
 * This adapter class controls the creation of tabs for the My Gas Consumption component of the app.
 */
public class GasPagerAdapter extends FragmentPagerAdapter {

	/**
	 * 
	 */
	private MyGasFragment MyGasFragment;

	public GasPagerAdapter(MyGasFragment MyGasFragment, FragmentManager fm) {
		super(fm);
		this.MyGasFragment = MyGasFragment;
	}

	/**
	 * getItem is called to instantiate the fragment for the given page. 
	 * Return an appropriate fragment object with the page number as its lone argument.
	 */
	@Override
	public Fragment getItem(int position) {
		Fragment fragment;
		if (position == 0)
			fragment = new EnterGasConsumptionFragment();
		else
			fragment = new ViewGasConsumptionFragment();
		Bundle args = new Bundle();
		args.putInt(EnterGasConsumptionFragment.ARG_SECTION_NUMBER, position + 1);
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 * Sets the number of tabs to be shown.
	 */
	@Override
	public int getCount() {
		return 2;
	}

	/**
	 * Returns the titles for the tabs.
	 */
	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return this.MyGasFragment.getString(R.string.title_section1_gas).toUpperCase(l);
		case 1:
			return this.MyGasFragment.getString(R.string.title_section2_gas).toUpperCase(l);
		}
		return null;
	}
}