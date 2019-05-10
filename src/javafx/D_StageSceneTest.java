package javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class D_StageSceneTest extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox root = new VBox(); // 컨트롤을 세로로 배치해 주는 컨테이너
		root.setPrefWidth(650); // VBox의 너비
		root.setPrefHeight(150); // VBox의 높이
		root.setAlignment(Pos.CENTER); // 컨트롤들을 가운데 정렬
		root.setSpacing(20); // 컨트롤들과 컨트롤 사이의 간격
		
		Label label = new Label(); // Label객체 생성
		label.setText("안녕하세요. JavaFX입니다.");
		label.setFont(new Font(50)); // Font객체를 이요아여 글자크기 설정
		
		Button button = new Button();
		button.setText("확 인");
		
		// 버튼에 대한 클릭 이벤트 처리
		// 방법1 -> 람다식을 사용하지 않은 경우
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// 처리할 내용을 기술하는 영역
				Platform.exit();
			}
		});
		
		// 방법2 -> 람다식을 사용한 처리
		button.setOnAction(event->Platform.exit());
		
		// VBox에 컨트롤을 추가하기
		root.getChildren().add(label); //컨테이너에 컨트롤들을 넣을때 getChiledren().add(컨트롤)로 추가함
		root.getChildren().add(button);
		
		// VBox를 루트컨테이너로 하는 Scene객체 생성
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("Stage와 Scene연습"); // 창 제목
		primaryStage.setScene(scene); // Stage에 Scene추가
		primaryStage.show(); // 창(Stage) 보이기
	}
}