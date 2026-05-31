package cz.osu.model;

public class OrderItem {
    private final String productName;
    private final int quantity;
    private final double unitPrice;

    public OrderItem(String productName, int quantity, double unitPrice) {
        this.productName = productName.trim();
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public double getTotalPrice() { return quantity * unitPrice; }

    @Override
    public String toString() {
        return String.format("  - %s: %d x %.2f Kč = %.2f Kč", productName, quantity, unitPrice, getTotalPrice());
    }
}
