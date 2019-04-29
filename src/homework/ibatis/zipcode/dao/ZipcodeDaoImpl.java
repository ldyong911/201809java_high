package homework.ibatis.zipcode.dao;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import homework.ibatis.zipcode.vo.ZipcodeVO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ZipcodeDaoImpl implements Initializable{

	@FXML ComboBox<String> cmb_sel;
	@FXML TextField tf_sel;
	@FXML Button btn_search;
	@FXML TableView<ZipcodeVO> tableView;
	@FXML TableColumn<ZipcodeVO, String> zipcodeCol;
	@FXML TableColumn<ZipcodeVO, String> sidoCol;
	@FXML TableColumn<ZipcodeVO, String> gugunCol;
	@FXML TableColumn<ZipcodeVO, String> dongCol;
	@FXML TableColumn<ZipcodeVO, String> bunjiCol;
	
	private SqlMapClient smc;
	
	public ZipcodeDaoImpl() {
		Reader rd;
		try {
			//xml문서 읽어오기
			rd = Resources.getResourceAsReader("SqlMapConfig_zipcode.xml");
			//Reader객체를 이용하여 실제 작업을 진행할 객체 생성
			smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			//rd객체 닫기
			rd.close();
		} catch (IOException e) {
			System.out.println("SqlMapClient객체 생성 실패!");
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//테이블 컬럼값 매핑
		zipcodeCol.setCellValueFactory(new PropertyValueFactory<>("zipcode"));
		sidoCol.setCellValueFactory(new PropertyValueFactory<>("sido"));
		gugunCol.setCellValueFactory(new PropertyValueFactory<>("gugun"));
		dongCol.setCellValueFactory(new PropertyValueFactory<>("dong"));
		bunjiCol.setCellValueFactory(new PropertyValueFactory<>("bunji"));
		
		//콤보박스
		cmb_sel.getItems().addAll("동이름", "우편번호");
		cmb_sel.setValue(cmb_sel.getItems().get(0));
		cmb_sel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				tf_sel.clear(); //콤보박스선택시 텍스트필드 지우기
				
				//검색 버튼 클릭했을때
				btn_search.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						String select = cmb_sel.getSelectionModel().getSelectedItem();
						ZipcodeVO vo = new ZipcodeVO();
						
						if(select.equals("동이름")) {
							vo.setDong(tf_sel.getText());
						}else if(select.equals("우편번호")) {
							vo.setZipcode(tf_sel.getText());
						}
						
						List<ZipcodeVO> zipcodeList = selectSearch(vo);
						
						tableView.getItems().setAll(zipcodeList);
					}
				});
			}
		});
		
	}
	
	public List<ZipcodeVO> selectSearch(ZipcodeVO vo){
		List<ZipcodeVO> list = new ArrayList<ZipcodeVO>();
		
		try {
			list = smc.queryForList("zipcodeSqlMap.selectSearch", vo);
		} catch (SQLException e) {
			list = null;
			e.printStackTrace();
		}
		
		return list;
	}
	
	//에러메세지창
	public void errMsg(String title, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.showAndWait();
	}

	//확인메세지창
	public void infoMsg(String title, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.showAndWait();
	}
}