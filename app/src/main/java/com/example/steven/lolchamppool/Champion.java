package com.example.steven.lolchamppool;

/**
 * Created by Steven on 5/4/2016.
 */
public class Champion {
	String name;
	double kda;
	int cs;
	int mins;
	int secs;

	public Champion() {
		name = "";
		kda = -1;
		cs = -1;
		mins = -1;
		secs = -1;
	}

	public Champion(String name, double kda, int cs, int mins, int secs) {
		this.name = name;
		this.kda = kda;
		this.cs = cs;
		this.mins = mins;
		this.secs = secs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getKda() {
		return kda;
	}

	public void setKda(double kda) {
		this.kda = kda;
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
}
