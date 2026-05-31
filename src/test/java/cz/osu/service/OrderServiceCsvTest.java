package cz.osu.service;

import cz.osu.model.Order;
import cz.osu.model.OrderItem;
import cz.osu.model.Subscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceCsvTest {
    private OrderService service;
    private Path tmpFile;

    @BeforeEach
    void setUp() throws IOException {
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

        tmpFile = Files.createTempFile("orders", ".csv");
    }

    @Test
    void exportToCsv_createsFile() throws IOException {
        service.exportToCsv(tmpFile.toString());
        assertTrue(Files.exists(tmpFile));
    }

    @Test
    void exportToCsv_headerContainsDatum() throws IOException {
        service.exportToCsv(tmpFile.toString());
        assertTrue(Files.readString(tmpFile).contains("Datum"));
    }

    @Test
    void exportToCsv_headerContainsCustomerColumn() throws IOException {
        service.exportToCsv(tmpFile.toString());
        assertTrue(Files.readString(tmpFile).contains("Jméno zákazníka"));
    }

    @Test
    void exportToCsv_correctRowCount() throws IOException {
        service.exportToCsv(tmpFile.toString());
        assertEquals(3, Files.readAllLines(tmpFile).size());
    }

    @Test
    void exportToCsv_containsAllCustomerNames() throws IOException {
        service.exportToCsv(tmpFile.toString());
        String content = Files.readString(tmpFile);
        assertTrue(content.contains("Jan"));
        assertTrue(content.contains("Petr"));
    }

    @Test
    void exportToCsv_containsDate() throws IOException {
        service.exportToCsv(tmpFile.toString());
        assertTrue(Files.readString(tmpFile).contains(LocalDate.now().toString()));
    }

    @Test
    void exportToCsv_emptyService_onlyHeader() throws IOException {
        Path emptyTmp = Files.createTempFile("orders_empty", ".csv");
        new OrderService().exportToCsv(emptyTmp.toString());
        assertEquals(1, Files.readAllLines(emptyTmp).size(),
                "Empty service should produce only the header row");
        Files.deleteIfExists(emptyTmp);
    }
}