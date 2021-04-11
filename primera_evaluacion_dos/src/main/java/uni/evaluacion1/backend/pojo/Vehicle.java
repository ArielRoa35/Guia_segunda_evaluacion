package uni.evaluacion1.backend.pojo;

public class Vehicle {
    private int stockNumber;//20
    private int year;//4
    private String make;//30
    private String model;//30
    private String style;//30
    private String vin;//20
    private String exteriorColor;//30
    private String interiorColor;//30
    private String miles;//7
    private float price;//7
    private Transmission transmission;//20
    private String engine;//50
    private String image;//100
    private String status;//20
    
    public enum Transmission{
        AUTOMATIC, MANUAL
    }

    public Vehicle() {
    }
    
    public Vehicle(int stockNumber, int year, String make, String model, Transmission transmission, String status) {
        this.stockNumber = stockNumber;
        this.year = year;
        this.make = make;
        this.model = model;
        this.transmission = transmission;
        this.status = status;
    }

    public Vehicle(int stockNumber, int year, String make, String model, String style, String vin, String exteriorColor, String interiorColor, String miles, float price, Transmission transmission, String engine, String image, String status) {
        this.stockNumber = stockNumber;
        this.year = year;
        this.make = make;
        this.model = model;
        this.style = style;
        this.vin = vin;
        this.exteriorColor = exteriorColor;
        this.interiorColor = interiorColor;
        this.miles = miles;
        this.price = price;
        this.transmission = transmission;
        this.engine = engine;
        this.image = image;
        this.status = status;
    }

    public int getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(int stockNumber) {
        this.stockNumber = stockNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getExteriorColor() {
        return exteriorColor;
    }

    public void setExteriorColor(String exteriorColor) {
        this.exteriorColor = exteriorColor;
    }

    public String getInteriorColor() {
        return interiorColor;
    }

    public void setInteriorColor(String interiorColor) {
        this.interiorColor = interiorColor;
    }

    public String getMiles() {
        return miles;
    }

    public void setMiles(String miles) {
        this.miles = miles;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    

    @Override
    public String toString() {
        return "Vehicle{" + "stockNumber=" + stockNumber + ", year=" + year + ", make=" + make + ", model=" + model + ", style=" + style + ", vin=" + vin + ", exteriorColor=" + exteriorColor + ", interiorColor=" + interiorColor + ", miles=" + miles + ", price=" + price + ", transmission=" + transmission + ", engine=" + engine + ", image=" + image + ", status=" + status + '}';
    }
    
    public boolean equals(Vehicle v){
        
        int i = 0;
        
        if(this.stockNumber == v.getStockNumber()){
        i++;}
        if(this.year == v.getYear()){
        i++;}
        if(this.make.equals(v.getMake())){
        i++;}
        if(this.model.equals(v.getModel())){
        i++;}
        if(this.style.equals(v.getStyle())){
        i++;}
        if(this.vin.equals(v.getVin())){
        i++;}
        if(this.interiorColor.equals(v.getInteriorColor())){
        i++;}
        if(this.exteriorColor.equals(v.getExteriorColor())){
        i++;}
        if(this.miles.equals(v.getMiles())){
        i++;}
        if(this.price == v.getPrice()){
        i++;}
        if((this.transmission).toString().equals(v.getTransmission().toString())){
        i++;}
        if(this.engine.equals(v.getEngine())){
        i++;}
        if(this.image.equals(v.getImage())){
        i++;}
        if(this.status.equals(v.getStatus())){
        i++;}
        
        if(i == 14){
            return true;
        }else{
            return false;
        }
            
        
    }
    
    public Object[] asArray(){
        Object[] obj = new Object[14];
        
        obj[0] = stockNumber;
        obj[1] = year;
        obj[2] = make;
        obj[3] = model;
        obj[4] = style;
        obj[5] = vin;
        obj[6] = exteriorColor;
        obj[7] = interiorColor;
        obj[8] = miles;
        obj[9] = price;
        obj[10] = transmission;
        obj[11] = engine;
        obj[12] = image;
        obj[13] = status;
        
        return obj;
    }
    
}
