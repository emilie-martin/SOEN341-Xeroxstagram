# SOEN341-InsertName
### Authors
Simon Lim (tech lead) | [@nasaku898](https://github.com/nasaku898)
:--|:--
Émilie Martin         | [@emilie-martin](https://github.com/emilie-martin)  
Daniela Venuta        | [@daniela-venuta](https://github.com/daniela-venuta)
Minh-Tam Do           | [@mint47](https://github.com/mint47)
Luc Nguyen            | [@1999lucnguyen](https://github.com/1999lucnguyen)  
Karin Kazarian        | [@Karin-kazarian](https://github.com/Karin-kazarian)  

### Description
This is a social media project based on the main features of Instagram.  
This project aims for us to learn the basics of the industrial software development process and was created for the course SOEN341 - Software Processes at Concordia University.

### Objectives
This application is intended to implement the same core features of Instagram, such that the user is able to post a picture, follow another user, and be allowed to comment under posted pictures. The project guideline also called for the implementation of additional features.

### Features
* User Service (Registration, Login and Account Management)
* Posting pictures (if registered and logged in)
* Liking (posts & comments)
* Following other users
* Commenting (edit & delete)
* Home feed (containing posts of followed users)
* User statistics (followers, following, post count)

[More about our core features](https://github.com/emilie-martin/SOEN341-InsertName/wiki/Program-Breakdown)

### Technology
| Frontend   | Backend     |
|------------|-------------|
| JavaScript | Java        |
| React      | Spring Boot |
| SCSS       | Maven       |
|            | MySQL       |
|            | Mockito     |

**Note:** Travis CI was used as Continuous Integration (CI) tool.
- - -

### Screenshots

##### Registration
![User registration](https://i.ibb.co/r2F4vSF/register.jpg)
> The client is prompted to create an account through the registration module.

##### Login
![Login module](https://i.ibb.co/ChTmSG6/login.jpg)
> Once a user has created an account, they can log in through the login module.

##### Uploading a Picture
![Picture upload](https://i.ibb.co/CWmy2vn/upload.jpg)
> Once logged in, a user can upload a picture through the upload module.

##### User Feed
![User feed ex1](https://i.ibb.co/ygJGBXQ/user.jpg)
> The uploaded pictures will appear on the corresponding users' feed. 

![User feed ex1](https://i.ibb.co/1ZQX0xx/user2.jpg)
> The uploaded pictures are sorted right to left from most dated to most recent, organized into roughly equal-heighted columns.

##### Post Interaction
![Post](https://i.ibb.co/bH7z93g/post.jpg)
> Once posted, an uploaded picture can be interacted with in two distinct ways: commenting and liking.
###### Comments
> To leave a comment, a logged-in user must simply input text in the bottom-right area designed to do so and, once complete, publish their comment to the post by clicking the `POST` at the bottom-right of the post itself.  
###### Likes
> If a user likes a post or a comment (from another user or their own) they can choose to 'like' it by clicking the moon icon. When a post or comment is liked, the icon will 'light up' and beside it, the number of likes will be displayed. 

![Comment edit](https://i.ibb.co/1Td59s2/editcomment.jpg)
> At any time, a logged-in user can edit or delete their comments, be it on their own posts on another users' post. If the post belongs to the logged-in user, they also have the possibility of removing comments left by other users.

##### Account Management
![Account management](https://i.ibb.co/xfgQ1VF/edit.jpg)
> Once logged in, a user can edit their own information, from profile to account. This encompasses display name, biography, email, password, and birthday.

##### Following
![Follow/Unfollow](https://i.ibb.co/Yfp3zD6/following.jpg)
> Once logged in, a user can follow another user. To do so, they must search a pre-existing user-name in the search bar and hit the `Search` button. If the username does not exist an error will pop up stating so, otherwise the specified user account will be shown. If the account isn't already being followed by the logged in user, the user will be able to follow the specified user. Otherwise, they will be able to unfollow them. The follower count will be adjusted accordingly. It is to note that is a user attempts to follow themself, an error will pop up.

- - -

## Details on technology used
### React
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
### Spring Boot
Spring Boot is an open source Java-based framework used to create a microservice.
It is developed by Pivotal Team and is used to build stand-alone and production ready spring applications.
 
### Travis CI
Travis CI is a hosted continuous integration service used to build and test software projects hosted at GitHub.
