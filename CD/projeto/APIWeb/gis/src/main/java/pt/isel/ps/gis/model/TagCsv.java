package pt.isel.ps.gis.model;

import com.opencsv.bean.CsvBindByPosition;

public class TagCsv {

    @CsvBindByPosition(position = 0, required = true)
    private String productName;
    @CsvBindByPosition(position = 1, required = true)
    private String brand;
    @CsvBindByPosition(position = 2, required = true)
    private String variety;
    @CsvBindByPosition(position = 3, required = true)
    private String segment;
    @CsvBindByPosition(position = 4, required = true)
    private String date;
    @CsvBindByPosition(position = 5)
    private String conditions;
    @CsvBindByPosition(position = 6)
    private String description;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
