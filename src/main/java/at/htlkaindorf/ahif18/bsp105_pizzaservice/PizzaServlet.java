package at.htlkaindorf.ahif18.bsp105_pizzaservice;

import at.htlkaindorf.ahif18.bsp105_pizzaservice.data.Bestellung;
import at.htlkaindorf.ahif18.bsp105_pizzaservice.data.Pizza;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "PizzaServlet", value = "/PizzaServlet")
public class PizzaServlet extends HttpServlet {

    private List<Pizza> pizzas;
    private List<Pizza> filteredPizzas;

    @Override
    public void init() throws ServletException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("PizzaAuswahl.csv");
        pizzas = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines().skip(1).map(Pizza::getPizzaFromCSV).collect(Collectors.toList());
        getServletContext().setAttribute("originalPizza", pizzas);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        List<Integer> pizzaAmount = (List<Integer>) getServletContext().getAttribute("pizzas");

        ObjectMapper om = new ObjectMapper();
        String data;

        if (req.getParameter("all") != null) {
            data = om.writeValueAsString(pizzas);
        } else {
            filteredPizzas = new ArrayList<>();

            for (int i = 0; i < pizzaAmount.size(); i++) {
                int amount = pizzaAmount.get(i);
                if (amount != 0){
                    pizzas.get(i).setAmount(amount);
                    filteredPizzas.add(pizzas.get(i));
                }
            }
            data = om.writeValueAsString(new Bestellung(filteredPizzas, (String)getServletContext().getAttribute("lieferadresse")));
        }

        try (PrintWriter out = resp.getWriter()){
            out.print(data);
        }
    }
}