package com.example.steven.lolchamppool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AddGameInfo extends Activity {

	public String allChamps;
	public String[] champList;
	Spinner champSelect;
	public String allRoles;
	public String[] roleList;
	Spinner roleSelect;
	public String allOutcomes;
	public String[] outcomeList;
	Spinner outcomeSelect;
	DatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_game_info);
		db = new DatabaseHandler(this);
		generateChampsForDropdown();
		generateRolesForDropdown();
		generateOptionsForOutcome();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_add_game_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void generateChampsForDropdown() {
		champSelect = (Spinner) findViewById(R.id.AllChamps);
		allChamps = getString(R.string.champ_names);
		champList = allChamps.split("-");
		champSelect.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, champList));
	}

	public void generateRolesForDropdown() {
		roleSelect = (Spinner) findViewById(R.id.AllRoles);
		allRoles = getString(R.string.roles);
		roleList = allRoles.split("-");
		roleSelect.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, roleList));
	}

	public void generateOptionsForOutcome() {
		outcomeSelect = (Spinner) findViewById(R.id.Win);
		allOutcomes = getString(R.string.outcomes);
		outcomeList = allOutcomes.split("-");
		outcomeSelect.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, outcomeList));
	}

	public void confirmGame(View v) {
		DatabaseHandler db = new DatabaseHandler(this);
		int id = db.getGameCount();
		String name = champSelect.getSelectedItem().toString();
		String role = roleSelect.getSelectedItem().toString();

		EditText numKills = (EditText) findViewById(R.id.NumKills);
		int kills = Integer.parseInt(numKills.getText() + "");

		EditText numDeaths = (EditText) findViewById(R.id.NumDeaths);
		int deaths = Integer.parseInt(numDeaths.getText() + "");

		EditText numAssists = (EditText) findViewById(R.id.NumAssists);
		int assists = Integer.parseInt(numAssists.getText() + "");

		EditText numCS = (EditText) findViewById(R.id.CreepScore);
		int CS = Integer.parseInt(numCS.getText() + "");

		EditText timeMins = (EditText) findViewById(R.id.Mins);
		int mins = Integer.parseInt(timeMins.getText() + "");

		EditText timeSecs = (EditText) findViewById(R.id.Secs);
		int secs = Integer.parseInt(timeSecs.getText() + "");

		String winLose = outcomeSelect.getSelectedItem().toString();

		int won = 0;
		if (winLose.equals("Won")) {
			won = 1;
		}

		final Game game = new Game(id, name, role, kills, deaths, assists, CS, mins, secs, won);
		Log.e("final add W/L", game.getWin() + "");

		LayoutInflater linf = LayoutInflater.from(this);
		final View inflator = linf.inflate(R.layout.alertdialog, null);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Confirm");
		alert.setView(inflator);

		final TextView championText = (TextView) inflator.findViewById(R.id.ChampionText);
		final TextView roleText = (TextView) inflator.findViewById(R.id.RoleText);
		final TextView kdaText = (TextView) inflator.findViewById(R.id.KDAText);
		final TextView csText = (TextView) inflator.findViewById(R.id.CSText);
		final TextView timeText = (TextView) inflator.findViewById(R.id.TimeText);
		final TextView outcomeText = (TextView) inflator.findViewById(R.id.OutcomeText);

		String champString = championText.getText() + "";
		String roleString = roleText.getText() + "";
		String kdaString = kdaText.getText() + "";
		String csString = csText.getText() + "";
		String timeString = timeText.getText() + "";
		String outcomeString = outcomeText.getText() + "";

		champString += name + " ";
		roleString = " " + roleString + role;
		kdaString += kills + "/" + deaths + "/" + assists + " ";
		csString = " " + csString + CS;
		timeString += mins + ":" + secs;
		outcomeString += winLose;

		championText.setText(champString);
		roleText.setText(roleString);
		kdaText.setText(kdaString);
		csText.setText(csString);
		timeText.setText(timeString);
		outcomeText.setText(outcomeString);

		alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				addGame(game);
			}
		});
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		alert.show();
	}

	public void addGame(Game game) {
		db.addGame(game);
		this.finish();
	}
}