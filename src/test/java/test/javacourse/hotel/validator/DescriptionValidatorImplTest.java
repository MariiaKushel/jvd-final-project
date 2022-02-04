package test.javacourse.hotel.validator;

import by.javacourse.hotel.validator.DescriptionValidator;
import by.javacourse.hotel.validator.impl.DescriptionValidatorImpl;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static by.javacourse.hotel.controller.command.SessionAttribute.DESCRIPTION_EN_SES;
import static by.javacourse.hotel.controller.command.SessionAttribute.DESCRIPTION_RU_SES;

public class DescriptionValidatorImplTest {

    private DescriptionValidator validator = DescriptionValidatorImpl.getInstance();

    @DataProvider(name = "descriptionDataProvider")
    public Object[][] createData() {
        Map<String, String> map1 = new HashMap<>();
        map1.put(DESCRIPTION_RU_SES, "уру уру");
        map1.put(DESCRIPTION_EN_SES, "aza aza");

        Map<String, String> map2 = new HashMap<>();
        map2.put(DESCRIPTION_RU_SES, "<уру> уру");
        map2.put(DESCRIPTION_EN_SES, "aza aza");

        Map<String, String> map3 = new HashMap<>();
        map3.put(DESCRIPTION_RU_SES, "уру уру");
        map3.put(DESCRIPTION_EN_SES, "aza <aza>");

        Map<String, String> map4 = new HashMap<>();
        map4.put(DESCRIPTION_RU_SES, "");
        map4.put(DESCRIPTION_EN_SES, "");

        return new Object[][]{
                {map1, true},
                {map2, false},
                {map3, false},
                {map4, false},
        };
    }

    @Test(dataProvider = "descriptionDataProvider")
    public void testValidateDescriptionData(Map<String, String> map, boolean expected) {
        boolean actual = validator.validateDescriptionData(map);
        Assert.assertEquals(actual, expected);
    }
}