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
    <table>
        <tr>
            <td>Id</td>
            <td>ФИО</td>
            <td>Email</td>
        </tr>
    <#list users as user>
        <tr>
            <td>${user.id}</td>
            <td>${user.fio}</td>
            <td>${user.email}</td>
        </tr>

    </#list>
    </table>
</body>
</html>