package view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import model.FilterQueryBuilder;
import model.QueriesController;
import model.Query;
import model.QuerySet;

public class ProjectView extends JFrame implements ActionListener{
	private DefaultTableModel model;
	private JLabel desc;
	private JComboBox<String> queryCBox, optimizeCBox;
	private JButton queryBtn, addCondBtn, timeBtn;
	private JTable table, conditionTable, timeTable;
	private JScrollPane tableSp, conditionSp, timeTableSp;
	private JTextArea execTimeTArea;
	private JTabbedPane tabbedPane;
	private JPanel queryPanel;
	private JPanel timePanel;
	private TableFromMySqlDatabase tfmsd;
	private QueriesController qc;
	private ArrayList<ConditionPanel> conditions;
	public ArrayList<String> columns1;
	public ArrayList<String> columns2;
	private String[][] values;
	
	public ProjectView(){
		qc = new QueriesController();
		tfmsd = new TableFromMySqlDatabase();
		
		desc = new JLabel("<html>List of the number of household members studying and how many of those are actively participating (for the past 12 months) in the food for school program per household that has a member that is not an ofw and works as a fisherman (mangingisda) that is not under the cash for work program who uses a fishing equipment that he/she does not own.</html>");
		desc.setBounds(10, 0, 710, 70);
		
		String columnNames[] = { "", "No Optimization", "Heuristic Optimization", "Indices", "Views", "Stored Procedures" };
		String[][] values = new String[7][6];
		values[0][0] = "1 Table";
		values[1][0] = "1 Table (1)";
		values[2][0] = "2 Table";
		values[3][0] = "2 Table (1)";
		values[4][0] = "3 Table";
		values[5][0] = "3 Table (1)";
		values[6][0] = "4 Table";
		timeTable = new JTable(values, columnNames){
            public boolean getScrollableTracksViewportWidth()
            {
                return getPreferredSize().width < getParent().getWidth();
            }
        };
		
		timeTableSp = new JScrollPane(timeTable);
		timeTableSp.setBounds(10,10,545,275);
		
		timeBtn = new JButton("Refresh Table");
		
		timeBtn.addActionListener(this);
		timeBtn.setBounds(600, 10, 100, 20);
		
		columns1 = new ArrayList();
		columns1.add("brgy");
		columns1.add("sex");
		columns1.add("age_yr");
		columns1.add("occup");
		columns1.add("gradel");
		columns1.add("work_ddhrs"); 
		columns1.add("sch_type");
		columns1.add("njob");
		columns1.add("jstatus");
		columns1.add("fadd_work_hrs");
		columns1.add("fxtra_wrk");
		columns1.add("workcl");
		columns1.add("pregind");
		columns1.add("pwd_ind");
		
		columns2 = new ArrayList();
		columns2.add("civstat");
		columns2.add("ynotsch");
		columns2.add("literind");
		columns2.add("regvotind");
		columns2.add("sss_ind");
		columns2.add("scid_ind");
		columns2.add("jobind");
		columns2.add("njob");
		columns2.add("jstatus");
		columns2.add("work_ddhrs");
		columns2.add("lastlookjob");
		columns2.add("wtwind");
		columns2.add("solo_parent");
		columns2.add("pwd_ind");
		
		UIManager.put("nimbusBase", new Color(255, 187, 0));
        UIManager.put("nimbusBlueGrey", new Color(3, 192, 60));
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }
        
		table=new JTable(){
            public boolean getScrollableTracksViewportWidth()
            {
                return getPreferredSize().width < getParent().getWidth();
            }
        };
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setEnabled(false);
		
		
		Font f = new Font(Font.SANS_SERIF, Font.PLAIN, 9);
		execTimeTArea = new JTextArea();
		execTimeTArea.setFont(f);
		execTimeTArea.setBounds(565,165,150,180);
		
		addCondBtn = new JButton("Add Condition");
	    addCondBtn.setBounds(400,375,150,30);
	    addCondBtn.addActionListener(this);
	    addCondBtn.setVisible(false);
		
		queryBtn = new JButton("Query");
		queryBtn.setBounds(565,135,150,25);
		queryBtn.addActionListener(this);
		
		queryCBox = new JComboBox<String>();
		queryCBox.addItem("1 Table");
		queryCBox.addItem("1 Table (2)");
		queryCBox.addItem("2 Tables w/ conditions");
		queryCBox.addItem("2 Tables w/ conditions (2)");
		queryCBox.addItem("3 Tables");
		queryCBox.addItem("3 Tables (2)");
		queryCBox.addItem("4 Tables");
		queryCBox.addActionListener(this);
		queryCBox.setBounds(565, 75, 150, 25);
		
		optimizeCBox = new JComboBox<String>();
		optimizeCBox.addItem("No optimization");
		optimizeCBox.addItem("Heuristic Optimization");
		optimizeCBox.addItem("Indices");
		optimizeCBox.addItem("Views");
		optimizeCBox.addItem("Stored Procedures");
		optimizeCBox.setBounds(565,105, 150,25);
		
