package cz.osu.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Order {
    private final LocalDate date;
    private final Subscriber subscriber;
    private final ArrayList<OrderItem> items;

    public Order(LocalDate date, Subscriber subscriber, ArrayList<OrderItem> items) {
        this.date = date;
        this.subscriber = subscriber;
        this.items = new ArrayList<>(items);
    }

    public LocalDate getDate() { return date; }
    public Subscriber getSubscriber() { return subscriber; }
    public ArrayList<OrderItem> getItems() { return items; }
}