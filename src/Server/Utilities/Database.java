package Server.Utilities;

// Needs to create a pool of connections, which get used when runQuery gets called, rather than slamming a single
// connection. Remember this is created and shared between models.
public class Database {
    // private db connection type data;
    public Database (){
        // Load the configuration from db.props. Use the Context class to load and process. Datasource might be better that Driver
        // Check for existing tables
        // If not found, create new tables
    }
    private void createTables(){}
    public String runQuery(String query){
        return "Query Result";
    }
    public void closeDBConnection(){
        // Closes the connection, used on program exit to avoid crashing the database.
    }
}
