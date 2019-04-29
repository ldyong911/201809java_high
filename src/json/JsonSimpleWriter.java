package json;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonSimpleWriter {
	public static void main(String[] args) throws IOException {
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("name", "홍길동");
		jsonObj.put("job", "대학생");
		jsonObj.put("age", "30");
		jsonObj.put("addr", "대전시 중구 대흥동");
		
		JSONArray singerList = new JSONArray();
		
		singerList.add("이문세");
		singerList.add("젝스키스");
		singerList.add("신승훈");
		
		jsonObj.put("singerList", singerList);
		
		FileWriter fw = new FileWriter("d:/myJsonFile.txt");
		fw.write(jsonObj.toString());
		
		fw.flush();
		fw.close();
		
		System.out.println("JSON객체 내용 출력: " + jsonObj);
	}
}