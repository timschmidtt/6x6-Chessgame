package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Klasse mit Ein-/Ausgabeanweisungen fuer die Console.
 *
 * @author Dietrich Boles (Universitaet Oldenburg)
 * @version 15.07.2021
 *
 */
public class IO {

    /**
     * Beispielprogramm fuer den Einsatz der Klasse IO.
     *
     * @param args
     */
    public static void main(String[] args) {
        double gewicht = IO.readDouble("Bitte geben Sie Ihr Gewicht ein (in kg): ");
        double groesse = IO.readDouble("Bitte geben Sie ihre Groesse ein (in m): ");
        double bmi = gewicht / (groesse * groesse);
        IO.println("BMI = " + bmi);
    }

    /**
     * Gibt die String-Repraesentation des uebergebenen Objektes auf die Console
     * aus. Beispielaufrufe:
     * <p>
     * IO.print("hello world");<br>
     * IO.print(4711);<br>
     * IO.print(new Double(23.4));<br>
     * IO.print("12 + 3 = " + (12 + 3));
     * </p>
     *
     * @param obj Wert bzw. Objekt, dessen String-Repraesentation auf der Console
     *            ausgegeben werden soll
     */
    public static void print(Object obj) {
        System.out.print(obj);
    }

    /**
     * Gibt die String-Repraesentation des uebergebenen Objektes auf die Console aus
     * und erwirkt einen Zeilenvorschub auf der Console, d.h. der Cursor sprint an
     * den Anfang der naechsten Zeile. Beispielaufrufe:
     * <p>
     * IO.println("hello world");<br>
     * IO.println(4711);<br>
     * IO.println(new Double(23.4));<br>
     * IO.println("12 + 3 = " + (12 + 3));
     * </p>
     *
     * @param obj Wert bzw. Objekt, dessen String-Repraesentation auf der Console
     *            ausgegeben werden soll
     */
    public static void println(Object obj) {
        System.out.println(obj);
    }

    /**
     * Erwirkt einen Zeilenvorschub auf der Console, d.h. der Cursor sprint an den
     * Anfang der naechsten Zeile. Beispielaufruf:
     * <p>
     * IO.println();
     * </p>
     */
    public static void println() {
        System.out.println();
    }

    /**
     * Erwartet die Eingabe eines booleschen Wertes in der Console. Die Eingabe muss
     * mit der ENTER-Taste abgeschlossen werden. Gibt der Benutzer die Zeichenkette
     * "true" (Gross-/Kleinschreibung wird ignoriert) ein, wird der Wert true
     * geliefert. In allen anderen Faellen wird der Wert false geliefert.
     * Beispielaufruf:
     * <p>
     * boolean eingabe = IO.readBoolean();
     * </p>
     *
     * @return ein vom Benutzer in der Console eingegebener boolean-Wert
     */
    public static boolean readBoolean() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String eingabe = input.readLine();
            return Boolean.parseBoolean(eingabe);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gibt die String-Repraesentation des uebergebenen Objektes (meist eine
     * Eingabeaufforderung) auf die Console aus und erwartet anschliessend die
     * Eingabe eines booleschen Wertes in der Console. Die Eingabe muss mit der
     * ENTER-Taste abgeschlossen werden. Gibt der Benutzer die Zeichenkette "true"
     * (Gross-/Kleinschreibung wird ignoriert) ein, wird der Wert true geliefert. In
     * allen anderen Faellen wird der Wert false geliefert. Beispielaufruf:
     * <p>
     * boolean eingabe = IO.readBoolean("true oder false?");
     * </p>
     *
     * @param aufforderung Wert bzw. Objekt, dessen String-Repraesentation vor der
     *                     Eingabeaufforderung auf der Console ausgegeben werden
     *                     soll
     * @return ein vom Benutzer in der Console eingegebener boolescher Wert
     */
    public static boolean readBoolean(Object aufforderung) {
        System.out.print(aufforderung);
        return IO.readBoolean();
    }

