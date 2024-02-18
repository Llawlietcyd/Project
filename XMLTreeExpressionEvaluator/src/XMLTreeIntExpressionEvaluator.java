import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @ydianchen!!!!! Put your name here
 *
 */
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static int evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        int result = 0; // Result of the evaluation

        if (exp.isTag()) {
            String tag = exp.label();
            if (tag.equals("number")) {
                result = Integer.parseInt(exp.attributeValue("value"));
            } else {
                int left = evaluate(exp.child(0)); // Recursively evaluate the left subtree
                int right = evaluate(exp.child(1)); // Recursively evaluate the right subtree

                if (tag.equals("plus")) {
                    result = left + right;
                } else if (tag.equals("minus")) {
                    result = left - right;
                } else if (tag.equals("times")) {
                    result = left * right;
                } else if (tag.equals("divide")) {
                    if (right != 0) {
                        result = left / right;
                    } else {
                        Reporter.fatalErrorToConsole(
                                "Division by zero error in expression.");
                    }
                } else {
                    Reporter.fatalErrorToConsole("Unknown operation: " + tag);
                }
            }
        }

        return result;
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

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}