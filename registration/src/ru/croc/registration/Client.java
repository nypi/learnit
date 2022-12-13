package registration;

public class Client {
    private String name;
    private String surname;
    private String numberPhone;
    private String email;
    private String login;
    private String password;

    public Client(String name, String surname, String numberPhone, String email, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.numberPhone = numberPhone;
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public Client() {
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
