package connection;

import java.sql.DriverManager;

public class DBConnection implements IDBConnection
{

    public java.sql.Connection establishDBConnection()  {
        java.sql.Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(connectionUrl, username, password);
        }
        catch(Exception E)
        {
            System.out.println("Connection Error !" +E);
        }
        return connection;
    }
}