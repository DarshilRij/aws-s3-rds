package connection;

import java.sql.Connection;

public interface IDBConnection
{
    String connectionUrl = "connectionUrl";
    String username = "username";
    String password = "password";

    Connection establishDBConnection();
}
