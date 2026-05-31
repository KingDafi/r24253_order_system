// File: src/cz/osu/service/OrderManager.java
package cz.osu.service;

import cz.osu.model.Order;
import cz.osu.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private final List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public double getTotalValue() {
        double total = 0;
        for (Order order : orders) {
            for (OrderItem item : order.getItems()) {
                total += item.getTotalPrice();
            }
        }
        return total;
    }

    public double getAverageOrderValue() {
        if (orders.isEmpty()) return 0;
        return getTotalValue() / orders.size();
    }
}