package pt.isel.ps.gis.model.requestParams;

public class StockItemRequestParam {

    private String product;
    private String brand;
    private String variety;
    private String segment;
    private Short storage;

    public StockItemRequestParam(String product, String brand, String variety, String segment, Short storage) {
        this.product = product;
        this.brand = brand;
        this.variety = variety;
        this.segment = segment;
        this.storage = storage;
    }

    public boolean isNull() {
        return product == null && brand == null && variety == null && segment == null && storage == 0;
    }

    public String getProduct() {
        return product;
    }

    public String getBrand() {
        return brand;
    }

    public String getVariety() {
        return variety;
    }

    public String getSegment() {
        return segment;
    }

    public Short getStorage() {
        return storage;
    }
}
