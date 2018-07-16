package pt.isel.ps.gis.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe utilitária para Emails
 */
public class EmailUtils {

    /**
     * Verifica se uma string é ou não uma data válida
     *
     * @param email email a validar
     * @return true se @param email é um email válido, false caso contrário
     */
    public static boolean isStringValidEmail(String email) {
        Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(email);
        return matcher.matches();
    }

}
