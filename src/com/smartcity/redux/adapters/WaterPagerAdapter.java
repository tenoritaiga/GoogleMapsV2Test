package com.smartcity.redux.adapters;

import java.util.Locale;

import com.smartcity.redux.MyWaterActivity;
import com.smartcity.redux.R;
import com.smartcity.redux.fragments.EnterWaterSectionFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class WaterPagerAdapter extends FragmentPagerAdapter {

	private MyWaterActivity myWaterActivity;
	
	public WaterPagerAdapter(MyWaterActivity myWaterActivity, FragmentManager fm) {
		super(fm);
		this.myWaterActivity = myWaterActivity;
	}

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		// Return a DummySectionFragment (defined as a static inner class
		// below) with the page number as its lone argument.
		Fragment fragment = new EnterWaterSectionFragment();
		Bundle args = new Bundle();
		args.putInt(EnterWaterSectionFragment.ARG_SECTION_NUMBER, position + 1);
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
			return this.myWaterActivity.getString(R.string.title_section1).toUpperCase(l);
		case 1:
			return this.myWaterActivity.getString(R.string.title_section2).toUpperCase(l);
		}
		return null;
	}
}
