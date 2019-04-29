package homework.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class MemberMVCController implements Initializable{
	@FXML TextField tf_id;
	@FXML TextField tf_name;
	@FXML TextField tf_tel;
	@FXML TextField tf_addr;
	@FXML Button btn_ins;
	@FXML Button btn_upd;
	@FXML Button btn_del;
	@FXML Button btn_ok;
	@FXML Button btn_no;
	@FXML TableView<Member> tableView;
	@FXML TableColumn<Member, String> idCol;
	@FXML TableColumn<Member, String> nameCol;
	@FXML TableColumn<Member, String> telCol;
	@FXML TableColumn<Member, String> addrCol;
	
	ObservableList<Member> list = FXCollections.observableArrayList(new Member("1", "와나나", "01012345678", "대전 중구 대흥동"));

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//테이블 데이터값 세팅
		tableView.setItems(list);
		
		//테이블 컬럼 매핑
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		telCol.setCellValueFactory(new PropertyValueFactory<>("tel"));
		addrCol.setCellValueFactory(new PropertyValueFactory<>("addr"));
		
		//테이블컬럼을 선택했을때 텍스트필드 세팅
		tableView.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Member mem = tableView.getSelectionModel().getSelectedItem();
				
				tf_id.setText(mem.getId());
				tf_name.setText(mem.getName());
				tf_tel.setText(mem.getTel());
				tf_addr.setText(mem.getAddr());
			}
		});
		
		//확인, 취소 비활성화 세팅
		btn_ok.setDisable(true);
		btn_no.setDisable(true);
		
		//추가버튼
		btn_ins.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//추가버튼을 누르면 수정,삭제 비활성화
				btn_upd.setDisable(true);
				btn_del.setDisable(true);
				
				//확인, 취소 활성화
				btn_ok.setDisable(false);
				btn_no.setDisable(false);
			}
		});
		
		//수정버튼
		btn_upd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//수정버튼을 누르면 추가, 삭제 비활성화
				btn_ins.setDisable(true);
				btn_del.setDisable(true);
				
				//확인, 취소 활성화
				btn_ok.setDisable(false);
				btn_no.setDisable(false);
			}
		});
		
		//삭제 기능
		btn_del.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//삭제버튼을 누르면 추가, 수정 비활성화
				btn_ins.setDisable(true);
				btn_upd.setDisable(true);
				
				//확인, 취소 활성화
				btn_ok.setDisable(false);
				btn_no.setDisable(false);
			}
		});
		
		//확인버튼
		btn_ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//추가버튼이 활성화되어있는지 체크
				if(btn_ins.isDisable() == false) {
					//항목값에 빈칸체크
					if(tf_id.getText().isEmpty() || tf_name.getText().isEmpty()
							|| tf_tel.getText().isEmpty() || tf_addr.getText().isEmpty()) {
						errMsg("오류", "항목에 빈칸이 존재합니다.");
						return;
					}
					
					//리스트에 넣기
					list.add(new Member(tf_id.getText(), tf_name.getText(), tf_tel.getText(), tf_addr.getText()));
					
					infoMsg("확인", tf_name.getText()+"님 정보를 추가했습니다.");
				}else if(btn_upd.isDisable() == false) {
					//항목값에 빈칸체크
					if(tf_id.getText().isEmpty() || tf_name.getText().isEmpty()
							|| tf_tel.getText().isEmpty() || tf_addr.getText().isEmpty()) {
						errMsg("오류", "항목에 빈칸이 존재합니다.");
						return;
					}
					
					//선택한 항목 불러와서 수정
					list.set(tableView.getSelectionModel().getSelectedIndex()
							, new Member(tf_id.getText(), tf_name.getText(), tf_tel.getText(), tf_addr.getText()));
					
					infoMsg("확인", tf_name.getText()+"님 정보가 수정되었습니다.");
				}else if(btn_del.isDisable() == false) {
					//선택한 자료가 있는지 체크
					if(tableView.getSelectionModel().isEmpty()) {
						errMsg("오류", "선택한 자료가 없습니다.");
						return;
					}
					
					//선택한 항목 리스트에서 삭제
					list.remove(tableView.getSelectionModel().getSelectedIndex());
					
					infoMsg("확인", tf_name.getText()+"님 정보가 삭제되었습니다.");
				}
				
				//빈칸으로 세팅
				tf_id.clear();
				tf_name.clear();
				tf_tel.clear();
				tf_addr.clear();
				
				//확인버튼을 누르면 추가, 수정, 삭제 활성화
				btn_ins.setDisable(false);
				btn_upd.setDisable(false);
				btn_del.setDisable(false);
				
				//확인, 취소 비활성화
				btn_ok.setDisable(true);
				btn_no.setDisable(true);
			}
		});
		
		//취소버튼
		btn_no.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//빈칸으로 세팅
				tf_id.clear();
				tf_name.clear();
				tf_tel.clear();
				tf_addr.clear();
				
				//취소버튼을 누르면 추가, 수정, 삭제 활성화
				btn_ins.setDisable(false);
				btn_upd.setDisable(false);
				btn_del.setDisable(false);
				
				//확인, 취소 비활성화
				btn_ok.setDisable(true);
				btn_no.setDisable(true);
			}
		});
	}
	
	//에러메세지창
	public void errMsg(String headerText, String msg) {
		Alert errAlert = new Alert(AlertType.ERROR);
		errAlert.setHeaderText(headerText);
		errAlert.setContentText(msg);
		errAlert.showAndWait();
	}
	
	//확인메세지창
	public void infoMsg(String headerText, String msg) {
		Alert infoAlert = new Alert(AlertType.INFORMATION);
		infoAlert.setHeaderText(headerText);
		infoAlert.setContentText(msg);
		infoAlert.showAndWait();
	}
	
	public class Member {
		private String id;
		private String name;
		private String tel;
		private String addr;
		
		public Member(String id, String name, String tel, String addr) {
			super();
			this.id = id;
			this.name = name;
			this.tel = tel;
			this.addr = addr;
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		public String getAddr() {
			return addr;
		}
		public void setAddr(String addr) {
			this.addr = addr;
		}
	}
}