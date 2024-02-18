import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Put a short phrase describing the program here.
 *
 * @author Yidian Chennnnnnnnnn!!!!!!!!!!!!!!!!!!!!!!! a boy really want to have
 *         a good sleep
 */

public class ABCDGuesser2 {
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
        out.println("Please enter a positive real number as μ: ");
        out.println(
                "(PS:The number is a physical or mathematical constant (π, e, etc.))");
        String n = in.nextLine();
        double m = 0;

        while (m <= 0 || !FormatChecker.canParseDouble(n)) {
            if (FormatChecker.canParseDouble(n)) {
                m = Double.parseDouble(n);
                if (m <= 0) {
                    // If the number is not greater than 0, the user is prompted to re-enter
                    out.println(
                            "The number must be greater than 0. Please enter a positive real number.");
                    n = in.nextLine();
                }
            } else {
                // If the input is not a valid double, prompt the user to re-enter
                out.println("Please enter a positive real number");
                n = in.nextLine();
            }
        }
        return m;
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
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {
        out.println("Please enter a positive real number not equal to 1.0");
        String n = in.nextLine();
        double m = 0;

        while (true) {
            if (FormatChecker.canParseDouble(n)) {
                m = Double.parseDouble(n);

                if (m > 0 && m != 1.0) {
                    return m;
                } else if (m == 1.0) {
                    out.println(
                            "The number cannot be one. Please enter a positive real number not equal to 1.0");
                } else {
                    out.println(
                            "Please enter a positive real number not equal to 1.0");
                }

            } else {
                out.println(
                        "Please enter a valid positive real number not equal to 1.0");
            }

            n = in.nextLine();
        }
    }

    private static double calculateValue(double w, double x, double y, double z,
            double a, double b, double c, double d) {
        return Math.pow(w, a) * Math.pow(x, b) * Math.pow(y, c)
                * Math.pow(z, d);
    }

    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        double mu = getPositiveDouble(in, out);
        out.println("Enter Value of w");
        double w = getPositiveDoubleNotOne(in, out);
        out.println("Enter Value of x");
        double x = getPositiveDoubleNotOne(in, out);
        out.println("Enter Value of y");
        double y = getPositiveDoubleNotOne(in, out);
        out.println("Enter Value of z");
        double z = getPositiveDoubleNotOne(in, out);

        final double[] list = { -5.0, -4.0, -3.0, -2.0, -1, -1.0 / 2.0,
                -1.0 / 3.0, -1.0 / 4.0, 0, 1.0 / 4.0, 1.0 / 3.0, 1.0 / 2.0, 1.0,
                2.0, 3.0, 4.0, 5.0 };
        double diff = Math.abs(mu - calculateValue(w, x, y, z, list[0], list[0],
                list[0], list[0]));
        double a = list[0];
        double b = list[0];
        double c = list[0];
        double d = list[0];
        // Iterate over each combination of powers to find the closest approximation to mu
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list.length; j++) {
                for (int k = 0; k < list.length; k++) {
                    for (int l = 0; l < list.length; l++) {
                        double current = calculateValue(w, x, y, z, list[i],
                                list[j], list[k], list[l]);
                        if (Math.abs(mu - current) < diff) {
                            diff = Math.abs(mu - current);
                            a = list[i];
                            b = list[j];
                            c = list[k];
                            d = list[l];
                        }
                    }
                }
            }
        }
        // Output the best approximation and the relative error
        out.println("a = " + a + ", b = " + b + ", c = " + c + ", d = " + d);
        double approximation = calculateValue(w, x, y, z, a, b, c, d);
        double error = (approximation - mu) / mu * 100.0;
        double absoluteError = Math.abs(error);

        out.println("Best approximation: " + approximation);
        out.println("Relative error: " + absoluteError + "%");

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }
}

// TODO Auto-generated method stub
