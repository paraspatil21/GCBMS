package backend;

public class Cylinder {
    private int cylinderId;
    private String category;
    private String weight;
    private double price;

    public Cylinder(int cylinderId, String category, String weight, double price) {
        this.cylinderId = cylinderId;
        this.category = category;
        this.weight = weight;
        this.price = price;
    }

    public int getCylinderId() { return cylinderId; }
    public String getCategory() { return category; }
    public String getWeight() { return weight; }
    public double getPrice() { return price; }
}
