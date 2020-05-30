package Server.Utilities;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
* Establishes a connection to the database specified in the Server/db.props file
* */
public class Database {
    // private db connection type data;
    private Connection dbConn;
    private Properties props;
    private Map<String, Boolean> tables;
    public Database () throws SQLSyntaxErrorException {
        // Populate the hashmap for use in table checking
        tables = new HashMap<String, Boolean>();
        tables.put("billboards", false);
        tables.put("users", false);
        tables.put("schedule", false);
        tables.put("permissions", false);
        try{
            props = new Properties();
            props.load(getClass().getResourceAsStream("../db.props"));
            this.dbConn = DriverManager.getConnection(props.getProperty("jdbc.url")+"/"+props.getProperty("jdbc" +
                            ".schema")+"?useLegacyDatetimeCode=false&serverTimezone=Australia/Brisbane",
                    props.getProperty("jdbc.username"), props.getProperty("jdbc.password"));
            System.out.println("Database connected\nChecking for existing tables");
            this.createTables();
        }catch(Exception e){
            throw new SQLSyntaxErrorException(e.getMessage());
        }
    }

    /**
    * Cleanly closes the database connection to prevent the database from getting choked
    * */
    public void closeConnection(){
        try{
            this.dbConn.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    };

    /**
    * Creates any missing tables according to the schema laid out in src/sql-scripts
    * @return void
    * */
    private void createTables() throws SQLSyntaxErrorException {
        // Check for tables. If none found, create some, otherwise pass.
        try{
            DatabaseMetaData meta = dbConn.getMetaData();
            ResultSet rs = meta.getTables(props.getProperty("jdbc.schema"), null, "%", null);
            String table = "";
            while(rs.next()){
                table = rs.getString(3);
                System.out.println("Found table: "+ table);
                if(tables.containsKey(table) == true){
                    tables.replace(table, true);
                }
            }
            Statement createTable = dbConn.createStatement();
            for(Map.Entry<String, Boolean> entry : tables.entrySet()){
                if(entry.getValue() == false){
                    // Create table
                    switch (entry.getKey()){

                        case "users":
                            createTable.executeUpdate("CREATE TABLE `users` (\n" +
                                    "  `id` int PRIMARY KEY AUTO_INCREMENT,\n" +
                                    "  `username` varchar(255) UNIQUE NOT NULL,\n" +
                                    "  `password` varchar(255) NOT NULL,\n" +
                                    "  `name` varchar(255) NOT NULL,\n" +
                                    "  `salt` varchar(255) NOT NULL\n" +
                                    ");");
                            break;
                        case "billboards":
                            createTable.executeUpdate("CREATE TABLE `billboards` (\n" +
                                    "  `id` int PRIMARY KEY AUTO_INCREMENT,\n" +
                                    "  `name` varchar(255) NOT NULL,\n" +
                                    "  `background` varchar(255),\n" +
                                    "  `message` varchar(1000),\n" +
                                    "  `message_color` varchar(255),\n" +
                                    "  `url` varchar(255),\n" +
                                    "  `data` mediumtext,\n" +
                                    "  `information` varchar(4000),\n" +
                                    "  `information_color` varchar(255),\n" +
                                    "  `owner` int\n" +
                                    ");");

                            break;
                        case "schedule":
                            createTable.executeUpdate("CREATE TABLE `schedule` (\n" +
                                    "  `id` int PRIMARY KEY AUTO_INCREMENT,\n" +
                                    "  `start_time` time NOT NULL,\n" +
                                    "  `end_time` time NOT NULL,\n" +
                                    "  `duration` int NOT NULL,\n" +
                                    "  `weekday` int NOT NULL,\n" +
                                    "  `recurs` int DEFAULT 0,\n" +
                                    "  `billboard` int NOT NULL\n" +
                                    ");");
                            break;
                        case "permissions":
                            createTable.executeUpdate("CREATE TABLE `permissions` (\n" +
                                    "  `id` int PRIMARY KEY AUTO_INCREMENT,\n" +
                                    "  `create_billboards` boolean DEFAULT false,\n" +
                                    "  `edit_billboards` boolean DEFAULT false,\n" +
                                    "  `schedule_billboards` boolean DEFAULT false,\n" +
                                    "  `edit_users` boolean DEFAULT false,\n" +
                                    "  `user` int\n" +
                                    ");");
                            break;
                        default: break;
                    }

                }
            }
            createTable.executeUpdate("ALTER TABLE `billboards` ADD FOREIGN KEY (`owner`) REFERENCES `users` (`id`);");
            createTable.executeUpdate("ALTER TABLE `schedule` ADD FOREIGN KEY (`billboard`) REFERENCES `billboards` (`id`);");
            createTable.executeUpdate("ALTER TABLE `permissions` ADD FOREIGN KEY (`user`) REFERENCES `users` (`id`);");
            System.out.println("Database is ready");

        }
        catch(Exception e){
            throw new SQLSyntaxErrorException(e.getMessage());
        }
    }

    /**
    * Used to execute a select statement query on the database
    * Cannot be used to modify data, only retrieve it
    * @param sql   The sql query to run
    * @return ResultSet   containing the results of the query
    * */
    public ResultSet runSelectQuery(String sql){
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

    /**
    * Executes an insert,update, or delete query on the database.
    * Used to modify data, cannot be used to retrieve it
    * @param sql  contains the sql query to run
    * @return int  representing the number of rows affected. Returns 0 if the query did nothing
    * */
    public int runUpdateQuery(String sql){
        int rowCount = 0;
        try{
            Statement query = this.dbConn.createStatement();
            rowCount = query.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println("Error: "+e.getMessage());
            e.printStackTrace();
        }
        return rowCount;
    }

}
