package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class QueriesController {
	private QuerySet[] querySets;

	public QueriesController() {
		ArrayList<String> createIndexes, dropIndexes, createViews;
		querySets = new QuerySet[7];

		FilterQueryBuilder twoTablesOptimiz1, twoTablesOptimiz2, twoTablesOptimiz3, twoTablesOptimiz4;
		FilterQueryBuilder twoTables2Optimiz1, twoTables2Optimiz2, twoTables2Optimiz3, twoTables2Optimiz4;
		
		//TABLE2 W/ COND (1) NO OPTIMIZATION//
		twoTablesOptimiz1 = new FilterQueryBuilder();
		twoTablesOptimiz1.addColumn("H.id, M.memno, H.brgy, M.sex, M.age_yr, M.occup, M.work_ddhrs");
		twoTablesOptimiz1.addTable("hpq_mem M, hpq_hh H");
		twoTablesOptimiz1.addCondition("jobind=1 AND age_yr<18 and educind=1 AND H.id = M.id");
		twoTablesOptimiz1.setOptimization("No Optimization");
		//TABLE2 W/ COND (1) NO OPTIMIZATION//

		//TABLE2 W/ COND (1) HEURISTIC OPTIMIZATION//
		twoTablesOptimiz2 = new FilterQueryBuilder();
		twoTablesOptimiz2.addColumn("H.id, M.memno, H.brgy, M.sex, M.age_yr, M.occup, M.work_ddhrs");
		twoTablesOptimiz2.addTable("(SELECT id, memno, sex, age_yr, occup, work_ddhrs FROM hpq_mem "
				+ "WHERE jobind=1 AND age_yr<18 and educind=1) M JOIN (SELECT id, brgy FROM hpq_hh) H "
				+ "ON H.id = M.id");
		twoTablesOptimiz2.setOptimization("Heuristic Optimization");
		//TABLE2 W/ COND (1) HEURISTIC OPTIMIZATION//
		
		//TABLE2 W/ COND (1) INDEXES OPTIMIZATION//
		createIndexes = new ArrayList<String>();
		createIndexes.add("CREATE INDEX mem_id_idx ON hpq_mem(id);");
		createIndexes.add("CREATE INDEX mem_no_idx ON hpq_mem(memno);");
		createIndexes.add("CREATE INDEX mem_sex_idx ON hpq_mem(sex);");
		createIndexes.add("CREATE INDEX mem_age_idx ON hpq_mem(age_yr);");
		createIndexes.add("CREATE INDEX mem_occup_idx ON hpq_mem(occup);");
		createIndexes.add("CREATE INDEX mem_workhrs_idx ON hpq_mem(work_ddhrs);");
		createIndexes.add("CREATE INDEX hh_id_idx ON hpq_hh(id);");
		createIndexes.add("CREATE INDEX hh_brgy_idx ON hpq_hh(brgy);");
		
		dropIndexes = new ArrayList<String>();
		dropIndexes.add("DROP INDEX mem_id_idx ON hpq_mem;");
		dropIndexes.add("DROP INDEX mem_no_idx ON hpq_mem;");
		dropIndexes.add("DROP INDEX mem_sex_idx ON hpq_mem;");
		dropIndexes.add("DROP INDEX mem_age_idx ON hpq_mem;");
		dropIndexes.add("DROP INDEX mem_occup_idx ON hpq_mem;");
		dropIndexes.add("DROP INDEX mem_workhrs_idx ON hpq_mem;");
		dropIndexes.add("DROP INDEX hh_id_idx ON hpq_hh;");
		dropIndexes.add("DROP INDEX hh_brgy_idx ON hpq_hh;");
		
		twoTablesOptimiz3 = new FilterQueryBuilder();
		twoTablesOptimiz3.addColumn("H.id, M.memno, H.brgy, M.sex, M.age_yr, M.occup, M.work_ddhrs");
		twoTablesOptimiz3.addTable("hpq_mem M, hpq_hh H");
		twoTablesOptimiz3.setOptimization("Indexes");
		twoTablesOptimiz3.addCondition("jobind=1 AND age_yr<18 and educind=1 AND H.id = M.id");
		twoTablesOptimiz3.setCreateIndexes(createIndexes);
		twoTablesOptimiz3.setDropIndexes(dropIndexes);
		//TABLE2 W/ COND (1) INDEXES OPTIMIZATION//
		
		
		//TABLE2 W/ COND (1) VIEWS OPTIMIZATION//
		createViews = new ArrayList<String>();
		createViews.add("CREATE OR REPLACE VIEW filteredMem_v AS SELECT id, memno, sex, age_yr, occup, work_ddhrs "
				+ "FROM hpq_mem WHERE jobind=1 AND age_yr<18 and educind=1;");
		createViews.add("CREATE OR REPLACE VIEW brgyIdHH_v AS SELECT id,brgy FROM hpq_hh;");
		
		twoTablesOptimiz4 = new FilterQueryBuilder();
		twoTablesOptimiz4.addColumn("H.id, M.memno, H.brgy, M.sex, M.age_yr, M.occup, M.work_ddhrs");
		twoTablesOptimiz4.addTable("filteredMem_v M, brgyIdHH_v H");
		twoTablesOptimiz4.addCondition("H.id = M.id");
		twoTablesOptimiz4.setOptimization("Views");
		twoTablesOptimiz4.setCreateViews(createViews);
		//TABLE2 W/ COND (1) VIEWS OPTIMIZATION//
		
		

		Query[] twoTablesSet1 = { twoTablesOptimiz1, twoTablesOptimiz2, twoTablesOptimiz3,
				twoTablesOptimiz4, new Query("SELECT * FROM hpq_hh LIMIT 5;", "") };

		
		//TABLE2 W/ COND (1) NO OPTIMIZATION//
		twoTables2Optimiz1 = new FilterQueryBuilder();
		twoTables2Optimiz1.addColumn("brgy, COUNT(*)");
		twoTables2Optimiz1.addTable("hpq_mem M, hpq_hh H");
		twoTables2Optimiz1.addCondition("educal NOT IN (210,300,400) AND age_yr>20 AND educind=2 AND sex=1 "
				+ "AND M.id=H.id");
		twoTables2Optimiz1.addGrouping("brgy");
		twoTables2Optimiz1.setOptimization("No Optimization");
		//TABLE2 W/ COND (1) NO OPTIMIZATION//

		//TABLE2 W/ COND (1) HEURISTIC OPTIMIZATION//
		twoTables2Optimiz2 = new FilterQueryBuilder();
		twoTables2Optimiz2.addColumn("brgy, COUNT(*)");
		twoTables2Optimiz2.addTable("(SELECT id, memno FROM hpq_mem WHERE educal NOT IN(210,300,400) "
				+ "AND age_yr>20 AND educind=2 AND sex=1) M JOIN (SELECT id,brgy FROM hpq_hh) H ON H.id=M.id");
		twoTables2Optimiz2.addGrouping("brgy");
		twoTables2Optimiz2.setOptimization("Heuristic Optimization");
		//TABLE2 W/ COND (1) HEURISTIC OPTIMIZATION//
		
		//TABLE2 W/ COND (1) INDEXES OPTIMIZATION//
		createIndexes = new ArrayList<String>();
		createIndexes.add("CREATE INDEX hh_brgy_idx ON hpq_hh(brgy);");
		createIndexes.add("CREATE INDEX hh_id_idx ON hpq_hh(id);");
		createIndexes.add("CREATE INDEX mem_educal_idx ON hpq_mem(educal);");
		createIndexes.add("CREATE INDEX mem_age_idx ON hpq_mem(age_yr);");
		createIndexes.add("CREATE INDEX mem_educind_idx ON hpq_mem(educind);");
		createIndexes.add("CREATE INDEX mem_sex_idx ON hpq_mem(sex);");
		createIndexes.add("CREATE INDEX mem_id_idx ON hpq_mem(id);");
		
		dropIndexes = new ArrayList<String>();
		dropIndexes.add("DROP INDEX hh_brgy_idx ON hpq_hh;");
		dropIndexes.add("DROP INDEX hh_id_idx ON hpq_hh;");
		dropIndexes.add("DROP INDEX mem_educal_idx ON hpq_mem;");
		dropIndexes.add("DROP INDEX mem_age_idx ON hpq_mem;");
		dropIndexes.add("DROP INDEX mem_educind_idx ON hpq_mem;");
		dropIndexes.add("DROP INDEX mem_sex_idx ON hpq_mem;");
		dropIndexes.add("DROP INDEX mem_id_idx ON hpq_mem;");
		
		twoTables2Optimiz3 = new FilterQueryBuilder();
		twoTables2Optimiz3.addColumn("brgy, COUNT(*)");
		twoTables2Optimiz3.addTable("hpq_mem M, hpq_hh H");
		twoTables2Optimiz3.setOptimization("Indexes");
		twoTables2Optimiz3.addCondition("educal NOT IN (210,300,400) AND age_yr>20 AND educind=2 AND sex=1 AND M.id=H.id");
		twoTables2Optimiz3.addGrouping("brgy");
		twoTables2Optimiz3.setCreateIndexes(createIndexes);
		twoTables2Optimiz3.setDropIndexes(dropIndexes);
		//TABLE2 W/ COND (1) INDEXES OPTIMIZATION//
		
		
		//TABLE2 W/ COND (1) VIEWS OPTIMIZATION//
		createViews = new ArrayList<String>();
		createViews.add("CREATE OR REPLACE VIEW filteredMem_v AS SELECT id, memno FROM hpq_mem "
				+ "WHERE educal NOT IN(210,300,400) AND age_yr>20 AND educind=2 AND sex=1;");
		createViews.add("CREATE OR REPLACE VIEW brgyIdHH_v AS SELECT id,brgy FROM hpq_hh;");
		
		twoTables2Optimiz4 = new FilterQueryBuilder();
		twoTables2Optimiz4.addColumn("brgy, COUNT(*)");
		twoTables2Optimiz4.addTable("filteredMem_v M, brgyIdHH_v H");
		twoTables2Optimiz4.addCondition("H.id=M.id");
		twoTables2Optimiz4.addGrouping("brgy");
		twoTables2Optimiz4.setOptimization("Views");
		twoTables2Optimiz4.setCreateViews(createViews);
		//TABLE2 W/ COND (1) VIEWS OPTIMIZATION//
		
		Query[] twoTablesSet2 = { twoTables2Optimiz1, twoTables2Optimiz2, twoTables2Optimiz3,
				twoTables2Optimiz4, new Query("SELECT * FROM hpq_hh LIMIT 5;", "") };
		
		
		
		
		
		
		
		
		
		createIndexes = new ArrayList<String>();
		createIndexes.add("CREATE INDEX member_psced7_idx ON hpq_mem (psced7);");
		createIndexes.add("CREATE INDEX member_educal_idx ON hpq_mem (educal);");
		createIndexes.add("CREATE INDEX member_jobind_idx ON hpq_mem (jobind);");
		
		
		
		createViews = new ArrayList<String>();
		createViews.add("CREATE OR REPLACE VIEW coursesView AS SELECT psced7 FROM hpq_mem  WHERE educal in (300, 400) AND jobind = 2;");
		
		Query[] queries = {
				new Query(
						"SELECT psced7, count(*) FROM hpq_mem WHERE educal in (300, 400) AND jobind = 2 GROUP BY psced7",
						"No Optimization"),
				new Query(
						"SELECT psced7, count(*) FROM (SELECT psced7 FROM hpq_mem WHERE educal in (300, 400) AND jobind = 2) t GROUP BY psced7", 
						"Heuristic Optimization"),
				new Query(
						"SELECT psced7, count(*) FROM hpq_mem WHERE educal in (300, 400) AND jobind = 2 GROUP BY psced7; ", 
						"Indexes",
						createIndexes, dropIndexes),
				new Query(
						"SELECT psced7, count(psced7)FROM coursesView GROUP BY psced7;",
						"Views",
						createViews),
				new Query(
						"SELECT * FROM hpq_hh LIMIT 5;",
						"Stored Procedures"), };
		querySets[0] = new QuerySet(queries, "description", "1 Table");
		querySets[1] = new QuerySet(queries,
				"Count of members who have college/masters/doctral degree but are unemployed per course.",
				"1 Table (2)");
		querySets[2] = new QuerySet(twoTablesSet1,
				"List of members that are aged 18 and below who are already working but at the same time still attending school.",
				"2 Tables w/ conditions");
		querySets[3] = new QuerySet(twoTablesSet2,
				"Count of male members per barangay who are not attending school anymore,aged > 22, and are not college graduates,",
				"2 Tables w/ conditions (2)");
		querySets[4] = new QuerySet(queries,
				"Average number of child (age < 18) deaths in a barangay per barangay adult (age >= 18) unemployment rate",
				"3 Tables");
		querySets[5] = new QuerySet(queries,
				"Average age of Philhealth - individually paying members per barangay, for households with at least 2 Philhealth - individually paying members",
				"3 Tables (2)");
		querySets[6] = new QuerySet(queries,
				"List of the number of household members studying and how many of those are actively participating (for the past 12 months) in the food for school program per household that has a member that is not an ofw and works as a fisherman (mangingisda) that is not under the cash for work program who uses a fishing equipment that he/she does not own.",
				"4 Table");
	}

	public String getQuery(int i, int j) {
		return querySets[i].getQuerries()[j].getQuery();
	}

	public Query getQueryObject(int i, int j) {
		return querySets[i].getQuerries()[j];
	}

	public QuerySet[] getQuerySets() {
		return querySets;
	}

	public void query10Times(int i, int j, Query q) {
		try {
			Connection connection = DBConnect.getConnection();
			Statement stmt = connection.createStatement();
			double[] execTimes = new double[10];
			for (int k = 0; k < execTimes.length; k++) {
				long startTime = 0;
				long endTime = 0;
				if(q.getOptimization().equals("No Optimization") || q.getOptimization().equals("Heuristic Optimization")) {
					startTime = System.currentTimeMillis();
					stmt.executeQuery(q.getQuery());
					endTime = System.currentTimeMillis();
				} else if(q.getOptimization().equals("Indexes")) {
					
					for (int l = 0; l < q.getCreateIndexes().size(); l++) {
						stmt.execute(q.getCreateIndexes().get(l));
					}
					startTime = System.currentTimeMillis();
					stmt.executeQuery(q.getQuery());
					endTime = System.currentTimeMillis();
					for (int l = 0; l < q.getDropIndexes().size(); l++) {
						stmt.execute(q.getDropIndexes().get(l));
					}
					
				} else if(q.getOptimization().equals("Views")) {
					for (int l = 0; l < q.getCreateViews().size(); l++) {
						stmt.execute(q.getCreateViews().get(l));
					}
					startTime = System.currentTimeMillis();
					stmt.executeQuery(q.getQuery());
					endTime = System.currentTimeMillis();
					
				}
				execTimes[k] = (endTime - startTime) / 1000.0;
			}
			querySets[i].getQuerries()[j].setExecTimes(execTimes);
		} catch (Exception e) {

		}
	}

	public void setTimes() {
		try {
			Connection connection = DBConnect.getConnection();
			Statement stmt = connection.createStatement();
			for (int i = 0; i < querySets.length; i++) {
				Query[] qs = querySets[i].getQuerries();
				for (int j = 0; j < qs.length; j++) {
					Query q = querySets[i].getQuerries()[j];
					double[] execTimes = new double[10];
					System.out.println(qs[j].getQuery());
					for (int k = 0; k < execTimes.length; k++) {

						long startTime = 0;
						long endTime = 0;
						if(q.getOptimization().equals("No Optimization") || q.getOptimization().equals("Heuristic Optimization")) {
							startTime = System.currentTimeMillis();
							stmt.executeQuery(q.getQuery());
							endTime = System.currentTimeMillis();
						} else if(q.getOptimization().equals("Indexes")) {
							for (int l = 0; l < q.getCreateIndexes().size(); l++) {
								stmt.execute(q.getCreateIndexes().get(l));
							}
							startTime = System.currentTimeMillis();
							stmt.executeQuery(q.getQuery());
							endTime = System.currentTimeMillis();
							for (int l = 0; l < q.getDropIndexes().size(); l++) {
								stmt.execute(q.getDropIndexes().get(l));
							}
						} else if(q.getOptimization().equals("Views")) {
							for (int l = 0; l < q.getCreateViews().size(); l++) {
								stmt.executeUpdate(q.getCreateViews().get(l));
							}
							startTime = System.currentTimeMillis();
							stmt.executeQuery(q.getQuery());
							endTime = System.currentTimeMillis();
							
						}
						execTimes[k] = (endTime - startTime) / 1000.0;
						System.out.println(execTimes[k]);
					}
					querySets[i].getQuerries()[j].setExecTimes(execTimes);
				}
			}
		} catch (Exception e) {

		}

	}

}
