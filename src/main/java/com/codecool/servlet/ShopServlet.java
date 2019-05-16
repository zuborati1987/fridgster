package com.codecool.servlet;

import com.codecool.dao.ShopDao;
import com.codecool.dao.database.DatabaseShopDao;
import com.codecool.model.Shop;
import com.codecool.service.ShopService;
import com.codecool.service.exception.ServiceException;
import com.codecool.service.simple.SimpleShopService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/protected/shop")
public final class ShopServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            ShopDao shopDao = new DatabaseShopDao(connection);
            ShopService shopService = new SimpleShopService(shopDao);

            String id = req.getParameter("id");

            Shop shop = shopService.getShop(id);

            sendMessage(resp, HttpServletResponse.SC_OK, shop);
        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
