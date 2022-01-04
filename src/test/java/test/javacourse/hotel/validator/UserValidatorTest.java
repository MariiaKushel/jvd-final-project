package test.javacourse.hotel.validator;

import by.javacourse.hotel.validator.UserValidator;
import by.javacourse.hotel.validator.impl.UserValidatorImpl;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UserValidatorTest {
    private static UserValidator validator = UserValidatorImpl.getInstance();

   @DataProvider (name = "loginDataProvider")
   public Object[][] createLogin(){
     return new Object[][]{
             {"petrov88@gmail.com", true},
             {"ivanov.ivan@e-company.by", true},
             {"ii@rambler.ua", true},
             {"sidiriv_sider.krut@iba_moskow.ru", true},
             {"azazazazazazazazazazazazazazazazazazazazazaz@gmail.com", false},
             {".ivanov@com.com", false},
             {"petrov@company.brest.com", false},
             {"sidorov@gmail.azazazaz", false},
     };
   }
   @Test (dataProvider = "loginDataProvider")
    public void testValidateLoginPositive(String login, boolean expected){
        boolean actual = validator.validateEmail(login);
       Assert.assertEquals(actual, expected);
    }
}
