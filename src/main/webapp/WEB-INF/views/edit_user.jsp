<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>iSport</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/navbar-style.css">
    <link rel="stylesheet" type="text/css" href="/css/account-style.css">
</head>
<body>
<nav class="navbar navbar-dark bg-dark nav-border">
    <div class="container-fluid">
        <a class="navbar-brand logo-font" href="/dashboard">iSport</a>
        <div>
            <button class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#navbarToggleExternalContent">
                <span class="navbar-toggler-icon"></span>
            </button>
        </div>
        <div class="navbar-dropdown collapse bg-dark p-4" id="navbarToggleExternalContent">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active float-end" href="/account/${currentUser.id}" tabindex="-1">
                        <img src="/images/person-circle.svg" alt="Person icon"/> Account
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active float-end" href="/event">New sport event</a>
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
    <div class="text-center"><img src="/images/isport-logo.png" alt="iSport logo" class="logo p-1"/></div>
    <h2 class="text-center isport-title m-2">Free Pickup Sport Events Finder and Organizer</h2>
    <div class="edit-user-form">
        <%--@elvariable id="user" type=""--%>
        <form:form action="/account/${user.id}/edit" method="post" modelAttribute="user">
            <input type="hidden" name="_method" value="put">
            <h2 class="text-decoration-underline text-center mb-3 edit">Your Account Details</h2>
            <div class="d-flex align-content-center m-1">
                <form:label path="firstName" class="col-sm-5 col-form-label">First Name:</form:label>
                <form:input path="firstName" class="form-control input-font"/>
            </div>
            <div class="d-flex align-content-center m-1"><form:errors path="firstName" class="text-danger"/></div>
            <div class="d-flex align-content-center m-1">
                <form:label path="lastName" class="col-sm-5 col-form-label">Last Name:</form:label>
                <form:input path="lastName" class="form-control input-font"/>
            </div>
            <div class="d-flex align-content-center m-1"><form:errors path="lastName" class="text-danger"/></div>
            <div class="d-flex align-content-center m-1">
                <form:label path="email" class="col-sm-5 col-form-label">Email:</form:label>
                <form:input path="email" class="form-control input-font"/>
            </div>
            <div class="d-flex align-content-center m-1"><form:errors path="email" class="text-danger"/></div>
            <div class="d-flex align-content-center m-1">
                <form:label path="birthdate" class="col-sm-5 col-form-label">Birthdate:</form:label>
                <form:input path="birthdate" type="date" class="form-control input-font"/>
            </div>
            <div class="d-flex align-content-center m-1"><form:errors path="birthdate" class="text-danger"/></div>
            <button class="btn btn-success edit-details-button mt-2">Edit Details</button>
        </form:form>
    </div>
</div>
<footer class="text-center text-white m-2 text-decoration-underline">Copyright © ${year} - Eldorado Agalliu</footer>
<script src="/webjars/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>