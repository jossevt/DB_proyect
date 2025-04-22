package domain;

public class Client {
    private String email;
    private String name;

    public Client(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    // Método para simular almacenamiento (puede ser mejorado con BD en el futuro)
    public void save() {
        System.out.println("Client registered: " + this);
    }
}

