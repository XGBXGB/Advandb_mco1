package view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import model.QueriesController;

public class ProjectView extends JFrame implements ActionListener{
	private DefaultTableModel model;
	private JComboBox<String> queryCBox, optimizeCBox;
	private JButton queryBtn, addCondBtn;
	private JTable table, conditionTable;
	private JScrollPane tableSp, conditionSp;
	private JTextArea execTimeTArea;
	private JTabbedPane tabbedPane;
	private JPanel queryPanel;
	private JPanel timePanel;
	private TableFromMySqlDatabase tfmsd;
	private QueriesController qc;
	private ArrayList<ConditionPanel> conditions;
	
	public ProjectView(){
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
        
       
		table=new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		qc = new QueriesController();
		tfmsd = new TableFromMySqlDatabase();
		
		execTimeTArea = new JTextArea();
		execTimeTArea.setBounds(565,105,150,180);
		
		addCondBtn = new JButton("Add Condition");
	    addCondBtn.setBounds(400,295,150,30);
	    addCondBtn.addActionListener(this);
	    addCondBtn.setVisible(false);
		
		queryBtn = new JButton("Query");
		queryBtn.setBounds(565,75,150,25);
		
		queryCBox = new JComboBox<String>();
		queryCBox.addItem("1 Table");
		queryCBox.addItem("1 Table (2)");
		queryCBox.addItem("2 Tables w/ conditions");
		queryCBox.addItem("2 Tables w/ conditions (2)");
		queryCBox.addItem("3 Tables");
		queryCBox.addItem("3 Tables (2)");
		queryCBox.addItem("4 Tables");
		queryCBox.addActionListener(this);
		
		queryCBox.setBounds(565, 15, 150, 25);
		
		optimizeCBox = new JComboBox<String>();
		optimizeCBox.addItem("No optimization");
		optimizeCBox.addItem("Heuristic Optimization");
		optimizeCBox.addItem("Indices");
		optimizeCBox.addItem("Views");
		optimizeCBox.addItem("Stored Procedures");
		
		optimizeCBox.setBounds(565,45, 150,25);
		
	
		tableSp = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tableSp.setBounds(10,10,545,275);
		queryPanel = new JPanel();
		queryPanel.setBounds(0,0,750,500);
		queryPanel.add(execTimeTArea);
		queryPanel.add(queryBtn);
		queryPanel.add(queryCBox);
		queryPanel.add(optimizeCBox);
		queryPanel.add(tableSp);
		queryPanel.add(addCondBtn);
		queryPanel.setLayout(null);
		
		timePanel = new JPanel();
		timePanel.setLayout(null);
		
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Query Tab", queryPanel);
		tabbedPane.addTab("Time Table", timePanel);
		queryBtn.addActionListener(this);
		getContentPane().add(tabbedPane);
		
		setVisible(true);
		setSize(750,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == queryBtn){
			int i = queryCBox.getSelectedIndex();
			int j = optimizeCBox.getSelectedIndex();
			
			
			table.setModel(tfmsd.getResultTable(qc.getQuery(i, j)));
			resizeColumnWidth(table);
			
			
			queryPanel.repaint();
			queryPanel.revalidate();
		}
		else if(e.getSource() == queryCBox){
			if(queryCBox.getSelectedItem().toString().startsWith("2")){
				addCondBtn.setVisible(true);
				if(conditionSp==null){
					conditions = new ArrayList();
					conditionTable=new JTable(new ConditionTableModel(conditions));
					conditionTable.setDefaultEditor(ConditionPanel.class, new ConditionCellEditor());
					conditionTable.setDefaultRenderer(ConditionPanel.class, new ConditionCellRenderer());
					conditionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					conditionSp = new JScrollPane(conditionTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					conditionSp.setBounds(10,295,376,125);
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
			
			conditions.add(new ConditionPanel(this));
			conditionTable.setModel(new ConditionTableModel(conditions));
			conditionTable.getColumnModel().getColumn(0).setPreferredWidth(370);
			conditionTable.setRowHeight(35);
			System.out.println(conditions.size());
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