    /**
     * Erwartet die Eingabe eines Zeichens in der Console. Die Eingabe muss mit der
     * ENTER-Taste abgeschlossen werden. Gibt der Benutzer mehrere Zeichen ein, wird
     * das erste eingegebene Zeichen als Funktionswert geliefert. Gibt der Benutzer
     * kein Zeichen ein, wird der ASCII-Wert 0 geliefert. Beispielaufruf:
     * <p>
     * char zeichen = IO.readChar();
     * </p>
     *
     * @return ein vom Benutzer in der Console eingegebenes Zeichen
     */
    public static char readChar() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String eingabe = input.readLine();
            return eingabe.charAt(0);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Gibt die String-Repraesentation des uebergebenen Objektes (meist eine
     * Eingabeaufforderung) auf die Console aus und erwartet anschliessend die
     * Eingabe eines Zeichens in der Console. Die Eingabe muss mit der ENTER-Taste
     * abgeschlossen werden. Gibt der Benutzer mehrere Zeichen ein, wird das erste
     * eingegebene Zeichen als Funktionswert geliefert. Gibt der Benutzer kein
     * Zeichen ein, wird der ASCII-Wert 0 geliefert. Beispielaufruf:
     * <p>
     * char zeichen = IO.readChar("Weiter (j/n)?");
     * </p>
     *
     * @param aufforderung Wert bzw. Objekt, dessen String-Repraesentation vor der
     *                     Eingabeaufforderung auf der Console ausgegeben werden
     *                     soll
     * @return ein vom Benutzer in der Console eingegebenes Zeichen
     */
    public static char readChar(Object aufforderung) {
        System.out.print(aufforderung);
        return IO.readChar();
    }

    /**
     * Erwartet die Eingabe eines short-Wertes in der Console. Die Eingabe muss mit
     * der ENTER-Taste abgeschlossen werden. Gibt der Benutzer ungueltige Zeichen
     * ein, wird der Wert 0 geliefert. Beispielaufruf:
     * <p>
     * short zahl = IO.readShort();
     * </p>
     *
     * @return ein vom Benutzer in der Console eingegebener short-Wert
     */
    public static short readShort() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String eingabe = input.readLine();
            return Short.parseShort(eingabe);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Gibt die String-Repraesentation des uebergebenen Objektes (meist eine
     * Eingabeaufforderung) auf die Console aus und erwartet anschliessend die
     * Eingabe eines short-Wertes in der Console. Die Eingabe muss mit der
     * ENTER-Taste abgeschlossen werden. Gibt der Benutzer ungueltige Zeichen ein,
     * wird der Wert 0 geliefert. Beispielaufruf:
     * <p>
     * short zahl = IO.readShort("Alter?");
     * </p>
     *
     * @param aufforderung Wert bzw. Objekt, dessen String-Repraesentation vor der
     *                     Eingabeaufforderung auf der Console ausgegeben werden
     *                     soll
     * @return ein vom Benutzer in der Console eingegebener short-Wert
     */
    public static short readShort(Object aufforderung) {
        System.out.print(aufforderung);
        return IO.readShort();
    }

