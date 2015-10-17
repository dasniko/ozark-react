<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Hello React from Ozark</title>

    <script src="${mvc.contextPath}/webjars/react/0.13.3/react.min.js"></script>
    <script src="${mvc.contextPath}/webjars/showdown/0.3.1/compressed/showdown.js"></script>
    <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>

    <link href="${mvc.contextPath}/webjars/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
    <link href="${mvc.contextPath}/css/app.css" rel="stylesheet">
</head>

<body>

    <div id="content">${content}</div>
    <script type="text/javascript" src="${mvc.contextPath}/js/commentBox.js"></script>
    <script type="text/javascript">
        $(function () {
            renderClient(${data});
        });
    </script>

</body>
</html>