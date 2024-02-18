import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 *
 * @author chenyidian
 *
 */
public final class Newton2 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton2() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            positive number to compute square root of.
     * @return estimate of square root.
     */
    private static double sqrt(double x) {
        final double EPSILON = 0.0001; //estimate epsilon 0.01%
        double t = x;
        if (x > 0) {
            while ((Math.abs(t * t - x) / x) > EPSILON * EPSILON) {
                t = (t + x / t) / 2;
            }
        }
        return t;
    }/*
      * we can enter 0 now
      */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Put your main program code here; it may call myMethod as shown
         */
        out.println("Enter a number: ");
        double x = in.nextDouble();
        double sqrt = sqrt(x);
        out.println(sqrt);
        out.println(
                "If you wish to continue calculate square root, just enter y.");
        out.println("if no, just enter another letter");
        String letter = in.nextLine();
        while (letter.equals("y")) {
            out.println("Enter number:");
            x = in.nextDouble();
            sqrt = sqrt(x);
            out.println(sqrt);
            out.println(
                    "If you wish to continue calculating other square root,just enter y.");
            out.println("if not, just enter another letter");
            letter = in.nextLine();
        }
        out.println("GAMEOVER!!!!");
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
