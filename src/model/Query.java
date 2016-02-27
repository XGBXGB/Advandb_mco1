package model;

public class Query {
	private String query;
	private double[] execTimes;
	
	public Query(){
		execTimes = new double[10];
	}
	
	public Query(String query) {
		this.query = query;
		execTimes = new double[10];
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
