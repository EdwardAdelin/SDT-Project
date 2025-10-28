package app;

// ConcreteUserFactory.java
public class ConcreteUserFactory implements UserFactory {
    @Override
    public User createUser(String role, int id, String name) {
        if ("client".equalsIgnoreCase(role)) {
            return new Client(id, name);
        } else if ("freelancer".equalsIgnoreCase(role)) {
            return new Freelancer(id, name);
        } else if ("admin".equalsIgnoreCase(role)) {
            return new Admin(id, name);
        } else {
            throw new IllegalArgumentException("Unknown role: " + role);
        }
    }

}