		tableSp = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		tableSp.setBounds(10,70,545,275);
		
		queryPanel = new JPanel();
		queryPanel.setBounds(0,0,750,500);
		queryPanel.add(execTimeTArea);
		queryPanel.add(queryBtn);
		queryPanel.add(queryCBox);
		queryPanel.add(optimizeCBox);
		queryPanel.add(tableSp);
		queryPanel.add(addCondBtn);
		queryPanel.add(desc);
		queryPanel.setLayout(null);
		
		timePanel = new JPanel();
		timePanel.setBounds(0, 0, 750, 500);
		timePanel.add(timeTableSp);
		timePanel.add(timeBtn);
		timePanel.setLayout(null);
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Query Tab", queryPanel);
		tabbedPane.addTab("Time Table", timePanel);
		
		getContentPane().add(tabbedPane);
		setVisible(true);
		setSize(750,560);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == queryBtn){
			
			int i = queryCBox.getSelectedIndex();
			int j = optimizeCBox.getSelectedIndex();
			Query q = qc.getQueryObject(i, j);
			if(queryCBox.getSelectedItem().toString().startsWith("2")){
				if(conditions!=null){
					FilterQueryBuilder query = ((FilterQueryBuilder)q).getCopy();
					for(int x=0; x<conditions.size(); x++){
						query.addCondition(conditions.get(x).getQueryCondition());
					}
					table.setModel(tfmsd.getResultTable(query));
					resizeColumnWidth(table);
					System.out.println(query.getQuery());
					q=query;
				}
			} else{
				table.setModel(tfmsd.getResultTable(q));
				resizeColumnWidth(table);
			}
			qc.query10Times(i, j, q);	
			q = qc.getQueryObject(i, j);
			String s = "";
			for (int k = 0; k < 10; k++) {
				switch(k) {
				case 0: s += "1st "; break;
				case 1: s += "2nd "; break;
				case 2: s += "3rd "; break;
				default: s += (k+1) + "th "; break;
				}
				s += q.getTimeAt(k) + "\n";
			}
			s+= "Ave: " + q.getExecTimeAverage();
			execTimeTArea.setText(s);
			
		}
		else if(e.getSource() == queryCBox){
			int i = queryCBox.getSelectedIndex();
			int j = optimizeCBox.getSelectedIndex();
			desc.setText("<html>" + qc.getQuerySets()[i].getDescription() + "</html>");
			if(queryCBox.getSelectedItem().toString().startsWith("2")){
				addCondBtn.setVisible(true);
				if(conditionSp==null){
					conditions = new ArrayList();
					conditionTable=new JTable(new ConditionTableModel(conditions));
					conditionTable.setDefaultEditor(ConditionPanel.class, new ConditionCellEditor());
					conditionTable.setDefaultRenderer(ConditionPanel.class, new ConditionCellRenderer());
					conditionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					conditionSp = new JScrollPane(conditionTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					conditionSp.setBounds(10,355,376,125);
					conditionTable.getColumnModel().getColumn(0).setPreferredWidth(370);
					conditionTable.setRowHeight(80);
					queryPanel.add(conditionSp);
				}else{
					conditions.clear();
					conditionTable.setModel(new ConditionTableModel(conditions));
					conditionTable.getColumnModel().getColumn(0).setPreferredWidth(370);
					conditionTable.setRowHeight(80);
				}
			}else{
				if(conditionSp!=null){
					addCondBtn.setVisible(false);
					conditions = null;
					queryPanel.remove(conditionSp);
					conditionSp=null;
					queryPanel.revalidate();
					queryPanel.repaint();
				}
			}
		}else if(e.getSource() == addCondBtn){
			if(queryCBox.getSelectedItem().toString().endsWith("(2)"))
				conditions.add(new ConditionPanel(this, columns2));
			else
				conditions.add(new ConditionPanel(this, columns1));
			
			conditionTable.setModel(new ConditionTableModel(conditions));
			conditionTable.getColumnModel().getColumn(0).setPreferredWidth(370);
			conditionTable.setRowHeight(35);
		} else if(e.getSource() == timeBtn) {
			qc.setTimes();
			DecimalFormat df = new DecimalFormat("#.000000");
			QuerySet[] qss = qc.getQuerySets();
			for (int i = 0; i < qss.length; i++) {
				Query[] qs = qss[i].getQuerries();
				for (int j = 0; j < qs.length; j++) {
					timeTable.setValueAt(String.valueOf(df.format(qs[j].getExecTimeAverage())), i, j+1);
				}
			}
		}
	}
	
	public void removePanel(ConditionPanel object){
		conditions.remove(object);
		conditionTable.setModel(new ConditionTableModel(conditions));
		conditionTable.getColumnModel().getColumn(0).setPreferredWidth(370);
		conditionTable.setRowHeight(35);
		
	}
	
	public void resizeColumnWidth(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 50; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}
	
	public static void main(String[] args){
		ProjectView pv = new ProjectView();
	}
}
