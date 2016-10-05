// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv.state;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link ru.d_shap.csv.state.CharBuffer}.
 *
 * @author Dmitry Shapovalov
 */
public final class CharBufferTest {

    /**
     * Test class constructor.
     */
    public CharBufferTest() {
        super();
    }

    /**
     * {@link ru.d_shap.csv.state.CharBuffer} class test.
     */
    @Test
    public void emptyCharBufferTest() {
        CharBuffer charBuffer = new CharBuffer();
        Assert.assertEquals(0, charBuffer.getLength());
        Assert.assertEquals("", charBuffer.toString());
    }

    /**
     * {@link ru.d_shap.csv.state.CharBuffer} class test.
     */
    @Test
    public void appendCharTest() {
        CharBuffer charBuffer = new CharBuffer();

        charBuffer.append('a');
        Assert.assertEquals(1, charBuffer.getLength());
        Assert.assertEquals("a", charBuffer.toString());

        charBuffer.append('b');
        Assert.assertEquals(2, charBuffer.getLength());
        Assert.assertEquals("ab", charBuffer.toString());

        charBuffer.append('c');
        Assert.assertEquals(3, charBuffer.getLength());
        Assert.assertEquals("abc", charBuffer.toString());
    }

    /**
     * {@link ru.d_shap.csv.state.CharBuffer} class test.
     */
    @Test
    public void charBufferExtensionTest() {
        CharBuffer charBuffer = new CharBuffer();
        charBuffer.append('1');
        charBuffer.append('2');
        charBuffer.append('3');
        charBuffer.append('4');
        charBuffer.append('5');
        charBuffer.append('6');
        charBuffer.append('7');
        charBuffer.append('8');
        charBuffer.append('9');
        charBuffer.append('0');
        charBuffer.append('1');
        charBuffer.append('2');
        charBuffer.append('3');
        charBuffer.append('4');
        charBuffer.append('5');
        charBuffer.append('6');
        charBuffer.append('7');
        charBuffer.append('8');
        charBuffer.append('9');
        charBuffer.append('0');
        charBuffer.append('1');
        charBuffer.append('2');
        charBuffer.append('3');
        charBuffer.append('4');
        charBuffer.append('5');
        Assert.assertEquals(25, charBuffer.getLength());
        Assert.assertEquals("1234567890123456789012345", charBuffer.toString());
    }

    /**
     * {@link ru.d_shap.csv.state.CharBuffer} class test.
     */
    @Test
    public void clearTest() {
        CharBuffer charBuffer = new CharBuffer();
        charBuffer.append('a');
        charBuffer.append('b');
        charBuffer.append('c');
        Assert.assertEquals(3, charBuffer.getLength());
        Assert.assertEquals("abc", charBuffer.toString());

        charBuffer.clear();
        Assert.assertEquals(0, charBuffer.getLength());
        Assert.assertEquals("", charBuffer.toString());
    }

}
