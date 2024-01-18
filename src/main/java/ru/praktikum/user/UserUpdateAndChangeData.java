package ru.praktikum.user;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static ru.praktikum.constants.MessageConst.AUTHORIZATION;
import static ru.praktikum.constants.UrlConst.TAKE_USER_DATA_API;
import static ru.praktikum.constants.UrlConst.UPDATE_USER_DATA_API;

public class UserUpdateAndChangeData
{
    @Step("Получение информации о пользователе")
    public Response getUserInfo(String accessToken)
    {
        return given()
                .header(AUTHORIZATION, accessToken)
                .get(TAKE_USER_DATA_API);
    }


    @Step("Изменение пользователя c авторизацией")
    public Response updateUser(Map<String, String> updateData, String accessToken)
    {
        return given()
                .contentType(ContentType.JSON)
                .header(AUTHORIZATION, accessToken)
                .and()
                .body(updateData)
                .when()
                .patch(UPDATE_USER_DATA_API);
    }

    @Step("Изменение пользователя без авторизации")
    public Response updateUserWithOutLogin(Map<String, String> updateData)
    {
        return given()
                .contentType(ContentType.JSON)
                .body(updateData)
                .patch(UPDATE_USER_DATA_API);
    }

}
