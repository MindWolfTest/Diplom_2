package ru.praktikum.Order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.models.order.Order;

import static io.restassured.RestAssured.given;
import static ru.praktikum.constants.MessageConst.AUTHORIZATION;
import static ru.praktikum.constants.UrlConst.ORDER_CREATE_API;

public class OrderCreate
{
    @Step("Создание заказа с авторизацией {order}")
    public Response createNewOrderWithLogin(String accessToken, Order order)
    {
        return given()
                .header(AUTHORIZATION, accessToken)
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(ORDER_CREATE_API);
    }
    @Step("Создание заказа без авторизации {order}")
    public Response createNewOrderWithOutLogin(Order order)
    {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(ORDER_CREATE_API);
    }
}
