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
import ru.praktikum.user.BeforeAndAfterTest;
import ru.praktikum.user.UserCreateAndAuthorization;


import java.util.Objects;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

import static ru.praktikum.constants.MessageConst.*;
import static ru.praktikum.user.UserGenerator.createCurrentRandomUser;

@Slf4j
@RunWith(Parameterized.class)
public class CreateOrderTest extends BeforeAndAfterTest
{
    private final UserCreateAndAuthorization userCreate = new UserCreateAndAuthorization();
    private final String[] _id;
    private final String booleanHash;
    private final String testName;


    public CreateOrderTest(String[] _id, String booleanHash, String testName)
    {
        this._id = _id;
        this.booleanHash = booleanHash;
        this.testName = testName;
    }

    @Parameterized.Parameters()
    public static Object[][] getParameters()
    {
        return new Object[][]{
                {new String[]{"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"}, HASH_OK, "Формирование заказа с корректным хэш 2ух ингредиентов"},
                {new String[]{"61c0c5a71d1f82001bdaaa6d"}, HASH_OK, "Формирование заказа с корректным хэш 1го ингридиента"},
                {new String[]{"61c0c5a71d1f82001bdaaa6d", "123"}, WRONG_HASH, "Формирование заказа с корректным хэш 1го ингредиента"},
                {new String[]{null}, HASH_NULL, "Формирование заказа без игридиентов"}
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
                    .and().body(SUCCESS, equalTo(FALSE));
        }


    }

        @Test
        @DisplayName("Создание заказа без авторизации")
        @Description("Создание заказа без авторизации")
        public void createOrderWithOutLogin ()
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
