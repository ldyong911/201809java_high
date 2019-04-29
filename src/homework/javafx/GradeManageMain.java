package homework.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GradeManageMain extends Application{
	Stage primaryStage = null; //부모창 정의할때 필요
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = FXMLLoader.load(getClass().getResource("GradeManage.fxml"));
		
		this.primaryStage = primaryStage;

		Scene scene = new Scene(root);
		primaryStage.setTitle("성적관리");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
