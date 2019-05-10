package javafx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class M_ComboGugudanController implements Initializable{
	
	@FXML //fxml 파일에서 해당하는 정보를 가져옴
	private ComboBox<Integer> cmbDan; //fxml 파일에 해당하는 컨트롤러의 fx:id와 일치시켜야함
	@FXML
	private Button btnDan;
	@FXML
	private TextArea txtResult;
	
	//초기화 역할을 담당하는 메서드
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//ObservableList : JavaFX에서 변경이 발생한 시간을 추적할 수 있는 리스너 역할을 하는 List
		//FXCollections : java.util.Collections의 메서드들의 사본들로 매핑되어 static 메서드로 구성된 유틸 클래스
		ObservableList<Integer> list = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9);
		
		//콤보박스에 항목 추가 방법1
		cmbDan.setItems(list);
		//콤보박스에 항목 추가 방법2
		cmbDan.getItems().add(10);
		
/*		//버튼 이벤트 처리 방법1
		btnDan.setOnAction(e->{
			String str = "";
			for(int i=1; i<=9; i++) {
				int dan = cmbDan.getValue(); // 단 가져오기
//				int dan2 = cmbDan.getSelectionModel().getSelectedItem(); //위에줄과 같은 역할을 하는 기능
				str += dan + " * " + i + " = " + (dan*i) +"\n";
			}
			txtResult.setText(str);
//			txtResult.setDisable(true); //읽기만 가능
		});*/
	}
	
	//버튼 이벤트 처리 방법2
	//fxml 파일에 컨트롤러에 해당하는 버튼 메서드 정의
	@FXML
	public void buttonClicked(ActionEvent event) {
		String str = "";
		for(int i=1; i<=9; i++) {
			int dan = cmbDan.getValue();
			str += dan + " * " + i + " = " + (dan*i) +"\n";
		}
		txtResult.setText(str);
	}
}