package kz.production.kuanysh.sellings.model;
public class SupplierItem{
    private String name;
    private String category;
    private String workingTime;
    private String address;

    public SupplierItem(String name, String category, String workingTime, String address) {
        this.name = name;
        this.category = category;
        this.workingTime = workingTime;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getWorkingTime() {
        return workingTime;
    }

    public String getAddress() {
        return address;
    }
}