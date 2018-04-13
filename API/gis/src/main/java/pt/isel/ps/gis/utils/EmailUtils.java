package pt.isel.ps.gis.utils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

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
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
