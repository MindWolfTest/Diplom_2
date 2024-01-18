package ru.praktikum.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.Order.OrderCreate;
import ru.praktikum.models.order.Order;
import ru.praktikum.models.order.OrderGenerator;
import ru.praktikum.models.user.User;
import ru.praktikum.BeforeAndAfterTest;
import ru.praktikum.user.UserCreateAndAuthorization;

import java.util.Objects;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

import static ru.praktikum.constants.HashDataConst.*;
import static ru.praktikum.constants.MessageConst.*;
import static ru.praktikum.user.UserGenerator.createCurrentRandomUser;

@Slf4j
@RunWith(Parameterized.class)
public class CreateOrderTest extends BeforeAndAfterTest
{
    private final UserCreateAndAuthorization userCreate = new UserCreateAndAuthorization();
    private final String[] _id;
    private final String booleanHash;


    public CreateOrderTest(String[] _id, String booleanHash)
    {
        this._id = _id;
        this.booleanHash = booleanHash;

    }

    @Parameterized.Parameters()
    public static Object[][] getParameters()
    {
        return new Object[][]{
                {CORRECT_DATA_HASH_WITH_TWO_INGREDIENTS, HASH_OK},
                {CORRECT_DATA_HASH_WITH_ONE_INGREDIENT, HASH_OK},
                {WRONG_DATA_INGREDIENT, WRONG_HASH},
                {NULL_DATA_INGREDIENTS, HASH_NULL}
        };
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    @Description("Создание заказа с авторизацией")
    public void createOrderFromLoginUser()
    {
        User user = createCurrentRandomUser();

        Response response = userCreate.createUser(user);
        accessToken = response.path(ACCESS_TOKEN);

        OrderGenerator createNewOrder = new OrderGenerator();
        Order order = createNewOrder.orderGenerator(_id);
        OrderCreate orderCreate = new OrderCreate();
        if (Objects.equals(booleanHash, HASH_OK))
        {
            Response responseOrder = orderCreate.createNewOrderWithLogin(accessToken, order);
            responseOrder.then().assertThat().statusCode(SC_OK)
                    .and().body(SUCCESS, equalTo(TRUE));
        }
        else if (Objects.equals(booleanHash, WRONG_HASH))
        {
            Response responseOrder = orderCreate.createNewOrderWithLogin(accessToken, order);
            responseOrder.then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
        }
        else if (Objects.equals(booleanHash, HASH_NULL))
        {
            Response responseOrder = orderCreate.createNewOrderWithLogin(accessToken, order);
            responseOrder.then().assertThat().statusCode(SC_BAD_REQUEST)
                    .and().body(SUCCESS, equalTo(FALSE)).body(MESSAGE, equalTo(WRONG_ID_ING));
        }


    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Создание заказа без авторизации")
    public void createOrderWithOutLogin()
    {

        OrderGenerator createNewOrder = new OrderGenerator();
        Order order = createNewOrder.orderGenerator(_id);
        OrderCreate orderCreate = new OrderCreate();
        if (Objects.equals(booleanHash, HASH_OK))
        {
            Response responseOrder = orderCreate.createNewOrderWithOutLogin(order);
            responseOrder.then().assertThat().statusCode(SC_OK)
                    .and().body(SUCCESS, equalTo(TRUE));
        }
        else if (Objects.equals(booleanHash, WRONG_HASH))
        {
            Response responseOrder = orderCreate.createNewOrderWithOutLogin(order);
            responseOrder.then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
        }
        else if (Objects.equals(booleanHash, HASH_NULL))
        {
            Response responseOrder = orderCreate.createNewOrderWithOutLogin(order);
            responseOrder.then().assertThat().statusCode(SC_BAD_REQUEST)
                    .and().body(SUCCESS, equalTo(FALSE)).body(MESSAGE, equalTo(WRONG_ID_ING));
        }
    }
}
