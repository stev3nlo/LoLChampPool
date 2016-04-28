package com.example.steven.lolchamppool;

/**
 * Created by Steven on 4/28/2016.
 */
public class Game {
	int id;
	String name;
	String role;
	int kills;
	int deaths;
	int assists;
	int creepScore;
	int mins;
	int secs;

	public Game() {
		id = -1;
		name = "";
		role = "";
		kills = -1;
		deaths = -1;
		assists = -1;
		creepScore = -1;
		mins = -1;
		secs = -1;
	}
}
