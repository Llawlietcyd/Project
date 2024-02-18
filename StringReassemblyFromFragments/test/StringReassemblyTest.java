import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class StringReassemblyTest {

    /*
     * tests of overlap
     */
    @Test
    public void testOverlapExampleOne() {
        String str1 = "java";
        String str2 = "vaprogram";
        int overlap = StringReassembly.overlap(str1, str2);
        assertEquals(2, overlap);
    }

    @Test
    public void testOverlapExampleTwo() {
        String str1 = "computer";
        String str2 = "uterworld";
        int overlap = StringReassembly.overlap(str1, str2);
        assertEquals(4, overlap);
    }

    @Test
    public void testOverlapExampleThree() {
        String str1 = "code";
        String str2 = "data";
        int overlap = StringReassembly.overlap(str1, str2);
        assertEquals(0, overlap);
    }

    /*
     * tests of combination
     */
    @Test
    public void testCombinationExampleOne() {
        String str1 = "java";
        String str2 = "vaprogram";
        int overlap = 2;
        String combine = StringReassembly.combination(str1, str2, overlap);
        assertEquals("javaprogram", combine);
    }

    @Test
    public void testCombinationExampleTwo() {
        String str1 = "computer";
        String str2 = "uterworld";
        int overlap = 4;
        String combine = StringReassembly.combination(str1, str2, overlap);
        assertEquals("computerworld", combine);
    }

    /*
     * tests of addToSetAvoidingSubstrings
     */
    @Test
    public void testAddToSetAvoidingSubstringsExampleOne() {
        Set<String> strSet = new Set1L<>();
        strSet.add("small");
        strSet.add("smaller");
        strSet.add("smallest");
        String str = "small";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertEquals(3, strSet.size()); // "small" already exists in the set
    }

    @Test
    public void testAddToSetAvoidingSubstringsExampleTwo() {
        Set<String> strSet = new Set1L<>();
        strSet.add("test");
        strSet.add("testing");
        String str = "tester";

        // Expected set should include "tester" and remove "test" as it's a substring of "tester"
        Set<String> expect = new Set1L<>();
        expect.add("testing");
        expect.add("tester");

        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertEquals(expect, strSet);
    }

    /*
     * tests of printWithLineSeparators
     */
    @Test
    public void testPrintWithLineSeparatorsExampleOne() {
        SimpleWriter out = new SimpleWriter1L("output1.txt");
        SimpleReader in = new SimpleReader1L("output1.txt");
        String text = "Java~is~fun";
        String expect = "Java\nis\nfun";
        StringReassembly.printWithLineSeparators(text, out);
        StringBuilder actual = new StringBuilder();
        while (!in.atEOS()) {
            actual.append(in.nextLine());
            if (!in.atEOS()) {
                actual.append("\n");
            }
        }
        in.close();
        out.close();
        assertEquals(expect, actual.toString());
    }

    @Test
    public void testPrintWithLineSeparatorsExampleTwo() {
        SimpleWriter out = new SimpleWriter1L("output2.txt");
        SimpleReader in = new SimpleReader1L("output2.txt");
        String text = "Hello~World~!";
        String expect = "Hello\nWorld\n!";
        StringReassembly.printWithLineSeparators(text, out);
        StringBuilder actual = new StringBuilder();
        while (!in.atEOS()) {
            actual.append(in.nextLine());
            if (!in.atEOS()) {
                actual.append("\n");
            }
        }
        in.close();
        out.close();
        assertEquals(expect, actual.toString());
    }

    /*
     * tests of assemble
     */
    @Test
    public void testAssembleExampleOne() {
        Set<String> strSet = new Set1L<>();
        strSet.add("I love");
        strSet.add("ove Java");
        strSet.add("Java is");
        strSet.add("is great");
        StringReassembly.assemble(strSet);
        assertEquals(1, strSet.size());
        assertTrue(strSet.contains("I love Java is great"));
    }

    @Test
    public void testAssembleExampleTwo() {
        Set<String> strSet = new Set1L<>();
        strSet.add("Hello ");
        strSet.add("World");
        strSet.add("Java");
        strSet.add("lo W");

        // Expected: The strings "Hello " and "lo W" should be combined with "World" to form "Hello World".
        // "Java" remains as it has no overlapping fragment.
        Set<String> expect = new Set1L<>();
        expect.add("Hello World");
        expect.add("Java");

        StringReassembly.assemble(strSet);
        assertEquals(expect, strSet);

        // Adding another assertion for a different scenario
        Set<String> anotherSet = new Set1L<>();
        anotherSet.add("Quick ");
        anotherSet.add("Brown Fox");
        anotherSet.add("k B");
        anotherSet.add("Fox Jumps");

        Set<String> anotherExpect = new Set1L<>();
        anotherExpect.add("Quick Brown Fox Jumps");

        StringReassembly.assemble(anotherSet);
        assertEquals(anotherExpect, anotherSet);
    }

}
