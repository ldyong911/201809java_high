package javafx;

import java.util.Optional;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class L_AlertTest extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		Alert alertInformation = new Alert(AlertType.INFORMATION);
		alertInformation.setTitle("INFORMATION");
		alertInformation.setContentText("INFORMATION Aler창 입니다.");
		alertInformation.showAndWait(); // Alert창 보이기
		
		//=========================================================
		
		Alert alertError = new Alert(AlertType.ERROR);
		alertError.setTitle("ERROR");
		alertError.setContentText("ERROR Alert창 입니다.");
		alertError.showAndWait();
		
		//=========================================================
		
		Alert alertWarning = new Alert(AlertType.WARNING);
		alertWarning.setTitle("WARNING");
		alertWarning.setContentText("WARNING Alert창 입니다.");
		alertWarning.showAndWait();
		
		//=========================================================
		
		Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
		alertWarning.setTitle("CONFIRMATION");
		alertWarning.setContentText("CONFIRMATION Alert창 입니다.");
		alertWarning.showAndWait();

		//=========================================================
		
		// Alert창을 보여주고 사용자가 누른 버튼 값 읽어오기
		ButtonType comfirmResult = alertConfirm.showAndWait().get();
		
		if(comfirmResult == ButtonType.OK) {
			System.out.println("OK 버튼을 눌렀습니다.");
		}else if(comfirmResult == ButtonType.CANCEL) {
			System.out.println("취소 버튼을 눌렀습니다.");
		}
		
		//=========================================================
		
		// Javascript의 prompt창과 같은 기능
		// '기본값'은 생략 가능
		TextInputDialog javaPrompt = new TextInputDialog("기본값"); // 텍스트필드에 "" 안에 값이 들어감
		
		javaPrompt.setTitle("PROMPT창"); // 창 제목
		javaPrompt.setHeaderText("TextInputDialog창 입니다."); // 출력메시지
		
		// 창을 보이고 입력한 값을 읽어오기
		Optional<String> result = javaPrompt.showAndWait();
		String strResult = null; // 입력한 값이 저장될 변수 선언
		
		// 입력한 값이 있는지 검사(값 입력후 'OK'버튼 눌렀는지 검사)
		if(result.isPresent()) {
			strResult = result.get(); // 값 가져오기
		}
		System.out.println("읽어온 값: " + strResult);
	}
}