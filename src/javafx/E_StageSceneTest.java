package javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class E_StageSceneTest extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// 방법2
		FXMLLoader loader = new FXMLLoader(getClass().getResource("StageScene.fxml"));
		Parent root2 = loader.load();
		
		Scene scene = new Scene(root2);
		primaryStage.setTitle("Stage와 Scene연습"); // 창 제목
		primaryStage.setScene(scene); // Stage에 Scene추가
		primaryStage.show(); // 창(Stage) 보이기
	}
}