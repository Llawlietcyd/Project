import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import components.map.Map;
import components.map.Map1L;
import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * @author Yidian chen
 */

/**
 * This class is used for generating a glossary from a file. The glossary is
 * output as a series of HTML files.
 */
public final class GlossaryGenerator {
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private GlossaryGenerator() {
        // Prevents instantiation of this utility class.
    }

    /**
     * Reads terms and their definitions from a specified file and stores them
     * in a Map.
     *
     * @param filePath
     *            The path of the input file
     * @return A Map containing terms and definitions
     */
    static Map<String, String> readTerms(String filePath) {
        Map<String, String> terms = new Map1L<>();
        SimpleReader fileInput = new SimpleReader1L(filePath);

        while (!fileInput.atEOS()) {
            String term = fileInput.nextLine();
            StringBuilder definition = new StringBuilder();
            String line = fileInput.nextLine();
            while (!line.isEmpty()) {
                definition.append(line).append("\n");
                line = fileInput.nextLine();
            }
            terms.add(term, definition.toString().trim());
        }

        fileInput.close();
        return terms;
    }

    /**
     * Generates HTML files.
     *
     * @param glossary
     *            The map of terms and definitions
     * @param folderPath
     *            The path to the folder where output files will be saved
     */
    private static void generateHTML(Map<String, String> glossary,
            String folderPath) {
        SimpleWriter indexFile = new SimpleWriter1L(folderPath + "/index.html");

        indexFile.println("<html><head><title>Glossary</title></head><body>");
        indexFile.println("<h1>Glossary</h1>");
        indexFile.println("<ul>");

        // Use Sequence to store terms
        Sequence<String> termsSequence = new Sequence1L<>();
        for (Map.Pair<String, String> termPair : glossary) {
            termsSequence.add(0, termPair.key());
        }

        // Convert Sequence to List for sorting
        List<String> sortedTerms = new ArrayList<>();
        while (termsSequence.length() > 0) {
            sortedTerms.add(termsSequence.remove(termsSequence.length() - 1));
        }
        Collections.sort(sortedTerms);

        for (String term : sortedTerms) {
            indexFile.println(
                    "<li><a href=\"" + term + ".html\">" + term + "</a></li>");

            SimpleWriter termFile = new SimpleWriter1L(
                    folderPath + "/" + term + ".html");
            termFile.println("<html><head><title>" + term + "</title>");
            termFile.println("<style>.term { color: red; "
                    + "font-weight: bold; font-style: italic; }</style>");
            termFile.println("</head><body>");
            termFile.println("<h1><span class='term'>" + term + "</span></h1>");

            String definition = glossary.value(term);
            for (String otherTerm : sortedTerms) {
                // Use regular expressions to ensure that only full words are replaced
                String regex = "\\b" + otherTerm + "\\b";
                String replacement = "<a href='" + otherTerm + ".html'>"
                        + otherTerm + "</a>";
                definition = definition.replaceAll(regex, replacement);
            }
            termFile.println("<p>" + definition + "</p>");
            termFile.println("<p><a href='index.html'>Return to Index</a></p>");
            termFile.println("</body></html>");
            termFile.close();
        }

        indexFile.println("</ul>");
        indexFile.println("</body></html>");
        indexFile.close();
    }

    /**
     * Main method. Reads terms and definitions from a file and generates a
     * glossary as HTML files.
     *
     * @param args
     *            Command line arguments (not used).
     */
    public static void main(String[] args) {
        SimpleReader input = new SimpleReader1L();
        SimpleWriter output = new SimpleWriter1L();

        output.print("Please enter the full path of the terms file: ");
        String filePath = input.nextLine();

        output.print(
                "Please enter the path of the folder to save the output files: ");
        String folderPath = input.nextLine();

        input.close();
        output.close();

        Map<String, String> glossary = readTerms(filePath);
        generateHTML(glossary, folderPath);
    }
}
