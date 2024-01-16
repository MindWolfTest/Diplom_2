package ru.praktikum.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.Order.OrderCreate;
import ru.praktikum.Order.OrderGetFromUser;
import ru.praktikum.models.order.Order;
import ru.praktikum.models.order.OrderGenerator;
import ru.praktikum.models.user.User;
import ru.praktikum.user.DeleteUser;
import ru.praktikum.user.UserCreateAndAuthorization;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.praktikum.constants.HashDataConst.CORRECT_DATA_HASH_WITH_TWO_INGREDIENTS;
import static ru.praktikum.constants.MessageConst.*;

import static ru.praktikum.constants.UrlConst.BASE_URL;
import static ru.praktikum.user.UserGenerator.createCurrentRandomUser;

public class GetUserOrderTest
{
    Order order;
    User user;
    private final UserCreateAndAuthorization userCreate = new UserCreateAndAuthorization();
    protected String accessToken;

    @Before
    public void setUp()
    {
        RestAssured.baseURI = BASE_URL;

        user = createCurrentRandomUser();

        Response response = userCreate.createUser(user);
        accessToken = response.path(ACCESS_TOKEN);

        OrderGenerator createNewOrder = new OrderGenerator();
        order = createNewOrder.orderGenerator(CORRECT_DATA_HASH_WITH_TWO_INGREDIENTS);

        OrderCreate orderCreate = new OrderCreate();
        Response responseCreatedOrder = orderCreate.createNewOrderWithLogin(accessToken, order);
        responseCreatedOrder.then().assertThat().statusCode(SC_OK)
                .and().body(SUCCESS, equalTo(TRUE));
    }


    @Test
    @DisplayName("Получение заказа пользователя с авторизацией")
    @Description("Получение заказа пользователя с авторизацией")
    public void getOrderFromLoginUser()
    {
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

        OrderGetFromUser getOrder = new OrderGetFromUser();
        Response responseGetOrderFromUser = getOrder.getOrderFromUserWithOutAuth();
        responseGetOrderFromUser.then().assertThat().statusCode(SC_UNAUTHORIZED)
                .and().body(SUCCESS, equalTo(FALSE));
    }

    @After
    public void tearDown()
    {
        if (accessToken != null)
        {
            DeleteUser userDelete = new DeleteUser();
            userDelete.deleteUser(accessToken);
        }
    }
}
