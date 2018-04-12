package pt.isel.ps.gis.utils;

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
    public static boolean isStringIsValidDate(String date) {
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
     * Converte um objeto Timestamp numa string
     *
     * @param date timestamp a converter
     * @return String correspondente à data no formato yyyy-MM-dd
     */
    public static String convertDateFormat(Timestamp date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}
