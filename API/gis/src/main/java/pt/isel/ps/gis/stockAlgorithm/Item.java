package pt.isel.ps.gis.stockAlgorithm;

import java.util.Date;

public class Item {
    private final String product;
    private final short quantity;
    private final Date date;

    public Item(String product, short quantity, Date date) {
        this.product = product;
        this.quantity = quantity;
        this.date = date;
    }

    public String getProduct() {
        return product;
    }

    public short getQuantity() {
        return quantity;
    }

    public Date getDate() {
        return date;
    }
}
