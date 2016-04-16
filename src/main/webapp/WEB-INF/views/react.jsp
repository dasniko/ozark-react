<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>ReactJS Bookstore with Ozark</title>

    <link href="${mvc.contextPath}/webjars/materializecss/0.97.5/css/materialize.min.css" rel="stylesheet">

    <script src="${mvc.contextPath}/webjars/jquery/2.2.3/jquery.min.js"></script>
    <script src="${mvc.contextPath}/webjars/materializecss/0.97.5/js/materialize.min.js"></script>
</head>

<body>

    <div id="content" class="container">${content}</div>
    <script type="text/javascript" src="${mvc.contextPath}/js/app.js"></script>
    <script type="text/javascript">
        $(function () {
            BookApp.renderClient(${data});
        });
    </script>

</body>
</html>