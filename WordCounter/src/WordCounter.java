import components.map.Map;
import components.map.Map1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This class counts the number of occurrences of each word in a text file and
 * outputs the result as an HTML file. author Yidian Chen
 */
public final class WordCounter {
    /**
     * No argument constructor--private to prevent instantiation.
     */
    private WordCounter() {
    }

    /**
     * The main method for the WordCounter class. This method is the entry point
     * of the application. It prompts the user for the names of the input and
     * output files, then reads words from the input file, counts the
     * occurrences of each word, and writes the word counts to the output file
     * in HTML format.
     *
     * @param args
     *            Command line arguments (not used).
     */
    public static void main(String[] args) {
        SimpleReader input = new SimpleReader1L();
        SimpleWriter output = new SimpleWriter1L();
        output.println("Enter the name of the input file:");
        String inputFileName = input.nextLine();
        output.println("Enter the name of the output file:");
        String outputFileName = input.nextLine();

        SimpleReader fileInput = new SimpleReader1L(inputFileName);

        Map<String, Integer> wordCounts = new Map1L<>();
        // Loop through each line of the input file
        while (!fileInput.atEOS()) {
            String line = fileInput.nextLine();
            String currentWord = "";
            // Iterate over each character in the line
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                // Check whether the character is a letter
                if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                    currentWord += c;
                } else if (!(currentWord.length() == 0)) { // Process complete words
                    if (!wordCounts.hasKey(currentWord)) {
                        wordCounts.add(currentWord, 1);
                    } else {
                        wordCounts.replaceValue(currentWord,
                                wordCounts.value(currentWord) + 1);
                    }
                    currentWord = "";
                }
            }

            // Check if the last word is not processed
            if (!(currentWord.length() == 0)) {
                if (!wordCounts.hasKey(currentWord)) {
                    wordCounts.add(currentWord, 1);
                } else {
                    wordCounts.replaceValue(currentWord,
                            wordCounts.value(currentWord) + 1);
                }
            }
        }

        fileInput.close();

        String[] wordsArray = new String[wordCounts.size()];
        int index = 0;
        for (Map.Pair<String, Integer> pair : wordCounts) {
            wordsArray[index++] = pair.key();
        }

        // Implement bubble sort for case-insensitive sorting
        for (int i = 0; i < wordsArray.length - 1; i++) {
            for (int j = 0; j < wordsArray.length - i - 1; j++) {
                // Convert both strings to lowercase and compare them
                if (wordsArray[j].toLowerCase()
                        .compareTo(wordsArray[j + 1].toLowerCase()) > 0) {
                    // Exchange wordsArray[j+1] and wordsArray[j]
                    String temp = wordsArray[j];
                    wordsArray[j] = wordsArray[j + 1];
                    wordsArray[j + 1] = temp;
                }
            }
        }

        // Generating HTML output using sorted array
        SimpleWriter fileOutput = new SimpleWriter1L(outputFileName);
        fileOutput
                .println("<html><head><title>Word Count</title></head><body>");
        fileOutput.println("<h1>Word Count for " + inputFileName + "</h1>");
        fileOutput.println("<table border=\"1\">");
        fileOutput.println("<tr><th>Word</th><th>Count</th></tr>");

        for (String word : wordsArray) {
            fileOutput.println("<tr><td>" + word + "</td><td>"
                    + wordCounts.value(word) + "</td></tr>");
        }

        fileOutput.println("</table></body></html>");
        fileOutput.close();

        input.close();
        output.close();
    }
}
