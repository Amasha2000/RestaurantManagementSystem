package model;

public class Owner {
    private String name;
    private String password;

    public Owner() {
    }

    public Owner(String name, String password) {
        this.setName(name);
        this.setPassword(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
