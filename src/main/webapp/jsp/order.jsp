<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.home" var="title"/>
<fmt:message key="message.welcom" var="welcom"/>
<fmt:message key="message.hotel_info" var="hotel_info"/>

<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
    <title>
        ${title}
    </title>
</head>
<header>
    <jsp:include page="header.jsp"/>
</header>
<body>
<div class="container text-secondary">
    <div class="row">
        <div class="col mb-3">
            Order
        </div>
    </div>
    <div class="row border-secondary">
        <div class="col mb-3 border-secondary">
            Room number: ${temp_room_number} (id ${temp_room_id})
        </div>
    </div>
    <div class="row border-secondary">
        <div class="col mb-3 border-secondary">
            Check in: ${temp_date_from}
        </div>
    </div>
    <div class="row border-secondary">
        <div class="col mb-3">
            Check out: ${temp_date_to}
        </div>
    </div>
    <div class="row border-secondary">
        <div class="col mb-3">
            Days: ${temp_days}
        </div>
    </div>
    <div class="row border-secondary">
        <div class="col mb-3">
            Price per day: ${temp_room_price}
        </div>
    </div>
    <div class="row border-secondary">
        <div class="col mb-3">
            Base amount: ${temp_base_amount}
        </div>
    </div>
    <div class="row border-secondary">
        <div class="col mb-3">
            Total amount: ${temp_total_amount} (discont ${temp_base_amount-temp_total_amount})
        </div>
    </div>

    <form method="post" action="${path}/controller">
        <input type="hidden" name="command" value="create_order"/>
        <input type="hidden" name="date_from" value="${temp_date_from}"/>
        <input type="hidden" name="date_to" value="${temp_date_to}"/>
        <input type="hidden" name="room_id" value="${temp_room_id}"/>
        <input type="hidden" name="total_amount" value="${temp_total_amount}"/>
        <input type="hidden" name="days" value="${temp_days}"/>

        <div class="row border-secondary">
            <div class="col mb-3">
                <div class="form-check form-check-inline">
                    <input type="checkbox" class="form-check-input " name="prepayment" value="true"/>
                    <label class="form-check-label">Prepayment</label>
                </div>
            </div>
        </div>
        <button type="submit" class="btn btn-secondary">
            confirm
        </button>
    </form>

</div>
<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>
