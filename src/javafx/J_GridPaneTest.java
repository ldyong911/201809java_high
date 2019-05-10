package javafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class J_GridPaneTest extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane grid = new GridPane(); // 셀의 크기가 고정적이지 않고 유동적인 패널이며 셀병합으로 다양한 입력폼 만들때 유용한 패널
		grid.setPrefSize(300, 200);
		grid.setPadding(new Insets(10));
		grid.setHgap(10);
		grid.setVgap(20);
		
		Label label1 = new Label("아이디: ");
		Label label2 = new Label("패스워드: ");
		
		TextField textField1 = new TextField();
		// 컨트롤의 글자색, 배경색, 배경이미지 등은 CSS를 이용하여 설정할 수 있다.
		textField1.setStyle("-fx-text-fill:red; -fx-background-color:#ccccff;");
		
		PasswordField passField = new PasswordField();
		
		Button btn1 = new Button("로그인");
		Button btn2 = new Button("취 소");
		
		// GridPane에 컨트롤을 추가하는 방법
		// 객체변수(grid).add(추가할 컨트롤, 열번호, 행번호, colspan수, rowspan수);
		grid.add(label1, 1, 1);
		grid.add(label2, 1, 2);
		grid.add(textField1, 3, 1, 3, 1);
		grid.add(passField, 3, 2, 3, 1);
		grid.add(btn1, 3, 4);
		grid.add(btn2, 4, 4);
		
		grid.setStyle("-fx-background-color:#ccffff");
		
		Scene scene = new Scene(grid);
		primaryStage.setTitle("GridPane 연습");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}