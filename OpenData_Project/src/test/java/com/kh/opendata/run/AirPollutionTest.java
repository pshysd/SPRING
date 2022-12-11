package com.kh.opendata.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.opendata.model.vo.AirVO;

public class AirPollutionTest {

	public static final String SERVICEKEY = "O4jV5ZsrBr%2Fb1sycvvWPF9189qLChUoSoYWgH5PmWXMFNYXNRPlb9XjB8zrhpQZKvKC2vkIsQemEkYwLwLwUTw%3D%3D";
	
//	공공데이터를 테스트하기 위한 간단한 테스트 환경 구성 -> JUnit

	@Test
	public void testRun() throws IOException { // UnsupportedEncodingException의 부모로 예외처리

//		1. OpenAPI 서버로 요청하고자 하는 url 만들기
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
			   url += "?serviceKey=" + SERVICEKEY;
			   url += "&sidoName=" + URLEncoder.encode("서울", "UTF-8"); // 요청 시 전달 값에 한글이 있을 경우 encoding 해야한다
			   url += "&returnType=json";
//		System.out.println(url);
//		서비스키가 제대로 부여되지 않았을 경우
//		-> SERVICE_KEY_IS_NOT_REGISTERED_ERROR가 발생

//		2. HttpURLConnection 객체를 이용해서 OpenAPI 요청 절차
//		2_1. 요청하고자 하는 url 주소를 전달하면서 java.net.URL 객체 생성
	    URL requestURL = new URL(url);
//	    2_2. 생성된 URL 객체를 가지고 HttpURLConnection 객체 생성
	    HttpURLConnection urlConnection = (HttpURLConnection)requestURL.openConnection();
	    
//	    2_3. 요청에 필요한 Header 설정하기
	    urlConnection.setRequestMethod("GET");
	    
//	    2_4. 해당 OpenAPI 서버로 요청을 보낸 후 스트림을 통해 응답 데이터 읽어들이기
	    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	    
//	    보조스트림은 단독 존재 불가
//	    보조스트림인 BuffredReader는 2바이트 사이즈의 문자스트림
//	    기반스트림인 InputStream은 1바이트 사이즈의 바이트스트림
//	    항상 보조스트림과 기반스트림의 통로의 사이즈가 일치해야함 -> InputStreamReader 객체를 통해 호환시켜줌
	    
//	    2_5. 반복적으로 매번 응답데이터를 한 줄씩 읽어들이기
	    String line; // 기본적으로 null이 담겨있음
	    
//	    응답 데이터를 받아둘 변수 선언 및 초기화
	    String responseText = "";
	    
	    while((line = br.readLine()) != null) {
	    	responseText += line;
	    }
	    
	    
/*
 * {
 * 		"response":
 * 			{
 * 				"body":
 * 					{
 * 						"totalCount":40,
 * 						"items":
 * 							[
 * 								{
 * 									"so2Grade":"1",
 * 									"coFlag":null,
 * 									"khaiValue":"92",
 * 									"so2Value":"0.003",
 * 									"coValue":"0.4",
 * 									"pm10Flag":null,
 * 									"o3Grade":"2",
 * 									"pm10Value":"25",
 * 									"khaiGrade":"2",
 * 									"sidoName":"서울",
 * 									"no2Flag":null,
 * 									"no2Grade":"1",
 * 									"o3Flag":null,
 * 									"so2Flag":null,
 * 									"dataTime":"2022-05-29 14:00",
 * 									"coGrade":"1",
 * 									"no2Value":"0.011",
 * 									"stationName":"중구",
 * 									"pm10Grade":"1",
 * 									"o3Value":"0.080"
 * 								},
 * 								{
 * 									"so2Grade":"1",
 * 									"coFlag":null,
 * 									"khaiValue":"67",
 * 									"so2Value":"0.003",
 * 									"coValue":"0.4",
 * 									"pm10Flag":null,
 *								...
 *								}
 *							],
 *						"pageNo":1,
 *						"numOfRows":10
 *					},
 *				"header":
 *					{
 *						"resultMsg":"NORMAL_CODE",
 *						"resultCode":"00"
 *					}
 *			}
 *	}
*/
	    
//	    생긴건 json 형식이지만 엄밀히 말하면 지금 String 타입의 데이터임
//	    JsonObject, JsonArray, JsonElement를 이용해서 파싱할 수 있음
//	    기존의 흐름: VO에 담긴 내용물을 JSON으로 가공
//	    이번의 흐름: JSON에 담긴 내용물을 VO로 가공
	    
//	    기존의 JSONObject, JSONArray랑은 엄밀히 다른 타입 (헷갈리지말것) -> json.jar 파일에서 제공
//	    JsonObject, JsonArray, JsonElement -> gson.jar 파일에서 제공
	    
//	    * JSON 데이터를 VO로 파싱하기
//	    1) 각각의 item 정보를 얻어내기 위해 문자열 타입인 responseText 변수를 JsonObject 타입으로 변환
//	    JsonObject totalObj = JsonParser.parseString(responseText).getAsJsonObject();
	    
//	    2) totalObj로부터 response라는 키 값에 딸린 객체 출력하기 
//	    JsonObject responseObj = totalObj.getAsJsonObject("response");
	    
//	    3) responseObj로부터 body라는 키값에 딸린 객체 추출하기
//	    JsonObject bodyObj = responseObj.getAsJsonObject("body");
	    
//	    4) bodyObj로부터 totalCount라는 키값에 딸린 데이터 추출하기
//	    int totalCount = bodyObj.get("totalCount").getAsInt();
	    
//	    System.out.println(totalCount);
	    
//	    5) bodyObj로부터 item라는 키값에 딸린 배열 추출하기
//	    JsonArray itemArr = bodyObj.getAsJsonArray("items");
	    
	    JsonArray itemArr = JsonParser.parseString(responseText).getAsJsonObject()
	    					.getAsJsonObject("response")
	    					.getAsJsonObject("body")
	    					.getAsJsonArray("items");
	    
//	    6) itemArr로부터 반복을 돌려가며 AirVO로 가공 후 ArrayList에 담기
	    List<AirVO> list = new ArrayList<>();
	    
	    for(int i=0; i<itemArr.size(); i++) {
	    	
//	    	itemArr의 각 인덱스에 들은 자바스크립트 객체를 추출
	    	JsonObject item = itemArr.get(i).getAsJsonObject();
//	    	System.out.println(item);
	    	
	    	AirVO air = new AirVO();
	    	
	    	air.setStationName(item.get("stationName").getAsString());
	    	air.setDataTime(item.get("dataTime").getAsString());
	    	air.setKhaiValue(item.get("khaiValue").getAsString());
	    	
	    	list.add(air);
	    }
	    
//	    ArrayList에 담긴 내용물을 확인
	    
	    for(AirVO a : list) {
	    	System.out.println(a);
	    }
//	    2_6. 다 사용한 스트림 반납 및 연결 끊기
	    br.close();
	    urlConnection.disconnect();
    }
}
