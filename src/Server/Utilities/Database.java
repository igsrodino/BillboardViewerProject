package Server.Utilities;
import javax.xml.transform.Result;
import java.sql.*;

// Needs to create a pool of connections, which get used when runQuery gets called, rather than slamming a single
// connection. Remember this is created and shared between models.
public class Database {
    // private db connection type data;
    private Connection dbConn;
    private String jdbcUrl = "jdbc:mysql://localhost:3306/CAB302?useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String username = "root";
    private String password = "admin";
    public Database (){
        // Load the configuration from db.props. Use the Context class to load and process. Datasource might be
        // better that Driver
        // Check for existing tables
        // If not found, create new tables

        // Create connection
        try{
            this.dbConn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Database connected");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void closeConnection(){
        try{
            this.dbConn.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    };
     void createTables(){}
    public ResultSet runQuery(String sql){
        ResultSet result = null;
        try{
            Statement query = this.dbConn.createStatement();
            result = query.executeQuery(sql);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return result;
    }
    public void closeDBConnection(){
        // Closes the connection, used on program exit to avoid crashing the database.
    }

}
