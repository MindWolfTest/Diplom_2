package ru.praktikum.models.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserCredsWithOutEmail
{
    private String password;

    public static UserCredsWithOutEmail fromUserWithOutEmail(User user)
    {
        return new UserCredsWithOutEmail(user.getPassword());
    }
}
