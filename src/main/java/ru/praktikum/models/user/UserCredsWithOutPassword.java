package ru.praktikum.models.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserCredsWithOutPassword
{
    private String email;

    public static UserCredsWithOutPassword fromUserWithOutPassword(User user)
    {
        return new UserCredsWithOutPassword(user.getEmail());
    }
}
