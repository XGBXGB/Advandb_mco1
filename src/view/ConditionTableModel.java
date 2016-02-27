/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Christian Gabriel
 */

public class ConditionTableModel extends AbstractTableModel {
	ArrayList<ConditionPanel> conditions;

	public ConditionTableModel(ArrayList<ConditionPanel> conditions) {
		this.conditions = conditions;
	}

	public Class getColumnClass(int columnIndex) {
		return ConditionPanel.class;
	}

	public int getColumnCount() {
		return 1;
	}

	public String getColumnName(int columnIndex) {
		return "Conditions";
	}

	public int getRowCount() {
		return (conditions == null) ? 0 : conditions.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return (conditions == null) ? null : conditions.get(rowIndex);
	}

	public boolean isCellEditable(int columnIndex, int rowIndex) {
		return true;
	}
}
