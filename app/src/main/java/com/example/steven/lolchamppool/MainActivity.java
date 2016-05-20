package com.example.steven.lolchamppool;

import android.app.Activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends Activity
		implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	private static final String STATS = "myStatsScreen";
	private static final String TOP = "topLaners";
	private static final String JUNGLE = "junglers";
	private static final String MID = "midLaners";
	private static final String ADC = "marksmen";
	private static final String SUPPORT = "supports";

	static String screen = STATS;
	static ArrayList<Game> games;
	static ArrayList<Champion> champs;
	static DatabaseHandler db;
	static Champion[] champsList;
	static ArrayAdapter<Champion> adapter;

	/**
	 * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity);
		db = new DatabaseHandler(this);
		champs = new ArrayList<>();

		champs.add(0, new Champion("Master Yi", 12.2, 3.5, 8.1, 128, 43, 24, .67));
		champs.add(1, new Champion("Katarina", 26.4, 9.3, 5.7, 156, 37, 48, .58));

		champsList = new Champion[champs.size()];
		int index = 0;
		for (Champion champ : champs) {
			champsList[index] = champ;
			index++;
		}

		mNavigationDrawerFragment = (NavigationDrawerFragment)
				getFragmentManager().findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(
				R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));


//			adapter = new ArrayAdapter<Champion>(getActivity(), R.layout.listview_item, champs);
		adapter = new ListAdapter1();
		Log.d("first",""+adapter);
		ListView list = (ListView) this.findViewById(R.id.Champions);
		Log.d("second", ""+ list);
		list.setAdapter(adapter);
	}



	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
				.commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
			case 1:
				mTitle = "My Overall Stats";
				screen = STATS;
				break;
			case 2:
				mTitle = "My Top Laners";
				screen = TOP;
				break;
			case 3:
				mTitle = "My Junglers";
				screen = JUNGLE;
				break;
			case 4:
				mTitle = "My Mid Laners";
				screen = MID;
				break;
			case 5:
				mTitle = "My AD Carries";
				screen = ADC;
				break;
			case 6:
				mTitle = "My Supports";
				screen = SUPPORT;
				break;
			case 7:
				startActivity(new Intent(this, AddGameInfo.class));
				break;

		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.summoner_info, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.reset) {
			LayoutInflater linf = LayoutInflater.from(this);
			final View inflator = linf.inflate(R.layout.alertdialog2, null);
			AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("Confirm");
			alert.setView(inflator);
			alert.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					db.clearAll();
					Toast.makeText(MainActivity.this, "All Data Erased.",
							Toast.LENGTH_LONG).show();
				}
			});
			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			});
			alert.show();
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */

		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section
		 * number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
								 Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_summoner_info, container, false);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(
					getArguments().getInt(ARG_SECTION_NUMBER));
		}
	}

	//new class to populate to list view
	private class ListAdapter1 extends ArrayAdapter<Champion> {
		public ListAdapter1() {
			super(MainActivity.this, R.layout.listview_item, champs);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View itemView = convertView;
			if(itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.listview_item, parent, false);
			}

			Champion champ = champs.get(position);
			String champName = champ.getName();
			String kda = champ.getKills() + "/" + champ.getDeaths() + "/" + champ.getAssists();
			String cs = champ.getCs() + "";
			String timeStamp = champ.getMins() + ":" + champ.getSecs();
			String winRate = champ.getWinrate() + "";

			TextView champTag = (TextView) itemView.findViewById(R.id.champTag);
			champTag.setText(champName);

			TextView kdaTag = (TextView) itemView.findViewById(R.id.kdaTag);
			kdaTag.setText(kda);

			TextView csTag = (TextView) itemView.findViewById(R.id.creepScoreTag);
			csTag.setText(cs);

			TextView timeStampTag = (TextView) itemView.findViewById(R.id.timeStampTag);
			timeStampTag.setText(timeStamp);

			TextView winRateTag = (TextView) itemView.findViewById(R.id.winRateTag);
			winRateTag.setText(winRate);

			return itemView;
		}
	}
}
