package com.codecool.servlet;

import com.codecool.dao.CouponDao;
import com.codecool.dao.ShopDao;
import com.codecool.dao.database.DatabaseCouponDao;
import com.codecool.dao.database.DatabaseShopDao;
import com.codecool.model.Coupon;
import com.codecool.service.CouponService;
import com.codecool.service.exception.ServiceException;
import com.codecool.service.simple.SimpleCouponService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/coupons")
public final class CouponsServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            CouponDao couponDao = new DatabaseCouponDao(connection);
            ShopDao shopDao = new DatabaseShopDao(connection);
            CouponService couponService = new SimpleCouponService(couponDao, shopDao);

            List<Coupon> coupons = couponService.getCoupons();

            sendMessage(resp, HttpServletResponse.SC_OK, coupons);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            CouponDao couponDao = new DatabaseCouponDao(connection);
            ShopDao shopDao = new DatabaseShopDao(connection);
            CouponService couponService = new SimpleCouponService(couponDao, shopDao);

            String name = req.getParameter("name");
            String percentage = req.getParameter("percentage");

            Coupon coupon = couponService.addCoupon(name, percentage);

            sendMessage(resp, HttpServletResponse.SC_OK, coupon);
        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
