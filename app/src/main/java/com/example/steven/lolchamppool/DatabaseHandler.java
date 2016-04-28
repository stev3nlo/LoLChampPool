package com.example.steven.lolchamppool;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Steven on 4/28/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "ChampionStats";
	private static final String TABLE_STATS = "stats";

	private static final String KEY_ID = "ID";
	private static final String KEY_CHAMPION_NAME = "Name";
	private static final String KEY_ROLE = "Role";
	private static final String KEY_KILLS = "Kills";
	private static final String KEY_DEATHS = "Deaths";
	private static final String KEY_ASSISTS = "Assists";
	private static final String KEY_CREEPSCORE = "Creep Score";
	private static final String KEY_MINS = "Minutes";
	private static final String KEY_SECS = "Seconds";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_STAT_TABLE = "CREATE TABLE " + TABLE_STATS + "(" + KEY_ID +
				" INTEGER PRIMARY KEY," + KEY_CHAMPION_NAME + " TEXT," + KEY_ROLE + " TEXT," +
				KEY_KILLS + " TEXT," + KEY_DEATHS + " TEXT," + KEY_ASSISTS + " TEXT," +
				KEY_CREEPSCORE + " TEXT," + KEY_MINS + " TEXT," + KEY_SECS + " TEXT" + ")";
		db.execSQL(CREATE_STAT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATS);

		onCreate(db);
	}

	public void addGame(Game game) {

	}
}
