package registration.database;

public interface WorkWithDatabase {
    // здесь указывается путь к вашей БД
    String CONNECTION_URL = "jdbc:h2:./data/db";
    String USER = "sa";
    String PASSWORD = "sa";
}
