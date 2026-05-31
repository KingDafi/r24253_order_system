package cz.osu.service;

import cz.osu.model.Order;
import cz.osu.model.OrderItem;
import cz.osu.model.Subscriber;
import cz.osu.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceStatisticsTest {

    private OrderService service;

    @BeforeEach
    void setUp() {
        service = new OrderService();

        ArrayList<OrderItem> items1 = new ArrayList<>();
        items1.add(new OrderItem("Notebook", 1, 10000.0));
        items1.add(new OrderItem("Myš",      3,   500.0));
        service.addOrder(new Order(LocalDate.now(),
                new Subscriber("Jan",  "jan@test.cz"),  items1));

        ArrayList<OrderItem> items2 = new ArrayList<>();
        items2.add(new OrderItem("Myš", 5, 400.0));
        service.addOrder(new Order(LocalDate.now(),
                new Subscriber("Petr", "petr@test.com"), items2));
    }

    @Test
    void getTotalValue_correct() {
        assertEquals(13500.0, service.getTotalValue(), 0.001);
    }

    @Test
    void getTotalValue_emptyService_returnsZero() {
        assertEquals(0.0, new OrderService().getTotalValue(), 0.001);
    }

    @Test
    void getTotalValue_singleItem() {
        OrderService s = new OrderService();
        s.addOrder(buildOrder(new OrderItem("Pero", 4, 25.0)));
        assertEquals(100.0, s.getTotalValue(), 0.001);
    }

    @Test
    void getAverageOrderValue_correct() {
        assertEquals(6750.0, service.getAverageOrderValue(), 0.001);
    }

    @Test
    void getAverageOrderValue_emptyService_returnsZero() {
        assertEquals(0.0, new OrderService().getAverageOrderValue(), 0.001);
    }

    @Test
    void getAverageOrderValue_singleOrder() {
        OrderService s = new OrderService();
        s.addOrder(buildOrder(new OrderItem("Sešit", 2, 30.0)));
        assertEquals(60.0, s.getAverageOrderValue(), 0.001);
    }

    @Test
    void getMostSoldProduct_returnsCorrectName() {
        assertTrue(service.getMostSoldProduct().startsWith("Myš"),
                "Expected 'Myš' but got: " + service.getMostSoldProduct());
    }

    @Test
    void getMostSoldProduct_containsQuantity() {
        assertTrue(service.getMostSoldProduct().contains("8"),
                "Expected quantity 8 in: " + service.getMostSoldProduct());
    }

    @Test
    void getMostSoldProduct_emptyService_returnsNoProduct() {
        assertEquals("Žádný produkt", new OrderService().getMostSoldProduct());
    }

    @Test
    void getMostSoldProduct_singleProduct() {
        OrderService s = new OrderService();
        s.addOrder(buildOrder(new OrderItem("Klávesnice", 3, 500.0)));
        assertTrue(s.getMostSoldProduct().startsWith("Klávesnice"));
    }

    private Order buildOrder(OrderItem... items) {
        return new Order(LocalDate.now(),
                new Subscriber("Test", "test@test.cz"),
                new ArrayList<>(List.of(items)));
    }
}