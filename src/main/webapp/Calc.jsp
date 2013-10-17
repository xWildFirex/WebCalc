<%@ page import="com.my.servlet.Calc" %>
<%--
  Created by IntelliJ IDEA.
  User: korbut-ve
  Date: 19.08.13
  Time: 9:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<html>
<head>
    <title>My Calc</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" >
</head>
<body>
    <br>
    <form method="POST" action="Calc">
        <div class="styled-select">
        <input type="text" name="value" class="inputbox" maxlength="15"
               value="<%if (request.getAttribute("result")!=null || request.getAttribute("message")!=null) {out.print(request.getParameter("value"));}%>">
            <select name="operations">
                <option>Choose operation</option>
                <option <%if (Calc.PLUS_OPERATION.equals(request.getParameter("operations"))) out.print("selected");%> value="plus"> + </option>
                <option <%if (Calc.MINUS_OPERATION.equals(request.getParameter("operations"))) out.print("selected");%> value="minus"> - </option>
                <option <%if (Calc.DIVIDE_OPERATION.equals(request.getParameter("operations"))) out.print("selected");%> value="divide"> / </option>
                <option <%if (Calc.MULTIPLY_OPERATION.equals(request.getParameter("operations"))) out.print("selected");%> value="multiply"> * </option>
                <option <%if (Calc.SQUARE_OPERATION.equals(request.getParameter("operations"))) out.print("selected");%> value="square"> pow </option>
                <option <%if (Calc.ROOT_OPERATION.equals(request.getParameter("operations"))) out.print("selected");%> value="root"> &radic;</option>
            </select>
        <input type="text" name="secvalue" class="inputbox" maxlength="15"
               value="<%if (request.getAttribute("result")!=null || request.getAttribute("message")!=null) {out.print(request.getParameter("secvalue"));}%>">
        <input type="submit" name="enter" value="="class="common-btn-large BlueColor" >
        <input type="text" name="result" class="inputbox" readonly="readonly"
               value="<%if (request.getAttribute("result")!=null) {out.print(request.getAttribute("result"));}%>">
        <br>
        </div>
    </form>
    <br>
    <div class="message">
    <%if (request.getAttribute("message")!=null) {out.print(request.getAttribute("message"));}%>
    </div>
</body>
</html>