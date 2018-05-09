package pt.isel.ps.gis.utils;

// TODO better name
public class InputUtils {

    /**
     * Receive a String a split numbers from letters.
     *
     * @param s String to be splitted.
     * @return Array of string with in first position a string with numbers and in second position a string with letters.
     */
    public static String[] splitNumbersFromLetters(String s) {
        StringBuilder numbers = new StringBuilder();
        StringBuilder letters = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (Character.isDigit(c))
                numbers.append(c);
            else
                letters.append(c);
        }
        return new String[]{numbers.toString(), letters.toString()};
    }
}
