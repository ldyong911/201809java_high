package homework.ibatis.board.dao;

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

import homework.ibatis.board.vo.BoardVO;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BoardDaoImpl implements Initializable{
	@FXML TableView<BoardVO> tableView;
	@FXML TableColumn<BoardVO, String> numCol;
	@FXML TableColumn<BoardVO, String> titleCol;
	@FXML TableColumn<BoardVO, String> writerCol;
	@FXML TableColumn<BoardVO, String> dateCol;
	@FXML ComboBox<String> cb_search;
	@FXML TextField tf_search;
	@FXML Button btn_search;
	@FXML Button btn_write;

	private SqlMapClient smc;
	private List<BoardVO> boardList;
	
	public BoardDaoImpl() {
		Reader rd;
		try {
			//xml문서 읽어오기
			rd = Resources.getResourceAsReader("SqlMapConfig_board.xml");
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
		//테이블 컬럼 값 매핑
		numCol.setCellValueFactory(new PropertyValueFactory<>("board_no"));
		titleCol.setCellValueFactory(new PropertyValueFactory<>("board_title"));
		writerCol.setCellValueFactory(new PropertyValueFactory<>("board_writer"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("board_date"));
		
		//전체 게시판을 select하고 table에 값 세팅
		boardList = selectBoardList();
		System.out.println(boardList.size());
		tableView.getItems().setAll(boardList);
//		tableView.setItems((ObservableList<BoardVO>)boardList);
		
		//테이블선택했을때
		tableView.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				//새로운창 생성하고 모달창여부 설정
				Stage childStage = new Stage(StageStyle.UTILITY);
				childStage.initModality(Modality.APPLICATION_MODAL);
				
				//자식창에 나타날 컨테이너 객체 생성
				Parent parent = null;
				try {
					parent = FXMLLoader.load(getClass().getResource("/ibatis/board/res/BoardUpdate.fxml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//부모창에서 FXML로 만든 자식창의 컨트롤 객체 얻기
				TextField tf_title = (TextField)parent.lookup("#tf_title");
				TextField tf_writer = (TextField)parent.lookup("#tf_writer");
				TextArea ta_content = (TextArea)parent.lookup("#ta_content");
				Button btn_update = (Button)parent.lookup("#btn_update");
				Button btn_delete = (Button)parent.lookup("#btn_delete");
				Button btn_revoke = (Button)parent.lookup("#btn_revoke");
				
				//선택한 게시판번호를 얻고, 게시판번호를 이용해 select하기
				String board_no = tableView.getSelectionModel().getSelectedItem().getBoard_no();
				BoardVO selectvo = selectBoard(board_no);
				
				//컨트롤객체에 정보 세팅
				tf_title.setText(selectvo.getBoard_title());
				tf_writer.setText(selectvo.getBoard_writer());
				ta_content.setText(selectvo.getBoard_content());
				
				btn_update.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						if(tf_title.getText().isEmpty()) {
							tf_title.requestFocus(); //포커스주기
							errMsg("에러", "제목을 입력해주세요!");
							return;
						}
						if(tf_writer.getText().isEmpty()) {
							tf_writer.requestFocus(); //포커스주기
							errMsg("에러", "작성자를 입력해주세요!");
							return;
						}
						if(ta_content.getText().isEmpty()) {
							ta_content.requestFocus(); //포커스주기
							errMsg("에러", "내용을 입력해주세요!");
							return;
						}
						
						//수정할 정보들을 vo에 세팅한후 update
						selectvo.setBoard_title(tf_title.getText());
						selectvo.setBoard_writer(tf_writer.getText());
						selectvo.setBoard_content(ta_content.getText());
						
						updateBoard(selectvo);
						
						//수정한후 tableView update
						tableView.getItems().setAll(selectBoardList());
						
						childStage.close();
					}
				});
				
				btn_delete.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						//삭제할 board_no를 이용해 delete
						deleteBoard(selectvo.getBoard_no());
						
						//삭제한후 tableView update
						tableView.getItems().setAll(selectBoardList());
						
						childStage.close();
					}
				});
				
				btn_revoke.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						childStage.close();
					}
				});
				
				//Scene 객체 생성해서 컨테이너 객체 추가하기
				Scene scene = new Scene(parent);
				
				//Stage 객체에 Scene 객체 추가
				childStage.setTitle("게시판 수정하기");
				childStage.setScene(scene);
				childStage.show();
			}
		});
		
		//콤보박스
		cb_search.getItems().addAll("*", "제목", "글쓴이", "내용");
		cb_search.setValue(cb_search.getItems().get(0)); //처음값 세팅
		cb_search.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				tf_search.clear();
				//검색 버튼 클릭했을때
				btn_search.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						String select = cb_search.getSelectionModel().getSelectedItem();
						BoardVO searchvo = new BoardVO();
						
						//검색할 정보들을 세팅후 select
						if(select.equals("*")) { //공백을 선택할경우 전체목록 세팅
							tableView.getItems().setAll(selectBoardList());
							return;
						}else if(select.equals("제목")) { //제목 선택할경우
							searchvo.setBoard_title(tf_search.getText());
						}else if(select.equals("글쓴이")) { //글쓴이 선택할경우
							searchvo.setBoard_writer(tf_search.getText());
						}else if(select.equals("내용")) { //내용 선택할경우
							searchvo.setBoard_content(tf_search.getText());
						}
						
						//vo에 세팅된 값을 가지고 동적쿼리로 작성한 메서드 실행
						List<BoardVO> searchList = selectSearch(searchvo);
						
						//검색한후 table을 세팅
						tableView.getItems().setAll(searchList);
					}
				});
			}
		});
		
		//글쓰기 버튼 클릭했을때
		btn_write.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//새로운창 생성하고 모달창여부 설정
				Stage childStage = new Stage(StageStyle.UTILITY);
				childStage.initModality(Modality.APPLICATION_MODAL);
				
				//자식창에 나타날 컨테이너 객체 생성
				Parent parent = null;
				try {
					parent = FXMLLoader.load(getClass().getResource("/ibatis/board/res/BoardInsert.fxml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//부모창에서 FXML로 만든 자식창의 컨트롤 객체 얻기
				TextField tf_title = (TextField)parent.lookup("#tf_title");
				TextField tf_writer = (TextField)parent.lookup("#tf_writer");
				TextArea ta_content = (TextArea)parent.lookup("#ta_content");
				Button btn_insert = (Button)parent.lookup("#btn_insert");
				Button btn_again = (Button)parent.lookup("#btn_again");
				Button btn_revoke = (Button)parent.lookup("#btn_revoke");
				
				btn_insert.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						if(tf_title.getText().isEmpty()) {
							tf_title.requestFocus(); //포커스주기
							errMsg("에러", "제목을 입력해주세요!");
							return;
						}
						if(tf_writer.getText().isEmpty()) {
							tf_writer.requestFocus(); //포커스주기
							errMsg("에러", "작성자를 입력해주세요!");
							return;
						}
						if(ta_content.getText().isEmpty()) {
							ta_content.requestFocus(); //포커스주기
							errMsg("에러", "내용을 입력해주세요!");
							return;
						}
						
						//등록할 정보들을 vo 세팅한 후 insert
						BoardVO insertvo = new BoardVO();
						insertvo.setBoard_title(tf_title.getText());
						insertvo.setBoard_writer(tf_writer.getText());
						insertvo.setBoard_content(ta_content.getText());
						
						insertBoard(insertvo);
						
						//등록한후 tableView update
						tableView.getItems().setAll(selectBoardList());
						
						childStage.close();
					}
				});
				
				btn_again.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						tf_title.clear();
						tf_writer.clear();
						ta_content.clear();
						tf_title.requestFocus(); //포커스 주기
						infoMsg("확인", "다시입력합니다.");
					}
				});
				
				btn_revoke.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						childStage.close();
					}
				});
				
				//Scene 객체 생성해서 컨테이너 객체 추가하기
				Scene scene = new Scene(parent);
				
				//Stage 객체에 Scene 객체 추가
				childStage.setTitle("게시판 작성");
				childStage.setScene(scene);
				childStage.show();
			}
		});
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
	
	//게시판 등록
	public int insertBoard(BoardVO vo) {
		int cnt = 0;
		try {
			Object obj = smc.insert("boardSqlMap.insertBoard", vo);
			if(obj == null) {
				cnt = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cnt;
	}
	
	//게시판 삭제
	public int deleteBoard(String board_no) {
		int cnt = 0;
		try {
			cnt = smc.delete("boardSqlMap.deleteBoard", board_no);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		
		return cnt;
	}
	
	//게시판 수정
	public int updateBoard(BoardVO vo) {
		int cnt = 0;
		try {
			cnt = smc.update("boardSqlMap.updateBoard", vo);
		} catch (SQLException e) {
			cnt = 0;
			e.printStackTrace();
		}
		
		return cnt;
	}
	
	//모든 게시판 select
	public List<BoardVO> selectBoardList(){
		List<BoardVO> list = new ArrayList<BoardVO>();
		try {
			list = smc.queryForList("boardSqlMap.selectBoardList", list);
			System.out.println(list);
		} catch (SQLException e) {
			list = null;
			e.printStackTrace();
		}
		
		return list;
	}
	
	//선택한 게시판 select
	public BoardVO selectBoard(String board_no) {
		BoardVO vo = null;
		try {
			vo = (BoardVO)smc.queryForObject("boardSqlMap.selectBoard", board_no);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return vo;
	}
	
	//제목, 작성자, 내용 중에 검색했을때 select
	public List<BoardVO> selectSearch(BoardVO vo){
		List<BoardVO> list = new ArrayList<BoardVO>();
		try {
			list = smc.queryForList("boardSqlMap.selectSearch", vo);
		} catch (SQLException e) {
			list = null;
			e.printStackTrace();
		}
		
		return list;
	}
	
}