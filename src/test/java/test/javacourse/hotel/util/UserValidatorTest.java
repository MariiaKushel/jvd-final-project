package test.javacourse.hotel.util;

import by.javacourse.hotel.util.UserValidator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UserValidatorTest {
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
        boolean actual = UserValidator.validateLogin(login);
       Assert.assertEquals(actual, expected);
    }
}
