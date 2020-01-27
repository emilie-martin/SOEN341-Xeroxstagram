# SOEN341-InsertName
### Authors
* Simon Lim (tech lead) | 
* Émilie Martin | @ emilie-martin
* Daniela Venuta | @ daniela-venuta
* Minh-Tam Do |
* Luc Nguyen |
* Karin Kazarian |

## Technology Used
#### React
React is a JavaScript library that boasts component reusability and is greatly used for frontend purposes.
For this project, React was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).
[More about React](https://reactjs.org/)
[More about Create React App](https://facebook.github.io/create-react-app/docs/getting-started)

##### Start up this project
Running the `npm start` command will automatically start the application and  open it in the user's browser.
If the project doesn't immediately open the application page, it can also be accessed through `localhost:3000`.

##### Run the tests
Running the `npm  test` command will run all test suits in 'watch mode'.
Watch mode implies that when any part of the code is modified, the modified test suite will re-run.
[Read more](https://facebook.github.io/create-react-app/docs/running-tests)

##### Build the app
Running the `npm build` command builds the app for production.
It correctly bundles React in production mode and optimizes the build for the best performance.
[Read more](https://facebook.github.io/create-react-app/docs/deployment)

## Setting up the Database
### Installing MySQL

#### Windows:
1 - Download the MySQL 5.7.29 Installer from this link: https://dev.mysql.com/downloads/windows/installer/5.7.html (Choose the `mysql-installer-community-5.7.29.0.msi`)

2 - Open the Installer and choose `Developer Default` (In theory `Server Only` should be enough, but Developer Default will give you access to MySQL Workbench if you want to use it)

3 - In the "Check Requirements" step, press "Execute". It will open up multiple new windows. Just install everything.

4 - Press "Next".

5 - In the "Installation" step, press "Execute". Once everything is finished downloading, press "Next".

6 - Keep pressing "Next" until you are at the "Accounts and Roles" step.

7 - Choose a MySQL Root Password. You MUST remember this password. For the sake of this tutorial, I will choose `1234`, but you should choose something else.

8 - Press "Next" (don't need to create a user account here, we will create it through the command line).

9 - Keep pressing "Next"/"Execute"/"Finish" until the "Connect To Server" step.

10 - Enter your root password you chose, i.e. `1234` in my case. Press "Check" to see if the connection is sucessful. Keep pressing "Next"/"Continue"/"Finish", and you should be finished.

#### Linux:
1 - Run `sudo apt-get install mysql-server-5.7` in the terminal.

---
### Opening mysql in cmd/terminal

#### Windows:

1 - Open a new Command Prompt window. Change the directory to where MySQL Server was downloaded. In my case, I would run this command:
`cd C:\Program Files\MySQL\MySQL Server 5.7\bin`.

2 - After `cd`'ing, run `mysql -u root -p`. It should ask you to enter your password. In my case, I would enter `1234`.

#### Linux:
1 - Open a terminal and run `sudo mysql --password`. When it prompts you for a password, just press enter (no password).

---
### Creating the Database

1 - Now we need to create a new database, a new user, and grant the new user access to the new database through the opened cmd/terminal.

Run these 3 commands:

`create database {DATABASE_NAME};`

`create user '{USERNAME}'@'localhost' identified by '{PASSWORD}';`

`grant all on {DATABASE_NAME}.* to '{USERNAME}'@'localhost';`

For instance, if you want to create a database called `instagram` and a user with username 'instagram_admin' and password 'instagram', run:

`create database instagram;`

`create user 'instagram_admin'@'localhost' identified by 'instagram';`

`grant all on instagram.* to 'instagram_admin'@'localhost';`


You should get `Query OK` for all these commands.

2 - Your database is now set up and the only step missing is to let project connect to the database. To do so, add the following lines to `backend/src/main/resources/application.properties`:

```
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/{DATABASE_NAME}
spring.datasource.username={USERNAME}
spring.datasource.password={PASSWORD}
```


With the values given earlier, you would edit the file with the following content:

```
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/instagram
spring.datasource.username=instagram_admin
spring.datasource.password=instagram
```

The next time you run the backend project, the database schema should be automatically built by `Hibernate`.

**NOTE**: For the sake of keeping database credentials private, please do not commit/push your own `application.properties` file to the project.

---
### Viewing the database

#### Windows
You can use the MySQL Workbench application that came with the MySQL installation. If you want to use `DBeaver`, you can download it here: https://dbeaver.io/download/ and follow the steps for "Linux" (ignoring the installation steps, i.e. starting from Step #2)

1 - Open MySQL Workbench.

2 - Press the "+" button next to "MySQL Connections".

3 - Choose a "Connection Name" (e.g. "Instagram"), and change the "Username" from "root" to whatever you chose earlier (in this case, "instagram_admin").

4 - Press "Test Connection". It should prompt you for a password. Put in the password you chose earlier (in this case, "instagram"). Check "Save password in vault" if you don't want to have to input it later on. Press "OK".

5 - A message saying the connection was succesful should show. Press "OK", then "OK" again (in the Setup New Connection window).

6 - A new connection should appear under the "MySQL Connections", with the "Connection Name" you chose earlier. Click it, and if it prompts for a password, input it.

7 - A new tab will show up. On the left, there should be a "Navigator", and there should be a "Schemas" tab on the bottom of it.

8 - Press "Schemas". You should see a database with the Database name you chose earlier (in this case, `instagram`) if all the steps were done succesfully.

9 - From there you will be able to access the database tables, their data, etc.

#### Linux:
1 - Download DBeaver in the default Linux Software application.

2 - Open DBeaver. The first time you open DBeaver, it should ask you to "Select your Database". If not, press the plug icon with a plus in the top left ("New Database Connection").

3 - Select MySQL 8+ and press Next. Change the "User name" and "Password" fields to what you chose earlier (in this case, `instagram_admin` and `instagram` respectively).

4 - Press "Test Connection" to test. If it prompts you to download something, download it. The test should be succesful.

5 - Press "Finish". In the left window, you should see a new connection, and `Databases --> {DATABASE_NAME}` should be there if everything was done succesfully (i.e. an `instagram` database should show in this case).

6 - From there you will be able to access the database tables, their data, etc.
