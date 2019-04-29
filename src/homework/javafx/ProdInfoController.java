package homework.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import homework.javafx.ProdInfoJdbc.Lprod;
import homework.javafx.ProdInfoJdbc.Prod;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProdInfoController implements Initializable{
	@FXML ComboBox<String> cmb_lprod;
	@FXML ComboBox<String> cmb_prod;
	@FXML TableView<Prod> tableView;
	@FXML TableColumn<Prod, String> idCol;
	@FXML TableColumn<Prod, String> nameCol;
	@FXML TableColumn<Prod, String> lguCol;
	@FXML TableColumn<Prod, String> buyerCol;
	@FXML TableColumn<Prod, String> costCol;
	@FXML TableColumn<Prod, String> priceCol;
	@FXML TableColumn<Prod, String> saleCol;
	@FXML TableColumn<Prod, String> outlineCol;
	@FXML TableColumn<Prod, String> detailCol;
	
	ProdInfoJdbc db = new ProdInfoJdbc();
	ObservableList<Lprod> lprod_list = db.lprod_list(); //lprod리스트
	ObservableList<Prod> prod_list = FXCollections.observableArrayList(); //prod리스트
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//테이블 컬럼 값 매핑
		idCol.setCellValueFactory(new PropertyValueFactory<>("prod_id"));
		nameCol.setCellValueFactory(new PropertyValueFactory<>("prod_name"));
		lguCol.setCellValueFactory(new PropertyValueFactory<>("prod_lgu"));
		buyerCol.setCellValueFactory(new PropertyValueFactory<>("prod_buyer"));
		costCol.setCellValueFactory(new PropertyValueFactory<>("prod_cost"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("prod_price"));
		saleCol.setCellValueFactory(new PropertyValueFactory<>("prod_sale"));
		outlineCol.setCellValueFactory(new PropertyValueFactory<>("prod_outline"));
		detailCol.setCellValueFactory(new PropertyValueFactory<>("prod_detail"));
		
		for(Lprod lprod : lprod_list) {
			cmb_lprod.getItems().add(lprod.getLprod_nm()); //첫번째 콤보박스에 lprod_nm 넣기
		}
		String lprod_first = lprod_list.get(0).getLprod_nm(); //lprod리스트의 첫번째값
		cmb_lprod.setValue(lprod_first); //첫번째 콤보박스에 lprod리스트의 첫번째값 세팅
		
		for(Prod prod : db.prod_list(lprod_first)) {
			cmb_prod.getItems().add(prod.getProd_name()); //두번째 콤보박스에 prod_name 넣기
		}
		String prod_first = db.prod_list(lprod_first).get(0).getProd_name(); //prod리스트의 첫번째값
		cmb_prod.setValue(prod_first); //두번째 콤보박스에 prod리스트의 첫번째값 세팅
		
		//맨처음 첫번째 콤보박스와 두번째 콤보박스가 기본값일때 table 세팅 
		ObservableList<Prod> prod = db.prod_list(cmb_lprod.getValue());
		prod_list.setAll(prod);
		for(int i=0; i<prod_list.size(); i++) {
			if(cmb_prod.getValue().equals(prod_list.get(i).getProd_name())) { //두번째 콤보박스값과 prod_name이 같다면
				tableView.getItems().setAll(prod_list.get(i));
			}
		}
		
		//첫번째 콤보박스를 선택했을때
		cmb_lprod.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				ObservableList<Prod> prod = db.prod_list(newValue); //lprod_nm에 해당하는 prod리스트
				prod_list.setAll(prod); //lprod_nm에 해당하는 prod리스트 세팅
				
				cmb_prod.getItems().clear(); //바뀌면 초기화
				for(int i=0; i<prod.size(); i++) {
					cmb_prod.getItems().add(prod.get(i).getProd_name()); //두번째 콤보박스에 lprod_nm에 해당하는 prod_name 넣기 
				}
				
				//====================================================
				//데이터가 존재하지 않을때
				if(cmb_prod.getItems().isEmpty()) {
					try {
						cmb_prod.setValue(prod.get(0).getProd_name());
					} catch (Exception e) {
						tableView.getItems().clear();
						errMsg("오류", "해당하는 데이터가 없습니다.");
						return;
					}
				}
				//====================================================
				
				cmb_prod.setValue(prod.get(0).getProd_name()); //두번째 콤보박스에 lprod_nm에 해당하는 prod_name 첫번째값 넣기
				System.out.println(cmb_prod.getItems()); //확인용
			}
		});
		
		//두번째 콤보박스를 선택했을때
		cmb_prod.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				for(int i=0; i<prod_list.size(); i++) {
					if(prod_list.get(i).getProd_name().equals(newValue)) { //prod_name이 선택한값과 같다면
																		   //(첫번째 콤보박스를 선택했을때
																		   //newValue값이 존재하지 않을수도 있기때문에
																		   //newValue값은 비교할때 뒤에옴) 
						tableView.getItems().setAll(prod_list.get(i)); //테이블에 prod_name에 해당하는 prod값 세팅
					}
				}
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
}