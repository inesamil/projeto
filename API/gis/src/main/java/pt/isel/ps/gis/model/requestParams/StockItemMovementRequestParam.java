package pt.isel.ps.gis.model.requestParams;

import pt.isel.ps.gis.exceptions.BadRequestException;
import pt.isel.ps.gis.utils.DateUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StockItemMovementRequestParam {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

    public boolean getType() {
        if (type == null)
            return false;
        return type;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public short getStorage() {
        if (storage == null)
            return 0;
        return storage;
    }

    public String getItem() {
        return item;
    }
}
