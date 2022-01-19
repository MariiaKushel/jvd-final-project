package test.javacourse.hotel.entity;

import by.javacourse.hotel.entity.User;
import org.testng.annotations.Test;

public class UserTest {
    @Test
    public void testBuild (){
        User user  = User.newBuilder()
                .setEmail("123@mail.com")
                .setName("Иван")
                .setPhoneNumber("+375291111111")
                .setEntityId(3L)
                .build();
    }

}
