package Server.Controllers;

import Server.Models.BillboardModel;
import Server.Models.ScheduleModel;
import Server.Models.UserModel;
import Server.Utilities.Database;
import Server.Utilities.UserAuthentication;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

import java.net.Socket;


public class ClientController implements Runnable {
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Database dbConn;

    public ClientController(Socket socket, DataInputStream inputSteam, DataOutputStream outputStream, Database dbConn) {
        this.socket = socket;
        this.inputStream = inputSteam;
        this.outputStream = outputStream;
        this.dbConn = dbConn;
    }

    /**
     * Reads the incoming request and processes it
     * Requests must have their access token verified (if one is needed), and user permissions
     * validated before calling the requisite controller method.
     */
    @Override
    public void run() {
        try {
            // Read the request from the client
            String request = inputStream.readUTF();

            Document parsedRequest = processRequestString(request);
            this.sendResponse(this.processRequest(parsedRequest));
            inputStream.close();

            return;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            this.sendResponse("Error: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            this.sendResponse("Error: " + e.getMessage());
        } catch (SAXException e) {
            e.printStackTrace();
            this.sendResponse("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Client has failed differently: " + e.getMessage());
            e.printStackTrace();
            this.sendResponse("Error: " + e.getMessage());
        }

    }

    /**
     * Sends the Response string and closes the stream, ending the connection
     *
     * @param response the stringified xml Response object to send
     */
    private void sendResponse(String response) {
        try {
            this.outputStream.writeUTF(response);
            this.inputStream.close();
            this.outputStream.close();
            this.socket.close();
        } catch (IOException e) {
            System.err.println("Client handler failed: " + e.getMessage());
        }
    }

    /**
     * Processes a stringified XML Request object into a Document object
     *
     * @param request the stringified XML Request object
     * @return a Document object containing the XML in the request string
     */

    private Document processRequestString(String request) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        return documentBuilder.parse(new InputSource(new StringReader(request)));
    }

