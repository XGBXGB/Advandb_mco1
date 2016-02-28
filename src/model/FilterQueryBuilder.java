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
            groupBy = groupBy + " " + group + ",";
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
    	String query ="";
    	query += select.substring(0, select.length() - 1) +" "+ from.substring(0, from.length() - 1) + " ";
    	if(where.length()>6)
    		query +=where.substring(0, where.length() - 4)+" ";
    	if(groupBy.length()!=0)
    		query +=groupBy.substring(0, groupBy.length() - 1);
    	if(having.length()!=0)
    		query +=having;
    	if(orderBy.length()!=0)
    		query +=orderBy;
    	return query;
    };
    
    public FilterQueryBuilder getCopy(){
    	FilterQueryBuilder o = new FilterQueryBuilder();
    	o.setSelect(select);
    	o.setFrom(from);
    	o.setWhere(where);
    	o.setGroupBy(groupBy);
    	o.setHaving(having);
    	o.setOrderBy(orderBy);
    	
    	return o;
    }
    
    /*
    public static void main (String[] args){
    	FilterQueryBuilder f = new FilterQueryBuilder();
    	f = new FilterQueryBuilder();
		f.addColumn("H.id, M.memno, H.brgy, M.sex, M.age_yr, M.occup, M.work_ddhrs");
		f.addTable("(SELECT id, memno, sex, age_yr, occup, work_ddhrs FROM hpq_mem "
				+ "WHERE jobind=1 AND age_yr<18 and educind=1) M JOIN (SELECT id, brgy FROM hpq_hh) H "
				+ "ON H.id = M.id");
		System.out.println(f.getQuery());
    }
    */
    
    
}
