package com.example.steven.lolchamppool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_game_info);
		generateChampsForDropdown();
		generateRolesForDropdown();
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

	public void confirmGame() {
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

//		Intent intent = new Intent(this, Confirmation.class);
//		intent.putExtra("ID", id);
//		intent.putExtra("name", name);
//		intent.putExtra("role", role);
//		intent.putExtra("kills", kills);
//		intent.putExtra("deaths", deaths);
//		intent.putExtra("assists", assists);
//		intent.putExtra("CS", CS);
//		intent.putExtra("mins", mins);
//		intent.putExtra("secs", secs);
//		startActivity(intent);

		new AlertDialog.Builder(this)
				.setTitle("Delete entry")
				.setMessage("Are you sure you want to delete this entry?")
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// continue with delete
					}
				})
				.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// do nothing
					}
				})
				.show();
	}
}
