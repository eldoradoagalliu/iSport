<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>iSport</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/header-style.css">
    <link rel="stylesheet" type="text/css" href="/css/account-style.css">
</head>
<body>
<nav class="navbar navbar-dark bg-dark nav-border">
    <div class="container-fluid">
        <a class="navbar-brand logo-font" href="/dashboard">iSport</a>
        <div>
            <button class="btn btn-light navbar-toggler" id="navbarButton">
                <span class="navbar-toggler-icon"></span>
            </button>
        </div>
        <div class="collapse bg-dark p-4" id="navbarDropdown">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active float-end" href="/account/${currentUser.id}" tabindex="-1"
                       aria-disabled="true">
                        <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" fill="white"
                             class="bi bi-person-circle" viewBox="0 0 16 16">
                            <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                            <path fill-rule="evenodd"
                                  d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                        </svg>
                        Account
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active float-end" href="/new">Add a new sport event</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active float-end" href="/search">Search event</a>
                </li>
                <li class="nav-item">
                    <form id="logoutForm" method="post" action="/logout">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button class="logout-button float-end">Logout</button>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="main-part">
    <div class="text-center"><img src="/images/iSport_logo.png" class="logo p-1"/></div>
    <h2 class="text-center isport-title m-2">Free Pickup Game Finder and Organizer</h2>

    <div class="edit-user-form">
        <%--@elvariable id="user" type="user"--%>
        <form:form action="/account/edit/${user.id}" method="post" modelAttribute="user">
            <input type="hidden" name="_method" value="put">
            <h2 class="text-decoration-underline text-center mb-3 edit">Edit Your Details</h2>
            <div class="d-flex align-content-center m-1">
                <form:label path="firstName" class="col-sm-5 col-form-label">First Name: </form:label>
                <form:input path="firstName" class="form-control input-font"/>
            </div>
            <div class="d-flex align-content-center m-1"><form:errors path="firstName" class="text-danger"/></div>

            <div class="d-flex align-content-center m-1">
                <form:label path="lastName" class="col-sm-5 col-form-label">Last Name: </form:label>
                <form:input path="lastName" class="form-control input-font"/>
            </div>
            <div class="d-flex align-content-center m-1"><form:errors path="lastName" class="text-danger"/></div>

            <div class="d-flex align-content-center m-1">
                <form:label path="email" class="col-sm-5 col-form-label">Email: </form:label>
                <form:input path="email" class="form-control input-font"/>
            </div>
            <div class="d-flex align-content-center m-1"><form:errors path="email" class="text-danger"/></div>

            <div class="d-flex align-content-center m-1">
                <form:label path="birthdate" class="col-sm-5 col-form-label">Birthdate: </form:label>
                <form:input path="birthdate" type="date" class="form-control input-font"/>
            </div>
            <div class="d-flex align-content-center m-1"><form:errors path="birthdate" class="text-danger"/></div>

            <button class="btn btn-success edit-details-button mt-2">Edit Details</button>
        </form:form>
    </div>
</div>
<footer class="text-center text-white m-2 text-decoration-underline">Copyright © ${year} - Eldorado Agalliu</footer>
<script type="text/javascript" src="/script/navbar-dropdown.js"></script>
</body>
</html>