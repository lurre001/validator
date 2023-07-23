import java.util.ArrayList;

/**
 * Abstract class för validering av strängar.
 * <p>
 * Classen innehåller funktionalitet för validering,
 * så som kontroller av längd, datum, Luhn kontrollsiffra osv.
 * </p>
 */
public abstract class ValidityCheck {
    protected String input;
    protected ArrayList<String> errors;

    public ValidityCheck() {
        errors = new ArrayList<>();
    }

    /**
     * Abstract metod för validering. Om checkar misslyckas så skrivs det med
     * System.out.print.
     *
     * @param input, strängen som ska valideras
     * @return returnerar resultatet av valideringen som en boolean
     */
    public abstract boolean validate(String input);

    public ArrayList<String> getErrors(){
        return errors;
    }

    public void clearErrorList(){
        errors.clear();
    }

    /**
     * Kollar om strängen endast innehåller siffror med regex
     *
     * @return true om strängen endast innehåller siffror
     */
    public boolean onlyDigits() {
        if (input.matches("[0-9]+")) {
            return true;
        }
        errors.add("Invalid characters!");
        return false;
    }

    /**
     * Tar bort bindesträck om den har positionen length-5
     *
     * @return true om bindesträck ej existerar eller om det borttogs på length-5 position
     */
    public boolean removeDash() {
        int indexDash = input.indexOf("-");
        if (indexDash == -1) {
            return true;
        } else if (input.charAt(input.length() - 5) == '-') {
            input = input.substring(0, input.length() - 5) + input.substring(input.length() - 4);
        } else {
            errors.add("Invalid position of '-'!");
            return false;
        }
        return true;
    }

    /**
     * Om plustecken används som skiljetecken på length-5 positionen så omformateras
     * strängen till att ha 19 som prefix för att beksriva århundradet på datumet.
     *
     * @return true om plustecken hittats på rätt positionen eller inte existerar, annars false
     */
    public boolean removePlus() {
        int indexPlus = input.indexOf("+");
        if (indexPlus == -1) {
            return true;
        } else if (input.length() >= 5 && input.charAt(input.length() - 5) == '+') {
            input = input.substring(0, input.length() - 5) + input.substring(input.length() - 4);
            // Adderar två första siffrorna av förra århundradet för unoformt format
            String previousCentury = "19";
            input = previousCentury + input;
        } else {
            errors.add("Invalid position of '+'!");
            return false;
        }
        return true;
    }


    /**
     * Kollar om strängen innehåller ett existerande datum, skottår inräknat.
     * OBS! Behöver uppdateras 2030
     *
     * @return true om datumet existerar, annars false
     */
    public boolean validDate() {
        int year;
        int month;
        int day;

        if (input.length() == 12) {
            year = Integer.parseInt(input.substring(0, 4));
            month = Integer.parseInt(input.substring(4, 6));
            day = Integer.parseInt(input.substring(6, 8));
        } else if (input.length() == 10) {
            year = Integer.parseInt(input.substring(0, 2));
            month = Integer.parseInt(input.substring(2, 4));
            day = Integer.parseInt(input.substring(4, 6));
            year += (year < 30) ? 2000 : 1900; // Assuming 30 as the cutoff year for 2000s vs 1900s
        } else {
            errors.add("Invalid input length!");
            return false;
        }

        if (month < 1 || month > 12) {
            errors.add("Invalid month!");
            return false;
        }

        if (day < 1 || day > 31) {
            errors.add("Invalid day!");
            return false;
        }

        if (month == 2) {
            if (day > 29) {
                errors.add("Invalid day!");
                return false;
            }
            if (day == 29 && !isLeapYear(year)) {
                errors.add("Invalid day!");
                return false;
            }
        }

        if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day > 30) {
                errors.add("Invalid day!");
                return false;
            }
        }

        return true;
    }

    /**
     * Kollar om det är skottår. Tar inte in aktuellt datum utan
     * förutsätter att YY < 23 menar på 20YY.
     *
     * @param year, året som ska kontrolleras YY eller YYYY
     * @return true om året är skottår, annars false
     */
    public boolean isLeapYear(int year) {
        if (year >= 1000 && year <= 9999) {
            return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
        } else if (year >= 0 && year <= 99) {
            int fullYear = (year < 23) ? year + 2000 : year + 1900;
            return isLeapYear(fullYear);
        } else {
            errors.add("Invalid year: " + year);
        }
        return false;
    }


    /**
     * Kollar om strängens längd finns bland de accepterade längderna
     * i arrayn.
     *
     * @param validLengths, en array med int vars längder är accepterade
     * @return true om längden av strängen finns i validLengths, annars false
     */
    public boolean validLength(int[] validLengths) {
        for (int length : validLengths) {
            if (input.length() == length) {
                return true;
            }
        }
        errors.add("Invalid length!");
        return false;
    }

    /**
     * Kollar om strängen har en korrekt kontrollsiffra enligt
     * Luhn algoritm. Accepterar både YY och YYYY år i strängen.
     *
     * @return true om strängen har en korrekt Luhn kontrollsiffra, annars false
     */
    public boolean checkLuhn() {
        int sum = 0;
        int length = input.length();
        int parity = length % 2;
        for (int i = length - 1; i >= (input.length() == 12 ? 2 : 0); i--) {
            int digit = Character.getNumericValue(input.charAt(i));
            if (i % 2 == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }
        if (sum % 10 != 0) {
            errors.add("Invalid Luhn!");
            return false;
        }
        return true;
    }

    /**
     * Tillderlar strängen som ska valideras
     *
     * @param input strängen som ska valideras
     */
    protected void setInput(String input) {
        if (input == null) {
            errors.add("Invalid input: null");
            return;
        }
        this.input = input;
    }
}
