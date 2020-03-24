package Server.Utilities;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


// Needs to create a pool of connections, which get used when runQuery gets called, rather than slamming a single
// connection. Remember this is created and shared between models.
public class Database {
    // private db connection type data;
    private Connection dbConn;
    private Properties props;
    private Map<String, Boolean> tables;
    public Database (){
        // Populate the hashmap for use in table checking
        tables = new HashMap<String, Boolean>();
        tables.put("billboards", false);
        tables.put("users", false);
        tables.put("permissions", false);
        try{
            props = new Properties();
            props.load(getClass().getResourceAsStream("../db.props"));
            this.dbConn = DriverManager.getConnection(props.getProperty("jdbc.url")+"/"+props.getProperty("jdbc" +
                            ".schema")+"?useLegacyDatetimeCode=false&serverTimezone=UTC",
                    props.getProperty("jdbc.username"), props.getProperty("jdbc.password"));
            System.out.println("Database connected\nChecking for existing tables");
            this.createTables();
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
    private void createTables(){
        // Check for tables. If none found, create some, otherwise pass.
        try{
            DatabaseMetaData meta = dbConn.getMetaData();
            ResultSet rs = meta.getTables(props.getProperty("jdbc.schema"), null, "%", null);
            String table = "";
            while(rs.next()){
                table = rs.getString(3);
                System.out.println("Table: "+ table);
                if(tables.containsKey(table) == true){
                    tables.replace(table, true);
                }
            }
            Statement createTable = dbConn.createStatement();
            for(Map.Entry<String, Boolean> entry : tables.entrySet()){
                if(entry.getValue() == false){
                    // Create table
                    switch (entry.getKey()){
                        case "billboards":
                            createTable.executeUpdate("CREATE TABLE `billboards` (\n" +
                                    "  `id` int PRIMARY KEY AUTO_INCREMENT,\n" +
                                    "  `background` varchar(255),\n" +
                                    "  `message` varchar(255),\n" +
                                    "  `message_color` varchar(255),\n" +
                                    "  `url` varchar(255),\n" +
                                    "  `data` varchar(255),\n" +
                                    "  `information` varchar(255),\n" +
                                    "  `information_color` varchar(255),\n" +
                                    "  `start_time` time,\n" +
                                    "  `end_time` time\n" +
                                    ");");
                            break;
                        case "users":
                            createTable.executeUpdate("CREATE TABLE `users` (\n" +
                                    "  `id` int PRIMARY KEY AUTO_INCREMENT,\n" +
                                    "  `username` varchar(255) UNIQUE NOT NULL,\n" +
                                    "  `password` varchar(255) NOT NULL\n" +
                                    ");");
                            break;
                        case "permissions":
                            createTable.executeUpdate("CREATE TABLE `permissions` (\n" +
                                    "  `id` int PRIMARY KEY AUTO_INCREMENT,\n" +
                                    "  `permission` enum('create_billboards', 'edit_billboards', 'schedule_billboards', 'edit_users') NOT NULL,\n" +
                                    "  `user` int\n" +
                                    ");");
                            createTable.executeUpdate("ALTER TABLE `permissions` ADD FOREIGN KEY (`user`) REFERENCES" +
                                    " `users` (`id`);");
                            break;
                        default: break;
                    }

                }
            }
            System.out.println("Database is ready");

        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }
    public ResultSet runQuery(String sql){
        ResultSet result = null;
        try{
            Statement query = this.dbConn.createStatement();
            result = query.executeQuery(sql);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
    public void runUpdate(String sql){
        try{
            Statement query = this.dbConn.createStatement();
            query.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error: "+e.getMessage());
            e.printStackTrace();
        }
    }

}
