/**
 * SamordningsnrValidator klassen ärver av ValidityCheck för att validerar svenska samordningsnummer.
 * Den overridear validate() för att inkludera specifika kontroller för svenska samordningsnummer.
 * Skapa en instance av klassen och skicka sedan strängen som ska valideras genom validate().
 */
public class SamordningsnrValidator extends ValidityCheck {
    /**
     * Constructor för SamordningsnrValidator class.
     * Skapar en instance av sin super, ValidityCheck.
     */
    public SamordningsnrValidator() {
        super();
    }

    /**
     * Konverterar strängen som beskriver dagen till ett legitimt datum genom
     * att subtrahera 60.
     *
     * @return boolean: true om konverteringen var lyckad, om dagen inte är 61-91 false
     */
    private boolean convertDay() {
        // remove 60 from the day if it is not between 61-91 return false and print error message
        int day = Integer.parseInt(input.substring(6, 8));
        if (day >= 61 && day <= 91) {
            day -= 60;
            String dayString = (day < 10) ? "0" + day : String.valueOf(day);
            input = input.substring(0, 6) + dayString + input.substring(8);
            return true;
        }
        errors.add("Invalid samordningssiffer format!");
        return false;
    }

    /**
     * Validerar strängen genom att använda flera generella funktioner från ValidityCheck
     * tillsammans med den specifika konverteringen för samordningsnummer.
     * Om false returneras så används System.out.print för att beskriva vilka
     * kontroller som misslyckades.
     *
     * @param input strängen som ska valideras som ett samordningsnummer
     * @return boolean: true om samordningsnummret är korrekt, annars false
     */
    @Override
    public boolean validate(String input) {
        setInput(input);
        return removeDash() && removePlus() && validLength(new int[]{10, 12}) && onlyDigits() && checkLuhn() && convertDay() && validDate();
    }

}