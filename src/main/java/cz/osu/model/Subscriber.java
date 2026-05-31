package cz.osu.model;

public class Subscriber {
    private final String name;
    private final String email;

    public Subscriber(String name, String email) {
        this.name = name.trim();
        this.email = email.trim();
    }

    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return name + " <" + email + ">";
    }
}
