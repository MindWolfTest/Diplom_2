package ru.praktikum.user;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;

import static ru.praktikum.constants.UrlConst.BASE_URL;

public class BeforeAndAfterTest
{
    protected String accessToken;

    @Before
    public void setUp()
    {
        RestAssured.baseURI = BASE_URL;
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
