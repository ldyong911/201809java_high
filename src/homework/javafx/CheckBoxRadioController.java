package homework.javafx;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class CheckBoxRadioController implements Initializable{
	@FXML private TextField txt_name;
	@FXML private RadioButton rb_man;
	@FXML private RadioButton rb_woman;
	@FXML private CheckBox chk1;
	@FXML private CheckBox chk2;
	@FXML private CheckBox chk3;
	@FXML private CheckBox chk4;
	@FXML private CheckBox chk5;
	@FXML private CheckBox chk6;
	@FXML private CheckBox chk7;
	@FXML private CheckBox chk8;
	@FXML private Button btnView;
	@FXML private TextArea txtArea;
	
	private String sex="";
	private ObservableList<CheckBox> chkList = FXCollections.observableArrayList();
	private ArrayList<String> hobby = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//리스트에 체크박스 넣기
		chkList.addAll(chk1, chk2, chk3, chk4, chk5, chk6, chk7, chk8);
		
		//라디오버튼 그룹화할 토글그룹 객체 생성
		ToggleGroup group = new ToggleGroup();

		rb_man.setUserData("남자"); // 선택 했을때의 값을 나타내기 위한 데이터 설정
		rb_woman.setUserData("여자");
		rb_man.setToggleGroup(group); // 라디오버튼을 토글그룹에 추가
		rb_woman.setToggleGroup(group);
		
		rb_man.setSelected(true); //초기값 남자 선택되게 세팅
		sex = group.getSelectedToggle().getUserData().toString(); // 초기값 설정
		
		//그룹화된 라디오버튼 중 선택된 것의 이벤트 처리
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				//선택된 항목이 있는지 검사
				if(group.getSelectedToggle().getUserData() != null) {
					sex = group.getSelectedToggle().getUserData().toString();
				}
			}
		});
		
		for(int i=0; i<chkList.size(); i++) {
			final CheckBox cb = chkList.get(i);
			
			cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if(newValue == true) {
						hobby.add(cb.getText());
					}else {
						hobby.remove(cb.getText());
					}
					System.out.println(hobby);
				}
			});
		}
		
		btnView.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String str_hobby="";
				for(int i=0; i<hobby.size(); i++) {
					if(i == 0) {
						str_hobby += hobby.get(i);
					}else {
						str_hobby += ", " + hobby.get(i);
					}
				}
				
				if(str_hobby.equals("")) {
					txtArea.setText(txt_name.getText() + "님은 성별은 " + sex + "이며" + "\n" + "취미는 없습니다.");
				}else {
					txtArea.setText(txt_name.getText() + "님은 성별은 " + sex + "이며 " + "\n" + "취미는 " + str_hobby + "입니다.");
				}
				txt_name.clear(); //이름을 입력하고나서 빈공백으로 세팅
			}
		});
	}
	
}