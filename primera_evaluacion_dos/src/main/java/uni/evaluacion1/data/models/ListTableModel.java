package uni.evaluacion1.data.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import uni.evaluacion1.backend.pojo.Vehicle;

public class ListTableModel extends AbstractTableModel implements PropertyChangeListener{
    private List<Vehicle> data;
    private String[] columnNames;

    public ListTableModel(List<Vehicle> data, String[] columns) {
        this.data = data;
        this.columnNames = columns;
    }

    public Vehicle getVehicleForIndex(int row){
        
        return data.get(row);
    }
    
    public void add(Vehicle v){
        
        this.data.add(v);
    }
    
    public void update (int row, Vehicle v){
        
        this.data.set(row, v);
        
    }
    
    public void delete (Vehicle v){
        
        this.data.remove(v);
    }
    
    @Override
    public int getRowCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames == null ? 0 : columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
            return data == null ? null  : data.isEmpty() ? null : data.get(rowIndex).asArray()[columnIndex];
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        
        delete((Vehicle)evt.getOldValue());
        System.out.println(evt.getOldValue());
        fireTableDataChanged();
        
        add((Vehicle)evt.getNewValue());
        System.out.println(evt.getNewValue());
        fireTableDataChanged();
    }
}
