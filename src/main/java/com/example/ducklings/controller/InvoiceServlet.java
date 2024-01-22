package com.example.ducklings.controller;

import com.example.ducklings.models.InvoiceModel;
import com.example.ducklings.service.DucklingsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/InvoiceServlet/*")

public class InvoiceServlet extends HttpServlet {

    private DucklingsService ducklingsService = new DucklingsService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String owner = (String) session.getAttribute("username");

        List<InvoiceModel> invoices = ducklingsService.getAll(owner);
        PrintWriter out = resp.getWriter();

        out.println("[Logged in as user: " + owner + "]");

        resp.setContentType("text/html");
        out.println("<h3> Your receipts: </h3>");

        for (InvoiceModel invoice: invoices){
            out.println("<br>" + "[ID: " + invoice.getId() + "]  " + "[Title: " + invoice.getTitle() + "]  " + "- [Date: "
                    + invoice.getDate() + "]  " + "- [Description: " +invoice.getDescription() + "]  " + "- [Category: "
                    + invoice.getCategory() + "]  " + "- [Price: " + invoice.getPrice() + "]");
        }
        req.getRequestDispatcher("/buttons.jsp").include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getPathInfo()) {
            case "/":
                createReceipt(req, resp);
                break;
            case "/delete":
                deleteReceipt(req, resp);
                break;
            case "/update":
                updateReceipt(req, resp);
                break;
        }
    }
        private void createReceipt(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            HttpSession session = req.getSession(true);
            String owner = (String) session.getAttribute("username");
            if (owner == null) {
                resp.sendRedirect("/login.jsp");
            } else {
                String title = req.getParameter("title");
                String dateString = req.getParameter("date");
                Date date = null;
                if (dateString != null) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        java.util.Date parsed = format.parse(dateString);
                        date = new java.sql.Date(parsed.getTime());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                String description = req.getParameter("description");
                String category = req.getParameter("category");

                BigDecimal price = null;
                try {
                    price = new BigDecimal(req.getParameter("price"));
                } catch (NumberFormatException e) {
                    //----
                }
                ducklingsService.create(title, date, description, category, price, owner);

                resp.sendRedirect(req.getContextPath() + req.getServletPath());
                }
            }

            private void deleteReceipt (HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
                int id = Integer.parseInt(req.getParameter("id"));
                ducklingsService.delete(id);
                resp.sendRedirect(req.getContextPath() + req.getServletPath());
            }

            private void updateReceipt (HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
                HttpSession session = req.getSession(true);
                String owner = (String) session.getAttribute("username");
                if (owner == null) {
                    resp.sendRedirect("/login.jsp");
                } else {
                    int id = Integer.parseInt(req.getParameter("id"));
                    String title = req.getParameter("title");
                    String dateString = req.getParameter("date");
                    Date date = null;
                    if (dateString != null) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            java.util.Date parsed = format.parse(dateString);
                            date = new java.sql.Date(parsed.getTime());
                        } catch (ParseException e) {
                        }
                    }
                    String description = req.getParameter("description");
                    String category = req.getParameter("category");
                    BigDecimal price = null;
                    try {
                        price = new BigDecimal(req.getParameter("price"));
                    } catch (NumberFormatException e) {
                    }
                    ducklingsService.update(id, title, date, description, category, price, owner);

                    resp.sendRedirect(req.getContextPath() + req.getServletPath());
                }
            }

}

