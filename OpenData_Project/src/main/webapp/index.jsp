<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
</head>
<body>

	<h2>실시간 대기오염 정보</h2>
	
	지역: 
	<select id="location">
		<option>서울</option>
		<option>부산</option>
		<option>대전</option>
		<option>제주</option>
	</select>
	
	<button id="btn1">해당 지역 대기오염 정보 보기</button>
	
	<br><br>
	
	<table id="result1" border="1" align="center">
		<thead>
			<tr>
				<th>측정소명</th>
				<th>측정일시</th>
				<th>통합대기환경수치</th>
				<th>미세먼지농도</th>
				<th>아황산가스농도</th>
				<th>일산화탄소농도</th>
				<th>이산화질소농도</th>
				<th>오존농도</th>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
	</table>
	
	<script>
		$(() => {
			$('#btn1').click(() => {
				
				// 응답 데이터를 JSON 형식으로 리턴해줄 경우
				/*
				$.ajax({
					url: 'air.do',
					data: {location: $('#location').val()},
					type: 'post',
					success: (res) => {
						
						const items = res.response.body.items
						
						let resultStr = '';
						
						items.forEach((item) => {
							
							resultStr += '<tr>'
										+ '<td>' + item.stationName + '</td>'
										+ '<td>' + item.dataTime + '</td>'
										+ '<td>' + item.khaiValue + '</td>'
										+ '<td>' + item.pm10Value + '</td>'
										+ '<td>' + item.so2Value + '</td>'
										+ '<td>' + item.coValue + '</td>'
										+ '<td>' + item.no2Value + '</td>'
										+ '<td>' + item.o3Value + '</td>'
									   + '</tr>';
						});

						$('#result1>tbody').html(resultStr)
					},
					error: () => {
						console.log('ajax로 대기오염 정보 불러오기 실패 !!');
					}
				});
				*/
				
				// 응답 데이터를 XML 형식으로 돌려받았을 경우
				$.ajax({
					url: 'air.do',
					type: 'get',
					data: {location: $('#location').val()},
					success: res => {
						// console.log(res);
						const items = res.querySelectorAll('item');
						const tbody = document.querySelector('#result1 > tbody');						
						let resultStr = '';

						items.forEach((item) => {
							resultStr += '<tr>'
									   + '<td>' + item.querySelector('stationName').textContent + '</td>'
									   + '<td>' + item.querySelector('dataTime').textContent + '</td>'
									   + '<td>' + item.querySelector('khaiValue').textContent + '</td>'
									   + '<td>' + item.querySelector('pm10Value').textContent + '</td>'
									   + '<td>' + item.querySelector('so2Value').textContent + '</td>'
									   + '<td>' + item.querySelector('coValue').textContent + '</td>'
									   + '<td>' + item.querySelector('no2Value').textContent + '</td>'
									   + '<td>' + item.querySelector('o3Value').textContent + '</td>'
									   + '</tr>';
						})
						tbody.innerHTML = resultStr;
					},
					error: () => console.log('ajax로 대기오염정보 불러오기 실패!')
				});
			});

			/*
				* JavaScript 문법
			 */
		})
	</script>
</body>
</html>