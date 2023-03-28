<%@ page import="dev.filipposcaramuzza.db2_telco.entities.User" %>
<%@ page import="dev.filipposcaramuzza.db2_telco.entities.ServicePackage" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.IOException" %>
<%@ page import="dev.filipposcaramuzza.db2_telco.entities.Service" %>
<%@ page import="java.sql.Date" %>
<%@ page import="dev.filipposcaramuzza.db2_telco.entities.Order" %>
<%@ page import="dev.filipposcaramuzza.db2_telco_webemployee.utilities.RequestType" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    User user;
    user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/packages");
    }
%>
<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.3/css/bulma.min.css">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
          integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <style>


        body {
            text-align: center;
        }

        .column {
            text-align: left;
        }

        /*.packages {
            border-right: 2px solid lightseagreen;
        }*/

        .tag {
            margin: 5px 0px;
        }
    </style>

    <title>Telco - Packages</title>
</head>
<body>
<section class="hero is-primary">
    <div class="hero-body">
        <p class="title">
            Telco Service &#174;
        </p>
        <p class="subtitle">
            Your Orders
        </p>
    </div>
</section>
<!-- <h1 class="title is-1">
    Telco Service &#174;
</h1>
<h1 class="sub-title">
    Available Packages and Your Subscriptions
</h1>-->
<br>
<div class="columns is-centered">
    <div class="column is-5 packages">
        <nav class="breadcrumb" aria-label="breadcrumbs">
            <ul>
                <li><a href="${pageContext.request.contextPath}/">Landing Page</a></li>
                <li class="is-active"><a href="#" aria-current="page">Orders</a></li>

            </ul>
        </nav>
        <h3 class="title is-3">
            Your Orders
        </h3>

        <c:choose>
            <c:when test="${not empty orders}">
                <c:forEach var="order" items="${orders}">
                    <div class="card mb-4">
                        <form method="post" action="delete">
                            <input type="hidden" name="IDOrder" value="${order.getID()}"/>
                        <button class="delete" aria-label="delete" type="submit"></button>
                        </form>
                        <div class="card-content">
                            <div class="media">
                                <div class="media-content">
                                    <c:choose>
                                        <c:when test="${order.isValid()}">
                                            <p class="title is-4">Status: <span
                                                    class="has-text-success">Activated</span></p>
                                        </c:when>
                                        <c:otherwise>
                                            <p class="title is-4">Status: <span class="has-text-danger">Payment to be reviewed</span>
                                            </p>
                                        </c:otherwise>
                                    </c:choose>
                                    <p class="title is-4">Total Value:</p>
                                    <p class="subtitle is-4">${order.getTotalValue().setScale(2)} €</p>
                                </div>
                            </div>

                            <div class="content">
                                <strong>Service Package: </strong>${order.getServicePackage().getName()}<br>
                                <strong>Creation Date and Time: </strong>${order.getCreationDate().toString()}
                                @ ${order.getCreationHour().toString()}<br>
                                <strong>Start Date: </strong>${order.getStartDate().toString()}<br>
                                <strong>Validity Period: </strong>${order.getValidityPeriod().getMonthsNum()} months<br>
                                <strong>Included Services:</strong><br>
                                <c:forEach var="service" items="${order.getServicePackage().getServices()}">
                                        <span class="tag is-info is-light">
                                                ${service.getName()}
                                        </span>
                                </c:forEach>
                                <br>
                                <strong>Included Optional Products:</strong><br>
                                <c:forEach var="optionalProduct"
                                           items="${order.getOptionalProducts()}">
                                        <span class="tag is-info is-light">
                                                <c:out value="${optionalProduct.getName()}"/>
                                        </span>

                                </c:forEach>
                            </div>
                            <c:if test="${not order.isValid()}">
                                <form method="post" action="${pageContext.request.contextPath}/confirm">
                                    <input type="hidden" name="requestType" value="${RequestType.PENDING_RETRY_POST}"/>
                                    <input type="hidden" name="IDOrder" value="${order.getID()}"/>

                                    <button class="button is-primary" type="submit">
                                <span class="icon is-small">
                                <i class="fas fa-redo"></i>
                                </span>
                                        <span>
                                    Retry Payment
                                </span>
                                    </button>
                                </form>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
        </c:choose>


    </div>
    <div class="column is-2">
        <h3 class="title is-3">
            Profile
        </h3>
        <c:choose>
            <c:when test="${user == null}">
                <div class="notification is-warning is-light">
                    You are browsing as guest. If you want to sign in go to the <a
                        href="${pageContext.request.contextPath}/">Log In page</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="box">
                    <b>Username:</b> ${user.getUsername()}<br>
                    <b>E-Mail:</b> ${user.getEmail()}<br>
                    <b>Name:</b> ${user.getName()}<br>
                    <b>Surname:</b> ${user.getSurname()}<br>
                    <c:if test='<%= user.isInsolvent() %>'>
                        <div class="icon-text">
                        <span class="icon has-text-warning">
                            <i class="fas fa-exclamation-triangle"></i>
                        </span>
                            <span>Insolvent</span>
                        </div>
                    </c:if>
                </div>
                <div class="box has-centered-text">
                    <a href="${pageContext.request.contextPath}/orders">Your Orders</a><br>
                    Status:
                    <c:choose>
                        <c:when test="${user.getOrders().stream().filter(o -> !o.isValid()).count() > 0}">
                            <span class="has-text-danger">You have ${user.getOrders().stream().filter(o -> !o.isValid()).count()} payments to be reviewed!</span>
                        </c:when>
                        <c:otherwise>
                            <span class="has-text-success">Everything is ok.</span>
                        </c:otherwise>
                    </c:choose>
                </div>



                <div class="box has-centered-text">
                    <a href="${pageContext.request.contextPath}/logout">Log Out</a>
                </div>

            </c:otherwise>
        </c:choose>
        <c:if test='<%= session.getAttribute("pendingServicePackage") != null %>'>
            <div class="notification is-warning is-light">
                You have a pending confirmation. Click <a href="${pageContext.request.contextPath}/confirm">here</a> to resume it.
            </div>
        </c:if>
    </div>
</div>

</body>
</html>
