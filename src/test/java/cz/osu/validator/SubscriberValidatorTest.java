package cz.osu.validator;

import cz.osu.exception.EmptyValueException;
import cz.osu.exception.InvalidEmailException;
import cz.osu.model.Subscriber;
import cz.osu.validator.SubscriberValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubscriberValidatorTest {

    private final SubscriberValidator validator = new SubscriberValidator();

    @Test
    void emptyName_throwsEmptyValueException() {
        assertThrows(EmptyValueException.class, () ->
                validator.validate(new Subscriber("", "jan@test.cz")));
    }

    @Test
    void blankName_throwsEmptyValueException() {
        assertThrows(EmptyValueException.class, () ->
                validator.validate(new Subscriber("   ", "jan@test.cz")));
    }

    @Test
    void emptyEmail_throwsEmptyValueException() {
        assertThrows(EmptyValueException.class, () ->
                validator.validate(new Subscriber("Jan", "")));
    }

    @Test
    void emailWithoutAt_throwsInvalidEmailException() {
        assertThrows(InvalidEmailException.class, () ->
                validator.validate(new Subscriber("Jan", "jantest.cz")));
    }

    @Test
    void emailWrongExtension_throwsInvalidEmailException() {
        assertThrows(InvalidEmailException.class, () ->
                validator.validate(new Subscriber("Jan", "jan@test.de")));
    }

    @Test
    void emailOnlyAt_throwsInvalidEmailException() {
        assertThrows(InvalidEmailException.class, () ->
                validator.validate(new Subscriber("Jan", "@")));
    }

    @Test
    void validEmailCz_doesNotThrow() {
        assertDoesNotThrow(() ->
                validator.validate(new Subscriber("Jan Novák", "jan@firma.cz")));
    }

    @Test
    void validEmailCom_doesNotThrow() {
        assertDoesNotThrow(() ->
                validator.validate(new Subscriber("Jane Doe", "jane@company.com")));
    }
}