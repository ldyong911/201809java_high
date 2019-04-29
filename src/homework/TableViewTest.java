package homework;

import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TableViewTest extends Application{
	private Pagination pagination = new Pagination();
	private int from, to, itemsPerPage;
	
	private ObservableList<Member> data, currentPageData;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TableView에 나타낼 데이터 구성하기
		data = FXCollections.observableArrayList(new Member("강현욱", "KANG HYUNWOOK", 29, "1111-1111", "대전")
											   , new Member("김나나", "KIM DOHYUN", 27, "2222-2222", "서울")
						  					   , new Member("김현지", "KIM HYUNJI", 32, "3333-3333", "부산")
						 					   , new Member("박경훈", "PARK KYUNGHUN", 31, "4444-4444", "광주"));
		
		BorderPane root = new BorderPane();
		
		// TableView에 데이터를 셋팅하기 => 방법1(TableView의 생성자 이용)
		TableView<Member> table = new TableView<>(data);
		
		// 해당 컬럼에 나타날 데이터 연결하기
		// (출력할 객체의 멤버변수와 출력할 컬럼을 매칭시킨다.)
		TableColumn<Member, String> nameCol = new TableColumn<>("이 름");
		
		TableColumn<Member, String> korNameCol = new TableColumn<>("한글이름");
		korNameCol.setCellValueFactory(new PropertyValueFactory<Member, String>("korName"));
		TableColumn<Member, String> engNameCol = new TableColumn<>("영어이름");
		engNameCol.setCellValueFactory(new PropertyValueFactory<Member, String>("engName"));
		
		nameCol.getColumns().addAll(korNameCol, engNameCol);
		
		TableColumn<Member, Integer> ageCol = new TableColumn<>("나 이");
		ageCol.setCellValueFactory(new PropertyValueFactory<Member, Integer>("age"));
		TableColumn<Member, String> telCol = new TableColumn<>("전화번호");
		telCol.setCellValueFactory(new PropertyValueFactory<Member, String>("tel"));
		TableColumn<Member, String> addrCol = new TableColumn<>("주 소");
		addrCol.setCellValueFactory(new PropertyValueFactory<Member, String>("addr"));
		
		// 생성된 각 컬럼들을 TableView에 추가한다.
		table.getColumns().addAll(nameCol, ageCol, telCol, addrCol);
		
		// TableView에 데이터를 셋팅하기 => 방법2(setItems()메서드 이용)
//		table.setItems(data);
		
		itemsPerPage = 1; // 한페이지 보여줄 항목 수 설정
		int totPageCount = data.size()%itemsPerPage == 0 ? data.size()/itemsPerPage : data.size()/itemsPerPage + 1;
		
		System.out.println(totPageCount);
		pagination.setPageCount(totPageCount); // 전체 페이지 수 설정
		pagination.setPageFactory(new Callback<Integer, Node>() {
			@Override
			public Node call(Integer pageIndex) {
				from = pageIndex * itemsPerPage;
				to = from + itemsPerPage - 1;
				table.setItems(getTableViewData(from, to));
				
				return table;
			}
		});
		
		pagination.setLayoutX(-1);
		pagination.setLayoutY(-3);
		
		//================================================================================
		
		GridPane grid = new GridPane();
		Text txt1 = new Text("한글이름");
		Text txt2 = new Text("영어이름");
		Text txt3 = new Text("나    이");
		Text txt4 = new Text("전화번호");
		Text txt5 = new Text("주    소");
		
		TextField txtKorName = new TextField();
		TextField txtEngName = new TextField();
		TextField txtAge = new TextField();
		TextField txtTel = new TextField();
		TextField txtAddr = new TextField();
		
		grid.add(txt1, 1, 1);
		grid.add(txt2, 2, 1);
		grid.add(txt3, 3, 1);
		grid.add(txt4, 4, 1);
		grid.add(txt5, 5, 1);
		
		grid.add(txtKorName, 1, 2);
		grid.add(txtEngName, 2, 2);
		grid.add(txtAge, 3, 2);
		grid.add(txtTel, 4, 2);
		grid.add(txtAddr, 5, 2);
		
		grid.setVgap(5);
		grid.setHgap(10);
		grid.setPadding(new Insets(10, 0, 10, 0));
		
		//================================================================================
		
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(10));
		
		Button btnAdd = new Button("추가");
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(txtKorName.getText().isEmpty() || txtEngName.getText().isEmpty() 
						|| txtAge.getText().isEmpty() || txtTel.getText().isEmpty() || txtAddr.getText().isEmpty()) {
					errMsg("작업오류", "빈 항목이 있습니다.");
					return;
				}
				
				if(!Pattern.matches("^[0-9]+$", txtAge.getText())) {
					errMsg("데이터 오류", "나이는 정수형으로 입력하세요.");
					txtAge.requestFocus(); // 해당객체에 Focus 주기
					return;
				}
				
				data.add(new Member(txtKorName.getText(), txtEngName.getText()
						          , Integer.parseInt(txtAge.getText()), txtTel.getText(), txtAddr.getText()));
				
				infoMsg("작업결과", txtKorName.getText() + "님 정보를 추가했습니다.");
				
				txtKorName.clear();
				txtEngName.clear();
				txtAge.clear();
				txtTel.clear();
				txtAddr.clear();
			}
		});
		
		Button btnEdit = new Button("수정");
		btnEdit.setOnAction(event -> { // 람다식
			if (txtKorName.getText().isEmpty() || txtEngName.getText().isEmpty()
					|| txtAge.getText().isEmpty() || txtTel.getText().isEmpty() || txtAddr.getText().isEmpty()) {
				errMsg("작업 오류", "빈 항목이 있습니다.");
				return;
			}

			if (!Pattern.matches("^[0-9]+$", txtAge.getText())) {
				errMsg("데이터 오류", "나이는 정수형으로 입력하세요");
				txtAge.requestFocus(); // 해당 객체에 Focus주기
				return;
			}
			
			data.set(table.getSelectionModel().getSelectedIndex(), // 선택한값을 가져옴
					new Member(txtKorName.getText(), txtEngName.getText(),
							Integer.parseInt(txtAge.getText()), txtTel.getText(), txtAddr.getText()));

			infoMsg("작업결과", txtKorName.getText() + "님 정보를 수정했습니다.");

			txtKorName.clear(); // 다시 초기화
			txtEngName.clear();
			txtAge.clear();
			txtTel.clear();
			txtAddr.clear();
		});
		
		Button btnDel = new Button("삭제");
		btnDel.setOnAction(event -> { // 람다식
			if (table.getSelectionModel().isEmpty()) {
				errMsg("작업 오류", "삭제할 자료를 선택한 후 삭제하세요.");
				// table view 만들고 table view 타입 만들어야함
				// 1컨트롤 객체 -> 어떤 데이터를 다룰지 타입설정 -> 테이블 컬럼 생성 -> .멤버객체.setCellValueFactory(
				// new PropertyValueFactory(갖고올 멤버변수명)
				// getColumns나 getChildren getItems은 ObservableList에 담을수있게 값을 리턴해준다.
				return;
			}

			data.remove(table.getSelectionModel().getSelectedIndex());

			infoMsg("작업결과", txtKorName.getText() + "님 정보를 삭제했습니다.");// 정상적인 추가 안내메세지 에러랑은 틀림

			txtKorName.clear(); // 입력초기화
			txtEngName.clear();
			txtAge.clear();
			txtTel.clear();
			txtAddr.clear();
		});

		Button btnTest1 = new Button("속성 연습 1(수정불가, 객체비활성화)");
		btnTest1.setOnAction(e -> { // 람다식
			// TextField, TextArea등 문자를 입력하는 객체를 ReadOnly 설정하는 메서드 => setEditable()
			// 이 메서드에 true를 설정하면 '입력가능' false를 설정하면 '읽기전용'이 된다.
			txtKorName.setEditable(false);// 수정불가
			txtEngName.setEditable(false);

			// 객체를 활성화 또는 비활성화 시키는 메서드 => setDisabled()
			// 이 메서드에 true를 설정하면 '비활성화' false를 설정하면 '활성화' 된다.
			btnAdd.setDisable(true); // 비활성화
			btnEdit.setDisable(true);

			txtKorName.setPromptText("한글이름입력"); // 입력상자에 흐릿하게 나타내는 메시지
			txtAddr.requestFocus(); // 포커스 주기
		});

		Button btnTest2 = new Button("속성 연습 2(수정가능, 객체활성화)");
		btnTest2.setOnAction(e -> { // 람다식
			txtKorName.setEditable(true); // 수정가능
			txtEngName.setEditable(true);

			btnAdd.setDisable(false); // 활성화
			btnEdit.setDisable(false);
		});

		//================================================================================
		
		// TableView를 클릭했을 때 처리
		table.setOnMouseClicked(new EventHandler<Event>() { // 마우스를 클릭해야만 이벤트 발생하게 해줌
			@Override
			public void handle(Event event) {
				// TableView에서 선택한 줄의 데이터를 얻는다.
				Member mem = table.getSelectionModel().getSelectedItem();

				txtKorName.setText(mem.getKorName());
				txtEngName.setText(mem.getEngName());
				// txtAge.setText("" + mem.getAge());
				txtAge.setText(String.valueOf(mem.getAge()));
				txtTel.setText(mem.getTel());
				txtAddr.setText(mem.getAddr());
			}
		});

		vbox.getChildren().addAll(btnAdd, btnEdit, btnDel, btnTest1, btnTest2);
		
		root.setTop(table);
		root.setCenter(pagination);
		root.setRight(vbox);
		root.setBottom(grid);
		root.setPadding(new Insets(10));

		Scene scene = new Scene(root, 800, 400);
		primaryStage.setTitle("TableView 연습");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private ObservableList<Member> getTableViewData(int from, int to){
		currentPageData = FXCollections.observableArrayList(); // 현재페이지 데이터 초기화
		int totSize = data.size();
		for(int i = from; i <= to && i <totSize; i++){
			currentPageData.add(data.get(i));
		}
		return currentPageData;
	}
	
	public void errMsg(String headerText, String msg) {
		Alert errAlert = new Alert(AlertType.ERROR);
		errAlert.setTitle("오류");
		errAlert.setHeaderText(headerText);
		errAlert.setContentText(msg);
		errAlert.showAndWait();
	}
	
	public void infoMsg(String headerText, String msg) {
		Alert infoAlert = new Alert(AlertType.INFORMATION);
		infoAlert.setTitle("정보 확인");
		infoAlert.setHeaderText(headerText);
		infoAlert.setContentText(msg);
		infoAlert.showAndWait();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public class Member{
		private String korName; // 한글이름
		private String engName; // 영문이름
		private int age; // 나이
		private String tel; // 전화번호
		private String addr; // 주소
		
		public Member(String korName, String engName, int age, String tel, String addr) {
			super();
			this.korName = korName;
			this.engName = engName;
			this.age = age;
			this.tel = tel;
			this.addr = addr;
		}

		// JavaFX에서 setter은 public 제어자를 사용 가능하지만 getter은 public 제어자를 사용하지 못함
		// getter와 setter에 public을 사용하려면 클래스가 내부에 존재하며 클래스 제어자 역시 public이어야함
		public String getKorName() {
			return korName;
		}
		public void setKorName(String korName) {
			this.korName = korName;
		}
		public String getEngName() {
			return engName;
		}
		public void setEngName(String engName) {
			this.engName = engName;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
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