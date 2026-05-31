package cz.osu.validator;

import cz.osu.exception.*;
import cz.osu.model.OrderItem;

public class OrderItemValidator implements Validator<OrderItem> {
    @Override
    public void validate(OrderItem orderItem) throws Exception {
        String productName = orderItem.getProductName();
        int quantity = orderItem.getQuantity();
        double unitPrice = orderItem.getUnitPrice();

        if (productName == null || productName.isBlank())
            throw new EmptyValueException("Přázdný název produktu");
        if (productName.trim().length() < 3)
            throw new ShortProductNameException("Krátký název produktu");
        if (quantity < 0)
            throw new NegativeValueException("Negativní množství produktu");
        if (unitPrice < 0)
            throw new NegativeValueException("Negativní jednotková cena");
    }
}
