<%--
  Created by IntelliJ IDEA.
  User: home
  Date: 18.12.2019
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <caption>Territory</caption>
    <tr>
        <th align="center" width="100">ID</th>
        <th align="center" width="100">X</th>
        <th align="center" width="100">Y</th>
        <th align="center" width="100">Width</th>
        <th align="center" width="100">Height</th>
    </tr>
    <c:forEach var="territories" items="${territory}" >
        <tr>
            <td>${territory.id}</td>
            <td>${department.x}</td>
            <td>${territory.y}</td>
            <td>${department.width}</td>
            <td>${territory.height}</td>
        </tr>
    </c:forEach>
</table>
<br />
<table>
    <caption>Attractions</caption>
    <tr>
        <th align="center" width="100">ID</th>
        <th align="center" width="100">Name</th>
        <th align="center" width="100">Build price</th>
        <th align="center" width="100">Time to repair</th>
        <th align="center" width="100">Territory ID</th>
        <th align="center" width="100">Type</th>
        <th align="center" width="100">Visitors love</th>
        <th align="center" width="100">Ride time</th>
        <th align="center" width="100">Ticket price</th>
    </tr>
    <c:forEach items="${attractions}" var="attraction">
        <tr>
            <td>${attraction.id}</td>
            <td>${attraction.name}</td>
            <td>${attraction.buildPrice}</td>
            <td>${attraction.timeToRepair}</td>
            <td>${attraction.territoryId}</td>
            <td>${attraction.type}</td>
            <td>${attraction.visitorsLove}</td>
            <td>${attraction.rideTime}</td>
            <td>${attraction.ticketPrice}</td>
        </tr>
    </c:forEach>
</table>
<br />
<table>
    <caption>ServiceBuildings</caption>
    <tr>
        <th align="center" width="100">ID</th>
        <th align="center" width="100">Name</th>
        <th align="center" width="100">Build price</th>
        <th align="center" width="100">Time to repair</th>
        <th align="center" width="100">Territory ID</th>
        <th align="center" width="100">Service</th>
        <th align="center" width="100">Price</th>
    </tr>
    <c:forEach items="${serviceBuildings}" var="serviceBuilding">
        <tr>
            <td>${serviceBuilding.id}</td>
            <td>${serviceBuilding.name}</td>
            <td>${serviceBuilding.buildPrice}</td>
            <td>${serviceBuilding.timeToRepair}</td>
            <td>${serviceBuilding.territoryId}</td>
            <td>${serviceBuilding.service}</td>
            <td>${serviceBuilding.price}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
