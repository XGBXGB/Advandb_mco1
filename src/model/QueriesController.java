package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class QueriesController {
	private QuerySet[] querySets;

	public QueriesController() {
		querySets = new QuerySet[7];

		FilterQueryBuilder twoTablesOptimiz1, twoTablesOptimiz2, twoTablesOptimiz3, twoTablesOptimiz4;
		twoTablesOptimiz1 = new FilterQueryBuilder();
		twoTablesOptimiz1.addColumn("H.id, M.memno, H.brgy, M.sex, M.age_yr, M.occup, M.work_ddhrs");
		twoTablesOptimiz1.addTable("hpq_mem M, hpq_hh H");
		twoTablesOptimiz1.addCondition("jobind=1 AND age_yr<18 and educind=1 AND H.id = M.id");

		twoTablesOptimiz2 = new FilterQueryBuilder();
		twoTablesOptimiz2.addColumn("H.id, M.memno, H.brgy, M.sex, M.age_yr, M.occup, M.work_ddhrs");
		twoTablesOptimiz2.addTable("(SELECT id, memno, sex, age_yr, occup, work_ddhrs FROM hpq_mem "
				+ "WHERE jobind=1 AND age_yr<18 and educind=1) M JOIN (SELECT id, brgy FROM hpq_hh) H "
				+ "ON H.id = M.id");

		twoTablesOptimiz4 = new FilterQueryBuilder();
		twoTablesOptimiz4.addColumn("H.id, M.memno, H.brgy, M.sex, M.age_yr, M.occup, M.work_ddhrs");
		twoTablesOptimiz4.addTable("filteredMem_v M, brgyIdHH_v H");
		twoTablesOptimiz4.addCondition("H.id = M.id");

		Query[] twoTablesSet1 = { twoTablesOptimiz1, twoTablesOptimiz2, new Query("SELECT * FROM hpq_hh LIMIT 3;"),
				twoTablesOptimiz4, new Query("SELECT * FROM hpq_hh LIMIT 5;") };

		Query[] queries = {
				new Query(
						"SELECT psced7, count(*) FROM hpq_mem WHERE educal in (300, 400) AND jobind = 2 GROUP BY psced7"),
				new Query(
						"SELECT psced7, count(*) FROM (SELECT psced7 FROM hpq_mem WHERE educal in (300, 400) AND jobind = 2) t GROUP BY psced7"),
				new Query(
						"CREATE INDEX member_psced7_idx ON hpq_mem (psced7); CREATE INDEX member_educal_idx ON hpq_mem (educal); CREATE INDEX member_jobind_idx ON hpq_mem (jobind); SELECT psced7, count(*) FROM hpq_mem WHERE educal in (300, 400) AND jobind = 2 GROUP BY psced7; DROP INDEX member_psced7_idx ON hpq_mem; DROP INDEX member_educal_idx ON hpq_mem;DROP INDEX member_jobind_idx ON hpq_mem;"),
				new Query(
						"CREATE OR REPLACE VIEW coursesView AS SELECT psced7FROM hpq_memWHERE educal in (300, 400) AND jobind = 2;            SELECT psced7, count(psced7)FROM coursesViewGROUP BY psced7;"),
				new Query("SELECT * FROM hpq_hh LIMIT 5;"), };
		querySets[0] = new QuerySet(queries, "description", "1 Table");
		querySets[1] = new QuerySet(queries,
				"Count of members who have college/masters/doctral degree but are unemployed per course.",
				"1 Table (2)");
		querySets[2] = new QuerySet(twoTablesSet1,
				"List of members that are aged 18 and below who are already working but at the same time still attending school.",
				"2 Tables w/ conditions");
		querySets[3] = new QuerySet(queries,
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

	public void query10Times(int i, int j, String queryString) {
		try {
			Connection connection = DBConnect.getConnection();
			Statement stmt = connection.createStatement();
			double[] execTimes = new double[10];
			for (int k = 0; k < execTimes.length; k++) {

				long startTime = System.currentTimeMillis();
				stmt.execute(queryString);
				long endTime = System.currentTimeMillis();
				execTimes[k] = (endTime - startTime) / 1000.0;
				System.out.println(execTimes[k]);
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
					double[] execTimes = new double[10];
					System.out.println(qs[j].getQuery());
					for (int k = 0; k < execTimes.length; k++) {

						long startTime = System.currentTimeMillis();
						stmt.execute(qs[j].getQuery());
						long endTime = System.currentTimeMillis();
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
