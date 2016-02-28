package model;

import java.util.ArrayList;

public class Query {
	private String query;
	private double[] execTimes;
	private String optimization;
	private ArrayList<String> createViews;
	private ArrayList<String> createIndexes;
	private ArrayList<String> dropIndexes;
	
	public Query(){
		execTimes = new double[10];
	}
	
	public Query(String query, String optimization) {
		this.query = query;
		this.optimization = optimization;
		execTimes = new double[10];
	}
	
	public Query(String query, String optimization, ArrayList<String> createIndexes, ArrayList<String> dropIndexes) {
		this.query = query;
		this.optimization = optimization;
		this.createIndexes = createIndexes;
		this.dropIndexes = dropIndexes;
		execTimes = new double[10];
	}
	
	public Query(String query, String optimization, ArrayList<String> createViews) {
		this.query = query;
		this.optimization = optimization;
		this.createViews = createViews;
		execTimes = new double[10];
	}
	
	public String getOptimization() {
		return optimization;
	}
	
	public ArrayList<String> getCreateIndexes() {
		return createIndexes;
	}
	
	public ArrayList<String> getDropIndexes() {
		return dropIndexes;
	}
	
	public ArrayList<String> getCreateViews() {
		return createViews;
	}
	
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public double getExecTimeAverage() {
		double sum = 0;
		for (int i = 0; i < execTimes.length; i++) {
			sum+=execTimes[i];
		}
		return sum/10;
	}
	
	public void setExecTimes(double[] execTimes) {
		this.execTimes = execTimes;
	}
	
	public double getTimeAt(int i) {
		return execTimes[i];
	}

}
