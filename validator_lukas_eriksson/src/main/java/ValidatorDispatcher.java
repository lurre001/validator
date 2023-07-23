import java.util.ArrayList;
/**
 * Klassen ansvarar för att validera en sträng med hjälp av en lista över tillhandahållna validerare.
 */
public class ValidatorDispatcher {
    private ArrayList<ValidityCheck> validators;

    /**
     * Konstruktör för ValidatorDispatcher.
     *
     * @param validators En ArrayList med objekt för giltighetskontroll.
     */
    public ValidatorDispatcher(ArrayList<ValidityCheck> validators) {
        this.validators = validators;
    }
    /**
     * Metod för att validera en sträng med hjälp av de tillhandahållna validerarna.
     * Returnerar sant om strängen är giltig enligt någon av validerarna.
     * Om strängen inte är giltig skrivs felen ut från den validerare som hade minst fel.
     *
     * @param input Strängen som ska valideras.
     * @return sant om strängen är giltig, falskt annars.
     */
    public boolean validate(String input) {
        input = preProcess(input);
        int smallestAmountErrors = Integer.MAX_VALUE;
        int smallestAmountErrorsIndex = -1;

        for (int i = 0; i < validators.size(); i++) {
            ValidityCheck validator = validators.get(i);
            if (validator.validate(input)) {
                clearSessionErrors();
                return true;
            } else {
                int errorsSize = validator.getErrors().size();
                if(errorsSize < smallestAmountErrors){
                    smallestAmountErrors = errorsSize;
                    smallestAmountErrorsIndex = i;
                }
            }
        }

        if (smallestAmountErrorsIndex != -1) {
            printErrors(validators.get(smallestAmountErrorsIndex));
        }
        clearSessionErrors();
        return false;
    }
    /**
     * Hjälpmetod för att förbehandla input-strängen.
     *
     * @param input Strängen som ska förbehandlas.
     * @return Den förbehandlade strängen.
     */
    private String preProcess(String input) {
        input = input.trim();
        return input;
    }
    /**
     * Hjälpmetod för att skriva ut felen från en given validerare.
     *
     * @param validator
     */
    private void printErrors(ValidityCheck validator){
        for(String s : validator.getErrors()){
            System.out.println(s);
        }

    }
    private void clearSessionErrors(){
        for(ValidityCheck validator: validators){
            validator.clearErrorList();
        }
    }
}