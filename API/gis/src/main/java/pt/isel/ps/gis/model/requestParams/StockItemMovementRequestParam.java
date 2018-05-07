package pt.isel.ps.gis.model.requestParams;

import pt.isel.ps.gis.exceptions.BadRequestException;
import pt.isel.ps.gis.utils.DateUtils;

import java.sql.Timestamp;
import java.text.ParseException;

public class StockItemMovementRequestParam {

    private Boolean type;
    private Timestamp datetime;
    private Short storage;
    private String item;

    public StockItemMovementRequestParam(Boolean type, String datetime, Short storage, String item) throws BadRequestException {
        this.type = type;
        if (datetime != null) {
            try {
                this.datetime = DateUtils.convertStringToTimestamp(datetime);
            } catch (ParseException e) {
                throw new BadRequestException();
            }
        }
        this.storage = storage;
        this.item = item;
    }

    public boolean isNull() {
        return type == null && datetime == null && storage == null && item == null;
    }

    public Boolean getType() {
        return type;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public Short getStorage() {
        return storage;
    }

    public String getItem() {
        return item;
    }
}
