/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Christian Gabriel
 */
public class ConditionCellEditor extends AbstractCellEditor implements TableCellEditor {
  ConditionPanel cp;
 
 
  public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int row, int column) {
	  ConditionPanel condition = (ConditionPanel)value;
        
        //cp = new CoursePanel(course.getCourse(),course.getGrade(), course.getUnits());
        //feedComponent.updateData(feed, true, table);
        return condition;
  }
 
  public Object getCellEditorValue() {
    return null;
  }
}
