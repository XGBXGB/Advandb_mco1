package model;

public class QueriesController {
	private QuerySet[] querySets;
	
	
	public QueriesController() {
		querySets = new QuerySet[7];
		Query[] queries = {
				new Query("SELECT * FROM hpq_hh LIMIT 1;"), 
				new Query("SELECT * FROM hpq_hh LIMIT 2;"),
				new Query("SELECT * FROM hpq_hh LIMIT 3;"),
				new Query("SELECT * FROM hpq_hh LIMIT 4;"),
				new Query("SELECT * FROM hpq_hh LIMIT 5;"),
				};
		querySets[0] = new QuerySet(queries, "description", "1 Table");
		querySets[1] = new QuerySet(queries, "description", "1 Table (2)");
		querySets[2] = new QuerySet(queries, "description", "2 Tables w/ conditions");
		querySets[3] = new QuerySet(queries, "description", "2 Tables w/ conditions (2)");
		querySets[4] = new QuerySet(queries, "description", "3 Tables");
		querySets[5] = new QuerySet(queries, "description", "3 Tables (2)");
		querySets[6] = new QuerySet(queries, "description", "4 Table");
	}
	
	public String getQuery(int i, int j) {
		return querySets[i].getQuerries()[j].getQuery();
	}

}
