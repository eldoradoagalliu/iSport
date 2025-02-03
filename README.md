# iSport App

iSport App is a web application made for any user that want to schedule and organize sport events for everyone.

This app was initially built with Java SE 8, Spring Framework, Spring MVC, Spring Security, MySQL, HTML, CSS and
Bootstrap. Lately it has been updated to Java 17 and the latest version of Spring.
---

Main features of iSport App:
-

- Dynamic UI/UX
- User Login/Registration with Spring Security
- Admin Dashboard
- Creation of Events
- Event Details (Location with Google Maps API)
- Ability to join, leave events, chat with other users that are attending the event
- User Profile Page (Details, Adding Profile Photos and Editing Option)
- Search Page for Events by using Event Name, Location or Creator Name
- Administrator access for editing or deleting App Users or Events

---

To run this application you need to add the database properties on the .env file (check .env.example).

To include the Google Maps feature for the event location, you also need to add your api key on the event_details.jsp
file.