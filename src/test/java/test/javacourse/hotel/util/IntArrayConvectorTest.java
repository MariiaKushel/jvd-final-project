package test.javacourse.hotel.util;

import by.javacourse.hotel.util.IntArrayConvector;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class IntArrayConvectorTest {

    @DataProvider(name = "arrayProvider")
    public Object[][] createData() {
        return new Object[][]{
                {new int[]{1, 2, 3}, "(?,?,?)", true},
                {new int[]{1, 2, 3}, "(?,?)", false},
                {new int[]{1, 2, 3}, "(?,?,?,?)", false},
                {new int[]{1}, "(?)", true}
        };
    }

    @Test (dataProvider = "arrayProvider")
    public void testConvertToSqlRequestPart(int[] arr, String s, boolean expected) {
        String arrayAsString = IntArrayConvector.convertToSqlRequestPart(arr);
        boolean actual = s.equals(arrayAsString);
        Assert.assertEquals(actual, expected);
    }
}