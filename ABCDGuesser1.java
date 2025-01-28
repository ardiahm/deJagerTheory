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
public final class ABCDGuesser1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser1() {
    }

    /**
     * Put a short phrase describing the static method myMethod here.
     */
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

        // essentially stating, keep running until the value of validInput has changed
        while (!validInput) {
            // prompt user to input a real number and capture it as a string
            out.println("Enter a positive real number (mu): ");
            muString = in.nextLine();
            // if the string turns out to be parsable as a Double
            if (FormatChecker.canParseDouble(muString)) {
                // if the parsed Double is positive
                if (Double.parseDouble(muString) > 0) {
                    // assign the parsed Double to mu and change state of validInput
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
                    // if it is, assign the string's value to input
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
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // collect user inputs
        double mu = getPositiveDouble(in, out);
        double w = getPositiveDoubleNotOne(in, out);
        double x = getPositiveDoubleNotOne(in, out);
        double y = getPositiveDoubleNotOne(in, out);
        double z = getPositiveDoubleNotOne(in, out);
        // test: out.println(mu + " " + w + " " + x + " " + y + " " + z);

        // establish exponentsList
        final double[] exponentsList = { -5, -4, -3, -2, -1, -1 / 2, -1 / 3, -1 / 4, 0,
                1 / 4, 1 / 3, 1 / 2, 1, 2, 3, 4, 5 };

        // initialize variables outside of loop
        final double acceptableError = 0.01;
        final double hundred = 100;
        double smallestError = hundred;
        double result = 0;
        double bestResult = 0;
        double relativeError = 0;
        double aBest = 0;
        double bBest = 0;
        double cBest = 0;
        double dBest = 0;

        // nested while loops to calculate every possible combination of exponents
        int i = 0; // index for a
        while (i < exponentsList.length) {
            int j = 0; // index for b
            while (j < exponentsList.length) {
                int k = 0; // index for c
                while (k < exponentsList.length) {
                    int l = 0; // index for d
                    while (l < exponentsList.length) {
                        // assign exponents
                        double a = exponentsList[i];
                        double b = exponentsList[j];
                        double c = exponentsList[k];
                        double d = exponentsList[l];

                        // calculate result
                        result = Math.pow(w, a) * Math.pow(x, b) * Math.pow(y, c)
                                * Math.pow(z, d);

                        // check relative error
                        relativeError = Math.abs(result - mu) / mu;
                        // if error is acceptable =>
                        // save bestResult, smallestError, and the best exponents
                        if (relativeError <= acceptableError) {
                            if (relativeError < smallestError) {
                                smallestError = relativeError;
                                bestResult = result;
                                aBest = a;
                                bBest = b;
                                cBest = c;
                                dBest = d;
                            }
                        }
                        l++;
                    }
                    k++;
                }
                j++;
            }
            i++;
        }

        // output results
        out.println("\n\nYou entered a target number: " + mu);
        out.println("You entered the 4 numbers: " + w + ", " + x + ", " + y + ", " + z);
        out.println("With the exponents (respectively): " + aBest + ", " + bBest + ", "
                + cBest + ", " + dBest);
        out.println("The closest match was: " + bestResult);
        out.println("With a percent of error: " + smallestError * hundred + "%");

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
