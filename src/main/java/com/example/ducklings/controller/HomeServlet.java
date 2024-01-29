package com.example.ducklings.controller;
import com.example.ducklings.models.UserModel;
import com.example.ducklings.repository.DucklingsRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


    @WebServlet("/home")
    public class HomeServlet extends HttpServlet {
        private DucklingsRepository ducklingsRepository = new DucklingsRepository();

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            
            PrintWriter out = resp.getWriter();
            List<UserModel> users = ducklingsRepository.getAllUsers();

            for(UserModel user: users){
                out.println("user: " + user.getName() + "password: " + user.getPassword());
            }
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            ducklingsRepository = new DucklingsRepository();
            ducklingsRepository.createUsers();
            ducklingsRepository.createPaymentsTable();

            UserModel user = ducklingsRepository.getUserByUsername(username);

            if (user != null && user.getPassword().equals(password)) {
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                resp.sendRedirect("/InvoiceServlet");
            }else {
                req.setAttribute("loginError", "Invalid username or password");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        }
    }
