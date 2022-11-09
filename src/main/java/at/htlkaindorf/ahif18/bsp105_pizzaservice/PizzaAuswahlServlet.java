package at.htlkaindorf.ahif18.bsp105_pizzaservice;

import at.htlkaindorf.ahif18.bsp105_pizzaservice.data.Pizza;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "PizzaAuswahlServlet", value = "/PizzaAuswahlServlet")
public class PizzaAuswahlServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        getServletContext().removeAttribute("pizzas");

        try (PrintWriter out = response.getWriter()) {
            request.getRequestDispatcher("PizzaAuswahl.html").include(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        List<Integer> pizzas = new ArrayList<>();

        for (int i = 0; i < ((List<Pizza>)getServletContext().getAttribute("originalPizza")).size(); i++) {
        //for (int i = 0; i < 5; i++) {
            pizzas.add(Integer.parseInt(req.getParameter("pizza" + i)));
        }

        System.out.println(pizzas);
        getServletContext().setAttribute("pizzas", pizzas);
        getServletContext().setAttribute("lieferadresse", req.getParameter("lieferadresse"));

        try (PrintWriter out = resp.getWriter()) {
            req.getRequestDispatcher("PizzaBestellung.html").forward(req, resp);
        }
    }

    public void destroy() {
    }
}