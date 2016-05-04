package com.example.steven.lolchamppool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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

	public Game getGame(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_STATS, new String[]{KEY_ID, KEY_CHAMPION_NAME, KEY_ROLE, KEY_KILLS,
						KEY_DEATHS, KEY_ASSISTS, KEY_CREEPSCORE, KEY_MINS, KEY_SECS}, KEY_ID + "=?", new String[]{String.valueOf(id)},
				null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Game game = new Game(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
				Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)),
				Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor.getString(7)), Integer.parseInt(cursor.getString(8)));

		return game;
	}

	public void addGame(Game game) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_ID, game.getId());
		values.put(KEY_CHAMPION_NAME, game.getName());
		values.put(KEY_ROLE, game.getRole());
		values.put(KEY_KILLS, game.getKills());
		values.put(KEY_DEATHS, game.getDeaths());
		values.put(KEY_ASSISTS, game.getAssists());
		values.put(KEY_CREEPSCORE, game.getCreepScore());
		values.put(KEY_MINS, game.getMins());
		values.put(KEY_SECS, game.getSecs());

		db.insert(TABLE_STATS, null, values);
		db.close();
	}

	public ArrayList<Game> getAllGames() {
		ArrayList<Game> games = new ArrayList<>();
		String selectQuery = "SELECT * FROM " + TABLE_STATS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Game game = new Game();
				game.setId(Integer.parseInt(cursor.getString(0)));
				game.setName(cursor.getString(1));
				game.setRole(cursor.getString(2));
				game.setKills(Integer.parseInt(cursor.getString(3)));
				game.setDeaths(Integer.parseInt(cursor.getString(4)));
				game.setAssists(Integer.parseInt(cursor.getString(5)));
				game.setCreepScore(Integer.parseInt(cursor.getString(6)));
				game.setMins(Integer.parseInt(cursor.getString(7)));
				game.setSecs(Integer.parseInt(cursor.getString(8)));
			} while (cursor.moveToNext());
		}
		return games;
	}

	public int getGameCount() {
		String countQuery = "SELECT * FROM " + TABLE_STATS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		return cursor.getCount();
	}

	public int updateGame(Game game) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		return db.update(TABLE_STATS, values, KEY_ID + " = ?", new String[]{String.valueOf(game.getId())});
	}

	public void deleteGame(Game game) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_STATS, KEY_ID + " = ?", new String[]{String.valueOf(game.getId())});
		db.close();
	}

	public void clearAll() {
		for (int i = 0; i < getGameCount() - 1; i++) {
			deleteGame(getGame(i));
		}
	}
}