package pt.isel.ps.gis.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Classe utilitária para Datas
 */
public class DateUtils {
    /**
     * Verifica se uma string é ou não uma data válida
     *
     * @param date data a validar
     * @return true se @param date é uma data válida, false caso contrário
     */
    public static boolean isStringValidDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    /**
     * Verifica se uma string é ou não uma data válida
     *
     * @param datetime data a validar
     * @return true se @param date é uma data válida, false caso contrário
     */
    public static boolean isStringValidDateTime(String datetime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(datetime.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    /**
     * Converte um objeto Date numa string
     *
     * @param date data a converter
     * @return String correspondente à data no formato yyyy-MM-dd
     */
    public static String convertDateFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     * Converte uma string num objeto Date
     *
     * @param date data a converter
     * @return Date correspondente à data no formato yyyy-MM-dd
     * @throws ParseException
     */
    public static Date convertStringToDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsedDate = dateFormat.parse(date);
        return new Date(parsedDate.getTime());
    }

    /**
     * Converte um objeto Timestamp numa String
     *
     * @param timestamp data a converter
     * @return String correspondente à data no formato yyyy-MM-dd HH:mm:ss
     */
    public static String convertTimestampFormat(Timestamp timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(timestamp);
    }

    public static Timestamp convertStringToTimestamp(String timestamp) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date parsedDate = dateFormat.parse(timestamp);
        return new Timestamp(parsedDate.getTime());
    }
}
