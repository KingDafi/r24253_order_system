package cz.osu.validator;

import cz.osu.exception.*;
import cz.osu.model.Subscriber;

public class SubscriberValidator implements Validator<Subscriber> {
    @Override
    public void validate(Subscriber subscriber) throws Exception {
        String name = subscriber.getName();
        String email = subscriber.getEmail();

        if (name == null || name.isBlank())
            throw new EmptyValueException("Prázdné jméno odběratele");
        if (email == null || email.isBlank())
            throw new EmptyValueException("Prázdný email");
        if (!isValidEmail(email))
            throw new InvalidEmailException("Invalidní email");
    }

    private static boolean isValidEmail(String email) {
        String e = email.trim().toLowerCase();
        return e.contains("@") && (e.endsWith(".cz") || e.endsWith(".com"));
    }
}
