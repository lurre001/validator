import java.util.*;

public class Main {
    public static void main(String[] args) {
        PersonnummerValidator pnValidator = new PersonnummerValidator();
        SamordningsnrValidator snValidator = new SamordningsnrValidator();
        OrgValidator orgValidator = new OrgValidator();
        ValidatorDispatcher validatorDispatcher = new ValidatorDispatcher(new ArrayList<>(Arrays.asList(orgValidator, snValidator, pnValidator)));

        if (args.length > 0) {
            for (String arg : args) {
                boolean isValid = validatorDispatcher.validate(arg);
                System.out.println("Validating: " + arg + " - Result: " + isValid + "\n");
            }
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter a string to validate:");
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                boolean isValid = validatorDispatcher.validate(input);
                System.out.println("Validating: " + input + " - Result: " + isValid + "\n");
                System.out.println("Enter a string to validate:");
            }
            scanner.close();
        }
    }
}
