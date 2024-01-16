package ru.praktikum.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import ru.praktikum.models.user.User;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.praktikum.constants.MessageConst.*;
import static ru.praktikum.constants.UrlConst.BASE_URL;
import static ru.praktikum.user.UserGenerator.createCurrentRandomUser;

public class DeleteUserTest
{

    @Test
    @DisplayName("Корректное удалние пользователя со случайными данными")
    @Description("Корректное удаление пользователя со случайными данными")
    public void deleteCurrentUserTest()
    {
        String accessToken;
        RestAssured.baseURI = BASE_URL;
        User user = createCurrentRandomUser();
        UserCreateAndAuthorization userCreate = new UserCreateAndAuthorization();

        Response response = userCreate.createUser(user);
        response.then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body(SUCCESS, equalTo(TRUE));

        accessToken = response.path(ACCESS_TOKEN);

        DeleteUser userDelete = new DeleteUser();
        Response deleteResponse =
        userDelete.deleteUser(accessToken);
        deleteResponse.then()
                .assertThat()
                .statusCode(SC_ACCEPTED)
                .and()
                .body(SUCCESS, equalTo(TRUE))
                .body(MESSAGE, equalTo(TEXT_USER_SUCCESSFULLY_REMOVED));
    }
}
