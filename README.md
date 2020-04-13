## USER STORIES - SUMMARIZED

Billboard Viewer program on displays must run autonomously once started.

Billboard Control Panel must be able to grant these combinations of privileges to users:
- Creating new billboards
- Editing existing billboards
- Scheduling billboards to be displayed on the Billboard Viewers
- Editing users (incl. creating new users and editing user permissions.)
If you created a billboard, you should be able to edit that without any special
permission. The ‘Editing existing billboards’ permission is just for editing others’ billboards.”

Design templates for faster Billboard Design: "typically have some nice big text at the top,
a picture in the middle, and some more information
down at the bottom. Sometimes we only have one or two of those elements, but you get the idea.
I want to be able to just type the text in, pick a picture, set some colours and go.

Billboard Scheduler must be able to see available billboards on system and schedule to
run at particular times, for how long and often to show. As well as showing billboards immediately.

User passwords should be stored
as a salted hash in the database (with a unique salt generated for each user)

Users should be able to change own passwords from Control Panel without needing special privileges.

Once a Billboard Scheduler has given a billboard the all-clear and scheduled it, it
should be locked from being edited, except for from admin/power users.

Viewer, Server and Control Panel should all be network-configurable via a properties file:
- For the Viewer and Control Panel, containing the address and port of the server.
- For the Server, containing the port to listen on.
"This will allow my team to rapidly reconfigure in case we have to move the server to a new
machine.”

The Billboard Server should be configurable through a db.props properties file 
(note: this file is described later on in this specification), containing fields
describing how to connect to the database server and the database connection to use. 
"This will allow us to change where the database is hosted and even change the type of 
database in use without recompiling the application.”

---

## Testing
The `test` folder should already be marked as 'Test Sources Root'. Organise your tests there so that the folder
 structure mirrors that of the package under test. So `src/Server/Controllers/ClientController` has its tests in
  `test/Server/Controllers/ClientControllerTest`

## Notes
* The billboard view/render parts of the Viewer need to be built so they can be used in the ControlPanel, as the same
 functionality is required. No point in duplicating work.
* The Models packages encapsulate data access. For the server this is the access to specific records in the database
, for the clients this is network calls to the server.
* Views contains the actual gui rendering parts of the app
* Controllers connect models and views together, and control the flow of data through the app. 

## Request Routes
These routes are what is put in the `<type></type>` tags in a request. See below for an example
### Billboards
* getBillboard
* listBillboards
* getBillboardInformation
* createBillboard
* deleteBillboard

### Users
* login
* listUsers
* createUser
* deleteUser
* getPermissions
* setPermissions
* setPassword
* logout

### Schedule
* getSchedule
* setSchedule
* deleteSchedule


## Request/response formats
Request without payload

```xml
<request>
	<type>getBillboard</type>
	<data></data>
    <token>CB2D1B97A1D8C4AAECCE3EE348DC7FFA53D50193</token>
</request>
```

Request with payload

```xml
<request>
	<type>login</type>
	<data>
		<username>bob</username>
		<password>password-hash</password>
	</data>
        <token></token>
</request>
```

Acknowledgement response. Type can be `success` or `failure` depending

```xml
<response>
	<type>success</type>
	<data></data>
</response>
```

Payload bearing response

```xml
<response>
	<type>success</type>
	<data>
		<?xml version="1.0" encoding="UTF-8"?> <billboard background="#0000FF">    <message colour="#FFFF00
">Welcome
 to the ____ Corporation's Annual Fundraiser!</message>    <picture url="https://example.com/fundraiser_image.jpg" />     <information colour="#00FFFF">Be sure to check out https://example.com/ for more information.</information> </billboard>
	</data>
</response>
```