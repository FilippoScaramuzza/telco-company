<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.IOException" %>
<%@ page import="dev.filipposcaramuzza.db2_telco.entities.*" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="dev.filipposcaramuzza.db2_telco_webemployee.utilities.RequestType" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    User user;
    user = (User) session.getAttribute("user");

    ServicePackage pendingServicePackage = (ServicePackage) session.getAttribute("pendingServicePackage");
    if (pendingServicePackage == null) {
        response.sendRedirect(request.getContextPath() + "/packages");
    }

    int pendingValidityPeriodID = Integer.parseInt((String) session.getAttribute("pendingValidityPeriodID"));

    ValidityPeriod pendingValidityPeriod = pendingServicePackage.getValidityPeriods()
            .stream().filter(vp -> vp.getID() == pendingValidityPeriodID).collect(Collectors.toList()).get(0);

    List<Integer> pendingOptionalProductsIDs = (ArrayList<Integer>) session.getAttribute("pendingOptionalProductsIDs");
    List<OptionalProduct> pendingOptionalProducts = null;
    if(pendingOptionalProductsIDs != null) {
        pendingOptionalProducts = pendingServicePackage.getOptionalProducts()
                .stream().filter(op -> pendingOptionalProductsIDs.contains(op.getID())).collect(Collectors.toList());
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
                <li><a href="${pageContext.request.contextPath}/packages">Packages</a></li>
                <li><a href="${pageContext.request.contextPath}/buy?packageID=${pendingServicePackage.getID()}">Buy</a>
                </li>
                <li class="is-active"><a href="#" aria-current="page">Confirmation</a></li>
            </ul>
        </nav>
        <h3 class="title is-3">
            Confirmation Page
        </h3>
        <h3 class="subtitle is-4">
            Review your subscription before the payment
        </h3>

        <strong>Chosen service package: </strong>${pendingServicePackage.getName()}
        <br><br>
        <strong>Included Services:</strong><br>
        <ul>
            <c:forEach var="service" items="${pendingServicePackage.getServices()}">
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
        <strong>Chosen Validity Period: </strong><span
            class="has-text-info-dark"><% out.print(pendingValidityPeriod.getMonthsNum());%></span> months at <span
            class="has-text-info-dark"><% out.print(pendingValidityPeriod.getMonthlyFee().setScale(2)); %>€</span>/month
        <br><br>
        <strong>Chosen Optional Products: </strong>
        <%
            if (pendingOptionalProducts == null) {
                out.print("No optional products selected.");
            } else {
                out.print("<ul>");
                for (OptionalProduct optionalProduct : pendingOptionalProducts) {

                    out.print("<li><span class='has-text-info-dark'>" +
                            optionalProduct.getName() +
                            "</span> at <span class='has-text-info-dark'>" +
                            optionalProduct.getMonthlyFee().setScale(2) + "€</span>/month</li>");
                }
                out.print("</ul>");
            }
        %>
        <br>

        <h3 class="title is-2">Total</h3>
        <h3 class="subtitle is-4">
            <%

                BigDecimal servicePackageTotal = pendingValidityPeriod.getMonthlyFee().multiply(BigDecimal.valueOf(pendingValidityPeriod.getMonthsNum()));

                BigDecimal optionalServiceFeesSum = BigDecimal.ZERO;
                if(pendingOptionalProducts != null) {
                    for (OptionalProduct optionalProduct : pendingOptionalProducts) {
                        optionalServiceFeesSum = optionalServiceFeesSum.add(optionalProduct.getMonthlyFee().multiply(BigDecimal.valueOf(pendingValidityPeriod.getMonthsNum())));
                    }
                }

                servicePackageTotal = servicePackageTotal.add(optionalServiceFeesSum);

                out.print(servicePackageTotal.setScale(2) + " €");
            %>
        </h3>

        <c:choose>
            <c:when test="${user == null}">
                <div class="notification is-warning is-light">
                    You are browsing as guest. In order to complete the payment you need to <a href="${pageContext.request.contextPath}/?loginNeeded=1">Log In</a>. After the Log In you will be redirected to this page.
                </div>
            </c:when>
            <c:otherwise>
                <c:if test="${request.getAttribute('RequestType') == 'SESSION_TIMEOUT'}">
                    <div class="notification is-danger is-light">
                        <button class="delete"></button>
                        Session timed out! You need to create another order! <a href="${pageContext.request.contextPath}/packages">Available Packages</a>
                    </div>
                </c:if>
                <form method="post" action="${pageContext.request.contextPath}/confirm">
                    <c:choose>
                        <c:when test='<%= (request.getAttribute("requestType") == RequestType.PENDING_RETRY_POST) %>'>
                            <input type="hidden" name="requestType" value="${RequestType.RETRY_POST}"/>
                            <input type="hidden" name="IDOrder" value='<%= request.getAttribute("IDOrder") %>'/>
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="requestType" value="${RequestType.CONFIRM_POST}"/>
                        </c:otherwise>
                    </c:choose>

                    <button class="button is-primary" type="submit">
                    <span class="icon is-small">
                    <i class="fas fa-shopping-cart"></i>
                    </span>
                        <span>
                        Confirm
                    </span>
                    </button>
                    <label>
                        <input type="checkbox" name="paymentFailure" />
                        Payment Fail
                    </label>
                </form>

            </c:otherwise>
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
