package ru.praktikum.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.praktikum.Order.OrderCreate;
import ru.praktikum.Order.OrderGetFromUser;
import ru.praktikum.models.order.Order;
import ru.praktikum.models.order.OrderGenerator;
import ru.praktikum.models.user.User;
import ru.praktikum.BeforeAndAfterTest;
import ru.praktikum.user.UserCreateAndAuthorization;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.praktikum.constants.MessageConst.*;

import static ru.praktikum.user.UserGenerator.createCurrentRandomUser;

public class GetUserOrderTest extends BeforeAndAfterTest
{
    Order order;
    User user;
    private final UserCreateAndAuthorization userCreate = new UserCreateAndAuthorization();
    private final String[] _id = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"};

    @Test
    @DisplayName("Получение заказа пользователя с авторизацией")
    @Description("Получение заказа пользователя с авторизацией")
    public void getOrderFromLoginUser()
    {
        user = createCurrentRandomUser();

        Response response = userCreate.createUser(user);
        accessToken = response.path(ACCESS_TOKEN);

        OrderGenerator createNewOrder = new OrderGenerator();
        order = createNewOrder.orderGenerator(_id);

        OrderCreate orderCreate = new OrderCreate();
        Response responseCreatedOrder = orderCreate.createNewOrderWithLogin(accessToken, order);
        responseCreatedOrder.then().assertThat().statusCode(SC_OK)
                .and().body(SUCCESS, equalTo(TRUE));

        OrderGetFromUser getOrder = new OrderGetFromUser();
        Response responseGetOrderFromUser = getOrder.getOrderFromUserWithAuth(accessToken);
        responseGetOrderFromUser.then().assertThat().statusCode(SC_OK)
                .and().body(SUCCESS, equalTo(TRUE));
    }

    @Test
    @DisplayName("Получение заказа пользователя без авторизации")
    @Description("Получение заказа пользователя без авторизации")
    public void getOrderWithOutLoginUser()
    {
        user = createCurrentRandomUser();

        Response response = userCreate.createUser(user);
        accessToken = response.path(ACCESS_TOKEN);

        OrderGenerator createNewOrder = new OrderGenerator();
        order = createNewOrder.orderGenerator(_id);

        OrderCreate orderCreate = new OrderCreate();
        Response responseCreatedOrder = orderCreate.createNewOrderWithLogin(accessToken, order);
        responseCreatedOrder.then().assertThat().statusCode(SC_OK)
                .and().body(SUCCESS, equalTo(TRUE));

        OrderGetFromUser getOrder = new OrderGetFromUser();
        Response responseGetOrderFromUser = getOrder.getOrderFromUserWithOutAuth();
        responseGetOrderFromUser.then().assertThat().statusCode(SC_UNAUTHORIZED)
                .and().body(SUCCESS, equalTo(FALSE));
    }
}
