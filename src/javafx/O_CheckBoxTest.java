package javafx;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class O_CheckBoxTest extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Rectangle rect = new Rectangle(90, 30); // 4각형 그리기
		rect.setArcHeight(10); // 둥근 4학형 만들기 위한 꼭지점 모따기
		rect.setArcWidth(10);
		rect.setFill(Color.rgb(41, 41, 41)); // 4각형 내부 색칠하기
		
		String[] names = new String[] {"Security", "Project", "Chart"};
		Image[] images = new Image[names.length];
		ImageView[] icons = new ImageView[names.length];
		
		CheckBox[] chkBoxs = new CheckBox[names.length];
		
		// JavaFX에서 Image를 사용하려면 ImageView 컨트롤에 Image객체를 붙여야함
		for(int i=0; i<names.length; i++) {
			// 출력할 이미지 읽어오기
			images[i] = new Image(getClass().getResourceAsStream("images/" + names[i] + ".png"));
			final Image img = images[i];
//			final Image img = images[i] = new Image(getClass().getResourceAsStream("images/" + names[i] + ".png")); //위에 두줄과 같은 역할(1.8버전부터 새로생긴 문법)

			// 이미지를 출력하는 객체 생성
			icons[i] = new ImageView();
			final ImageView icon = icons[i];
//			final ImageView icon = icons[i] = new ImageView(); //위에 두줄과 같은 역할(1.8버전부터 새로생긴 문법)
			chkBoxs[i] = new CheckBox(names[i]);
			final CheckBox cb = chkBoxs[i];
//			final CheckBox cb = chkBoxs[i] = new CheckBox(names[i]); //위에 두줄과 같은 역할(1.8버전부터 새로생긴 문법)
			
			// CheckBox를 클릭해서 값이 변경되었을 때의 이벤트 처리
			cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if(newValue == true) {
						icon.setImage(img);
					}else {
						// ImageView에서 이미지삭제
						icon.setImage(null);
					}
				}
			});
			
//			// CheckBox를 클릭해서 값이 변경되었을 때의 이벤트 처리(람다식 이용)
//			cb.selectedProperty().addListener( (observable,oldValue,newValue) -> {
//					if(newValue == true) {
//						icon.setImage(img);
//					}else {
//						icon.setImage(null);
//					}
//				}
//			);
		}
			
		Button btnOk = new Button("확인");
		
		btnOk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(chkBoxs[1].isSelected() == true) {
					showInfo(chkBoxs[1].getText() + "체크");
				}else {
					showInfo(chkBoxs[1].getText() + "체크 해제");
				}
				
				// CheckBox의 check여부 셋팅하기
				chkBoxs[0].setSelected(!chkBoxs[1].isSelected());
			}
		});
		
		//람다식 이용
//		btnOk.setOnAction((event) -> {
//				if(chkBoxs[1].isSelected() == true) {
//					showInfo(chkBoxs[1].getText() + "체크");
//				}else {
//					showInfo(chkBoxs[1].getText() + "체크 해제");
//				}
//				
//				chkBoxs[0].setSelected(!chkBoxs[1].isSelected());
//			}
//		);
		
		VBox vbox = new VBox(5);
		vbox.getChildren().addAll(chkBoxs);
		vbox.getChildren().add(btnOk);
		
		HBox hbox = new HBox();
		hbox.getChildren().addAll(icons);
		hbox.setPadding(new Insets(0, 0, 5, 5));
		
		// StackPane은 컨트롤들을 쌓아놓는 방식으로 배치하는 컨테이너이다.
		StackPane stack = new StackPane();
		stack.getChildren().addAll(rect, hbox);
		StackPane.setAlignment(rect, Pos.TOP_CENTER);
		
		HBox root = new HBox();
		root.setSpacing(40);
		root.setPadding(new Insets(20, 10, 10, 20));
		root.getChildren().addAll(vbox, stack);
		
		Scene scene = new Scene(root);
		primaryStage.setTitle("CheckBox 연습");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void showInfo(String msg) {
		Alert alertInfomation = new Alert(Alert.AlertType.INFORMATION);
		
		alertInfomation.setTitle("INFORMATION");
		alertInfomation.setContentText(msg);
		alertInfomation.showAndWait();
	}
}