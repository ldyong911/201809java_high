package homework.javafx;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GradeManageController implements Initializable{
	@FXML TableView<Student> tableView;
	@FXML TableColumn<Student, String> nameCol;
	@FXML TableColumn<Student, String> korCol;
	@FXML TableColumn<Student, String> mathCol;
	@FXML TableColumn<Student, String> engCol;
	@FXML Button btn_ins;
	@FXML Button btn_barChart;
	
	ObservableList<Student> list = FXCollections.observableArrayList(new Student("와나나","100","100","100"));

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//테이블 데이터값 세팅
		tableView.setItems(list);
		
		//테이블 컬럼 매핑
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		korCol.setCellValueFactory(new PropertyValueFactory<>("kor"));
		mathCol.setCellValueFactory(new PropertyValueFactory<>("math"));
		engCol.setCellValueFactory(new PropertyValueFactory<>("eng"));
		
		//테이블컬럼을 선택했을때 파이그래프 띄우기
		tableView.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Student std = tableView.getSelectionModel().getSelectedItem();
				
				//차트에 나타날 데이터 구성하기
				PieChart pieChart = new PieChart();
				ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
						new PieChart.Data(korCol.getText(), Integer.parseInt(std.kor))
				      , new PieChart.Data(mathCol.getText(), Integer.parseInt(std.math))
				      , new PieChart.Data(engCol.getText(), Integer.parseInt(std.eng)));
				
				pieChart.setTitle("파이 점수");
				pieChart.setLabelLineLength(30);
				pieChart.setLegendSide(Side.BOTTOM); // 범례가 나타날 위치 지정
				pieChart.setData(pieChartData); // 데이터 세팅
				
				//새로운창 생성하고 모달창 여부 설정
				Stage childStage = new Stage(StageStyle.UTILITY);
				childStage.initModality(Modality.APPLICATION_MODAL);
				
				//VBox에 파이차트와 닫기버튼 추가
				VBox vbox = new VBox();
				Button btnClose = new Button("닫기");
				btnClose.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						childStage.close();
					}
				});
				vbox.setAlignment(Pos.CENTER); //가운데 정렬
				vbox.getChildren().addAll(pieChart, btnClose);
				
				//새로운창(스테이지)에 Scene 세팅
				Scene scene = new Scene(vbox);
				childStage.setTitle("파이 그래프");
				childStage.setScene(scene);
				childStage.show();
			}
		});
		
		//추가버튼을 눌렀을때 사용자정의 Dialog창 띄우기
		btn_ins.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//1. 새창지우기위해 Stage 객체 생성
				Stage childStage = new Stage(StageStyle.UTILITY);
				
				//2. 모달창 여부 설정
				childStage.initModality(Modality.APPLICATION_MODAL);
				
				//3. 부모창 정의
