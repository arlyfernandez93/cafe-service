package com.atradius.cafe.domain.repository;

import com.atradius.cafe.domain.model.Order;

import java.util.List;
public interface OrderRepository {

    List<Order> findByUser(String user);
}