    /**
     * Checks the request type, creates the correct controller, and calls the relevant method
     *
     * @param request the parsed XML document
     * @return a string containing the response from the controller
     */
    private String processRequest(Document request) {
        String response = "";

        // Create the controllers
        BillboardModel billboardModel = new BillboardModel(dbConn);
        BillboardController billboard = new BillboardController(billboardModel);
        UserModel userModel = new UserModel(dbConn);
        UserController userController = new UserController(userModel);
        ScheduleModel scheduleModel = new ScheduleModel(dbConn);
        ScheduleController scheduleController = new ScheduleController(scheduleModel);
        try {
            String route = request.getElementsByTagName("type").item(0).getTextContent();
            // Variable declarations
            int billboardID = -1;
            int userID = -1;
            String username = "";
            String password = "";
            int requestedUserID = -1;
            String token = "";
            int startTime = -1;
            // If a token is present, extract the userID
            if (request.getElementsByTagName("token").getLength() > 0) {
                token = request.getElementsByTagName("token").item(0).getTextContent();
                userID = UserAuthentication.extractUserIDFromToken(request.getElementsByTagName(
                        "token").item(0).getTextContent());
                // Uncomment to enable route authentication protection
//                if(UserAuthentication.isValidSessionToken(token)<0){
//                    // Access token is invalid
//                    return "<response>" +
//                        "<type>error</type>" +
//                        "<data>User not authenticated</data>" +
//                        "</response>"
//                }

//            } else if (!route.equals("login") && !route.equals("getBillboard")) {
//
//                return "<response>" +
//                        "<type>error</type>" +
//                        "<data>User not authenticated</data>" +
//                        "</response>";

            } else {
                switch (route) {
                    case "getBillboard":
                        response = billboard.getBillboard(scheduleController.getCurrentBillboard());

                        break;
                    case "listBillboards":
                        response = billboard.getBillboardList();
                        break;
                    case "getBillboardInformation":
                        billboardID =
                                Integer.parseInt(request.getElementsByTagName("data").item(0).getTextContent());
                        response = billboard.getBillboard(billboardID);
                        break;
                    case "createBillboard":
                        //if (userController.checkPermission(userID, "edit_permission")) {
                                           response = billboard.createBillboard(102, "Federer", "#0000FF", "Babolat AeroPro","#FFFF00"," ", "url",
                                                   "Wilson RF97","#00FFFF", 6);
                           //String background, String message, String message_color, String image,
                            //                                   String imageType, String information, String information_color, int owner
                       // }

                        break;
                    case "deleteBillboard":
                       // if (userController.checkPermission(userID, "edit_permission")) {
                            billboardID =
                                    Integer.parseInt(request.getElementsByTagName("data").item(0).getTextContent());
                            response = billboard.deleteBillboard(billboardID);
                        //}
                        break;
                    case "login":
                        username =
                                request.getElementsByTagName("username").item(0).getTextContent();
                        password =
                                request.getElementsByTagName("password").item(0).getTextContent();
                        response = userController.login(username, password);
                        break;
                    case "listUsers":
                        if (userController.checkPermission(userID, "edit_users")) {
                            response = userController.listUsers();
                        }
                        break;
                    case "createUser":
                        if (userController.checkPermission(userID, "edit_users")) {
                            username = request.getElementsByTagName("username").item(0).getTextContent();
                            password = request.getElementsByTagName("password").item(0).getTextContent();
                            response = userController.createUser(username, password);
                        }
                        break;
                    case "deleteUser":
                        if (userController.checkPermission(userID, "edit_users")) {
                            username = request.getElementsByTagName("username").item(0).getTextContent();
                            response = userController.deleteUser(username);
                        }
                        break;
                    case "getPermissions":
                        requestedUserID = userController.getUserID(request.getElementsByTagName("username").item(0).getTextContent());
                        if (userController.checkPermission(userID, "edit_users") || requestedUserID == userID) {
                            username = request.getElementsByTagName("username").item(0).getTextContent();
                            response = userController.getUserPermissions(username);
                        }
                        break;
                    case "setPermissions":
                        requestedUserID = userController.getUserID(request.getElementsByTagName("username").item(0).getTextContent());
                        if (userController.checkPermission(userID, "edit_users") && requestedUserID != userID) {
                            username = request.getElementsByTagName("username").item(0).getTextContent();
                            String[] permissions =
                                    request.getElementsByTagName("permissions").item(0).getTextContent().split(",");
                            response = userController.setPermissions(username, permissions);
                        }
                        break;
                    case "setPassword":
                        if (userController.checkPermission(userID, "edit_users") || requestedUserID == userID) {
                            username = request.getElementsByTagName("username").item(0).getTextContent();
                            password = request.getElementsByTagName("password").item(0).getTextContent();
                            response = userController.setUserPassword(username, password);
                        }
                        break;
                    case "logout":
                        response = userController.logout(token);
                        break;
                    case "getSchedule":
//                        if (userController.checkPermission(userID, "schedule_billboards")) {
                            response = scheduleController.getSchedule();
//                        }
                        break;
                    case "setSchedule":
                        if (userController.checkPermission(userID, "schedule_billboards")) {
                            billboardID =
                                    Integer.parseInt(request.getElementsByTagName("billboard").item(0).getTextContent());
                            startTime =
                                    Integer.parseInt(request.getElementsByTagName("startTime").item(0).getTextContent());
                            int endTime =
                                    Integer.parseInt(request.getElementsByTagName("endTime").item(0).getTextContent());
                            int recurs =
                                    Integer.parseInt(request.getElementsByTagName("recurs").item(0).getTextContent());
                            response = scheduleController.setBillboardSchedule(billboardID,
                                    startTime, endTime, recurs);
                        }
                        break;
                    case "deleteSchedule":
                        if (userController.checkPermission(userID, "schedule_billboards")) {
                            billboardID =
                                    Integer.parseInt(request.getElementsByTagName("billboard").item(0).getTextContent());
                            startTime =
                                    Integer.parseInt(request.getElementsByTagName("startTime").item(0).getTextContent());
                            response = scheduleController.removeSchedule(billboardID, startTime);
                        }
                        break;
                    default:
                        response = "<response>\n" +
                                "    <type>error</type>\n" +
                                "    <data>Route not specified</data>\n" +
                                "</response>";
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            response = "<response>" +
                    "<type>error</type>" +
                    "<data></data>" +
                    "</response>";
            return response;
        }
        return response;
    }
}
