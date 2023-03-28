<%@ page import="dev.filipposcaramuzza.db2_telco.entities.User" %>
<%@ page import="dev.filipposcaramuzza.db2_telco.entities.ServicePackage" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.IOException" %>
<%@ page import="dev.filipposcaramuzza.db2_telco.entities.Service" %>
<%@ page import="dev.filipposcaramuzza.db2_telco_webemployee.utilities.RequestType" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    User user;
    user = (User) session.getAttribute("user");
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
            Buy Package
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
                <li><a href="${pageContext.request.contextPath}/packages">Packages</a></li>
                <li class="is-active"><a href="#" aria-current="page">Buy</a></li>
            </ul>
        </nav>
        <h3 class="title is-3">
            Customize your Subscription
        </h3>

        <c:if test="${not empty servicePackage}">
            <h4 class="title is-4">${servicePackage.getName()}</h4>
            <h4 class="subtitle is-6">Service Package</h4>

            <strong>Included Services:</strong><br>
            <ul>
                <c:forEach var="service" items="${servicePackage.getServices()}">
                                        <span class="tag is-info is-light">
                                                ${service.getName()}
                                        </span>
                    <c:choose>
                        <c:when test="${service.getServiceOptions().size() > 0}">
                            <c:forEach var="serviceOption"
                                       items="${service.getServiceOptions()}">
                                <li>
                                                    <span class="has-text-info-dark ml-5">
                                                            ${serviceOption.getOptionNum()}
                                                    </span>
                                        ${serviceOption.getName()}
                                    (extra ${serviceOption.getName()} at
                                    <span class="has-text-info-dark">
                                                                    ${serviceOption.getExtraOptionFee().setScale(2)} €</span>)
                                </li>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <li class="ml-5">
                                No Limits
                            </li>
                        </c:otherwise>
                    </c:choose>

                </c:forEach>
            </ul>
            <br>
            <form method="post" action="${pageContext.request.contextPath}/confirm">

                <input type="hidden" name="servicePackageID" value="${servicePackage.getID()}"/>
                <input type="hidden" name="requestType" value="${RequestType.PENDING_POST}"/>

                <strong>Choose one of the following Validity Periods:</strong><br>

                <div class="control">
                    <c:forEach var="validityPeriod" items="${servicePackage.getValidityPeriods()}">
                        <label class="radio">
                            <input type="radio" name="validityPeriodID" value="${validityPeriod.getID()}" required>
                            <span class="has-text-info-dark ml-5">
                                    ${validityPeriod.getMonthsNum()}
                            </span>months at
                            <span class="has-text-info-dark">
                                ${validityPeriod.getMonthlyFee().setScale(2)}€</span>/month
                        </label><br>
                    </c:forEach>
                </div>
                <br>

                <strong>Choose one or more Optional Products:</strong><br>

                <div class="control">
                    <c:forEach var="optionalProduct" items="${servicePackage.getOptionalProducts()}">
                        <label class="checkbox">
                            <input type="checkbox" name="optionalProducts[]" value="${optionalProduct.getID()}">
                            <span class="has-text-info-dark ml-5">
                                ${optionalProduct.getName()}
                            </span> at
                            <span class="has-text-info-dark">
                                ${optionalProduct.getMonthlyFee().setScale(2)}€</span>/month
                        </label><br>
                    </c:forEach>
                </div>
                <br>
                <strong>Choose the subscription starting date:</strong><br>

                <label>
                    <input id="datefield" class="input" type="date" name="startingDate" style="width: auto!important" required/>
                </label>
                <br><br>

                <button class="button is-primary" type="submit">
                    <span class="icon is-small">
                    <i class="fas fa-check"></i>
                    </span>
                    <span>
                        Confirm
                    </span>
                </button>
            </form>
        </c:if>

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
<script>
    document.getElementById('datefield').value = new Date().getDate();
</script>
</body>
</html>
