<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="utf-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>MyJSP</title>
</head>
<body>
<form action="AmusementParkServlet" method="get">
    <input type="hidden" name="command" value="forward" />
    Enter path to xml-file:<br/>
    <label>
        <input type="text" name="path" value="" />
    </label><br/>
    <p><b>Check parser:</b></p>
    <p><input name="parser" type="radio" value="sax" checked> SAX</p>
    <p><input name="parser" type="radio" value="stax"> StAX</p>
    <p><input name="parser" type="radio" value="dom"> DOM</p>
    <input type="submit" value="Отправить" /><br/>
</form>
</body>
</html>