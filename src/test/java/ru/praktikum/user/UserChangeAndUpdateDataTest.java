package ru.praktikum.user;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.BeforeAndAfterTest;
import ru.praktikum.models.user.User;

import java.util.HashMap;
import java.util.Map;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.praktikum.constants.MessageConst.*;
import static ru.praktikum.user.UserGenerator.createCurrentRandomUser;
import static ru.praktikum.user.UserGenerator.faker;

@Slf4j
@RunWith(Parameterized.class)
public class UserChangeAndUpdateDataTest extends BeforeAndAfterTest
{
    private final UserUpdateAndChangeData userUpdate = new UserUpdateAndChangeData();
    private final UserCreateAndAuthorization userCreate = new UserCreateAndAuthorization();
    private final String email;
    private final String password;
    private final String name;
    private final String testName;


    public UserChangeAndUpdateDataTest(String email, String password, String name, String testName)
    {
        this.email = email;
        this.password = password;
        this.name = name;
        this.testName = testName;
    }

    @Parameterized.Parameters(name = "{index} : update {3}")
    public static Object[][] getParameters()
    {
        return new Object[][]{
                {generateEmail(), generatePassword(), generateName(), "Замена всех полей"},
                {generateEmail(), OLD, OLD, "Замена поля почты, поля пароль и имя старые"},
                {OLD, generatePassword(), OLD, "Замена поля пароль, поля почта и имя старые"},
                {OLD, OLD, generateName(), "Замена поля имя, поля почта и пароль старые"},
                {generateEmail(), NULL, NULL, "Замена поля почты, поля пароль и имя пустые"},
                {NULL, generatePassword(), NULL, "Замена поля пароля, поля почта и имя пустые"},
                {NULL, NULL, generateName(), "Замена поля имя"},
                {generateEmail(), generatePassword(), NULL, "Замена полей почты и пароль"},
                {NULL, generatePassword(), generateName(), "Замена полей пароль и имя"},
                {generateEmail(), NULL, generateName(), "Замена полей почта и имя, поле пароль пустое"}
        };
    }

    @Test
    public void updateLoginUser()
    {
        User user = createCurrentRandomUser();

        Response response = userCreate.createUser(user);
        accessToken = response.path(ACCESS_TOKEN);

        Map<String, String> updateData = createMap(email, password, name);
        log.info("Обновляемые данные: {}", updateData);

        Response responseUpdate = userUpdate.updateUser(updateData, accessToken);
        log.info("Получен ответ от сервера {}", responseUpdate.body().asString());

        responseUpdate.then().statusCode(SC_OK)
                .and().body(SUCCESS, equalTo(true));

    }

    @Test
    public void updateWithOutLogin()
    {

        Map<String, String> updateData = createMap(email, password, name);
        log.info("Обновляемые данные: {}", updateData);

        Response responseUpdate = userUpdate.updateUserWithOutLogin(updateData);
        log.info("Получен ответ от сервера {}", responseUpdate.body().asString());

        responseUpdate.then()
                .statusCode(SC_UNAUTHORIZED)
                .and().body(SUCCESS, equalTo(false))
                .and().body(MESSAGE, equalTo(TEXT_YOU_SHOULD_BE_AUTHORISED));
    }

    private Map<String, String> createMap(String email, String password, String name)
    {
        User user = new User();

        Map<String, String> updateData = new HashMap<>();
        if (email.contains(OLD))
        {
            updateData.put(EMAIL, user.getEmail());
        } else if (!email.contains(NULL))
        {
            updateData.put(EMAIL, email);
        }
        if (password.contains(OLD))
        {
            updateData.put(PASSWORD, user.getPassword());
        } else if (!password.contains(NULL))
        {
            updateData.put(PASSWORD, password);
        }
        if (name.contains(OLD))
        {
            updateData.put(NAME, user.getName());
        } else if (!name.contains(NULL))
        {
            updateData.put(NAME, name);
        }

        return updateData;
    }

    public static String generateEmail()
    {
        return faker.internet().safeEmailAddress();
    }

    public static String generatePassword()
    {
        return faker.internet().password(8, 10);
    }

    public static String generateName()
    {
        return faker.name().firstName();
    }
}