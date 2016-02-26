package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ConditionPanel extends JPanel implements ActionListener{
	private ProjectView parent;
	private JComboBox column, operator;
	private JTextField condition;
	private JPanel container;
	private JButton close;
	private ArrayList<String> columns;
	private ArrayList<String> operators;

	
	public ConditionPanel(ProjectView parent, ArrayList<String> columns){
		this.columns = columns;
		
		operators = new ArrayList();
		operators.add("LIKE");
		operators.add("=");
		operators.add("<");
		operators.add(">");
		operators.add("<=");
		operators.add(">=");
		
		
		this.parent = parent;
		container = new JPanel();
		container.setBounds(0, 0, 370, 80);
		
		close = new JButton("X");

		close.setBounds(305, 4, 45, 28);
		close.addActionListener(this);
		
		column = new JComboBox(columns.toArray());
		condition = new JTextField();
		operator = new JComboBox(operators.toArray());
		
		column.setBounds(5, 4, 120, 30);
		operator.setBounds(130, 4, 55, 30);
		condition.setBounds(190, 4, 110, 30);
		
		container.add(column);
		container.add(operator);
		container.add(condition);
		container.add(close);
		this.setLayout(null);
		container.setLayout(null);
		this.add(container);
		this.setVisible(true);
		this.setSize(370, 80);
		
	}
	
	public String getColumn(){
		return column.getSelectedItem().toString();
	}
	
	public String getOperator(){
		return operator.getSelectedItem().toString();
	}
	
	public String getCondition(){
		return condition.getText();
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==close){
			parent.removePanel(this);
		}
	}
	

}
