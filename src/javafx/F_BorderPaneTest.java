package javafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class F_BorderPaneTest extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane(); // top, left, right, bottom 영역에 배치하지 않으면 center영역이 확장되어 빈공백이 없어지는 패널 
		root.setPrefSize(300, 200); // width와 height를 한번에 설정
		
		ToolBar toolBar = new ToolBar(new Button("첫번째버튼"), new Button("두번째버튼"));
		
		TextArea txtArea = new TextArea(); // 여러줄로 입력가능
		
		root.setTop(toolBar); // Top영역에 ToolBar 추가하기
		root.setCenter(txtArea); // Center영역에 TextArea 추가하기
		
		BorderPane bottom = new BorderPane();
		bottom.setPadding(new Insets(10));
		
		TextField txtField = new TextField();
		Button btn1 = new Button("확인");
		bottom.setCenter(txtField);
		bottom.setRight(btn1);
		
		root.setBottom(bottom);
		
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("BorderPane 연습");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}