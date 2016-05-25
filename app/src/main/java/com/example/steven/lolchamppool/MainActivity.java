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

import org.w3c.dom.Text;

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
	static ArrayList<Game> currRoleGames;
	static ArrayList<Champion> champs;
	static DatabaseHandler db;
	static ArrayAdapter<Champion> adapter;
	private String avgKDA;
	private String avgCS;
	private String avgTime;
	private String avgWinRate;

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
		games = new ArrayList<>();
		currRoleGames = new ArrayList<>();
		champs = new ArrayList<>();

		mNavigationDrawerFragment = (NavigationDrawerFragment)
				getFragmentManager().findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(
				R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		games = db.getAllGames();
		Log.e("db games", games.toString());
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
				compileChamps(games);
				updateStats();
				Log.e("db games", db.getAllGames().toString());
				Log.e("games", games.toString());
				Log.e("champs", champs.toString());
				break;
			case 2:
				mTitle = "My Top Laners";
				screen = TOP;
				currRoleGames = filterGames("Top Laner");
				champs = compileChamps(currRoleGames);
				updateStats();
				populateList();
				Log.e("games", currRoleGames.toString());
				Log.e("champs", champs.toString());
				break;
			case 3:
				mTitle = "My Junglers";
				screen = JUNGLE;
				currRoleGames = filterGames("Jungler");
				champs = compileChamps(currRoleGames);
				updateStats();
				populateList();
				Log.e("games", currRoleGames.toString());
				Log.e("champs", champs.toString());
				break;
			case 4:
				mTitle = "My Mid Laners";
				screen = MID;
				currRoleGames = filterGames("Mid Laner");
				champs = compileChamps(currRoleGames);
				updateStats();
				populateList();
				Log.e("games", currRoleGames.toString());
				Log.e("champs", champs.toString());
				break;
			case 5:
				mTitle = "My AD Carries";
				screen = ADC;
				currRoleGames = filterGames("AD Carry");
				champs = compileChamps(currRoleGames);
				updateStats();
				populateList();
				Log.e("games", currRoleGames.toString());
				Log.e("champs", champs.toString());
				break;
			case 6:
				mTitle = "My Supports";
				screen = SUPPORT;
				currRoleGames = filterGames("Support");
				champs = compileChamps(currRoleGames);
				updateStats();
				populateList();
				Log.e("games", currRoleGames.toString());
				Log.e("champs", champs.toString());
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
					MainActivity.this.
					db.clearAll();
					Toast.makeText(MainActivity.this, "All Data Erased.",
							Toast.LENGTH_LONG).show();
					Intent intent = new Intent(MainActivity.this, MainActivity.class);
					finish();
					startActivity(intent);
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
			winRateTag.setText(winRate + "%");

			return itemView;
		}
	}

	public ArrayList<Game> filterGames(String role) {
		ArrayList<Game> filteredGames = new ArrayList<>();
		for (int i = 0; i < games.size() - 1; i++) {
			if (games.get(i).getRole().equals(role)) {
				filteredGames.add(games.get(i));
			}
		}
		return filteredGames;
	}

	public ArrayList<Champion> compileChamps(ArrayList<Game> games) {
		ArrayList<Champion> champs = new ArrayList<>();
		for (int i = 0; i < games.size(); i++) {
			Log.e("Champ Names(W/L): " + i, games.get(i).getName() + "/" + games.get(i).isWin());
			for (int j = 0; j < champs.size(); j++) {
				if (!games.get(i).getName().equals(champs.get(j).getName())) {
					champs.add(new Champion(games.get(i).getName()));
					Log.e("Champ " + i + " Names", champs.get(i).getName());
				}
			}
			if (champs.size() == 0) {
				champs.add(new Champion(games.get(i).getName()));
				Log.e("Champ " + i + " Names", champs.get(i).getName());
			}
		}
		for (int i = 0; i < champs.size(); i++) {
			int numGames = 0;
			int kills = 0;
			int deaths = 0;
			int assists = 0;
			int cs = 0;
			int mins = 0;
			int secs = 0;
			double wins = 0;
			for (int j = 0; j < games.size(); j++) {
				if (champs.get(i).getName().equals(games.get(j).getName())) {
					numGames++;
					kills += games.get(j).getKills();
					deaths += games.get(j).getDeaths();
					assists += games.get(j).getAssists();
					cs += games.get(j).getCreepScore();
					mins += games.get(j).getMins();
					secs += games.get(j).getSecs();
					if (games.get(j).isWin()) {
						wins++;
					}
				}
			}
			double avgKills = Math.round((kills / numGames) * 10) / 10.0;
			double avgDeaths = Math.round((deaths / numGames) * 10) / 10.0;
			double avgAssists = Math.round((assists / numGames) * 10) / 10.0;
			int avgCs = cs / numGames;
			int avgMins = mins / numGames;
			int avgSecs = secs / numGames;
			double winRate = Math.round((wins / numGames) * 10) / 10.0;
			champs.get(i).setKills(avgKills);
			champs.get(i).setDeaths(avgDeaths);
			champs.get(i).setAssists(avgAssists);
			champs.get(i).setCs(avgCs);
			champs.get(i).setMins(avgMins);
			champs.get(i).setSecs(avgSecs);
			champs.get(i).setWinrate(winRate);
		}
		return champs;
	}

	public void updateStats() {
		double kills = 0;
		double deaths = 0;
		double assists = 0;
		int cs = 0;
		int mins = 0;
		int secs = 0;
		double win = 0;
		for (Champion champ : champs) {
			kills += champ.getKills();
			deaths += champ.getDeaths();
			assists += champ.getAssists();
			cs += champ.getCs();
			mins += champ.getMins();
			secs += champ.getSecs();
			win += champ.getWinrate();
		}
		int numChamps = champs.size();
		if (numChamps != 0) {
			kills = Math.round((kills / numChamps) * 10) / 10.0;
			deaths = Math.round((deaths / numChamps) * 10) / 10.0;
			assists = Math.round((assists / numChamps) * 10) / 10.0;
			avgKDA = kills + "/" + deaths + "/" + assists;
			avgCS = cs / numChamps + "";
			mins = mins / numChamps;
			secs = secs / numChamps;
			avgTime = mins + ":" + secs;
			win = win / numChamps * 100;
			avgWinRate = win + "%";

			TextView myKDA = (TextView) findViewById(R.id.myKDA);
			TextView myCS = (TextView) findViewById(R.id.myCS);
			TextView myTime = (TextView) findViewById(R.id.myTime);
			TextView myWinRate = (TextView) findViewById(R.id.myWinRate);

			myKDA.setText("My Average KDA: " + avgKDA);
			myCS.setText("My Average CS: " + avgCS);
			myTime.setText("My Average Game Time: " + avgTime);
			myWinRate.setText("My Average Win Rate: " + avgWinRate);
		}
	}

	public void populateList() {
		adapter = new ListAdapter1();
//		Log.d("first",""+adapter);
		ListView list = (ListView) this.findViewById(R.id.Champions);
//		Log.d("second", ""+ list);
		list.setAdapter(adapter);
	}
}
