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
	boolean win;

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
		win = false;
	}

	public Game(int id, String name, String role, int kills, int deaths, int assists, int creepScore, int mins, int secs, boolean win) {
		this.id = id;
		this.name = name;
		this.role = role;
		this.kills = kills;
		this.deaths = deaths;
		this.assists = assists;
		this.creepScore = creepScore;
		this.mins = mins;
		this.secs = secs;
		this.win = win;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getAssists() {
		return assists;
	}

	public void setAssists(int assists) {
		this.assists = assists;
	}

	public int getCreepScore() {
		return creepScore;
	}

	public void setCreepScore(int creepScore) {
		this.creepScore = creepScore;
	}

	public int getMins() {
		return mins;
	}

	public void setMins(int mins) {
		this.mins = mins;
	}

	public int getSecs() {
		return secs;
	}

	public void setSecs(int secs) {
		this.secs = secs;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}
}