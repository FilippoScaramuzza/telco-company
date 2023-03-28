<%@ page import="dev.filipposcaramuzza.db2_telco.entities.User" %>
<%@ page import="dev.filipposcaramuzza.db2_telco.entities.ServicePackage" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.IOException" %>
<%@ page import="dev.filipposcaramuzza.db2_telco.entities.Service" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="dev.filipposcaramuzza.db2_telco.entities.Order" %>
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
            Available Packages and Your Subscriptions
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
                <li class="is-active"><a href="#" aria-current="page">Packages</a></li>
            </ul>
        </nav>
        <h3 class="title is-3">
            Available Packages
        </h3>
        <div class="columns is-multiline">
            <c:choose>
                <c:when test="${not empty result}">
                    <c:forEach var="servicePackage" items="${result}">
                        <div class="column is-half">
                            <div class="card js-modal-trigger" data-target="modal-js-${servicePackage.getID()}"
                                 style="cursor: pointer;">
                                <div class="card-content">
                                    <div class="media">
                                        <div class="media-content">
                                            <p class="title is-4"><c:out value="${servicePackage.getName()}"/></p>
                                            <p class="subtitle has-text-grey-light is-6">Service Package</p>
                                        </div>
                                    </div>

                                    <div class="content">

                                        <strong><c:out value="${servicePackage.getServices().size()}"/> included
                                            services:</strong>
                                        <c:forEach var="service" items="${servicePackage.getServices()}">
                                            <span class="tag is-info is-light"><c:out
                                                    value="${service.getName()}"/></span>
                                        </c:forEach>
                                        <br>
                                        <strong>Validity Periods (months): </strong>
                                        <c:forEach var="validityPeriod" items="${servicePackage.getValidityPeriods()}">
                                            <span class="tag is-info is-light">${validityPeriod.getMonthsNum()}</span>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal" id="modal-js-${servicePackage.getID()}">
                            <div class="modal-background"></div>
                            <div class="modal-card">
                                <header class="modal-card-head">
                                    <p class="modal-card-title">${servicePackage.getName()}</p>
                                    <button class="delete" aria-label="close"></button>
                                </header>
                                <section class="modal-card-body">
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
                                    <strong>Available Validity Periods:</strong><br>
                                    <ul>
                                        <c:forEach var="validityPeriod" items="${servicePackage.getValidityPeriods()}">
                                            <li>
                                                <span class="has-text-info-dark ml-5">
                                                        ${validityPeriod.getMonthsNum()}
                                                </span>months at
                                                <span class="has-text-info-dark">
                                                        ${validityPeriod.getMonthlyFee().setScale(2)}€</span>/month
                                            </li>
                                        </c:forEach>
                                    </ul>
                                    <br>
                                    <strong>Available Optional Products:</strong><br>
                                    <ul>
                                        <c:forEach var="optionalProduct"
                                                   items="${servicePackage.getOptionalProducts()}">
                                            <li>
                                                <span class="has-text-info-dark ml-5">
                                                        <c:out value="${optionalProduct.getName()}"/>
                                                </span> at
                                                <span class="has-text-info-dark">
                                                        ${optionalProduct.getMonthlyFee().setScale(2)}€</span>/month
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </section>
                                <footer class="modal-card-foot">
                                    <form method="get" action="${pageContext.request.contextPath}/buy">
                                        <input type="hidden" value="${servicePackage.getID()}" name="packageID"/>
                                        <button type="submit" class="button is-primary">Buy Package</button>
                                    </form>

                                </footer>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </div>
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
                You have a pending confirmation. Click <a
                    href="${pageContext.request.contextPath}/confirm">here</a> to resume it.
            </div>
        </c:if>
    </div>
</div>
<script>
    document.addEventListener('DOMContentLoaded', () => {
        // Functions to open and close a modal
        function openModal($el) {
            $el.classList.add('is-active');
        }

        function closeModal($el) {
            $el.classList.remove('is-active');
        }

        function closeAllModals() {
            (document.querySelectorAll('.modal') || []).forEach(($modal) => {
                closeModal($modal);
            });
        }

        // Add a click event on buttons to open a specific modal
        (document.querySelectorAll('.js-modal-trigger') || []).forEach(($trigger) => {
            const modal = $trigger.dataset.target;
            const $target = document.getElementById(modal);
            console.log($target);

            $trigger.addEventListener('click', () => {
                openModal($target);
            });
        });

        // Add a click event on various child elements to close the parent modal
        (document.querySelectorAll('.modal-background, .modal-close, .modal-card-head .delete, .modal-card-foot .button') || []).forEach(($close) => {
            const $target = $close.closest('.modal');

            $close.addEventListener('click', () => {
                closeModal($target);
            });
        });

        // Add a keyboard event to close all modals
        document.addEventListener('keydown', (event) => {
            const e = event || window.event;

            if (e.keyCode === 27) { // Escape key
                closeAllModals();
            }
        });
    });
</script>
</body>
</html>
