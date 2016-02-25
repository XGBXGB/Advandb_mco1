package model;

public class QuerySet {
	private Query[] querries;
	private String description;
	private String name;
	
	public QuerySet(Query[] queries, String description, String name) {
		this.querries = queries;
		this.description = description;
		this.name = name;
	}
	
	public Query[] getQuerries() {
		return querries;
	}
	public void setQuerries(Query[] querries) {
		this.querries = querries;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
