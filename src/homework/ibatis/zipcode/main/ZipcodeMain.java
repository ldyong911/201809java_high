package homework.ibatis.zipcode.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ZipcodeMain extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = FXMLLoader.load(getClass().getResource("/ibatis/zipcode/res/Zipcode.fxml"));
		
		Scene scene = new Scene(root);
		primaryStage.setTitle("우편번호검색");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}