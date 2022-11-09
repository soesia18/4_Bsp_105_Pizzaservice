package at.htlkaindorf.ahif18.bsp105_pizzaservice.data;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.MalformedURLException;
import java.net.URL;

@Data
public class Pizza {
    private String name;
    private double price;
    private URL image;
    private int amount;

    public Pizza(String name, double price, URL image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public static Pizza getPizzaFromCSV (String line){
        String[] parameters = line.split(";");
        try {
            return new Pizza(parameters[0], Double.parseDouble(parameters[1]), new URL(parameters[2]));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
