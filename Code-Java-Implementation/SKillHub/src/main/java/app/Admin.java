package app;

// Admin.java
public class Admin extends User {
    public Admin(int id, String name) {
        super(id, name);
    }

    public void banClient(Client client) {
        System.out.println("[Admin] " + name + " banned client: " + client.getName());
        // In a real system, you'd mark client as inactive in DB.
    }

    public void banFreelancer(Freelancer freelancer) {
        System.out.println("[Admin] " + name + " banned freelancer: " + freelancer.getName());
        // Similarly, you’d mark freelancer as banned in DB or config.
    }
}
