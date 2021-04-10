package uni.evaluacion1.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import uni.evaluacion1.backend.dao.implementation.JsonVehicleDaoImpl;
import uni.evaluacion1.backend.pojo.Vehicle;
import uni.evaluacion1.backend.pojo.VehicleSubModel;
import uni.evaluacion1.views.panels.PnlVehicle;

public class PnlVehicleController {

    private PnlVehicle pnlVehicle;
    private Gson gson;
    private List<VehicleSubModel> vehicleSubModels;
    private DefaultComboBoxModel cmbModelMake;
    private DefaultComboBoxModel cmbModelModel;
    private DefaultComboBoxModel cmbModelYear;
    private DefaultComboBoxModel cmbModelEColor;
    private DefaultComboBoxModel cmbModelIColor;
    private DefaultComboBoxModel cmbModelStatus;
    private String status[] = new String[]{"Active","Not available","Mantainance"};
    private JFileChooser fileChooser;
    private JsonVehicleDaoImpl jvdao;
    private PropertyChangeSupport propertySupport;
    private Vehicle vehicle;
    private List<Vehicle> vehicles;
    
    public PnlVehicleController(PnlVehicle pnlVehicle) {
        this.pnlVehicle = pnlVehicle;
        initComponent();
    }
    
    public void addPropertyChangeListener(PropertyChangeListener pc1){
        
        propertySupport.addPropertyChangeListener(pc1);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener pc1){
        
        propertySupport.removePropertyChangeListener(pc1);
    }

