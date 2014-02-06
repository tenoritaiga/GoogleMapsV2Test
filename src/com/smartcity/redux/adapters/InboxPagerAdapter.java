package com.smartcity.redux.adapters;

import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.smartcity.redux.InboxActivity;
import com.smartcity.redux.R;
import com.smartcity.redux.fragments.ReceivedMessagesFragment;
import com.smartcity.redux.fragments.SentMessagesFragment;


public class InboxPagerAdapter extends FragmentPagerAdapter {

	/**
	 * 
	 */
	private InboxActivity myInboxActivity;

	public InboxPagerAdapter(InboxActivity myInboxActivity, FragmentManager fm) {
		super(fm);
		this.myInboxActivity = myInboxActivity;
	}

	/**
	 * getItem is called to instantiate the fragment for the given page. 
	 * Return an appropriate fragment object with the page number as its lone argument.
	 */
	@Override
	public Fragment getItem(int position) {
		Fragment fragment;
		if (position == 0)
			fragment = new ReceivedMessagesFragment();
		else
			fragment = new SentMessagesFragment();
		Bundle args = new Bundle();
		args.putInt(ReceivedMessagesFragment.ARG_SECTION_NUMBER, position + 1);
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
			return this.myInboxActivity.getString(R.string.title_section1_inbox).toUpperCase(l);
		case 1:
			return this.myInboxActivity.getString(R.string.title_section2_inbox).toUpperCase(l);
		}
		return null;
	}
}