package ru.praktikum.models.user;

import lombok.*;

import static ru.praktikum.constants.WrongData.WRONG_EMAIL;
import static ru.praktikum.constants.WrongData.WRONG_PASSWORD;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserCreds
{
    private String email;
    private String password;
    public static UserCreds fromCorrectUser(User user)
    {
        return new UserCreds(user.getEmail(), user.getPassword());
    }

    public static UserCreds fromUserWithWrongEmail(User user)
    {
        return new UserCreds(WRONG_EMAIL, user.getPassword());
    }

    public static UserCreds fromUserWithWrongPassword(User user)
    {
        return new UserCreds(user.getEmail(), WRONG_PASSWORD);
    }
}
