package ru.praktikum.models.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class User
{
    private String email;
    private String password;
    private String name;
}