//				childStage.initOwner(new GradeManageMain().primaryStage);
				
				//4. 자식창에 나타날 컨테이너 객체 생성
				Parent parent = null;
				try {
					parent = FXMLLoader.load(getClass().getResource("GradeManageInsertDialog.fxml"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//부모창에서 FXML로 만든 자식창의 컨트롤 객체 얻기
				TextField txtName = (TextField)parent.lookup("#tf_id");
				TextField txtKor = (TextField)parent.lookup("#tf_kor");
				TextField txtMath = (TextField)parent.lookup("#tf_math");
				TextField txtEng = (TextField)parent.lookup("#tf_eng");
				
				Button btnIns = (Button)parent.lookup("#btn_ins");
				btnIns.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						//빈칸 체크
						if(txtName.getText().isEmpty() || txtKor.getText().isEmpty()
								|| txtMath.getText().isEmpty() || txtEng.getText().isEmpty()) {
							errMsg("오류", "항목에 빈칸이 존재합니다.");
							return;
						}
						
						//과목점수 정수형 체크
						if(!Pattern.matches("^[0-9]+$", txtKor.getText())) {
							errMsg("오류", "과목점수는 정수형으로 입력하세요.");
							txtKor.requestFocus(); //잘못입력한값 포커스주기
							return;
						}else if(!Pattern.matches("^[0-9]+$", txtMath.getText())){
							errMsg("오류", "과목점수는 정수형으로 입력하세요.");
							txtMath.requestFocus();
							return;
						}else if(!Pattern.matches("^[0-9]+$", txtEng.getText())) {
							errMsg("오류", "과목점수는 정수형으로 입력하세요.");
							txtEng.requestFocus();
							return;
						}
						
						//리스트에 추가
						list.add(new Student(txtName.getText(), txtKor.getText()
								, txtMath.getText(), txtEng.getText()));
						
						infoMsg("확인", txtName.getText()+"님 정보가 추가되었습니다.");
						
						childStage.close();
					}
				});
				
				Button btnNo = (Button)parent.lookup("#btn_no");
				btnNo.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						//창 닫기
						childStage.close();
					}
				});
				
				//5. Scene 객체 생성해서 컨테이너 객체 추가하기
				Scene scene = new Scene(parent);

				//6. Stage 객체에 Scene 객체 추가
				childStage.setTitle("추가");
				childStage.setScene(scene);
				childStage.setResizable(false);// 크기고정
				childStage.show();
			}
		});
		
		//학생별 막대그래프 버튼을 눌렀을때 사용자정의 막대그래프창 띄우기
		btn_barChart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// X축, Y축을 이용한 Chart는 해당 축 객체를 생성한다.
				
				// 축의 값이 주로 문자열일 때 사용하는 객체
				CategoryAxis xAxis = new CategoryAxis();
				
				// 축의 값이 숫자일 때 사용하는 객체
				NumberAxis yAxis = new NumberAxis();
				yAxis.setAutoRanging(false);
				yAxis.setUpperBound(100); //Y축 최대값 100으로 제한
				yAxis.setTickUnit(25); //Y축 한눈금당 25씩 증가된값 표현
				
				// 위에서 만든 축 정보를 이용한 BarChart객체 생성
				BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
				
				barChart.setTitle("막대 점수");
				xAxis.setLabel("");
				yAxis.setLabel("");
				
				//BarChart에 나타날 데이터 구성하기
				XYChart.Series<String, Number> ser_kor = new XYChart.Series<>();
				ser_kor.setName(korCol.getText());
				XYChart.Series<String, Number> ser_math = new XYChart.Series<>();
				ser_math.setName(mathCol.getText());
				XYChart.Series<String, Number> ser_eng = new XYChart.Series<>();
				ser_eng.setName(engCol.getText());
				for(int i=0; i<list.size(); i++) {
					ser_kor.getData().add(new XYChart.Data<String, Number>
					(list.get(i).getName(), Integer.parseInt(list.get(i).getKor())));
					
					ser_math.getData().add(new XYChart.Data<String, Number>
					(list.get(i).getName(), Integer.parseInt(list.get(i).getMath())));
					
					ser_eng.getData().add(new XYChart.Data<String, Number>
					(list.get(i).getName(), Integer.parseInt(list.get(i).getEng())));
				}
				
				barChart.getData().addAll(ser_kor, ser_math, ser_eng);
				
				//새로운창 생성하고 모달창여부 설정
				Stage childStage = new Stage(StageStyle.UTILITY);
				childStage.initModality(Modality.APPLICATION_MODAL);
				
				//VBox에 막대차트와 닫기버튼 추가
				VBox vbox = new VBox();
				Button btn_Close = new Button("닫기");
				btn_Close.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						//창닫기
						childStage.close();
					}
				});
				vbox.setAlignment(Pos.CENTER); //가운데정렬
				vbox.getChildren().addAll(barChart, btn_Close);
				
				//새로운창(스테이지)에 Scene 세팅
				Scene scene = new Scene(vbox);
				childStage.setTitle("막대 그래프");
				childStage.setScene(scene);
				childStage.show();
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
	
	public class Student{
		private String name;
		private String kor;
		private String math;
		private String eng;
		
		public Student(String name, String kor, String math, String eng) {
			super();
			this.name = name;
			this.kor = kor;
			this.math = math;
			this.eng = eng;
		}

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getKor() {
			return kor;
		}
		public void setKor(String kor) {
			this.kor = kor;
		}
		public String getMath() {
			return math;
		}
		public void setMath(String math) {
			this.math = math;
		}
		public String getEng() {
			return eng;
		}
		public void setEng(String eng) {
			this.eng = eng;
		}
	}
}