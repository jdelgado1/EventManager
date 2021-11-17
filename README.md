# 2021-fall-group-group4

# EventApp: A Summary

    EventApp is a platform for creating, managing, discovering, and attending
    events on campus. It's a one-stop app where users can create profiles, post about their events, and search for &
    be reccomended events based on popularity and individual user interests. In addition, the app will serve 
    as a check-in/check-out mechanism for events, management system for event attendence, and more.

# Built With
    IntelliJ
    ORMLite
    JDBC
    SparkJava
    Velocity Template Engine
    JQuery
    Bootstrap
    Collaboration Platform: Github
    Deployment: Heroku

# Getting Started
    To get a local copy up and running follow these simple steps.

### Prerequisites and Dependencies
    Project dependencies: JUnit 5.6.0, ORMLite 5.6, Spark Java 2.9.3, JDBC 3.36.02, Spark Template Velocity 2.7.1
    Clone the repo (enter in cmd line): git clone https://github.com/jhu-oose/2021-fall-group-group4.git

### Usage of EventApp
    1. Run the Server class (In Project/src/java)
    2. In your browser, go to localhost:7000 to access the web app
    3. You will be met with a homepage with three columns: a user profile column, and event feed column, and an event reccomendation/check-in column
        Currently, you can add a new event (either a seminar or social event) by clicking the center "+" button, which will give you a pop-up form to fill out. Fill out the necessary event details and click "add event" to see it displayed on the current event feed!
        In addition, you can use the search bar to search listed events based on keywords in their title/description, or filter events based on associated content tags (buzzwords that give users more insight about the event's theme/purpose). 
        On each listed event, click "see details" to get the event's address and date!
        The left columns allows the user to see a short description of all user profiles (grup or individual) via a dropdown and search bar. In addition, you can create a new profile by clicking the "add user button."
        The right column allows the user to invite another user to a private event  - an email will be sent to this user with the invitation.
        The right column allows the user to check into a currently occuring event that they will be attending (marking they have arrived at the event, for attendance management purposes). In addition, this column holds the hottest event, so the user can see which is the most popular event right now. This event is based on an algorithm that time weights the number of check-ins versus number of check outs against the current time. Events with no one checked in will have a hot score of zero. As more and more people check into an event, there will be an increasing hot score. Also, note that you cannot check into an event unless you have first been invited.

    NOTE: There is a "debug menu" link on the right column. This is listed for developers and clicking it will navigate you to an index of implemented features. To see the hot score of a particular event, you can see it using the debug menu. 

#### How to end 
    1. Finish whatever feature you're using on the web app
    2. Close out of the web app (localhost 7000 browser) and end the Server locally
    
#### Deployment
    https://group4-eventapp.herokuapp.com/
    Please contact if it is down for any reason.

# Roadmap (Currently Implemented Features)
    View all current events in a general fead, which can be searched/filtered based on event title and description or associated content tags.
    Add a new event (seminar or social event) and store details such as date, address, host, and optional content tags.
    Create a new user profile (individual or group) and supply details like name, email, and optional user interests.
    Add individual users to a group (i.e. for campus groups of students)
    See and search through all user profiles
    Check into a currently occuring event (to mark attendance)
    Be reccomended an event based on individual user interests
    Be reccomended an event based on its popularity ("hottest" event)
    

    
