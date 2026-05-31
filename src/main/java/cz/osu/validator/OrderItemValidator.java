package cz.osu.validator;

import cz.osu.exception.*;
import cz.osu.model.OrderItem;

public class OrderItemValidator implements Validator<OrderItem> {
    @Override
    public void validate(OrderItem orderItem) throws Exception {
        if (orderItem.getProductName() == null || orderItem.getProductName().isBlank())
            throw new EmptyValueException("Přázdný název produktu");
        if (orderItem.getProductName().trim().length() < 3)
            throw new ShortProductNameException("Krátký název produktu");
        if (orderItem.getQuantity() < 0)
            throw new NegativeValueException("Negativní množství produktu");
        if (orderItem.getUnitPrice() < 0)
            throw new NegativeValueException("Negativní jednotková cena");
    }
}
