package javafx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

public class V_PieChartTest extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception {
		PieChart pieChart = new PieChart();
		
		// 차트에 나터널 데이터 구성하기
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("포도", 10000), new PieChart.Data("사과", 18000)
			  , new PieChart.Data("배", 25000), new PieChart.Data("복숭아", 15000)
			  , new PieChart.Data("와나나", 2200), new PieChart.Data("파인애플", 25000));
		
		pieChart.setTitle("과일 가격");
		pieChart.setLabelLineLength(50);
		pieChart.setLegendSide(Side.RIGHT); // 범례가 나타날 위치 지정
		pieChart.setData(pieChartData); // 데이터 세팅
		
		Scene scene = new Scene(pieChart, 500, 500);
		primaryStage.setTitle("PieChart 연습");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}