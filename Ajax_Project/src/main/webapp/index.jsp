<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
</head>
<body>
    <h1>Spring에서의 AJAX 사용법</h1>

    <h3>1. 요청 시 값 전달, 응답 결과 받아보기</h3>

    이름: <input type="text" id="name"> <br>
    나이: <input type="number" id="age"> <br>
    <button id="btn1">전송</button>

    <script>
        const btn1 = document.getElementById('btn1');
        const userName = document.getElementById('name');
        const userAge = document.getElementById('age');
        const test1 = () => {
            $.ajax({
                url:"ajax1.do",
                data: { name: userName.value,
                        age: userAge.value },
                success: (res) => console.log(res),
                error: () => console.log('ajax 통신 실패!'),
                

            })
        }

        btn1.addEventListener('click', test1);
    </script>

</body>
</html>