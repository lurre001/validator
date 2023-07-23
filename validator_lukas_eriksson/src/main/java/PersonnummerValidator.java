/**
 * PersonnummerValidator är en subklass till ValidityCheck. Klassen används för att validera Svenska
 * personnummer. Detta görs genom en kombination av funktionaliteten i ValidityCheck.
 * Skapa en instance av klassen och skicka sedan strängen som ska valideras genom validate().
 */
public class PersonnummerValidator extends ValidityCheck {
    /**
     * Constructor för PersonnummerValidator class.
     * Skapar en instance av sin super, ValidityCheck.
     */
    public PersonnummerValidator() {
        super();
    }


    /**
     * Validerar strängen genom att använda flera generella funktioner från ValidityCheck.
     * Om false returneras så används System.out.print för att beskriva vilka
     * kontroller som misslyckades.
     *
     * @param input strängen som ska valideras som ett svenskt personnummer
     * @return boolean: true om personnummret är korrekt, annars false
     */
    @Override
    public boolean validate(String input) {
        setInput(input);
        return removeDash() && removePlus() && validLength(new int[]{10, 12}) && onlyDigits() && validDate() && checkLuhn();
    }

}


