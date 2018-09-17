<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <h1>Добро пожаловать!</h1>
    <div id="app"></div>
    <#if isDevMode>
        <script src="http://localhost:8000/build.js"></script>
    <#else>
        <script src="/js/build.js"></script>
    </#if>
</body>
</html>