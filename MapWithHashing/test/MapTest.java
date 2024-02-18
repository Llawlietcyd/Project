import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Yidian Chen and Yanchu Dong
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     * Test for Constructor.
     */
    @Test
    public final void testConstructor2() {
        Map<String, String> m = this.createFromArgsTest("a", "1", "b", "2");
        Map<String, String> mExpected = this.createFromArgsRef("a", "1", "b",
                "2");

        assertEquals(m, mExpected);
    }

    /**
     * Test Add method.
     */
    @Test
    public final void testAdd() {
        Map<String, String> m = this.createFromArgsTest("a", "1", "b", "2");
        Map<String, String> mExpected = this.createFromArgsRef("a", "1");
        mExpected.add("b", "2");
        assertEquals(m, mExpected);
    }

    /**
     * Test Add method.
     */
    @Test
    public final void testAdd2() {
        Map<String, String> m = this.createFromArgsTest("a", "1");
        Map<String, String> mExpected = this.createFromArgsRef();
        mExpected.add("a", "1");
        assertEquals(m, mExpected);
    }

    /**
     * Test Add method.
     */
    @Test
    public final void testAdd3() {
        Map<String, String> m = this.createFromArgsTest("a", "1", "b", "2");
        Map<String, String> mExpected = this.createFromArgsRef();
        mExpected.add("a", "1");
        mExpected.add("b", "2");
        assertEquals(m, mExpected);
    }

    /**
     * Test Add method.
     */
    @Test
    public final void testAdd4() {
        Map<String, String> m = this.createFromArgsTest("a", "1", "b", "2");
        Map<String, String> mExpected = this.createFromArgsRef("a", "1", "b",
                "2", "c", "3");
        m.add("c", "3");
        assertEquals(m, mExpected);
    }

    /**
     * Test Add method.
     */
    @Test
    public final void testAdd5() {
        Map<String, String> m = this.createFromArgsTest("a", "1", "b", "2", "c",
                "3");
        Map<String, String> mExpected = this.createFromArgsRef("a", "1", "b",
                "2", "c", "3", "d", "4");
        m.add("d", "4");
        assertEquals(m, mExpected);
    }

    /**
     * Test Remove method.
     */
    @Test
    public final void testRemove1() {
        Map<String, String> m = this.createFromArgsTest("a", "1");
        Map<String, String> mExpected = this.createFromArgsRef("a", "1", "b",
                "2");
        mExpected.remove("b");
        assertEquals(m, mExpected);
    }

    /**
     * Test Remove method.
     */
    @Test
    public final void testRemove2() {
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef("a", "1");
        mExpected.remove("a");
        assertEquals(m, mExpected);
    }

    /**
     * Test Remove method.
     */
    @Test
    public final void testRemove3() {
        Map<String, String> m = this.createFromArgsTest("a", "1", "b", "2");
        Map<String, String> mExpected = this.createFromArgsRef("a", "1", "b",
                "2", "c", "3");
        mExpected.remove("c");
        assertEquals(m, mExpected);
    }

    /**
     * Test Remove method.
     */
    @Test
    public final void testRemove4() {
        Map<String, String> m = this.createFromArgsTest("abcd", "1", "efgh",
                "2", "ijkl", "3");
        Map<String, String> mExpected = this.createFromArgsRef("abcd", "1",
                "efgh", "2");
        Pair<String, String> p = m.remove("ijkl");
        assertEquals("ijkl", p.key());
        assertEquals("3", p.value());
        assertEquals(m, mExpected);

    }

    /**
     * Test Remove method.
     */
    @Test
    public final void testRemove5() {
        Map<String, String> m = this.createFromArgsTest("abcd", "1", "efgh",
                "2", "ijkl", "3");
        Map<String, String> mExpected = this.createFromArgsRef("abcd", "1",
                "ijkl", "3");
        Pair<String, String> p = m.remove("efgh");
        assertEquals("efgh", p.key());
        assertEquals("2", p.value());
        assertEquals(m, mExpected);

    }

    /**
     * Tests RemoveAny method.
     */
    @Test
    public final void testRemoveAny() {
        Map<String, String> m = this.createFromArgsTest("a", "1");
        Map<String, String> mExpected = this.createFromArgsRef();
        Pair<String, String> s = m.removeAny();
        assertTrue(!mExpected.hasKey(s.key()));

        assertEquals(m, mExpected);
    }

    /**
     * Tests RemoveAny.
     */
    @Test
    public final void testRemoveAny2() {
        Map<String, String> m = this.createFromArgsTest("a", "1", "b", "2", "c",
                "3");
        Map<String, String> mExpected = this.createFromArgsRef("a", "1", "b",
                "2", "c", "3");
        Pair<String, String> s = m.removeAny();
        mExpected.remove(s.key());
        assertTrue(!mExpected.hasKey(s.key()));

        assertEquals(m, mExpected);
    }

    /**
     * Tests RemoveAny.
     */
    @Test
    public final void testRemoveAny3() {
        Map<String, String> m = this.createFromArgsTest("a", "1", "b", "2");
        Map<String, String> mExpected = this.createFromArgsRef("a", "1", "b",
                "2");
        Pair<String, String> s = m.removeAny();
        assertTrue(mExpected.hasKey(s.key()));
        mExpected.remove(s.key());
        assertEquals(m, mExpected);
    }

    /**
     * Tests RemoveAny.
     */
    @Test
    public final void testRemoveAny4() {
        Map<String, String> m = this.createFromArgsTest("a", "1", "b", "2", "c",
                "3", "d", "4", "e", "5", "f", "6", "g", "7", "h", "8");
        Map<String, String> mExpected = this.createFromArgsRef("a", "1", "b",
                "2", "c", "3", "d", "4", "e", "5", "f", "6", "g", "7", "h",
                "8");
        Pair<String, String> s = m.removeAny();
        assertTrue(mExpected.hasKey(s.key()));
        mExpected.remove(s.key());
        assertEquals(m, mExpected);
    }

    /**
     * Test value method.
     */
    @Test
    public final void testValue() {
        Map<String, String> m = this.createFromArgsTest("a", "1", "b", "2");
        assertEquals("1", m.value("a"));
    }

    /**
     * Test value method.
     */
    @Test
    public final void testValue2() {
        Map<String, String> m = this.createFromArgsTest("a", "1", "b", "2", "c",
                "3");
        assertEquals("1", m.value("a"));
    }

    /**
     * Test value method.
     */
    @Test
    public final void testValue3() {
        Map<String, String> m = this.createFromArgsTest("a", "1");
        assertEquals("1", m.value("a"));
    }

    /**
     * Test value method.
     */
    @Test
    public final void testValue4() {
        Map<String, String> m = this.createFromArgsTest("a", "1", "b", "2", "c",
                "3");
        assertEquals("3", m.value("c"));
    }

    /**
     * Test value method.
     */
    @Test

    public final void testValue5() {
        Map<String, String> m = this.createFromArgsTest("a", "1", "b", "2", "c",
                "3", "d", "4", "e", "5", "f", "6", "g", "7", "h", "8");
        assertEquals("8", m.value("h"));
    }

    /**
     * Test hasKey method.
     */
    @Test
    public final void testHaskey() {
        Map<String, String> m = this.createFromArgsTest("a", "1", "b", "2");
        assertEquals(true, m.hasKey("b"));
    }

    /**
     * Test hasKey method.
     */
    @Test
    public final void testHasKey2() {
        Map<String, String> m = this.createFromArgsTest();
        assertEquals(false, m.hasKey("a"));
    }

    /**
     * Test hasKey method.
     */
    @Test
    public final void testHasKey3() {
        Map<String, String> m = this.createFromArgsTest("a", "1", "b", "2", "c",
                "3");
        assertTrue(m.hasKey("a") && m.hasKey("b") && m.hasKey("c"));
    }

    /**
     * Test size method.
     */
    @Test
    public final void testSize() {
        Map<String, String> m = this.createFromArgsTest("a", "1", "b", "2");
        assertEquals(2, m.size());
    }

    /**
     * Test size method.
     */
    @Test
    public final void testSingleSize() {
        Map<String, String> m = this.createFromArgsTest("a", "1");
        assertEquals(1, m.size());
    }

    /**
     * Test size method.
     */
    @Test
    public final void testEmptySize() {
        Map<String, String> m = this.createFromArgsTest();
        assertEquals(0, m.size());
    }

    /**
     * Test size method.
     */
    @Test
    public final void testMultiSize() {
        Map<String, String> m = this.createFromArgsTest("a", "1", "b", "2", "c",
                "3", "d", "4", "e", "5", "f", "6", "g", "7", "h", "8");
        assertEquals(8, m.size());
    }

    /**
     * Test size method.
     */
    @Test
    public final void testMoreSize() {
        Map<String, String> m = this.createFromArgsTest("a", "1", "b", "2", "c",
                "3", "d", "4", "e", "5", "f", "6", "g", "7", "h", "8", "i", "9",
                "j", "10", "k", "11", "l", "12", "m", "13", "n", "14", "o",
                "15", "p", "16", "q", "17", "r", "18", "s", "19", "t", "20");
        assertEquals(20, m.size());

    }

}