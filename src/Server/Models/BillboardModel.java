package Server.Models;

import Server.Utilities.Database;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.StringJoiner;


/**
 * Contains the data retrieval methods.
 */
public class BillboardModel {
    private Database dbConn;
    private int id;
    private String background;
    private String message;
    private String message_color;
    private String url;
    private String data;
    private String information;
    private String information_color;
    private int owner;

    /**
     * Constructs the Billboard object
     *
     * @param dbConnection allows access to the database via the connection established at server start
     */
    public BillboardModel(Database dbConnection) {
        this.dbConn = dbConnection;
        this.background = "#0000FF";
        this.id = 0;
        this.message = "Hello WOrld";
        this.message_color = "";
        this.url = "";
        this.data = "";
        this.information = "";
        this.information_color = "";

        this.owner = 0;

    }

    public int getId() {
        return this.id;
    }

    public String getBackground() {
        return this.background;
    }

    public String getMessage() {
        return this.message;
    }

    public String getMessage_color() {
        return this.message_color;
    }

    public String getUrl() {
        return this.url;
    }

    public String getData() {
        return this.data;
    }

    public String getInformation() {
        return this.information;
    }

    public String getInformation_color() {
        return this.information_color;
    }

    public int getOwner() {
        return this.owner;
    }

    /**
     * Fetches the specified billboard.
     * It will populate the Billboard model object with the
     * billboard data. This can then be
     * accessed via the getter methods
     *
     * @param billboardID the billboard ID to retrieve
     * @return true if the operation was successful, or false if no billboard was found
     */
    public boolean getBillboard(int billboardID) {
        boolean status = false;
        ResultSet rs =
                this.dbConn.runSelectQuery("select * from billboards where id = " + billboardID + " " + "order by id limit 1");
        try {
            while (rs.next()) {
                this.owner = rs.getInt("owner") > 0 ? rs.getInt("owner") : -1;
                this.id = rs.getInt("id") > 0 ? rs.getInt("id") : -1;
                this.background = rs.getString("background") != null ?
                        rs.getString("background") : "";
                this.message = rs.getString("message") != null ? rs.getString("message") : "";
                this.message_color = rs.getString("message_color") != null ? rs.getString(
                        "message_color") : "";
                this.url = rs.getString("url") != null ? rs.getString("url") : "";
                this.data = rs.getString("data") != null ? rs.getString("data") : "";
                this.information = rs.getString("information") != null ? rs.getString("information") :
                        "";
                this.information_color = rs.getString("information_color") != null ?
                        rs.getString("information_color") : "";
            }
            status = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return status;
    }

    /**
     * Deletes the specified billboard
     *
     * @param billboardID the id of the billboard to delete
     * @return the number of rows affected by the delete (should always = either 1 or 0)
     */
    public void deleteBillboard(int billboardID) {
        this.dbConn.runUpdateQuery("delete from billboards where id = " + billboardID);
    }


    /**
     * Retrieves a complete list of all billboards
     *
     * @return a Document object with the list of billboard objects contained in a root
     * data element
     */
    public Document listBillboards() {
        ResultSet rs =
                this.dbConn.runSelectQuery("select * from billboards");
        try {
            //Creating the new document
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element data = doc.createElement("root");
            while (rs.next()) {
                Element billboard = doc.createElement("billboard");
                if (rs.getInt("id") > 0) {
                    Attr attr = doc.createAttribute("id");
                    attr.setValue(Integer.toString(rs.getInt("id")));
                    billboard.setAttributeNode(attr);
                }
                if (rs.getInt("owner") > 0) {
                    Attr attr = doc.createAttribute("owner");
                    attr.setValue(Integer.toString(rs.getInt("owner")));
                    billboard.setAttributeNode(attr);
                }
                if (rs.getString("background") != null) {
                    Attr attr = doc.createAttribute("background");
                    attr.setValue(rs.getString("background"));
                    billboard.setAttributeNode(attr);
                }
                if (rs.getString("message") != null) {
                    Element messageElement = doc.createElement("message");
                    if (rs.getString("message_color") != null) {
                        Attr attrType = doc.createAttribute("colour");
                        attrType.setValue(rs.getString("message_color"));
                        messageElement.setAttributeNode(attrType);
                    }
                    messageElement.appendChild(doc.createTextNode(rs.getString("message")));
                    billboard.appendChild(messageElement);

                }
                if (rs.getString("url") != null && rs.getString("url").length() > 0) {
                    Element pictureElement = doc.createElement("picture");
                    Attr attrType1 = doc.createAttribute("url");
                    attrType1.setValue(rs.getString("url"));
                    pictureElement.setAttributeNode(attrType1);
                    billboard.appendChild(pictureElement);
                } else if (rs.getString("data") != null && rs.getString("data").length() > 0) {
                    Element pictureElement = doc.createElement("picture");
                    Attr attrType1 = doc.createAttribute("data");
                    attrType1.setValue(rs.getString("data"));
                    pictureElement.setAttributeNode(attrType1);

                    billboard.appendChild(pictureElement);
                }
                if (rs.getString("information") != null) {
                    Element informationElement = doc.createElement("information");

                    if (rs.getString("information_color") != null) {
                        Attr attrType2 = doc.createAttribute("colour");
                        attrType2.setValue(rs.getString("information_color"));
                        informationElement.setAttributeNode(attrType2);
                    }
                    informationElement.appendChild(doc.createTextNode(rs.getString("information")));
                    billboard.appendChild(informationElement);
                }

                data.appendChild(billboard);
            }
            doc.appendChild(data);
            return doc;
        } catch (ParserConfigurationException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a billboard with the provided information
     *
     * @param background        a hex value representing the color of the background
     * @param message           the billboard message
     * @param message_color     the color of the message text
     * @param image             the url or base64 data string of the image
     * @param imageType         the type of image. The two valid options are: 'url' or 'data'
     * @param information       in depth details about the billboard message
     * @param information_color the hex code for the information text color
     * @param owner             the id of the user creating the billboard
     * @return int representing the number of rows inserted
     */
    public int createBillboard(int id, String name, String background, String message,
                               String message_color, String image, String imageType,
                               String information, String information_color, int owner) {
        if (owner == -1) {
            return -1;
        }
        int result = 0;
        if (id > -1) {
//            if(imageType.equals("url") || imageType.equals("data")){
            // Update an existing billboard
            String insertQuery = "UPDATE billboards SET ";
            String queryA = "";
            if (name.length() > 0) {
                queryA += ", name = \"" + name + "\"";
            }
            if (background.length() > 0) {
                queryA += ", background = \"" + background + "\"";
            }
            if (message.length() > 0) {
                queryA += ", message = \"" + message + "\"";
            }
            if (message_color.length() > 0) {
                queryA += ", message_color = \"" + message_color + "\"";
            }
            if (image.length() > 0) {
                if (imageType.equals("url")) {
                    queryA += ", url = \"" + image + "\"";
                } else if (imageType.equals("data")) {
                    queryA += ", data = \"" + image + "\"";
                }
            }
            if (information.length() > 0) {
                queryA += ", information = \"" + information + "\"";
            }
            if (information_color.length() > 0) {
                queryA += ", information_color = \"" + information_color + "\"";
            }
            insertQuery += queryA.substring(2);
            insertQuery += " where id = " + id;

            result = this.dbConn.runUpdateQuery(insertQuery);
//            }
        } else {
            // Create a new billboard
            String queryA = "insert into billboards ( owner";
            String queryB = "values ( " + owner;
            if (name.length() > 0) {
                queryA += ", name";
                queryB += ", \"" + name+"\"";
            }
            if (background.length() > 0) {
                queryA += ", background";
                queryB += ", \"" + background+"\"";
            }
            if (message.length() > 0) {
                queryA += ", message";
                queryB += ", \"" + message+"\"";
            }
            if (message_color.length() > 0) {
                queryA += ", message_color";
                queryB += ", \"" + message_color+"\"";
            }
            if (image.length() > 0) {
                if (imageType.equals("url")) {
                    queryA += ", url";
                } else if (imageType.equals("data")) {
                    queryA += ", data";
                }
                queryB += ", \"" + image+"\"";
            }
            if (information.length() > 0) {
                queryA += ", information";
                queryB += ", \"" + information+"\"";
            }
            if (information_color.length() > 0) {
                queryA += ", information_color";
                queryB += ", \"" + information_color+"\"";
            }
            queryA += ") ";
            queryB += ")";
            result = this.dbConn.runUpdateQuery(queryA+queryB);
        }
        return result;
    }


}
