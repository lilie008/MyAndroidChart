package com.example.mycharts;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.example.entity.IChartData;
import com.example.entity.IStickEntity;
import com.example.entity.ListChartData;
import com.example.entity.StickEntity;

public class MainActivity extends Activity {
	SlipStickChart gridchart;
	
	List<IStickEntity> vol;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initVOL();
		
		initGridChart();
		initSlipStickChart();
	}

	private void initGridChart() {
		this.gridchart = (SlipStickChart) findViewById(R.id.gridchart);

//		gridchart.setAxisXColor(Color.LTGRAY);
//		gridchart.setAxisYColor(Color.LTGRAY);
//		gridchart.setBorderColor(Color.LTGRAY);
//		gridchart.setLatitudeNum(5);
//		gridchart.setLongitudeNum(6);
//		gridchart.setDataQuadrantPaddingTop(5);
//		gridchart.setDataQuadrantPaddingBottom(5);
//		gridchart.setDataQuadrantPaddingLeft(5);
//		gridchart.setDataQuadrantPaddingRight(5);
//		gridchart.setAxisYTitleQuadrantWidth(50);
//		gridchart.setAxisXTitleQuadrantHeight(20);
//		gridchart.setAxisXPosition(GridChart.AXIS_X_POSITION_BOTTOM);
//		gridchart.setAxisYPosition(GridChart.AXIS_Y_POSITION_RIGHT);
//		gridchart.setLongitudeFontSize(14);
//		gridchart.setLatitudeFontSize(14);
//		gridchart.setLongitudeFontColor(Color.WHITE);
//		gridchart.setLatitudeColor(Color.GRAY);
//		gridchart.setLatitudeFontColor(Color.WHITE);
//		gridchart.setLongitudeColor(Color.GRAY);
//		gridchart.setDisplayLongitudeTitle(true);
//		gridchart.setDisplayLatitudeTitle(true);
//		gridchart.setDisplayLatitude(true);
//		gridchart.setDisplayLongitude(true);
//		gridchart.setCrossLinesColor(Color.BLUE);
//		gridchart.setCrossLinesFontColor(Color.GREEN);
		
		//slip
		gridchart.setAxisXColor(Color.LTGRAY);
		gridchart.setAxisYColor(Color.LTGRAY);
		gridchart.setLatitudeColor(Color.GRAY);
		gridchart.setLongitudeColor(Color.GRAY);
		gridchart.setBorderColor(Color.LTGRAY);
		gridchart.setLongitudeFontColor(Color.WHITE);
		gridchart.setLatitudeFontColor(Color.WHITE);
		gridchart.setStickFillColor(Color.YELLOW);

		gridchart.setDataQuadrantPaddingTop(6);
		gridchart.setDataQuadrantPaddingBottom(1);
		gridchart.setDataQuadrantPaddingLeft(1);
		gridchart.setDataQuadrantPaddingRight(1);
		gridchart.setAxisYTitleQuadrantWidth(50);
		gridchart.setAxisXTitleQuadrantHeight(20);
		gridchart.setAxisXPosition(SlipStickChart.AXIS_X_POSITION_BOTTOM);
		gridchart.setAxisYPosition(SlipStickChart.AXIS_Y_POSITION_RIGHT);

		// 最大纬线数
		gridchart.setLatitudeNum(5);
		// 最大经线数
		gridchart.setLongitudeNum(6);
		// 最大价格
		gridchart.setMaxValue(600000);
		// 最小价格
		gridchart.setMinValue(100);

		gridchart.setDisplayFrom(10);

		gridchart.setDisplayNumber(30);

		gridchart.setMinDisplayNumber(5);


		gridchart.setDisplayLongitudeTitle(true);
		gridchart.setDisplayLatitudeTitle(true);
		gridchart.setDisplayLatitude(true);
		gridchart.setDisplayLongitude(true);
//		gridchart.setBackgroundColor(Color.BLACK);
		
		gridchart.setDataMultiple(100);
		gridchart.setAxisYDecimalFormat("#,##0.00");
		gridchart.setAxisXDateTargetFormat("yyyy/MM/dd");
		gridchart.setAxisXDateSourceFormat("yyyyMMdd");
		
		IChartData<IStickEntity> vol = new ListChartData<IStickEntity>(this.vol);
		// 为chart1增加均线
		gridchart.setStickData(vol);
	}
	
	private void initSlipStickChart() {
		
	}

	private void initVOL() {
		List<IStickEntity> stick = new ArrayList<IStickEntity>();

		stick.add(new StickEntity(406000, 0, 20110825));
		stick.add(new StickEntity(232000, 0, 20110824));
		stick.add(new StickEntity(355000, 0, 20110823));
		stick.add(new StickEntity(437000, 0, 20110822));
		stick.add(new StickEntity(460000, 0, 20110819));
		stick.add(new StickEntity(422000, 0, 20110818));
		stick.add(new StickEntity(502000, 0, 20110817));
		stick.add(new StickEntity(509000, 0, 20110816));
		stick.add(new StickEntity(110000, 0, 20110815));
		stick.add(new StickEntity(110000, 0, 20110812));
		stick.add(new StickEntity(310000, 0, 20110811));
		stick.add(new StickEntity(210000, 0, 20110810));
		stick.add(new StickEntity(211000, 0, 20110809));
		stick.add(new StickEntity(577000, 0, 20110808));
		stick.add(new StickEntity(493000, 0, 20110805));
		stick.add(new StickEntity(433000, 0, 20110804));
		stick.add(new StickEntity(479000, 0, 20110803));
		stick.add(new StickEntity(360000, 0, 20110802));
		stick.add(new StickEntity(437000, 0, 20110801));
		stick.add(new StickEntity(504000, 0, 20110729));
		stick.add(new StickEntity(520000, 0, 20110728));
		stick.add(new StickEntity(494000, 0, 20110727));
		stick.add(new StickEntity(312000, 0, 20110726));
		stick.add(new StickEntity(424000, 0, 20110725));
		stick.add(new StickEntity(557000, 0, 20110722));
		stick.add(new StickEntity(596000, 0, 20110721));
		stick.add(new StickEntity(311000, 0, 20110720));
		stick.add(new StickEntity(312000, 0, 20110719));
		stick.add(new StickEntity(312000, 0, 20110718));
		stick.add(new StickEntity(312000, 0, 20110715));
		stick.add(new StickEntity(410000, 0, 20110714));
		stick.add(new StickEntity(311000, 0, 20110713));
		stick.add(new StickEntity(210000, 0, 20110712));
		stick.add(new StickEntity(410000, 0, 20110711));
		stick.add(new StickEntity(214000, 0, 20110708));
		stick.add(new StickEntity(312000, 0, 20110707));
		stick.add(new StickEntity(212000, 0, 20110706));
		stick.add(new StickEntity(414000, 0, 20110705));
		stick.add(new StickEntity(310000, 0, 20110704));
		stick.add(new StickEntity(210000, 0, 20110701));
		stick.add(new StickEntity(190000, 0, 20110630));
		stick.add(new StickEntity(210000, 0, 20110629));
		stick.add(new StickEntity(116000, 0, 20110628));
		stick.add(new StickEntity(142000, 0, 20110627));
		stick.add(new StickEntity(524000, 0, 20110624));
		stick.add(new StickEntity(246000, 0, 20110623));
		stick.add(new StickEntity(432000, 0, 20110622));
		stick.add(new StickEntity(352000, 0, 20110621));
		stick.add(new StickEntity(243000, 0, 20110620));
		stick.add(new StickEntity(165000, 0, 20110617));
		stick.add(new StickEntity(554000, 0, 20110616));
		stick.add(new StickEntity(552000, 0, 20110615));
		stick.add(new StickEntity(431000, 0, 20110614));
		stick.add(new StickEntity(317000, 0, 20110613));
		stick.add(new StickEntity(512000, 0, 20110610));
		stick.add(new StickEntity(283000, 0, 20110609));
		stick.add(new StickEntity(144000, 0, 20110608));
		stick.add(new StickEntity(273000, 0, 20110607));
		stick.add(new StickEntity(278000, 0, 20110603));
		stick.add(new StickEntity(624000, 0, 20110602));
		stick.add(new StickEntity(217000, 0, 20110601));
		stick.add(new StickEntity(116000, 0, 20110531));
		stick.add(new StickEntity(191000, 0, 20110530));
		stick.add(new StickEntity(204000, 0, 20110527));
		stick.add(new StickEntity(236000, 0, 20110526));
		stick.add(new StickEntity(421000, 0, 20110525));
		stick.add(new StickEntity(114000, 0, 20110524));
		stick.add(new StickEntity(403000, 0, 20110523));
		stick.add(new StickEntity(205000, 0, 20110520));
		stick.add(new StickEntity(328000, 0, 20110519));
		stick.add(new StickEntity(109000, 0, 20110518));
		stick.add(new StickEntity(286000, 0, 20110517));
		stick.add(new StickEntity(103000, 0, 20110516));
		stick.add(new StickEntity(114000, 0, 20110513));
		stick.add(new StickEntity(107000, 0, 20110512));
		stick.add(new StickEntity(106000, 0, 20110511));
		stick.add(new StickEntity(146000, 0, 20110510));
		stick.add(new StickEntity(105000, 0, 20110509));
		stick.add(new StickEntity(312000, 0, 20110506));
		stick.add(new StickEntity(530000, 0, 20110505));
		stick.add(new StickEntity(275000, 0, 20110504));
		stick.add(new StickEntity(432000, 0, 20110503));
		// stick.add(new StickEntity(157000,0,20110429));
		// stick.add(new StickEntity(148000,0,20110428));
		// stick.add(new StickEntity(224000,0,20110427));
		// stick.add(new StickEntity(405000,0,20110426));
		// stick.add(new StickEntity(374000,0,20110425));
		// stick.add(new StickEntity(473000,0,20110422));
		// stick.add(new StickEntity(437000,0,20110421));
		// stick.add(new StickEntity(121000,0,20110420));
		// stick.add(new StickEntity(208000,0,20110419));
		// stick.add(new StickEntity(486000,0,20110418));
		// stick.add(new StickEntity(486000,0,20110415));
		// stick.add(new StickEntity(473000,0,20110414));
		// stick.add(new StickEntity(256000,0,20110413));
		// stick.add(new StickEntity(275000,0,20110412));
		// stick.add(new StickEntity(471000,0,20110411));
		// stick.add(new StickEntity(529000,0,20110408));
		// stick.add(new StickEntity(564000,0,20110407));
		// stick.add(new StickEntity(257000,0,20110406));
		// stick.add(new StickEntity(344000,0,20110404));
		// stick.add(new StickEntity(525000,0,20110401));

		this.vol = new ArrayList<IStickEntity>();
		for (int i = stick.size(); i > 0; i--) {
			this.vol.add(stick.get(i - 1));
		}

		// this.vol.addAll(stick);
	}
}
