package Server.Utilities;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


// Needs to create a pool of connections, which get used when runQuery gets called, rather than slamming a single
// connection. Remember this is created and shared between models.
public class Database {
    // private db connection type data;
    private Connection dbConn;
    public Database (){
        // Load the configuration from db.props. Use the Context class to load and process. Datasource might be
        // better that Driver
        // Check for existing tables
        // If not found, create new tables

        // Create connection
        try{
            Properties props = new Properties();
            InputStream input = new FileInputStream(System.getProperty("user.dir")+ "/db.props");
            props.load(input);
            this.dbConn = DriverManager.getConnection(props.getProperty("jdbc.url")+"/"+props.getProperty("jdbc" +
                            ".schema")+"?useLegacyDatetimeCode=false&serverTimezone=UTC",
                    props.getProperty("jdbc.username"), props.getProperty("jdbc.password"));
            System.out.println("Database connected");
            input.close();
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
