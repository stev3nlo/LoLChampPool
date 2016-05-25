package com.example.steven.lolchamppool;

/**
 * Created by Steven on 5/4/2016.
 */
public class Champion {
	String name;
	double kills;
	double deaths;
	double assists;
	int cs;
	int mins;
	int secs;
	double winrate;

	public Champion() {
		name = "";
		kills = -1.0;
		deaths = -1.0;
		assists = -1.0;
		cs = -1;
		mins = -1;
		secs = -1;
		winrate = -1.0;
	}

	public Champion(String name) {
		this.name = name;
		kills = -1.0;
		deaths = -1.0;
		assists = -1.0;
		cs = -1;
		mins = -1;
		secs = -1;
		winrate = -1.0;
	}

	public Champion(String name, double kills, double deaths, double assists, int cs, int mins, int secs, double winrate) {
		this.name = name;
		this.kills = kills;
		this.deaths = deaths;
		this.assists = assists;
		this.cs = cs;
		this.mins = mins;
		this.secs = secs;
		this.winrate = winrate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getKills() {
		return kills;
	}

	public void setKills(double kills) {
		this.kills = kills;
	}

	public double getDeaths() {
		return deaths;
	}

	public void setDeaths(double deaths) {
		this.deaths = deaths;
	}

	public double getAssists() {
		return assists;
	}

	public void setAssists(double assists) {
		this.assists = assists;
	}

	public int getCs() {
		return cs;
	}

	public void setCs(int cs) {
		this.cs = cs;
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

	public double getWinrate() {
		return winrate;
	}

	public void setWinrate(double winrate) {
		this.winrate = winrate;
	}
}
