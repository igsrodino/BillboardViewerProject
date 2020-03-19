









/*USER STORIES - SUMMARIZED

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
database in use without recompiling the application.”*/
































When you're done, you can delete the content in this README and update the file with details for others getting started with your repository.

*We recommend that you open this README in another tab as you perform the tasks below. You can [watch our video](https://youtu.be/0ocf7u76WSo) for a full demo of all the steps in this tutorial. Open the video in a new tab to avoid leaving Bitbucket.*

---

## Edit a file

You’ll start by editing this README file to learn how to edit a file in Bitbucket.

1. Click **Source** on the left side.
2. Click the README.md link from the list of files.
3. Click the **Edit** button.
4. Delete the following text: *Delete this line to make a change to the README from Bitbucket.*
5. After making your change, click **Commit** and then **Commit** again in the dialog. The commit page will open and you’ll see the change you just made.
6. Go back to the **Source** page.

---

## Create a file

Next, you’ll add a new file to this repository.

1. Click the **New file** button at the top of the **Source** page.
2. Give the file a filename of **contributors.txt**.
3. Enter your name in the empty file space.
4. Click **Commit** and then **Commit** again in the dialog.
5. Go back to the **Source** page.

Before you move on, go ahead and explore the repository. You've already seen the **Source** page, but check out the **Commits**, **Branches**, and **Settings** pages.

---

## Clone a repository

Use these steps to clone from SourceTree, our client for using the repository command-line free. Cloning allows you to work on your files locally. If you don't yet have SourceTree, [download and install first](https://www.sourcetreeapp.com/). If you prefer to clone from the command line, see [Clone a repository](https://confluence.atlassian.com/x/4whODQ).

1. You’ll see the clone button under the **Source** heading. Click that button.
2. Now click **Check out in SourceTree**. You may need to create a SourceTree account or log in.
3. When you see the **Clone New** dialog in SourceTree, update the destination path and name if you’d like to and then click **Clone**.
4. Open the directory you just created to see your repository’s files.

Now that you're more familiar with your Bitbucket repository, go ahead and add a new file locally. You can [push your change back to Bitbucket with SourceTree](https://confluence.atlassian.com/x/iqyBMg), or you can [add, commit,](https://confluence.atlassian.com/x/8QhODQ) and [push from the command line](https://confluence.atlassian.com/x/NQ0zDQ).