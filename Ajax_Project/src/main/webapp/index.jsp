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

    <div id="result1"></div>
    <script>
        const btn1 = document.getElementById('btn1');
        const userName = document.getElementById('name');
        const userAge = document.getElementById('age');
        const test1 = () => {
            $.ajax({
                url:"ajax1.do",
                data: { name: userName.value,
                        age: userAge.value },
                success: (res) => {
                    // console.log(res);

                    // 응답 데이터가 여러 개일 경우 (JSONArray로 보냈을 경우)
                    // $('#result1').text(res);

                    // 응답 데이터가 여러 개일 경우 (JSONArray로 보냈을 경우)
                    // let resultStr = '이름: '+res[0]+ '<br>' +
                                    // '나이: '+res[1]+ '<br>';

                    // $('#result1').html(resultStr);

                    // 응답 데이터가 여러 개일 경우 (JSONObject로 보냈을 경우)
                    let resultStr = '이름: '+res.name+ '<br>' +
                                    '나이: '+res.age+ '<br>';

                    $('#result1').html(resultStr);
                },
                error: () => console.log('ajax 통신 실패!'),
                

            })
        }

        btn1.addEventListener('click', test1);
    </script>

    <hr>

    <h3>2. 조회 요청 후 조회된 한 회원의 객체를 응답 받아서 출력해보기</h3>
    조회할 회원번호: <input type="number" id="userNo">
    <button id="btn">조회</button>
    <br>
    <div id="result2"></div>

    <script>
        $(() => {
            $('#btn').click(() => {
                const userNo = $('#userNo').val();

                $.ajax({
                    url: 'ajax2.do',
                    data: {userNo: userNo},
                    type: 'get',
                    success: (res) => {

                        // console.log(res);

                        let resultStr = '<ul>'
                                            +'<li>이름: ' +res.userName+ "</li>"
                                            +'<li>아이디: ' +res.userId+ "</li>"
                                            +'<li>나이: ' +res.age+ "</li>"
                                            +'<li>휴대폰: ' +res.phone+ "</li>"
                        
                        $('#result2').html(resultStr);
                    },
                    error: () => {
                        console.log('ajax 통신 실패!');
                    },
                });
            })
        })
    </script>
    
    <hr>
    
    <h3>3. 조회요청 후 조회된 회원 리스트를 응답받아서 출력해보기</h3>
    
    <button onclick="test3();">회원 전체조회</button>
    <br><br>
    
    <table border="1" id="result3">
    	<thead>
    		<tr>
    			<th>아이디</th>
    			<th>이름</th>
    			<th>나이</th>
    			<th>전화번호</th>
    		</tr>
    	</thead>
        <tbody></tbody>
    </table>
    
    <script>
        function test3() {
            
            $.ajax({
                url: 'ajax3.do',
                success: (res) => {

                    console.log(res);
                    
                    let resultStr = '';

                    for(let i=0; i<res.length; i++){
                        resultStr += '<tr>'
                                        + '<td>'+res[i].userId+'</td>'
                                        + '<td>'+res[i].userName+'</td>'
                                        + '<td>'+res[i].age+'</td>'
                                        + '<td>'+res[i].phone+'</td>'
                                    +'</tr>';
                    }

                    $('#result3>tbody').html(resultStr);
                },

                error: () => {
                    console.log('ajax 통신 실패');
                }
            });
        }
    </script>
</body>
</html>