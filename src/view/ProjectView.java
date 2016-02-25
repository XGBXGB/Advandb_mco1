package view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import model.QueriesController;

public class ProjectView extends JFrame implements ActionListener{
	private DefaultTableModel model;
	private JComboBox<String> queryCBox, optimizeCBox;
	private JButton queryBtn;
	private JTable table;
	private JScrollPane tableSp;
	private JTextArea execTimeTArea;
	private JTabbedPane tabbedPane;
	private JPanel queryPanel;
	private JPanel timePanel;
	private TableFromMySqlDatabase tfmsd;
	private QueriesController qc;
	public ProjectView(){
		table=new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		qc = new QueriesController();
		tfmsd = new TableFromMySqlDatabase();
		
		execTimeTArea = new JTextArea();
		execTimeTArea.setBounds(565,220,150,200);
		
		
		queryBtn = new JButton("Query");
		queryBtn.setBounds(565,90,150,25);
		
		queryCBox = new JComboBox<String>();
		queryCBox.addItem("1 Table");
		queryCBox.addItem("1 Table (2)");
		queryCBox.addItem("2 Tables w/ conditions");
		queryCBox.addItem("2 Tables w/ conditions (2)");
		queryCBox.addItem("3 Tables");
		queryCBox.addItem("3 Tables (2)");
		queryCBox.addItem("4 Tables");
		
		
		queryCBox.setBounds(565, 30, 150, 25);
		
		optimizeCBox = new JComboBox<String>();
		optimizeCBox.addItem("No optimization");
		optimizeCBox.addItem("Heuristic Optimization");
		optimizeCBox.addItem("Indices");
		optimizeCBox.addItem("Views");
		optimizeCBox.addItem("Stored Procedures");
		
		optimizeCBox.setBounds(565,60, 150,25);
		
		
		tableSp = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tableSp.setBounds(10,10,545,415);
		queryPanel = new JPanel();
		queryPanel.setBounds(0,0,750,500);
		queryPanel.add(execTimeTArea);
		queryPanel.add(queryBtn);
		queryPanel.add(queryCBox);
		queryPanel.add(optimizeCBox);
		queryPanel.add(tableSp);
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
