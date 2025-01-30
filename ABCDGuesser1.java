import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * A java program which prompts a user to input any positive real number (let's
 * call it mu), then input 4 additional positive real numbers (called w, x, y,
 * and z). Given an array of potential exponents (a, b, c, and d), the program
 * will raise w, x, y, and z to the power of a, b, c, and d: guaranteeing the
 * result is within 1% of the target number (mu).
 *
 * @author Ardi Ahmed
 *
 */
public final class ABCDGuesser2 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser2() {
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        // establish variables outside of loop
        boolean validInput = false;
        double mu = 0;
        String muString = "";

        // essentially stating, keep running until the value of
        // validInput has changed
        while (!validInput) {
            // prompt user to input a real number and capture it as a string
            out.println("Enter a positive real number (mu): ");
            muString = in.nextLine();
            // if the string turns out to be parse-able as a Double
            if (FormatChecker.canParseDouble(muString)) {
                // if the parsed Double is positive
                if (Double.parseDouble(muString) > 0) {
                    // assign the parsed Double to mu and change state of
                    // validInput
                    mu = Double.parseDouble(muString);
                    validInput = true;
                } else {
                    out.println("You have not entered a positive real number.\n\n");
                }
            } else {
                out.println("You have not entered a number.\n\n");
            }
        }
        return mu;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in, SimpleWriter out) {
        // establish variables outside of loop
        boolean validInput = false;
        double input = 0;
        String inputString = "";

        // keep looping until the state of validInput changes
        while (!validInput) {
            // prompt user to input and capture it as a string
            out.println("Enter a positive real number not equal to 1.0: ");
            inputString = in.nextLine();
            // if the string can be parsed as a Double
            if (FormatChecker.canParseDouble(inputString)) {
                // check if the string is != 1 and is positive
                if (Double.parseDouble(inputString) != 1.0
                        && Double.parseDouble(inputString) > 0) {
                    // if valid, assign the string's value to input
                    // and change the value of validInput
                    input = Double.parseDouble(inputString);
                    validInput = true;
                } else {
                    out.println("You have entered either a negative number or 1.0\n\n");
                }
            } else {
                out.println("You have not entered a number.\n\n");
            }
        }

        return input;

    }

    /**
     * Method which outputs the best exponents, best result, and smallest error
     * (as strings) given the user's first 4 original inputs.
     *
     * @param in
     *            the input stream
     *
     * @param out
     *            the output stream
     *
     * @param mu
     *            user's inputed target number
     *
     * @param userValues
     *            user's inputed doubles stored in a double[] array. indexes 0-3
     *            represent w, x, y, z respectively
     *
     * @param bestExponentValues
     *            the exponents calculated stored in a double[] array, for which
     *            raising each w, x, y, z to will produce the best and closest
     *            result
     *
     * @param smallestError
     *            best (as in smallest) error calculated due to raising w,x,y,z
     *            to aBest,bBest,cBest,dBest
     *
     * @param bestResult
     *            the most accurate value calculated due to raising w,x,y,z to
     *            aBest,bBest,cBest,dBest
     *
     */

    private static void performOutput(SimpleReader in, SimpleWriter out, double mu,
            double[] userValues, double[] bestExponentValues, double smallestError,
            double bestResult) {

        // initialize number variables to avoid magic number warning
        final double hundred = 100;
        final int one = 1;
        final int two = 2;
        final int three = 3;

        final double smallestPercent = smallestError * hundred;

        // output results
        out.println("\n\nYou entered a target number: " + mu);
        out.println("You entered the 4 numbers: " + userValues[0] + ", " + userValues[one]
                + ", " + userValues[two] + ", " + userValues[three]);
        out.println("With the exponents (respectively): " + bestExponentValues[0] + ", "
                + bestExponentValues[one] + ", " + bestExponentValues[two] + ", "
                + bestExponentValues[three]);
        out.println("The closest match was: " + bestResult);
        out.print("With a percent of error: ");
        // OSU provided documentation to trim doubles to a set number decimal places
        out.print(smallestPercent, 2, false);
        out.print("%");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        // initialize input and output stream
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // declare numbers here to avoid magic number warning
        final double hundred = 100;
        final int one = 1;
        final int two = 2;
        final int three = 3;
        final int four = 4;

        // initialize double arrays for both user inputs and best exponents
        double[] userValues = new double[four];
        double[] bestExponentValues = new double[four];

        // collect user inputs and store in userValues array
        double mu = getPositiveDouble(in, out);
        double w = getPositiveDoubleNotOne(in, out);
        userValues[0] = w;
        double x = getPositiveDoubleNotOne(in, out);
        userValues[one] = x;
        double y = getPositiveDoubleNotOne(in, out);
        userValues[two] = y;
        double z = getPositiveDoubleNotOne(in, out);
        userValues[three] = z;

        // establish exponentsList
        final double[] exponentsList = { -5, -4, -3, -2, -1, -1 / 2, -1 / 3, -1 / 4, 0,
                1 / 4, 1 / 3, 1 / 2, 1, 2, 3, 4, 5 };

        // initialize variables outside of loop (avoiding magic number warning)
        final double acceptableError = 0.01;
        double smallestError = hundred;
        double result = 0;
        double bestResult = 0;
        double relativeError = 0;

        // nested while loops to calculate every possible combination of exponents
        for (int i = 0; i < exponentsList.length; i++) {
            for (int j = 0; j < exponentsList.length; j++) {
                for (int k = 0; k < exponentsList.length; k++) {
                    for (int l = 0; l < exponentsList.length; l++) {
                        // assign exponents
                        double a = exponentsList[i];
                        double b = exponentsList[j];
                        double c = exponentsList[k];
                        double d = exponentsList[l];

                        // calculate result by raising each w, x, y, z to
                        // a, b, c, d respectively
                        result = Math.pow(w, a) * Math.pow(x, b) * Math.pow(y, c)
                                * Math.pow(z, d);

                        // calculate relative (current) error
                        relativeError = Math.abs(result - mu) / mu;

                        // if relative error falls within acceptableError range
                        if (relativeError <= acceptableError) {
                            // if relative error is less than current smallestError
                            if (relativeError < smallestError) {
                                // save current values of error and result in
                                // smallestError and bestResult
                                smallestError = relativeError;
                                bestResult = result;
                                // store exponents in bestExponentValues[] array
                                bestExponentValues[0] = a;
                                bestExponentValues[one] = b;
                                bestExponentValues[two] = c;
                                bestExponentValues[three] = d;
                            }
                        }

                    }
                }
            }
        }

        // call performOutput method and pass through mu, list of userValues,
        // list of bestExponentValues, smallestError and bestResult

        performOutput(in, out, mu, userValues, bestExponentValues, smallestError,
                bestResult);

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
