package com.kh.opendata.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.opendata.model.vo.AirVO;

public class AirPollutionTest {
	
	// 발급받은 인증키 정보를 상수필드에 담아두기 (변할 일이 없기 때문)
	public static final String SERVICEKEY = "Yr16XkfGx9Vy2lEjt4bQy%2BSYthnLjMGP%2BMz3T%2FfsiD%2FTpHcQewhjnP97drW0gBlWKg%2FXGl6AX%2BXRPVz7UKx8ew%3D%3D";
	
	// 공공데이터를 테스트하기 위한 간단한 테스트 환경 구성 => JUnit
	@Test
	public void testRun() throws IOException { // UnsupportedEncodingException 의 부모로 예외처리
		
		// 1. OpenAPI 서버로 요청하고자 하는 url 만들기
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
			   url += "?serviceKey=" + SERVICEKEY;
			   url += "&sidoName=" + URLEncoder.encode("서울", "UTF-8");
			   						 // 요청 시 전달값에 한글이 있을 경우 encoding 해야함
			   url += "&returnType=json";
		
		// System.out.println(url);
		// 서비스키가 제대로 부여되지 않았을 경우
		// => SERVICE_KEY_IS_NOT_REGISTERED_ERROR 가 발생!!
		
		// 2. HttpURLConnection 객체를 이용해서 OpenAPI 요청 절차
		// 2_1. 요청하고자 하는 url 주소를 전달하면서 java.net.URL 객체 생성
		URL requestUrl = new URL(url);
		
		// 2_2. 생성된 URL 객체를 가지고 HttpURLConnection 객체 생성
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
	
		// 2_3. 요청에 필요한 Header 설정하기
		urlConnection.setRequestMethod("GET");
		
		// 2_4. 해당 OpenAPI 서버로 요청을 보낸 후 스트림을 통해 응답데이터 읽어들이기
		BufferedReader br = new BufferedReader( new InputStreamReader( urlConnection.getInputStream() ) );
		// 보조스트림은 단독 존재 불가!
		// 보조스트림인 BufferedReader 는 2바이트 사이즈의 문자스트림
		// 기반스트림인 InputStream 은 1바이트 사이즈의 바이트스트림
		// 항상 보조스트림과 기반스트림의 통로의 사이즈가 일치해야함 => InputStreamReader 객체를 통해 호환시켜줌
		
		// 2_5. 반복적으로 매번 응답데이터를 한줄씩 읽어들이기
		String line; // 기본적으로 null 이 담겨있음
		
		// 응답데이터를 받아 둘 변수 선언 및 초기화
		String responseText = "";
		
		while((line = br.readLine()) != null) {
			
			// System.out.println(line);
			responseText += line;
		}
		
		// System.out.println(responseText);
		
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
		
		// 생긴거는 json 형식이지만 엄밀히 말하면 지금 String 타입의 데이터임!!
		// JsonObject, JsonArray, JsonElement 를 이용해서 "파싱" 할 수 있음
		// 기존의 흐름 : VO 에 담긴 내용물을 JSON 으로 가공
		// 이번의 흐름 : JSON 에 담긴 내용물을 VO 로 가공
		
		// 기존의 JSONObject, JSONArray 랑은 엄밀히 다른 타입 (헷갈리지 말것) => json.jar 파일에서 제공
		// JsonObject, JsonArray, JsonElement => gson.jar 파일에서 제공
		
		// * JSON 데이터를 VO 로 파싱하기
		// 1) 각각의 item 정보를 얻어내기 위해 문자열 타입인 responseText 변수를 JsonObject 타입으로 변환
		JsonObject totalObj = JsonParser.parseString(responseText).getAsJsonObject();
		
		// 2) totalObj 로부터 response 라는 키값에 딸린 객체만 추출하기
		JsonObject responseObj = totalObj.getAsJsonObject("response");
		
		// 3) responseObj 로부터 body 라는 키값에 딸린 객체만 추출하기
		JsonObject bodyObj = responseObj.getAsJsonObject("body");
		
		// 4) bodyObj 로부터 totalCount 라는 키값에 딸린 데이터만 추출하기
		int totalCount = bodyObj.get("totalCount").getAsInt();
		// System.out.println(totalCount);
		
		// 5) bodyObj 로부터 items 라는 키값에 딸린 배열만 추출하기
		JsonArray itemArr = bodyObj.getAsJsonArray("items");
		// System.out.println(itemArr);
		
		// 6) itemArr 로부터 반복을 돌려가며 AirVO 로 가공 후 ArrayList 에 담기
		ArrayList<AirVO> list = new ArrayList<>();
		
		for(int i = 0; i < itemArr.size(); i++) {
			
			// itemArr 의 각 인덱스에 들은 자바스크립트 객체를 또 추출
			JsonObject item = itemArr.get(i).getAsJsonObject();
			// System.out.println(item);
			
			AirVO air = new AirVO();
			air.setStationName(item.get("stationName").getAsString());
			air.setDataTime(item.get("dataTime").getAsString());
			air.setKhaiValue(item.get("khaiValue").getAsString());
			
			// 나머지 필드에 대해서도 가공한다면 모든 정보 파싱 가능!!
			
			list.add(air);
		}
		
		// ArrayList 에 담긴 내용물을 확인
		for(AirVO a : list) {
			System.out.println(a);
		}
		
		// 2_6. 다 사용한 스트림 반납 및 연결 끊기
		br.close();
		urlConnection.disconnect();
		
	}

}





