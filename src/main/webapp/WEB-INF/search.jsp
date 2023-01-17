<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Search</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap" rel="stylesheet">
</head>
<body class="container" style="background: #037759">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark" style="height: 50px; width: 1000px;margin-left: 50px">
    <div class="container-fluid">
        <a class="navbar-brand" href="/dashboard" style="font-size: 24px">iSport</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav" style="margin-left: 540px">
                <li class="nav-item" style="margin-left: 20px">
                    <a class="nav-link active" aria-current="page" href="/dashboard">Home</a>
                </li>
                <li class="nav-item" style="margin-left: 20px">
                    <a class="nav-link active" href="/new">New</a>
                </li>
                <li class="nav-item" style="margin-left: 20px">
                    <a class="nav-link active" href="/search">Search</a>
                </li>
                <li style="margin-top: 7px; margin-left: 20px"><svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="white" class="bi bi-person-circle" viewBox="0 0 16 16">
                    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                    <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                </svg></li>
                <li class="nav-item">
                    <a class="nav-link active" href="/account/${user.id}" tabindex="-1" aria-disabled="true">Account</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div style="font-weight: 600; width: 1000px; height: 580px;background: #ebd402;margin-left: 50px; padding: 20px; font-size: 18px">
    <form:form action="/search" method="post">
        <div class="d-flex align-content-center" style="margin-bottom: 10px">
            <h1 style="margin-left: 10px">Search by:</h1>
            <select name="selectedOption" id="selectedOption" style="margin-left: 20px; border: 2px solid black; width: 200px;  height: 40px; font-weight: 600; margin-top: 7px">
                <option value="eventName">Event Name</option>
                <option value="locationName">Location Name</option>
                <option value="creator">Creator</option>
            </select>
            <div style="margin-left:150px; margin-top: 7px"><input type="text" name="search" id="search" placeholder="Search" required="required"/></div>
            <div style="margin-left: 20px; margin-top: 7px"><button style="box-shadow: 1px 1px black; border: 2px solid black; font-size: 18px; font-weight: 600; color: white; padding: 3px 20px; background: #037759" class="btn">Submit</button></div>
        </div>
    </form:form>

    <table class="table table-secondary table-striped table-hover">
        <thead>
        <th>Event Name</th>
        <th>Location Name</th>
        <th>Member</th>
        <th>Date</th>
        <th>Creator</th>
        <th>Action</th>
        </thead>
        <tbody>
        <c:forEach var="event" items="${events}">
            <tr>
                <td><a href="/event/${event.id}" style="color: black; text-decoration: none">${event.eventName}</a></td>
                <td>${event.location}</td>
                <td>${event.getNumberOfAttenders()}/${event.attendees}</td>
                <td>${event.fullDateFormatter()}</td>
                <td><a href="/account/${event.creator.id}" class="text-dark">${event.creator.fullName()}</a></td>
                <td>
                    <c:if test="${event.getNumberOfAttenders() == event.attendees}">Full</c:if>
                    <c:if test="${event.getNumberOfAttenders() < event.attendees}"><a href="/event/join/${event.id}">Join event</a></c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<footer class="text-center text-white m-1 text-decoration-underline">Copyright © 2022 - Eldorado Agalliu</footer>
</body>
</html>