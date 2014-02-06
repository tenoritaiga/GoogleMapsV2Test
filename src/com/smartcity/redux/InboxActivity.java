package com.smartcity.redux;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;

import com.smartcity.redux.adapters.InboxPagerAdapter;

public class InboxActivity extends FragmentActivity implements
ActionBar.TabListener {
	
	InboxPagerAdapter mInboxPagerAdapter;
	ViewPager mViewPager;
	Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inbox);
		
		// Set up the action bar.
				final ActionBar actionBar = getActionBar();
				actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
				// Show the Up button in the action bar.
				actionBar.setDisplayHomeAsUpEnabled(true);

				// Create the adapter that will return a fragment for each of the two
				// primary sections of the app.
				mInboxPagerAdapter = new InboxPagerAdapter(
						this, getSupportFragmentManager());

				// Set up the ViewPager with the sections adapter.
				mViewPager = (ViewPager) findViewById(R.id.pager);
				mViewPager.setAdapter(mInboxPagerAdapter);

				// When swiping between different sections, select the corresponding
				// tab. We can also use ActionBar.Tab#select() to do this if we have
				// a reference to the Tab.
				mViewPager
						.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
							@Override
							public void onPageSelected(int position) {
								actionBar.setSelectedNavigationItem(position);
							}
						});

				// For each of the sections in the app, add a tab to the action bar.
				for (int i = 0; i < mInboxPagerAdapter.getCount(); i++) {
					// Create a tab with text corresponding to the page title defined by
					// the adapter. Also specify this Activity object, which implements
					// the TabListener interface, as the callback (listener) for when
					// this tab is selected.
					Log.d("IN LOOP","IN LOOP");
					actionBar.addTab(actionBar.newTab()
							.setText(mInboxPagerAdapter.getPageTitle(i))
							.setTabListener(this));
				}
				
				activity = this;
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inbox, menu);
		return true;
	}
	
	/**
	 * Called when a tab is selected.
	 */
	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}
	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}
	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

}
