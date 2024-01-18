package ru.praktikum.models.order;

import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class Order
{
    private String[] ingredients;

    public Order setIngredients(String[] ingredients)
    {
        this.ingredients = ingredients;
        return this;
    }
}
