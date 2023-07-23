
/**
 * SamordningsnrValidator klassen ärver av ValidityCheck för att validerar svenska organisationsnummer.
 * Den overridear validate() för att inkludera specifika kontroller för svenska organisationsnummer.
 * Skapa en instance av klassen och skicka sedan strängen som ska valideras genom validate().
 */
public class OrgValidator extends ValidityCheck {
    /**
     * Konstruktorn för OrgValidator-klassen.
     * Den anropar konstruktorn för superklassen ValidityCheck.
     */
    public OrgValidator() {
        super();
    }

    /**
     * Kontrollerar att mittersta siffrorna i organisationsnumret är minst 20.
     * Kompatibelt med 12 och 10 i längd av organisationsnummret.
     *
     * @return boolean: true om mittersta siffrorna är över eller lika med 20, annars false
     */
    private boolean validMiddleDigit() {
        int middleIndex;

        switch(input.length()) {
            case 12:
                middleIndex = 4; // For 12-digit numbers, the middle digits are at index 6 and 7
                break;
            case 10:
                middleIndex = 2; // For 10-digit numbers, the middle digits are at index 4 and 5
                break;
            default:
                errors.add("Invalid length of organization number!");
                return false;
        }

        int firstDigit = Character.getNumericValue(input.charAt(middleIndex));
        int secondDigit = Character.getNumericValue(input.charAt(middleIndex + 1));
        if (firstDigit * 10 + secondDigit >= 20) {
            return true;
        }

        errors.add("Invalid middle organization digit!");
        return false;
    }

    /**
     * Kontrollerar om en sträng börjar med "16" och är 12 tecken lång.
     * @return boolean: true om strängen som börjar med "16" är 12 tecken lång eller om den inte börjar med 16, annars false.
     */
    public boolean checkStartsWith16() {

        if (input.startsWith("16")) {
            if (input.length() == 12) {
                return true;
            } else {
                return false;
            }
        }

        return true;
    }

    /**
     * Validerar indata som ett svenskt organisationsnummer.
     * Om valideringen inte går igenom så skrivs de kontroller
     * som misslyckades i konsolen.
     *
     * @param input indata-strängen att validera
     * @return true om indata är ett giltigt organisationsnummer och false om inte
     */
    @Override
    public boolean validate(String input) {
        setInput(input);
        return removeDash() && validLength(new int[]{10, 12}) && onlyDigits() && checkStartsWith16() && validMiddleDigit() && checkLuhn();
    }

}
