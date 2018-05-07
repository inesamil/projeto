package pt.isel.ps.gis.model.requestParams;

public class StockItemRequestParams {

    private String product;
    private String brand;
    private String variety;
    private String segment;
    private short storage;

    public boolean allFieldsAreNull() {
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

    public short getStorage() {
        return storage;
    }
}
