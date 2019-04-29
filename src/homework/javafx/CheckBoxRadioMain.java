package homework.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CheckBoxRadioMain extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane root = FXMLLoader.load(getClass().getResource("CheckBoxRadio.fxml"));
		
		Scene scene = new Scene(root);
		primaryStage.setTitle("체크박스 라디오");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}