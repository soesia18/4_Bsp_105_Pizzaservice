package at.htlkaindorf.ahif18.bsp105_pizzaservice.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Bestellung {
    private List<Pizza> pizzas;
    private String address;
}
