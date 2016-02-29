/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Iterator;

/**
 *
 * @author Christian Gabriel
 */
public class FilterQueryBuilder extends Query{
	private String select;
    private String from;
    private String where;
    private String groupBy;
    private String having;
    private String orderBy;
    private String runnerSP;
    private boolean sp=false;
    

    
    public void setSpTrue(){
    	sp=true;
    }
    
    public String getRunnerSP() {
		return runnerSP;
	}

	public void setRunnerSP(String runnerSP) {
		this.runnerSP = runnerSP;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public void setHaving(String having) {
		this.having = having;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public FilterQueryBuilder() {
        select = "";
        from = "";
        where = "WHERE ";
        groupBy = "";
        having = "";
        orderBy = "";
    }

    public void addColumn(String column) {
        if (select.length() == 0) {
            select = "SELECT " + column + ",";
        } else {
            select = select + " " + column + ",";
        }
    }

    public void addTable(String table) {
        if (from.length() == 0) {
            from = "FROM " + table + ",";
        } else {
            from = from + " " + table + ",";
        }
    }

    public void addCondition(String condition){
    	where = where + condition +" AND ";
    };
    
    public void addGrouping(String group) {
        if (groupBy.length() == 0) {
            groupBy = "GROUP BY " + group + ", ";
        } else {
            groupBy = groupBy + " " + group + ", ";
        }
    }

    public void addGroupingCondition(String groupCondition) {
        if (having.length() == 0) {
            having = "HAVING " + groupCondition + " ";
        }
    }

    public void addOrderCriteria(String orderCriteria) {
        if (orderBy.length() == 0) {
            orderBy = "ORDER BY " + orderCriteria + " ";
        }
    }

    public String getQuery(){
    	if(sp == false){
	    	String query ="";
	    	query += select.substring(0, select.length() - 1) +" "+ from.substring(0, from.length() - 1) + " ";
	    	if(where.length()>6)
	    		query +=where.substring(0, where.length() - 4)+" ";
	    	if(groupBy.length()!=0)
	    		query +=groupBy.substring(0, groupBy.length() - 2);
	    	if(having.length()!=0)
	    		query +=having;
	    	if(orderBy.length()!=0)
	    		query +=orderBy;
	    	return query;
    	}else{
    		return runnerSP;
    	}
    	
    };
    
    public FilterQueryBuilder getCopy(){
    	FilterQueryBuilder o = new FilterQueryBuilder();
    	o.setSelect(select);
    	o.setFrom(from);
    	o.setWhere(where);
    	o.setGroupBy(groupBy);
    	o.setHaving(having);
    	o.setOrderBy(orderBy);
    	o.setOptimization(optimization);
    	o.setCreateIndexes(createIndexes);
    	o.setDropIndexes(dropIndexes);
    	o.setCreateViews(createViews);
    	
    	return o;
    }
    
    /*
    public static void main (String[] args){
    	FilterQueryBuilder f = new FilterQueryBuilder();
    	f.addColumn("id, memno");
		f.addTable("hpq_mem");
		f.addCondition("educal NOT IN(210,300,400) AND age_yr>20 AND educind=2 AND sex=1");
		f.addGrouping("brgy");
		System.out.println(f.getQuery());
    }*/
    
    
    
}
