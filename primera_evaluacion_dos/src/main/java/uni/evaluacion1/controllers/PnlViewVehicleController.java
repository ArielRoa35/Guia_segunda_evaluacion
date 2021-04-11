package uni.evaluacion1.controllers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import uni.evaluacion1.backend.dao.implementation.JsonVehicleDaoImpl;
import uni.evaluacion1.backend.pojo.Vehicle;
import uni.evaluacion1.data.models.ListTableModel;
import uni.evaluacion1.views.panels.PnlViewVehicle;

public class PnlViewVehicleController {
    private PnlViewVehicle pnlViewVehicle;
    private JsonVehicleDaoImpl jsonVehicleDaoImpl;
    private ListTableModel tblViewVehicleModel;
    private List<Vehicle> vehicles;
    private final String[] HEADERS = new String[]{"StockNumber","Year","Make","Model",
    "Style","Vin","Exterior color","Interior color","Miles","price","Transmission",
    "Engine","Image","Status"};
    private TableRowSorter<ListTableModel> tblRowSorter;
    private PropertyChangeSupport propertySupport;
    private Vehicle v;
    
    public PnlViewVehicleController(PnlViewVehicle pnlViewVehicle) {
        this.pnlViewVehicle = pnlViewVehicle;
        initComponent();
    }
    
    public void addPropertyChangeListener(PropertyChangeListener pc1){
        
        propertySupport.addPropertyChangeListener(pc1);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener pc1){
        
        propertySupport.removePropertyChangeListener(pc1);
    }
    
    public ListTableModel getTblViewModel(){
        return tblViewVehicleModel;
    }
    
    private void initComponent(){
        try {
            jsonVehicleDaoImpl = new JsonVehicleDaoImpl();
            loadTable();  
            
            pnlViewVehicle.getTxtFinder().addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e){
                    txtFinderKeyTyped(e);
                }
            });
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PnlViewVehicleController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PnlViewVehicleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void txtFinderKeyTyped(KeyEvent e){
        RowFilter<ListTableModel, Object> rf = null;        
        rf = RowFilter.regexFilter(pnlViewVehicle.getTxtFinder().getText(), 0,1,2,3,4,5,6,7,8,9);
        tblRowSorter.setRowFilter(rf);
    }
    
    private void loadTable() throws IOException{
        vehicles = jsonVehicleDaoImpl.getAll().stream().collect(Collectors.toList());
        tblViewVehicleModel = new ListTableModel(vehicles, HEADERS);
        tblRowSorter = new TableRowSorter<>(tblViewVehicleModel);
        
        pnlViewVehicle.getTblViewVehicle().setModel(tblViewVehicleModel);
        pnlViewVehicle.getTblViewVehicle().setRowSorter(tblRowSorter);
    }
    
    public void deleteVehicle(int index) throws FileNotFoundException, IOException{
        
        jsonVehicleDaoImpl = new JsonVehicleDaoImpl();
        propertySupport = new PropertyChangeSupport(this);
        vehicles = jsonVehicleDaoImpl.getAll().stream().collect(Collectors.toList());
        Vehicle vehicle = vehicles.get(index);
        
        if(jsonVehicleDaoImpl.delete(vehicle)){
            JOptionPane.showMessageDialog(null, "Vehicle delete successfully.", 
                    "Information message", JOptionPane.INFORMATION_MESSAGE);   
        }else{
            JOptionPane.showMessageDialog(null, "Vehicle delete Unsuccessfully.", 
                    "Error message", JOptionPane.ERROR_MESSAGE);
        }
        propertySupport.firePropertyChange("Vehicle", v, vehicle);
        
    }
//    private Object[][] getData() throws IOException{
//        vehicles = jsonVehicleDaoImpl.getAll().stream().collect(Collectors.toList());
//        Object[][] data = new Object[vehicles.size()][vehicles.get(0).asArray().length];
//        
//        IntStream.range(0, vehicles.size()).forEach(i -> {
//            data[i] = vehicles.get(i).asArray();
//        });
//        
//        return data;
//    }
}
