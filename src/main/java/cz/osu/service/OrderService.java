package cz.osu.service;

import cz.osu.model.Order;
import cz.osu.model.OrderItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public String getMostSoldProduct() {
        if (orders.isEmpty()) return "Žádný produkt";

        Map<String, Integer> productQuantities = new HashMap<>();
        for (Order order : orders) {
            for (OrderItem item : order.getItems()) {
                String name = item.getProductName();
                productQuantities.put(name, productQuantities.getOrDefault(name, 0) + item.getQuantity());
            }
        }

        String mostSold = null;
        int maxQuantity = -1;

        for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
            if (entry.getValue() > maxQuantity) {
                maxQuantity = entry.getValue();
                mostSold = entry.getKey();
            }
        }
        return mostSold != null ? mostSold + " (" + maxQuantity + " ks)" : "Žádný produkt";
    }
}