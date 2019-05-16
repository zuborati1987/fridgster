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
import java.util.List;

@WebServlet("/protected/shops")
public final class ShopsServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            ShopDao shopDao = new DatabaseShopDao(connection);
            ShopService shopService = new SimpleShopService(shopDao);

            List<Shop> shops = shopService.getShops();

            sendMessage(resp, HttpServletResponse.SC_OK, shops);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            ShopDao shopDao = new DatabaseShopDao(connection);
            ShopService shopService = new SimpleShopService(shopDao);

            String name = req.getParameter("name");

            Shop shop = shopService.addShop(name);

            sendMessage(resp, HttpServletResponse.SC_OK, shop);
        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
