package test.javacourse.hotel.util;

import by.javacourse.hotel.util.CurrentPageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CurrentPageExtractorTest {

    private HttpServletRequest requestMock;

    @BeforeMethod
    public void initialize() {
        requestMock = Mockito.mock(HttpServletRequest.class);
    }

    @Test
    public void testExtract() {
        String testStr = "azaza";
        Mockito.when(requestMock.getQueryString()).thenReturn(testStr);
        String actual = CurrentPageExtractor.extract(requestMock);
        String expected = "/controller?" + testStr;
        Assert.assertEquals(actual, expected);
    }

    @AfterMethod
    public void clean() {
        requestMock = null;
    }
}