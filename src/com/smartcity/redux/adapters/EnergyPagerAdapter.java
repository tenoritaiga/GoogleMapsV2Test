package com.smartcity.redux.adapters;

import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.smartcity.redux.MyEnergyActivity;
import com.smartcity.redux.R;
import com.smartcity.redux.fragments.EnterEnergyConsumptionFragment;
import com.smartcity.redux.fragments.ViewEnergyConsumptionFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class EnergyPagerAdapter extends FragmentPagerAdapter {

	private MyEnergyActivity myEnergyActivity;
	
	public EnergyPagerAdapter(MyEnergyActivity myEnergyActivity, FragmentManager fm) {
		super(fm);
		this.myEnergyActivity = myEnergyActivity;
	}

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		// Return a DummySectionFragment (defined as a static inner class
		// below) with the page number as its lone argument.
		Fragment fragment;
		if (position == 0)
			fragment = new EnterEnergyConsumptionFragment();
		else
			fragment = new ViewEnergyConsumptionFragment();
		Bundle args = new Bundle();
		args.putInt(EnterEnergyConsumptionFragment.ARG_SECTION_NUMBER, position + 1);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		// Show 2 total pages.
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return this.myEnergyActivity.getString(R.string.title_section1_energy).toUpperCase(l);
		case 1:
			return this.myEnergyActivity.getString(R.string.title_section2_energy).toUpperCase(l);
		}
		return null;
	}
}
