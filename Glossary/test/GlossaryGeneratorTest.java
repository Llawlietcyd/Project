import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import components.map.Map;

public class GlossaryGeneratorTest {

    private Map<String, String> terms;
    private final String testFilePath = "test/terms.txt"; // Test file path

    @Before
    public void setUp() throws Exception {
        // Before each test, read the test file
        this.terms = GlossaryGenerator.readTerms(this.testFilePath);
    }

    @Test
    public void testReadTermsNotEmpty() {
        // Check that the mapping is not empty
        assertTrue("The map of terms should not be empty",
                this.terms.size() > 0);
    }

    @Test
    public void testTermNotPresent() {
        // Check for terms that do not exist
        String nonExistentTerm = "Terms that don't exist";
        assertFalse("This term should not exist in the term map",
                this.terms.hasKey(nonExistentTerm));
    }

    @Test
    public void testNonExistentTerm() {
        // Check for a term that does not exist
        String nonExistentTerm = "nonExistentTerm";

        assertFalse("The non-existent term should not be in the map",
                this.terms.hasKey(nonExistentTerm));
    }

}
