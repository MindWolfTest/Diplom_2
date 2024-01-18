package ru.praktikum.Order;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static ru.praktikum.constants.MessageConst.AUTHORIZATION;
import static ru.praktikum.constants.UrlConst.ORDER_GET_API;

public class OrderGetFromUser
{
    @Step("Получение заказа пользователя с авторизацией")
    public Response getOrderFromUserWithAuth(String accessToken)
    {
        return given()
                .header(AUTHORIZATION, accessToken)
                .header("Content-type", "application/json")
                .when()
                .get(ORDER_GET_API);
    }
    @Step("Получение заказа пользвателя без авторизации")
    public Response getOrderFromUserWithOutAuth()
    {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(ORDER_GET_API);
    }
}