    /**
     * Erwartet die Eingabe eines int-Wertes in der Console. Die Eingabe muss mit
     * der ENTER-Taste abgeschlossen werden. Gibt der Benutzer ungueltige Zeichen
     * ein, wird der Wert 0 geliefert. Beispielaufruf:
     * <p>
     * int zahl = IO.readInt();
     * </p>
     *
     * @return ein vom Benutzer in der Console eingegebener int-Wert
     */
    public static int readInt() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String eingabe = input.readLine();
            return Integer.parseInt(eingabe);
        } catch (Exception exc) {
            return 0;
        }
    }

    /**
     * Gibt die String-Repraesentation des uebergebenen Objektes (meist eine
     * Eingabeaufforderung) auf die Console aus und erwartet anschliessend die
     * Eingabe eines int-Wertes in der Console. Die Eingabe muss mit der ENTER-Taste
     * abgeschlossen werden. Gibt der Benutzer ungueltige Zeichen ein, wird der Wert
     * 0 geliefert. Beispielaufruf:
     * <p>
     * int zahl = IO.readInt("3 + 4 = ?");
     * </p>
     *
     * @param aufforderung Wert bzw. Objekt, dessen String-Repraesentation vor der
     *                     Eingabeaufforderung auf der Console ausgegeben werden
     *                     soll
     * @return ein vom Benutzer in der Console eingegebener int-Wert
     */
    public static int readInt(Object aufforderung) {
        System.out.print(aufforderung);
        return IO.readInt();
    }

    /**
     * Erwartet die Eingabe eines long-Wertes in der Console. Die Eingabe muss mit
     * der ENTER-Taste abgeschlossen werden. Gibt der Benutzer ungueltige Zeichen
     * ein, wird der Wert 0L geliefert. Beispielaufruf:
     * <p>
     * long zahl = IO.readLong();
     * </p>
     *
     * @return ein vom Benutzer in der Console eingegebener long-Wert
     */
    public static long readLong() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String eingabe = input.readLine();
            return Long.parseLong(eingabe);
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * Gibt die String-Repraesentation des uebergebenen Objektes (meist eine
     * Eingabeaufforderung) auf die Console aus und erwartet anschliessend die
     * Eingabe eines long-Wertes in der Console. Die Eingabe muss mit der
     * ENTER-Taste abgeschlossen werden. Gibt der Benutzer ungueltige Zeichen ein,
     * wird der Wert 0L geliefert. Beispielaufruf:
     * <p>
     * long zahl = IO.readLong("Bitte grosse Zahl eingeben: ");
     * </p>
     *
     * @param aufforderung Wert bzw. Objekt, dessen String-Repraesentation vor der
     *                     Eingabeaufforderung auf der Console ausgegeben werden
     *                     soll
     * @return ein vom Benutzer in der Console eingegebener long-Wert
     */
    public static long readLong(Object aufforderung) {
        System.out.print(aufforderung);
        return IO.readLong();
    }

    /**
     * Erwartet die Eingabe eines float-Wertes in der Console. Die Eingabe muss mit
     * der ENTER-Taste abgeschlossen werden. Gibt der Benutzer ungueltige Zeichen
     * ein, wird der Wert 0.0F geliefert. Beispielaufruf:
     * <p>
     * float zahl = IO.readFloat();
     * </p>
     *
     * @return ein vom Benutzer in der Console eingegebener float-Wert
     */
    public static float readFloat() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String eingabe = input.readLine();
            return Float.parseFloat(eingabe);
        } catch (Exception e) {
            return 0.0F;
        }
    }

    /**
     * Gibt die String-Repraesentation des uebergebenen Objektes (meist eine
     * Eingabeaufforderung) auf die Console aus und erwartet anschliessend die
     * Eingabe eines float-Wertes in der Console. Die Eingabe muss mit der
     * ENTER-Taste abgeschlossen werden. Gibt der Benutzer ungueltige Zeichen ein,
     * wird der Wert 0.0F geliefert. Beispielaufruf:
     * <p>
     * float zahl = IO.readFloat("PI = ?");
     * </p>
     *
     * @param aufforderung Wert bzw. Objekt, dessen String-Repraesentation vor der
     *                     Eingabeaufforderung auf der Console ausgegeben werden
     *                     soll
     * @return ein vom Benutzer in der Console eingegebener float-Wert
     */
    public static float readFloat(Object aufforderung) {
        System.out.print(aufforderung);
        return IO.readFloat();
    }

    /**
     * Erwartet die Eingabe eines double-Wertes in der Console. Die Eingabe muss mit
     * der ENTER-Taste abgeschlossen werden. Gibt der Benutzer ungueltige Zeichen
     * ein, wird der Wert 0.0 geliefert. Beispielaufruf:
     * <p>
     * double zahl = IO.readDouble();
     * </p>
     *
     * @return ein vom Benutzer in der Console eingegebener double-Wert
     */
    public static double readDouble() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String eingabe = input.readLine();
            return Double.parseDouble(eingabe);
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * Gibt die String-Repraesentation des uebergebenen Objektes (meist eine
     * Eingabeaufforderung) auf die Console aus und erwartet anschliessend die
     * Eingabe eines double-Wertes in der Console. Die Eingabe muss mit der
     * ENTER-Taste abgeschlossen werden. Gibt der Benutzer ungueltige Zeichen ein,
     * wird der Wert 0.0 geliefert. Beispielaufruf:
     * <p>
     * double zahl = IO.readDouble("PI = ?");
     * </p>
     *
     * @param aufforderung Wert bzw. Objekt, dessen String-Repraesentation vor der
     *                     Eingabeaufforderung auf der Console ausgegeben werden
     *                     soll
     * @return ein vom Benutzer in der Console eingegebener double-Wert
     */
    public static double readDouble(Object aufforderung) {
        System.out.print(aufforderung);
        return IO.readDouble();
    }

    /**
     * Erwartet die Eingabe einer Zeichenkette in der Console. Die Eingabe muss mit
     * der ENTER-Taste abgeschlossen werden. Beispielaufruf:
     * <p>
     * String zahl = IO.readString();
     * </p>
     *
     * @return eine vom Benutzer in der Console eingegebene Zeichenkette
     */
    public static String readString() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            return input.readLine();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Gibt die String-Repraesentation des uebergebenen Objektes (meist eine
     * Eingabeaufforderung) auf die Console aus und erwartet anschliessend die
     * Eingabe einer Zeichenkette in der Console. Die Eingabe muss mit der
     * ENTER-Taste abgeschlossen werden. Beispielaufruf:
     * <p>
     * String zahl = IO.readString("Wie heissen Sie?");
     * </p>
     *
     * @param aufforderung Wert bzw. Objekt, dessen String-Repraesentation vor der
     *                     Eingabeaufforderung auf der Console ausgegeben werden
     *                     soll
     * @return eine vom Benutzer in der Console eingegebener Zeichenkette
     */
    public static String readString(Object aufforderung) {
        System.out.print(aufforderung);
        return IO.readString();
    }

    /**
     * Liest die im Parameter angegebene Textdatei aus und liefert ein char-Array
     * zurueck, dessen Elemente die einzelnen Zeichen der Datei enthalten;
     * Zeilenvorschubzeichen sind nicht enthalten.
     *
     * @param filename Name der auszulesenden Datei
     * @return char-Array mit den einzelnen Zeichen der Datei; im Fehlerfall wird
     *         der Wert null geliefert.
     */
    public static char[] readFileAsCharArray(String filename) {
        ArrayList<Character> chars = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            String line = in.readLine();
            while (line != null) {
                for (char ch : line.toCharArray()) {
                    chars.add(ch);
                }
                line = in.readLine();
            }
            char[] result = new char[chars.size()];
            for (int i = 0; i < chars.size(); i++) {
                result[i] = chars.get(i);
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Liest die im Parameter angegebene Textdatei aus und liefert ein
     * zwei-dimensionale char-Array zurueck, dessen Elemente die Zeichen der Datei
     * enthalten; dabei wird in der gelieferten Matrix die Zeilen- und
     * Spaltenstruktur der Datei widergespiegelt
     *
     * @param filename Name der auszulesenden Datei
     * @return char-Matrix mit den einzelnen Zeichen der Datei; im Fehlerfall wird
     *         der Wert null geliefert.
     */
    public static char[][] readFileAsCharMatrix(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            String line = in.readLine();
            while (line != null) {
                lines.add(line);
                line = in.readLine();
            }
            char[][] result = new char[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                result[i] = lines.get(i).toCharArray();
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Liest die im Parameter angegebene Textdatei aus und liefert ein String-Array
     * zurueck, dessen Elemente die einzelnen Zeilen der Datei enthalten
     *
     * @param filename Name der auszulesenden Datei
     * @return String-Array mit den einzelnen Zeilen der Datei; im Fehlerfall wird
     *         der Wert null geliefert.
     */
    public static String[] readFileAsStringArray(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            String line = in.readLine();
            while (line != null) {
                lines.add(line);
                line = in.readLine();
            }
            return lines.toArray(new String[lines.size()]);
        } catch (Exception e) {
            return null;
        }
    }

}
