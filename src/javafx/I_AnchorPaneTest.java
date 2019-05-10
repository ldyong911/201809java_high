package javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class I_AnchorPaneTest extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// 방법2
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AnchorPane.fxml"));
		Parent root2 = loader.load();
		
		Scene scene = new Scene(root2);
		primaryStage.setTitle("AnchorPane연습");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}