package model;

public class Query {
	private String query;
	private double execTime;
	
	public Query(String query) {
		this.query = query;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public double getExecTime() {
		return execTime;
	}
	public void setExecTime(double execTime) {
		this.execTime = execTime;
	}
	
	

}
