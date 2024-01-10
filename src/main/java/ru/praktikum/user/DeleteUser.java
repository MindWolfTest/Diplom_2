package ru.praktikum.user;

import io.qameta.allure.Step;

import io.restassured.response.Response;


import static io.restassured.RestAssured.given;
import static ru.praktikum.constants.MessageConst.AUTHORIZATION;
import static ru.praktikum.constants.UrlConst.DELETE_USER_API;


public class DeleteUser
{
    @Step("Удаление пользователя")
    public Response deleteUser(String accessToken)
    {
        return given()
                .header(AUTHORIZATION, accessToken)
                .delete(DELETE_USER_API);
    }
}
