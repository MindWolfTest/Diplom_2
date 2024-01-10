package ru.praktikum.models.order;


public class OrderGenerator
{
    public Order orderGenerator(String[] _id)
    {
        return new Order()
                .setIngredients(_id);
    }
}
