package cz.osu.validator;

import cz.osu.exception.EmptyValueException;
import cz.osu.exception.NegativeValueException;
import cz.osu.exception.ShortProductNameException;
import cz.osu.model.OrderItem;
import cz.osu.validator.OrderItemValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemValidatorTest {

    private final OrderItemValidator validator = new OrderItemValidator();

    @Test
    void emptyProductName_throwsEmptyValueException() {
        assertThrows(EmptyValueException.class, () ->
                validator.validate(new OrderItem("", 1, 10.0)));
    }

    @Test
    void blankProductName_throwsEmptyValueException() {
        assertThrows(EmptyValueException.class, () ->
                validator.validate(new OrderItem("   ", 1, 10.0)));
    }

    @Test
    void oneCharName_throwsShortProductNameException() {
        assertThrows(ShortProductNameException.class, () ->
                validator.validate(new OrderItem("A", 1, 10.0)));
    }

    @Test
    void twoCharName_throwsShortProductNameException() {
        assertThrows(ShortProductNameException.class, () ->
                validator.validate(new OrderItem("AB", 1, 10.0)));
    }

    @Test
    void threeCharName_doesNotThrow() {
        assertDoesNotThrow(() ->
                validator.validate(new OrderItem("Abc", 1, 10.0)));
    }

    @Test
    void negativeQuantity_throwsNegativeValueException() {
        assertThrows(NegativeValueException.class, () ->
                validator.validate(new OrderItem("Tužka", -1, 5.0)));
    }

    @Test
    void negativeUnitPrice_throwsNegativeValueException() {
        assertThrows(NegativeValueException.class, () ->
                validator.validate(new OrderItem("Tužka", 1, -5.0)));
    }

    @Test
    void zeroQuantity_doesNotThrow() {
        assertDoesNotThrow(() ->
                validator.validate(new OrderItem("Tužka", 0, 5.0)));
    }

    @Test
    void zeroUnitPrice_doesNotThrow() {
        assertDoesNotThrow(() ->
                validator.validate(new OrderItem("Tužka", 1, 0.0)));
    }
}