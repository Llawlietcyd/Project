import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Yidian Chen and Yanchu Dong
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    /**
     * Test constructor from default.
     */
    @Test
    public final void testDefaultConstructor() {
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();
        assertEquals(n, nExpected);

    }

    /**
     * Test constructor from {@code int}.
     */
    @Test
    public final void testIntConstructor() {
        NaturalNumber n = this.constructorTest(123);
        NaturalNumber nExpected = this.constructorRef(123);
        assertEquals(n, nExpected);

    }

    /**
     * Test constructor from {@code int} zero.
     */
    @Test
    public final void testIntZeroConstructor() {
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        assertEquals(n, nExpected);

    }

    /**
     * Test constructor from {@code int} max.
     */
    public final void testIntConstructorMax() {
        NaturalNumber n = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber nExpected = this.constructorRef(Integer.MAX_VALUE);
        assertEquals(n, nExpected);

    }

    /**
     * Test constructor from {@code String}.
     */
    @Test
    public final void testStringConstructor() {
        NaturalNumber n = this.constructorTest("123");
        NaturalNumber nExpected = this.constructorRef("123");
        assertEquals(n, nExpected);

    }

    /**
     * Test constructor from {@code String} zero.
     *
     */
    @Test
    public final void testStringZeroConstructor() {
        NaturalNumber n = this.constructorTest("0");
        NaturalNumber nExpected = this.constructorRef("0");
        assertEquals(n, nExpected);

    }

    /**
     *
     * Test constructor from {@code NaturalNumber}.
     */
    @Test
    public final void testNaturalNumberConstructor() {
        NaturalNumber n = this.constructorTest(this.constructorRef(123));
        NaturalNumber nExpected = this.constructorRef(123);
        assertEquals(n, nExpected);

    }

    /**
     * Test constructor from {@code NaturalNumber} zero.
     */
    @Test
    public final void testNaturalNumberZeroConstructor() {
        NaturalNumber n = this.constructorTest(this.constructorRef(0));
        NaturalNumber nExpected = this.constructorRef(0);
        assertEquals(n, nExpected);

    }

    /**
     * Test constructor from {@code NaturalNumber} max.
     */
    @Test
    public final void testNaturalNumberConstructorMax() {
        NaturalNumber n = this
                .constructorTest(this.constructorRef(Integer.MAX_VALUE));
        NaturalNumber nExpected = this.constructorRef(Integer.MAX_VALUE);
        assertEquals(n, nExpected);

    }

    /**
     * Test for multiplyBy10 on 0.
     */
    @Test
    public final void testMultiplyBy10on0() {
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        n.multiplyBy10(0);
        assertEquals(n, nExpected);

    }

    /**
     * Test for multiplyBy10 on 20.
     */
    @Test
    public final void testMultiplyBy10onOneDigit() {
        NaturalNumber n = this.constructorTest(2);
        NaturalNumber nExpected = this.constructorRef(20);
        n.multiplyBy10(0);
        assertEquals(n, nExpected);

    }

    /**
     * Test for multiplyBy10 on 0 with addition of value.
     */
    @Test
    public final void testMultiplyBy10on0withAdd() {
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(5);
        n.multiplyBy10(5);
        assertEquals(n, nExpected);
    }

    /**
     * Test for multiplyBy10 on nonZero.
     */
    @Test
    public final void testMultiplyBy10NonZero() {
        NaturalNumber n = this.constructorTest(5);
        NaturalNumber nExpected = this.constructorRef(53);
        n.multiplyBy10(3);
        assertEquals(n, nExpected);
    }

    /**
     *
     * Test for multiplyBy10 on Max value.
     */
    @Test
    public final void testMultiplyBy10onMax() {
        NaturalNumber n = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber nExpected = this.constructorRef(Integer.MAX_VALUE + "0");
        n.multiplyBy10(0);
        assertEquals(n, nExpected);

    }

    /**
     * Test for divideBy10() on 0.
     */
    @Test
    public final void testDivideBy10on0() {
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        n.divideBy10();
        assertEquals(n, nExpected);

    }

    /**
     * Test for divideBy10() on 1.
     */
    @Test
    public final void testDivideBy10on1() {
        NaturalNumber n = this.constructorTest(1);
        NaturalNumber nExpected = this.constructorRef(0);
        n.divideBy10();
        assertEquals(n, nExpected);

    }

    /**
     * Test for divideBy10() on 10.
     */
    @Test
    public final void testDivideBy10on10() {
        NaturalNumber n = this.constructorTest(10);
        NaturalNumber nExpected = this.constructorRef(1);
        n.divideBy10();
        assertEquals(n, nExpected);

    }

    /**
     * Test for divideBy10() on 23.
     */
    @Test
    public final void testDivideBy10on23() {
        NaturalNumber n = this.constructorTest(23);
        NaturalNumber nExpected = this.constructorRef(2);
        n.divideBy10();
        assertEquals(n, nExpected);

    }

    /**
     * Test for divideBy10() on 1000.
     */
    @Test
    public final void testDivideBy10on100() {
        NaturalNumber n = this.constructorTest(100);
        NaturalNumber nExpected = this.constructorRef(10);
        n.divideBy10();
        assertEquals(n, nExpected);

    }

    /**
     *
     * Test for divideBy10() on Max value.
     */
    @Test
    public final void testDivideBy10onMax() {
        NaturalNumber n = this.constructorTest(Integer.MAX_VALUE + "0");
        NaturalNumber nExpected = this.constructorRef(Integer.MAX_VALUE);
        n.divideBy10();
        assertEquals(n, nExpected);

    }

    /**
     * Test for isZero() on 0.
     */
    @Test
    public final void testIsZeroon0() {
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        assertEquals(n.isZero(), nExpected.isZero());

    }

    /**
     * Test for isZero() on nonZero.
     */
    @Test
    public final void testIsZeroNonZeroNumber() {
        NaturalNumber n = this.constructorTest(123);
        NaturalNumber nExpected = this.constructorRef(123);
        assertEquals(n.isZero(), nExpected.isZero());

    }

    /**
     * Test for newInstance.
     */
    @Test
    public final void testNewInstance() {
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        assertEquals(n.newInstance(), nExpected.newInstance());
    }

    /**
     * Test for clear.
     */
    @Test
    public final void testClear() {
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        n.clear();
        assertEquals(n, nExpected);
    }

    /**
     * Test for transferFrom.
     */
    @Test
    public final void testTransferFrom() {
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber n2 = this.constructorTest(1);
        n.transferFrom(n2);
        NaturalNumber nExpected = this.constructorRef(1);
        assertEquals(n, nExpected);
        NaturalNumber n2Initial = this.constructorRef(0);
        assertEquals(n2, n2Initial);
    }
}
