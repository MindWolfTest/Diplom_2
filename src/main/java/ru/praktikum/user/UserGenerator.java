package ru.praktikum.user;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import ru.praktikum.models.user.User;

public class UserGenerator
{
    static Faker faker = new Faker();
    public static User createCurrentRandomUser()
    {
        return User.builder()
                .email(faker.internet().safeEmailAddress())
                .password(faker.internet().password(8,10))
                .name(faker.name().firstName())
                .build();
    }

    public static User createRandomUserWithOutPassword()
    {
        return User.builder()
                .email(faker.internet().safeEmailAddress())
                .name(faker.name().firstName())
                .build();
    }

    public static User createRandomUserWithOutEmail()
    {
        return User.builder()
                .password(faker.internet().password(8,10))
                .name(faker.name().firstName())
                .build();
    }

    public static User createRandomUserWithOutName()
    {
        return User.builder()
                .email(faker.internet().safeEmailAddress())
                .password(faker.internet().password(8,10))
                .build();
    }

    public UserGenerator generateEmail()
    {
        faker.internet().safeEmailAddress();
        return this;
    }

    public UserGenerator generatePassword()
    {
        faker.internet().password(8,10);
        return this;
    }
    public UserGenerator generateName()
    {
        faker.name().firstName();
        return this;
    }



}
