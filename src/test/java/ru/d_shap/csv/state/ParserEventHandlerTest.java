// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv.state;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ru.d_shap.csv.NotRectangularException;

/**
 * Tests for {@link ParserEventHandler}.
 *
 * @author Dmitry Shapovalov
 */
public final class ParserEventHandlerTest {

    /**
     * Test class constructor.
     */
    public ParserEventHandlerTest() {
        super();
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void newObjectTest() {
        ParserEventHandler parserEventHandler = new ParserEventHandler(false);
        Assert.assertEquals("", parserEventHandler.getLastSymbols());
        Assert.assertNull(parserEventHandler.getResult());
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void addLastSymbolTest() {
        ParserEventHandler parserEventHandler = new ParserEventHandler(false);

        parserEventHandler.addLastSymbol('a');
        Assert.assertEquals("a", parserEventHandler.getLastSymbols());

        parserEventHandler.addLastSymbol('b');
        parserEventHandler.addLastSymbol('c');
        parserEventHandler.addLastSymbol('d');
        parserEventHandler.addLastSymbol('e');
        parserEventHandler.addLastSymbol('f');
        parserEventHandler.addLastSymbol('g');
        parserEventHandler.addLastSymbol('h');
        parserEventHandler.addLastSymbol('i');
        Assert.assertEquals("abcdefghi", parserEventHandler.getLastSymbols());

        parserEventHandler.addLastSymbol('j');
        Assert.assertEquals("abcdefghij", parserEventHandler.getLastSymbols());

        parserEventHandler.addLastSymbol('1');
        Assert.assertEquals("bcdefghij1", parserEventHandler.getLastSymbols());

        parserEventHandler.addLastSymbol('2');
        parserEventHandler.addLastSymbol('3');
        parserEventHandler.addLastSymbol('4');
        parserEventHandler.addLastSymbol('5');
        parserEventHandler.addLastSymbol('6');
        parserEventHandler.addLastSymbol('7');
        parserEventHandler.addLastSymbol('8');
        parserEventHandler.addLastSymbol('9');
        Assert.assertEquals("j123456789", parserEventHandler.getLastSymbols());

        parserEventHandler.addLastSymbol('0');
        Assert.assertEquals("1234567890", parserEventHandler.getLastSymbols());

        parserEventHandler.addLastSymbol('a');
        Assert.assertEquals("234567890a", parserEventHandler.getLastSymbols());

        parserEventHandler.addLastSymbol('b');
        parserEventHandler.addLastSymbol('c');
        parserEventHandler.addLastSymbol('d');
        Assert.assertEquals("567890abcd", parserEventHandler.getLastSymbols());
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void pushSymbolTest() {
        ParserEventHandler parserEventHandler = new ParserEventHandler(false);

        parserEventHandler.pushSymbol('a');
        Assert.assertNull(parserEventHandler.getResult());

        parserEventHandler.pushSymbol('b');
        Assert.assertNull(parserEventHandler.getResult());

        parserEventHandler.pushSymbol('c');
        Assert.assertNull(parserEventHandler.getResult());
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void pushColumnTest() {
        ParserEventHandler parserEventHandler = new ParserEventHandler(false);

        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        Assert.assertNull(parserEventHandler.getResult());

        parserEventHandler.pushSymbol('b');
        parserEventHandler.pushColumn();
        Assert.assertNull(parserEventHandler.getResult());

        parserEventHandler.pushSymbol('c');
        parserEventHandler.pushColumn();
        Assert.assertNull(parserEventHandler.getResult());
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void pushRowTest() {
        ParserEventHandler parserEventHandler = new ParserEventHandler(false);

        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        List<List<String>> list1 = parserEventHandler.getResult();
        Assert.assertNotNull(list1);
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(1, list1.get(0).size());
        Assert.assertEquals("a", list1.get(0).get(0));

        parserEventHandler.pushSymbol('b');
        parserEventHandler.pushColumn();
        parserEventHandler.pushSymbol('c');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        List<List<String>> list2 = parserEventHandler.getResult();
        Assert.assertNotNull(list2);
        Assert.assertEquals(1, list2.size());
        Assert.assertEquals(2, list2.get(0).size());
        Assert.assertEquals("b", list2.get(0).get(0));
        Assert.assertEquals("c", list2.get(0).get(1));
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void pushEmptyColumnTest() {
        ParserEventHandler parserEventHandler = new ParserEventHandler(false);

        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        List<List<String>> list1 = parserEventHandler.getResult();
        Assert.assertNotNull(list1);
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(1, list1.get(0).size());
        Assert.assertEquals("", list1.get(0).get(0));

        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        parserEventHandler.pushColumn();
        parserEventHandler.pushSymbol('b');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        List<List<String>> list2 = parserEventHandler.getResult();
        Assert.assertNotNull(list2);
        Assert.assertEquals(1, list2.size());
        Assert.assertEquals(3, list2.get(0).size());
        Assert.assertEquals("a", list2.get(0).get(0));
        Assert.assertEquals("", list2.get(0).get(1));
        Assert.assertEquals("b", list2.get(0).get(2));

        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        List<List<String>> list3 = parserEventHandler.getResult();
        Assert.assertNotNull(list3);
        Assert.assertEquals(1, list3.size());
        Assert.assertEquals(2, list3.get(0).size());
        Assert.assertEquals("a", list3.get(0).get(0));
        Assert.assertEquals("", list3.get(0).get(1));
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void pushEmptyRowTest() {
        ParserEventHandler parserEventHandler = new ParserEventHandler(false);

        parserEventHandler.pushRow();
        List<List<String>> list1 = parserEventHandler.getResult();
        Assert.assertNotNull(list1);
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(0, list1.get(0).size());

        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        parserEventHandler.pushRow();
        parserEventHandler.pushRow();
        parserEventHandler.pushSymbol('b');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        List<List<String>> list2 = parserEventHandler.getResult();
        Assert.assertNotNull(list2);
        Assert.assertEquals(4, list2.size());
        Assert.assertEquals(1, list2.get(0).size());
        Assert.assertEquals("a", list2.get(0).get(0));
        Assert.assertEquals(0, list2.get(1).size());
        Assert.assertEquals(0, list2.get(2).size());
        Assert.assertEquals(1, list2.get(3).size());
        Assert.assertEquals("b", list2.get(3).get(0));

        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        parserEventHandler.pushRow();
        List<List<String>> list3 = parserEventHandler.getResult();
        Assert.assertNotNull(list3);
        Assert.assertEquals(2, list3.size());
        Assert.assertEquals(1, list3.get(0).size());
        Assert.assertEquals("a", list3.get(0).get(0));
        Assert.assertEquals(0, list3.get(1).size());
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void putMultipleSymbolsTest() {
        ParserEventHandler parserEventHandler = new ParserEventHandler(false);
        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        parserEventHandler.pushSymbol('b');
        parserEventHandler.pushSymbol('c');
        parserEventHandler.pushSymbol('d');
        parserEventHandler.pushSymbol('e');
        parserEventHandler.pushColumn();
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        List<List<String>> list = parserEventHandler.getResult();
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(3, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
        Assert.assertEquals("bcde", list.get(0).get(1));
        Assert.assertEquals("", list.get(0).get(2));
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void skipPushColumnTest() {
        ParserEventHandler parserEventHandler = new ParserEventHandler(false);
        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushRow();
        parserEventHandler.pushSymbol('b');
        parserEventHandler.pushSymbol('c');
        parserEventHandler.pushSymbol('d');
        parserEventHandler.pushSymbol('e');
        parserEventHandler.pushRow();
        parserEventHandler.pushSymbol('f');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        List<List<String>> list = parserEventHandler.getResult();
        Assert.assertNotNull(list);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(0, list.get(0).size());
        Assert.assertEquals(0, list.get(1).size());
        Assert.assertEquals(1, list.get(2).size());
        Assert.assertEquals("f", list.get(2).get(0));
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test(expected = NotRectangularException.class)
    public void checkRectangularTest() {
        ParserEventHandler parserEventHandler = new ParserEventHandler(true);
        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        parserEventHandler.pushSymbol('b');
        parserEventHandler.pushColumn();
        parserEventHandler.pushSymbol('c');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
    }

}
