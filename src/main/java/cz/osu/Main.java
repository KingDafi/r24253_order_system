// File: src/cz/osu/Main.java
package cz.osu;

import cz.osu.model.Order;
import cz.osu.model.OrderItem;
import cz.osu.model.Subscriber;
import cz.osu.service.OrderService;
import cz.osu.validator.OrderItemValidator;
import cz.osu.validator.SubscriberValidator;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final OrderService orderService = new OrderService();
    private static final SubscriberValidator subscriberValidator = new SubscriberValidator();
    private static final OrderItemValidator orderItemValidator = new OrderItemValidator();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initMockData();

        boolean running = true;
        while (running) {
            System.out.println("\n===\\ SYSTÉM PRO ZADÁVÁNÍ OBJEDNÁVEK /===");
            System.out.println("1. Zadat novou objednávku");
            System.out.println("2. Zobrazit statistiky");
            System.out.println("3. Exportovat do CSV");
            System.out.println("4. Konec");
            System.out.print("Vyberte možnost: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> inputNewOrder();
                case "2" -> printStatistics();
                case "3" -> exportData();
                case "4" -> {
                    running = false;
                    System.out.println("Konec");
                }
                default -> System.out.println("Neplatná volba! Zkuste to znovu.");
            }
        }
    }

    private static void inputNewOrder() {
        System.out.println("\n___| Zadávání nové objednávky |___");
        try {
            System.out.print("Zadejte jméno odběratele: ");
            String name = scanner.nextLine();
            System.out.print("Zadejte email odběratele: ");
            String email = scanner.nextLine();

            Subscriber subscriber = new Subscriber(name, email);
            subscriberValidator.validate(subscriber);

            ArrayList<OrderItem> items = new ArrayList<>();
            boolean addingItems = true;

            while (addingItems) {
                System.out.print("Zadejte název produktu: ");
                String prodName = scanner.nextLine();
                System.out.print("Zadejte množství (celé číslo): ");
                int quantity = Integer.parseInt(scanner.nextLine());
                System.out.print("Zadejte cenu za kus: ");
                double price = Double.parseDouble(scanner.nextLine());

                OrderItem item = new OrderItem(prodName, quantity, price);
                orderItemValidator.validate(item);
                items.add(item);

                System.out.print("Chcete přidat další produkt? (a/n): ");
                String next = scanner.nextLine().trim().toLowerCase();
                if (!next.equals("a")) {
                    addingItems = false;
                }
            }

            if (items.isEmpty()) {
                System.out.println("Chyba: Objednávka musí obsahovat alespoň jednu položku.");
                return;
            }

            Order order = new Order(LocalDate.now(), subscriber, items);
            orderService.addOrder(order);
            System.out.println("-> Objednávka byla úspěšně uložena do systému.");

        } catch (NumberFormatException e) {
            System.out.println("Chyba vstupu: Množství nebo cena musí být číselná hodnota!");
        } catch (Exception e) {
            System.out.println("Chyba validace: " + e.getMessage());
        }
    }

    private static void printStatistics() {
        System.out.println("\n___| VYHODNOCENÍ STATISTIK |___");
        System.out.printf("Celková hodnota všech objednávek: %.2f Kč\n", orderService.getTotalValue());
        System.out.printf("Průměrná hodnota objednávky:     %.2f Kč\n", orderService.getAverageOrderValue());
        System.out.println("Nejvíce prodávaný produkt:        " + orderService.getMostSoldProduct());
    }

    private static void exportData() {
        String filename = "objednavky_export.csv";
        try {
            orderService.exportToCsv(filename);
            System.out.println("-> Data byla úspěšně exportována do souboru: " + filename);
        } catch (IOException e) {
            System.out.println("Chyba při zápisu do souboru: " + e.getMessage());
        }
    }

    private static void initMockData() {
        try {
            ArrayList<OrderItem> items1 = new ArrayList<>();
            items1.add(new OrderItem("Notebook Lenovo", 2, 15000));
            items1.add(new OrderItem("Myš Logitech", 5, 500));
            orderService.addOrder(new Order(LocalDate.now().minusDays(2), new Subscriber("Jan Novák", "novak@seznam.cz"), items1));

            ArrayList<OrderItem> items2 = new ArrayList<>();
            items2.add(new OrderItem("Myš Logitech", 10, 450));
            items2.add(new OrderItem("Klávesnice Dell", 1, 1200));
            orderService.addOrder(new Order(LocalDate.now(), new Subscriber("Firma ABC", "info@abc.com"), items2));
        } catch (Exception ignored) {}
    }
}