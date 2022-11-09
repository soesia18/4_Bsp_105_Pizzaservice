package at.htlkaindorf.ahif18.bsp105_pizzaservice;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "Language", value = "/Language")
public class Language extends HttpServlet {

    private List<String> deutsch;
    private List<String> english;

    @Override
    public void init() throws ServletException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("language.csv");
        deutsch = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines().map(s -> s.split(";")[0]).collect(Collectors.toList());
        is = getClass().getClassLoader().getResourceAsStream("language.csv");
        english = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines().map(s -> s.split(";")[1]).collect(Collectors.toList());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        

        ObjectMapper om = new ObjectMapper();
        String data = "";
        String language = "";

        if (req.getParameter("language") == null) {
            if (req.getCookies() == null) {
                resp.addCookie(new Cookie("language", "deutsch"));
            } else {
                if (req.getCookies()[0].getValue().equals("deutsch")){
                    data = om.writeValueAsString(deutsch);
                } else {
                    data = om.writeValueAsString(english);
                }
            }
        } else {
            if (req.getParameter("language").equalsIgnoreCase("deutsch")){
                resp.addCookie(new Cookie("language", "deutsch"));
                data = om.writeValueAsString(deutsch);
            } else {
                resp.addCookie(new Cookie("language", "english"));
                data = om.writeValueAsString(english);
            }
        }

        try (PrintWriter out = resp.getWriter()){
            out.println(data);
        }
    }
}
