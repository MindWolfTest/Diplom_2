package ru.praktikum.models.order;

import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

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

    /*@Override
    public String toString()
    {
        return "Order{" +
                "ingredients='" + _id  + '\'' +
                '}';
    }*/

}
