// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv.state;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link ru.d_shap.csv.state.Result}.
 *
 * @author Dmitry Shapovalov
 */
public final class ResultTest {

    /**
     * Test class constructor.
     */
    public ResultTest() {
        super();
    }

    /**
     * {@link ru.d_shap.csv.state.Result} class test.
     */
    @Test
    public void newObjectTest() {
        Result result = new Result();
        Assert.assertNull(result.getResult());
    }

    /**
     * {@link ru.d_shap.csv.state.Result} class test.
     */
    @Test
    public void pushCharTest() {
        Result result = new Result();

        result.pushSymbol('a');
        Assert.assertNull(result.getResult());

        result.pushSymbol('b');
        Assert.assertNull(result.getResult());

        result.pushSymbol('c');
        Assert.assertNull(result.getResult());
    }

    /**
     * {@link ru.d_shap.csv.state.Result} class test.
     */
    @Test
    public void pushColumnTest() {
        Result result = new Result();

        result.pushSymbol('a');
        result.pushColumn();
        Assert.assertNull(result.getResult());

        result.pushSymbol('b');
        result.pushColumn();
        Assert.assertNull(result.getResult());

        result.pushSymbol('c');
        result.pushColumn();
        Assert.assertNull(result.getResult());
    }

    /**
     * {@link ru.d_shap.csv.state.Result} class test.
     */
    @Test
    public void pushRowTest() {
        Result result = new Result();

        result.pushSymbol('a');
        result.pushColumn();
        result.pushRow();
        List<List<String>> list1 = result.getResult();
        Assert.assertNotNull(list1);
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(1, list1.get(0).size());
        Assert.assertEquals("a", list1.get(0).get(0));

        result.pushSymbol('b');
        result.pushColumn();
        result.pushSymbol('c');
        result.pushColumn();
        result.pushRow();
        List<List<String>> list2 = result.getResult();
        Assert.assertNotNull(list2);
        Assert.assertEquals(1, list2.size());
        Assert.assertEquals(2, list2.get(0).size());
        Assert.assertEquals("b", list2.get(0).get(0));
        Assert.assertEquals("c", list2.get(0).get(1));
    }

    /**
     * {@link ru.d_shap.csv.state.Result} class test.
     */
    @Test
    public void pushEmptyColumnTest() {
        Result result = new Result();

        result.pushColumn();
        result.pushRow();
        List<List<String>> list1 = result.getResult();
        Assert.assertNotNull(list1);
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(1, list1.get(0).size());
        Assert.assertEquals("", list1.get(0).get(0));

        result.pushSymbol('a');
        result.pushColumn();
        result.pushColumn();
        result.pushSymbol('b');
        result.pushColumn();
        result.pushRow();
        List<List<String>> list2 = result.getResult();
        Assert.assertNotNull(list2);
        Assert.assertEquals(1, list2.size());
        Assert.assertEquals(3, list2.get(0).size());
        Assert.assertEquals("a", list2.get(0).get(0));
        Assert.assertEquals("", list2.get(0).get(1));
        Assert.assertEquals("b", list2.get(0).get(2));

        result.pushSymbol('a');
        result.pushColumn();
        result.pushColumn();
        result.pushRow();
        List<List<String>> list3 = result.getResult();
        Assert.assertNotNull(list3);
        Assert.assertEquals(1, list3.size());
        Assert.assertEquals(2, list3.get(0).size());
        Assert.assertEquals("a", list3.get(0).get(0));
        Assert.assertEquals("", list3.get(0).get(1));
    }

    /**
     * {@link ru.d_shap.csv.state.Result} class test.
     */
    @Test
    public void pushEmptyRowTest() {
        Result result = new Result();

        result.pushRow();
        List<List<String>> list1 = result.getResult();
        Assert.assertNotNull(list1);
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(0, list1.get(0).size());

        result.pushSymbol('a');
        result.pushColumn();
        result.pushRow();
        result.pushRow();
        result.pushRow();
        result.pushSymbol('b');
        result.pushColumn();
        result.pushRow();
        List<List<String>> list2 = result.getResult();
        Assert.assertNotNull(list2);
        Assert.assertEquals(4, list2.size());
        Assert.assertEquals(1, list2.get(0).size());
        Assert.assertEquals("a", list2.get(0).get(0));
        Assert.assertEquals(0, list2.get(1).size());
        Assert.assertEquals(0, list2.get(2).size());
        Assert.assertEquals(1, list2.get(3).size());
        Assert.assertEquals("b", list2.get(3).get(0));

        result.pushSymbol('a');
        result.pushColumn();
        result.pushRow();
        result.pushRow();
        List<List<String>> list3 = result.getResult();
        Assert.assertNotNull(list3);
        Assert.assertEquals(2, list3.size());
        Assert.assertEquals(1, list3.get(0).size());
        Assert.assertEquals("a", list3.get(0).get(0));
        Assert.assertEquals(0, list3.get(1).size());
    }

    /**
     * {@link ru.d_shap.csv.state.Result} class test.
     */
    @Test
    public void putMultipleCharsTest() {
        Result result = new Result();
        result.pushSymbol('a');
        result.pushColumn();
        result.pushSymbol('b');
        result.pushSymbol('c');
        result.pushSymbol('d');
        result.pushSymbol('e');
        result.pushColumn();
        result.pushColumn();
        result.pushRow();
        List<List<String>> list = result.getResult();
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(3, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
        Assert.assertEquals("bcde", list.get(0).get(1));
        Assert.assertEquals("", list.get(0).get(2));
    }

    /**
     * {@link ru.d_shap.csv.state.Result} class test.
     */
    @Test
    public void skipPushColumnTest() {
        Result result = new Result();
        result.pushSymbol('a');
        result.pushRow();
        result.pushSymbol('b');
        result.pushSymbol('c');
        result.pushSymbol('d');
        result.pushSymbol('e');
        result.pushRow();
        result.pushSymbol('f');
        result.pushColumn();
        result.pushRow();
        List<List<String>> list = result.getResult();
        Assert.assertNotNull(list);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(0, list.get(0).size());
        Assert.assertEquals(0, list.get(1).size());
        Assert.assertEquals(1, list.get(2).size());
        Assert.assertEquals("f", list.get(2).get(0));
    }

}