    private void initComponent(){
        try{
            gson = new Gson();
            jvdao = new JsonVehicleDaoImpl();

            propertySupport = new PropertyChangeSupport(this);
            JsonReader jreader = new JsonReader(new BufferedReader(
                    new InputStreamReader(getClass().getResourceAsStream("/jsons/vehicleData.json"))
            ));

            Type listType = new TypeToken<ArrayList<VehicleSubModel>>(){}.getType();
            vehicleSubModels = gson.fromJson(jreader, listType);

            List<String> makes = vehicleSubModels.stream()
                    .map(v -> v.getMake())
                    .collect(Collectors.toList());
            List<String> models = vehicleSubModels.stream()
                    .map(v -> v.getModel()).collect(Collectors.toList());
            List<String> years = vehicleSubModels.stream()
                    .map(v -> v.getYear()).collect(Collectors.toList());
            List<String> colors = vehicleSubModels.stream()
                    .map(v -> v.getColor()).collect(Collectors.toList());

            Collections.sort(makes);
            Collections.sort(models);
            Collections.sort(years);
            Collections.sort(colors);

            cmbModelMake = new DefaultComboBoxModel<>(makes.toArray());
            cmbModelModel = new DefaultComboBoxModel(models.toArray());
            cmbModelYear = new DefaultComboBoxModel(years.toArray());
            cmbModelEColor = new DefaultComboBoxModel(colors.toArray());
            cmbModelIColor = new DefaultComboBoxModel(colors.toArray());
            cmbModelStatus = new DefaultComboBoxModel(status);

            pnlVehicle.getCmbMake().setModel(cmbModelMake);
            pnlVehicle.getCmbModel().setModel(cmbModelModel);
            pnlVehicle.getCmbYear().setModel(cmbModelYear);
            pnlVehicle.getCmbEColor().setModel(cmbModelEColor);
            pnlVehicle.getCmbIColor().setModel(cmbModelIColor);
            pnlVehicle.getCmbStatus().setModel(cmbModelStatus);

            pnlVehicle.getTxtStock().requestFocus();
            pnlVehicle.getTxtStyle().requestFocus();
            pnlVehicle.getFmtVin().requestFocus();
            pnlVehicle.getTxtEngine().requestFocus();
            pnlVehicle.getTxtImage().requestFocus();

            pnlVehicle.getBtnBrowse().addActionListener((e)->{
                btnBrowseActionListener(e);
            });

            pnlVehicle.getBtnSave().addActionListener((e)->{
                btnSaveActionListener(e);
                btnCancelActionListener(e);
            });

            pnlVehicle.getBtnCancel().addActionListener((e) ->{
                btnCancelActionListener(e);
            });
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PnlVehicleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void btnSaveActionListener(ActionEvent e){
        int stock, year;
        String make, model, style, vin, eColor, iColor, miles, engine, image, status;
        float price;
        Vehicle.Transmission transmission = Vehicle.Transmission.AUTOMATIC;
        
        if(pnlVehicle.getTxtStock().getText().isEmpty()|| pnlVehicle.getTxtStyle().getText().isEmpty() 
                || pnlVehicle.getTxtEngine().getText().isEmpty() || pnlVehicle.getTxtImage().getText().isEmpty()
                || pnlVehicle.getFmtVin().getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Ingrese todos los valores.", "Error de ingreso", JOptionPane.ERROR_MESSAGE);
            return;
        }
        stock = Integer.parseInt(pnlVehicle.getTxtStock().getText());
        year = Integer.parseInt(pnlVehicle.getCmbYear().getSelectedItem().toString());
        make = pnlVehicle.getCmbMake().getSelectedItem().toString();
        model = pnlVehicle.getCmbModel().getSelectedItem().toString();
        style = pnlVehicle.getTxtStyle().getText();
        vin = pnlVehicle.getFmtVin().getText();
        eColor = pnlVehicle.getCmbEColor().getSelectedItem().toString();
        iColor = pnlVehicle.getCmbIColor().getSelectedItem().toString();
        miles = pnlVehicle.getSpnMiles().getModel().getValue().toString();
        price = Float.parseFloat(pnlVehicle.getSpnPrice().getModel().getValue().toString());
        engine = pnlVehicle.getTxtEngine().getText();
        image = pnlVehicle.getTxtImage().getText();
        status = pnlVehicle.getCmbStatus().getSelectedItem().toString();
        transmission = pnlVehicle.getRbtnAut().isSelected() ?
                transmission : Vehicle.Transmission.MANUAL;
                
        Vehicle v = new Vehicle(stock, year, make, model, 
                style, vin, eColor, iColor, miles, price, transmission, engine, image, status);
        
        try {
            jvdao.create(v);
            propertySupport.firePropertyChange("Vehicle", vehicle, v);
            JOptionPane.showMessageDialog(null, "Vehicle saved successfully.", 
                    "Information message", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), 
                    "Error message", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(PnlVehicleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void btnBrowseActionListener(ActionEvent e){
        fileChooser = new JFileChooser();
        
        int option = fileChooser.showOpenDialog(null);
        if(option == JFileChooser.CANCEL_OPTION){
            return;
        }
        
        File file = fileChooser.getSelectedFile();
        if(!file.exists()){
            JOptionPane.showMessageDialog(null, "Image does not exists", 
                    "Error Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        pnlVehicle.getTxtImage().setText(file.getPath());        
    }
    
    private void btnCancelActionListener(ActionEvent e){
        
        pnlVehicle.getTxtStock().setText("");
        pnlVehicle.getCmbYear().setSelectedIndex(0);
        pnlVehicle.getCmbMake().setSelectedIndex(0);
        pnlVehicle.getCmbModel().setSelectedIndex(0);
        pnlVehicle.getTxtStyle().setText("");
        pnlVehicle.getFmtVin().setText("");
        pnlVehicle.getCmbIColor().setSelectedIndex(0);
        pnlVehicle.getCmbEColor().setSelectedIndex(0);
        pnlVehicle.getSpnMiles().setValue(0);
        pnlVehicle.getSpnPrice().setValue(0);
        pnlVehicle.getTxtEngine().setText("");
        pnlVehicle.getTxtImage().setText("");
        pnlVehicle.getCmbStatus().setSelectedIndex(0);
        pnlVehicle.getRbtnAut().setSelected(true);
    }
    
    public void updateVehicle(int index) throws IOException{
        
        vehicles = jvdao.getAll().stream().collect(Collectors.toList());
        vehicle = vehicles.get(index);
        
        pnlVehicle.getTxtStock().setText(Integer.toString(vehicle.getStockNumber()));
        pnlVehicle.getCmbYear().setSelectedIndex(vehicle.getYear());
        pnlVehicle.getCmbMake().setSelectedIndex(Integer.parseInt(vehicle.getMake()));
        pnlVehicle.getCmbModel().setSelectedIndex(Integer.parseInt(vehicle.getModel()));
        pnlVehicle.getTxtStyle().setText(vehicle.getStyle());
        pnlVehicle.getFmtVin().setText(vehicle.getVin());
        pnlVehicle.getCmbIColor().setSelectedIndex(Integer.parseInt(vehicle.getInteriorColor()));
        pnlVehicle.getCmbEColor().setSelectedIndex(Integer.parseInt(vehicle.getExteriorColor()));
        pnlVehicle.getSpnMiles().setValue(vehicle.getMiles());
        pnlVehicle.getSpnPrice().setValue(vehicle.getPrice());
        pnlVehicle.getTxtEngine().setText(vehicle.getEngine());
        pnlVehicle.getTxtImage().setText(vehicle.getImage());
        pnlVehicle.getCmbStatus().setSelectedIndex(Integer.parseInt(vehicle.getStatus()));
        pnlVehicle.getRbtnAut().setSelected("AUTOMATIC".equals(vehicle.getTransmission().toString()));
        
        propertySupport.firePropertyChange("Delete", vehicle, null);
    }
}
