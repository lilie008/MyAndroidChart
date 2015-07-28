package com.example.mycharts;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.entity.IChartData;
import com.example.entity.IMeasurable;
import com.example.entity.IStickEntity;

/**
 * @ClassName: GridChart
 * @Description: TODO
 * @author li
 * @date 2015年7月27日
 *
 */
    
public class SlipStickChart extends SurfaceView implements SurfaceHolder.Callback{
	//坐标方向定义
	public static final int AXIS_X_POSITION_BOTTOM = 1 << 0;
	@Deprecated
	public static final int AXIS_X_POSITION_TOP = 1 << 1;
	public static final int AXIS_Y_POSITION_LEFT = 1 << 2;
	public static final int AXIS_Y_POSITION_RIGHT = 1 << 3;


	public static final int DEFAULT_BACKGROUND_COLOR = Color.BLACK;
	public static final int DEFAULT_AXIS_X_COLOR = Color.RED;
	public static final int DEFAULT_AXIS_Y_COLOR = Color.RED;
	public static final float DEFAULT_AXIS_WIDTH = 1f;

	public static final int DEFAULT_AXIS_X_POSITION = AXIS_X_POSITION_BOTTOM;

	public static final int DEFAULT_AXIS_Y_POSITION = AXIS_Y_POSITION_LEFT;
	
	public static final int DEFAULT_LONGITUDE_COLOR = Color.RED;
	public static final int DEFAULT_LAITUDE_COLOR = Color.RED;
	public static final int DEFAULT_BORDER_COLOR = Color.RED;
	public static final int DEFAULT_LATITUDE_FONT_COLOR = Color.RED;
	public static final int DEFAULT_LONGITUDE_FONT_COLOR = Color.WHITE;
	
	public static final int DEFAULT_CROSS_LINES_COLOR = Color.CYAN;
	public static final int DEFAULT_CROSS_LINES_FONT_COLOR = Color.CYAN;
	
	public static final float DEFAULT_AXIS_Y_TITLE_QUADRANT_WIDTH = 16f;
	public static final float DEFAULT_AXIS_X_TITLE_QUADRANT_HEIGHT = 16f;
	
	public static final float DEFAULT_DATA_QUADRANT_PADDING_TOP = 5f;
	public static final float DEFAULT_DATA_QUADRANT_PADDING_BOTTOM = 5f;
	public static final float DEFAULT_DATA_QUADRANT_PADDING_LEFT = 5f;
	public static final float DEFAULT_DATA_QUADRANT_PADDING_RIGHT = 5f;
	
	public static final int DEFAULT_LATITUDE_NUM = 4;
	public static final int DEFAULT_LONGITUDE_NUM = 3;
	
	public static final boolean DEFAULT_DISPLAY_LONGITUDE = Boolean.TRUE;
	public static final boolean DEFAULT_DASH_LONGITUDE = Boolean.TRUE;
	public static final boolean DEFAULT_DISPLAY_LATITUDE = Boolean.TRUE;
	public static final boolean DEFAULT_DASH_LATITUDE = Boolean.TRUE;
	public static final boolean DEFAULT_DISPLAY_LONGITUDE_TITLE = Boolean.TRUE;
	public static final boolean DEFAULT_DISPLAY_LATITUDE_TITLE = Boolean.TRUE;
	public static final boolean DEFAULT_DISPLAY_BORDER = Boolean.TRUE;
	
	public static final float DEFAULT_LONGITUDE_WIDTH = 1f;
	public static final float DEFAULT_LATITUDE_WIDTH = 1f;
	public static final float DEFAULT_BORDER_WIDTH = 1f;
	
	public static final int DEFAULT_LONGITUDE_FONT_SIZE = 12;
	public static final int DEFAULT_LATITUDE_FONT_SIZE = 12;
	
	public static final int DEFAULT_LATITUDE_MAX_TITLE_LENGTH = 5;
	//虚线
	public static final PathEffect DEFAULT_DASH_EFFECT = new DashPathEffect(
			new float[] { 6, 3, 6, 3 }, 1);
	
	public static final boolean DEFAULT_DISPLAY_CROSS_X_ON_TOUCH = true;
	
	public static final boolean DEFAULT_DISPLAY_CROSS_Y_ON_TOUCH = true;
	
	private int axisXColor = DEFAULT_AXIS_X_COLOR;
	private int axisYColor = DEFAULT_AXIS_Y_COLOR;
	
	private int crossLinesColor = DEFAULT_CROSS_LINES_COLOR;
	private int crossLinesFontColor = DEFAULT_CROSS_LINES_FONT_COLOR;
	
	private int longitudeColor = DEFAULT_LONGITUDE_COLOR;
	private int latitudeColor = DEFAULT_LAITUDE_COLOR;
	
	private int longitudeFontColor = DEFAULT_LONGITUDE_FONT_COLOR;
	private int latitudeFontColor = DEFAULT_LATITUDE_FONT_COLOR;
	
	private int borderColor = DEFAULT_BORDER_COLOR;
	
	private float axisWidth = DEFAULT_AXIS_WIDTH;
	protected int axisXPosition = DEFAULT_AXIS_X_POSITION;
	protected int axisYPosition = DEFAULT_AXIS_Y_POSITION;
	
	protected float axisYTitleQuadrantWidth = DEFAULT_AXIS_Y_TITLE_QUADRANT_WIDTH;
	protected float axisXTitleQuadrantHeight = DEFAULT_AXIS_X_TITLE_QUADRANT_HEIGHT;
	
	protected float dataQuadrantPaddingTop = DEFAULT_DATA_QUADRANT_PADDING_TOP;
	protected float dataQuadrantPaddingLeft = DEFAULT_DATA_QUADRANT_PADDING_LEFT;
	protected float dataQuadrantPaddingBottom = DEFAULT_DATA_QUADRANT_PADDING_BOTTOM;
	protected float dataQuadrantPaddingRight = DEFAULT_DATA_QUADRANT_PADDING_RIGHT;
	
	private boolean displayLongitudeTitle = DEFAULT_DISPLAY_LONGITUDE_TITLE;
	private boolean displayLatitudeTitle = DEFAULT_DISPLAY_LATITUDE_TITLE;
	
	private float longitudeWidth = DEFAULT_LONGITUDE_WIDTH;
	private float latitudeWidth = DEFAULT_LATITUDE_WIDTH;
	
	protected int latitudeNum = DEFAULT_LATITUDE_NUM;
	protected int longitudeNum = DEFAULT_LONGITUDE_NUM;
	
	private boolean displayLatitude = DEFAULT_DISPLAY_LATITUDE;
	private boolean displayLongitude = DEFAULT_DISPLAY_LONGITUDE;
	private boolean dashLongitude = DEFAULT_DASH_LONGITUDE;	
	private boolean dashLatitude = DEFAULT_DASH_LATITUDE;
	
	private PathEffect dashEffect = DEFAULT_DASH_EFFECT;
	
	private boolean displayBorder = DEFAULT_DISPLAY_BORDER;
	protected float borderWidth = DEFAULT_BORDER_WIDTH;
	
	private int longitudeFontSize = DEFAULT_LONGITUDE_FONT_SIZE;
	private int latitudeFontSize = DEFAULT_LATITUDE_FONT_SIZE;
	
	public static final int DEFAULT_STICK_FILL_COLOR = Color.RED;
	public static final int DEFAULT_STICK_BORDER_COLOR = Color.RED;
	
	//Touch事件
	private int SLIP_STEP = 1;
	protected float olddistance = 0f;
	protected float newdistance = 0f;
	protected PointF startPointA;
	protected PointF startPointB;
	protected int touchMode = TOUCH_MODE_NONE;
	static final int TOUCH_MODE_NONE = 0;
	static final int TOUCH_MODE_SINGLE = 1;
	static final int TOUCH_MODE_MULTI = 2;
	
	static final int TOUCH_MOVE_MIN_DISTANCE = 6;
	
	//缩放
	static final int ZOOM_BASE_LINE_CENTER = 0;
	static final int ZOOM_BASE_LINE_LEFT = 1;
	static final int ZOOM_BASE_LINE_RIGHT = 2;
	
	static final int ZOOM_NONE = 0;
	static final int ZOOM_IN = 1;
	static final int ZOOM_OUT = 2;
	
	static final int ZOOM_STEP = 4;
	static final int ALIGN_TYPE_CENTER = 0;
	static final int ALIGN_TYPE_JUSTIFY = 1;
	
	protected int zoomBaseLine = DEFAULT_ZOOM_BASE_LINE;
	
	public static final int DEFAULT_STICK_ALIGN_TYPE = ALIGN_TYPE_CENTER;

	public static final int DEFAULT_ZOOM_BASE_LINE = ZOOM_BASE_LINE_CENTER;
	
	//坐标相关变量
	protected List<String> longitudeTitles;
	protected List<String> latitudeTitles;
	
	private int latitudeMaxTitleLength = DEFAULT_LATITUDE_MAX_TITLE_LENGTH;
	
	private boolean displayCrossXOnTouch = DEFAULT_DISPLAY_CROSS_X_ON_TOUCH;
	private boolean displayCrossYOnTouch = DEFAULT_DISPLAY_CROSS_Y_ON_TOUCH;
	
	protected PointF touchPoint;
	
	private int stickFillColor = DEFAULT_STICK_FILL_COLOR;
	
	private int stickBorderColor = DEFAULT_STICK_BORDER_COLOR;

	private SurfaceHolder surfaceHolder;
	private OnDrawThread onDrawThread;
	
	public SlipStickChart(Context context) {
		super(context);
	}

	
	public SlipStickChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		surfaceHolder = this.getHolder();
		surfaceHolder.addCallback(this);
	}
	
	public SlipStickChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	    
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//valid
		if (!isValidTouchPoint(event.getX(),event.getY())) {
			return false;
		}
		
		if (null == stickData || stickData.size() == 0) {
			return false;
		}

		final float MIN_LENGTH = (super.getWidth() / 40) < 5 ? 5 : (super
				.getWidth() / 50);

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		// 设置拖拉模式
		case MotionEvent.ACTION_DOWN:
			touchMode = TOUCH_MODE_SINGLE;
			if (event.getPointerCount() == 1) {
				touchPoint = new PointF(event.getX(), event.getY());
			}
			break;
		case MotionEvent.ACTION_UP:
			touchMode = TOUCH_MODE_NONE;
			startPointA = null;
			startPointB = null;
			break;
		case MotionEvent.ACTION_POINTER_UP:
			touchMode = TOUCH_MODE_NONE;
			startPointA = null;
			startPointB = null;
			return super.onTouchEvent(event);
			// 设置多点触摸模式
		case MotionEvent.ACTION_POINTER_DOWN:
			olddistance = calcDistance(event);
			if (olddistance > MIN_LENGTH) {
				touchMode = TOUCH_MODE_MULTI;
				startPointA = new PointF(event.getX(0), event.getY(0));
				startPointB = new PointF(event.getX(1), event.getY(1));
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (touchMode == TOUCH_MODE_MULTI) {
				newdistance = calcDistance(event);
				if (newdistance > MIN_LENGTH) {
//					if (startPointA.x >= event.getX(0)
//							&& startPointB.x >= event.getX(1)) {
//						moveRight();
//					} else if (startPointA.x <= event.getX(0)
//							&& startPointB.x <= event.getX(1)) {
//						moveLeft();
//					} else {
						if (Math.abs(newdistance - olddistance) > MIN_LENGTH) {
							if (newdistance > olddistance) {
								zoomIn();
							} else {
								zoomOut();
							}
							// 重置距离
							olddistance = newdistance;
						}
//					}
					startPointA = new PointF(event.getX(0), event.getY(0));
					startPointB = new PointF(event.getX(1), event.getY(1));
				}
			} else {
				// 单点拖动效果
				if (event.getPointerCount() == 1) {
					float moveXdistance = Math.abs(event.getX() - touchPoint.x);				 
					if(moveXdistance > MIN_LENGTH){
						if(touchPoint.x - event.getX() > 0){
							int startindex = getSelectedIndex();
							touchPoint = new PointF(event.getX(), event.getY());
							int gapindex = Math.abs(getSelectedIndex()-startindex);
							for(int i = 0;i<gapindex;i++){
								moveRight();
							}
						}else if(touchPoint.x - event.getX() < 0){
							int startindex = getSelectedIndex();
							touchPoint = new PointF(event.getX(), event.getY());
							int gapindex = Math.abs(getSelectedIndex()-startindex);
							for(int i = 0;i<gapindex;i++){
								moveLeft();
							}
						}
					}
				}
			}
			break;
		}
		return true;
	}


	/** 
	 * @Description: 放大 
	 * @return void
	 */
	public void zoomIn() {
		if (displayNumber > minDisplayNumber) {
			// 区分缩放方向
			if (zoomBaseLine == ZOOM_BASE_LINE_CENTER) {
				displayNumber = displayNumber - ZOOM_STEP;
				displayFrom = displayFrom + ZOOM_STEP / 2;
			} else if (zoomBaseLine == ZOOM_BASE_LINE_LEFT) {
				displayNumber = displayNumber - ZOOM_STEP;
			} else if (zoomBaseLine == ZOOM_BASE_LINE_RIGHT) {
				displayNumber = displayNumber - ZOOM_STEP;
				displayFrom = displayFrom + ZOOM_STEP;
			}

			// 处理displayNumber越界
			if (displayNumber < minDisplayNumber) {
				displayNumber = minDisplayNumber;
			}

			// 处理displayFrom越界
			if (displayFrom + displayNumber >= stickData.size()) {
				displayFrom = stickData.size() - displayNumber;
			}
		}
	}

	/** 
	 * @Description: 缩小
	 * @return void
	 */
	public void zoomOut() {
		if (displayNumber < stickData.size() - 1) {
			if (displayNumber + ZOOM_STEP > stickData.size() - 1) {
				displayNumber = stickData.size() - 1;
				displayFrom = 0;
			} else {
				// 区分缩放方向
				if (zoomBaseLine == ZOOM_BASE_LINE_CENTER) {
					displayNumber = displayNumber + ZOOM_STEP;
					if (displayFrom > 1) {
						displayFrom = displayFrom - ZOOM_STEP / 2;
					} else {
						displayFrom = 0;
					}
				} else if (zoomBaseLine == ZOOM_BASE_LINE_LEFT) {
					displayNumber = displayNumber + ZOOM_STEP;
				} else if (zoomBaseLine == ZOOM_BASE_LINE_RIGHT) {
					displayNumber = displayNumber + ZOOM_STEP;
					if (displayFrom > ZOOM_STEP) {
						displayFrom = displayFrom - ZOOM_STEP;
					} else {
						displayFrom = 0;
					}
				}
			}

			if (displayFrom + displayNumber >= stickData.size()) {
				displayNumber = stickData.size() - displayFrom;
			}

		}
	}
	
	/** 
	* @Description: 绘制X轴
	* @param canvas 
	* @return void
	*/
	protected void drawXAxis(Canvas canvas){
		float length = super.getWidth();
		float postY;
		if (axisXPosition == AXIS_X_POSITION_BOTTOM) {
			postY = super.getHeight() - axisXTitleQuadrantHeight - borderWidth
					- axisWidth / 2;
		} else {
			postY = super.getHeight() - borderWidth - axisWidth / 2;
		}

		Paint mPaint = new Paint();
		mPaint.setColor(axisXColor);
		mPaint.setStrokeWidth(axisWidth);

		canvas.drawLine(borderWidth, postY, length, postY, mPaint);
	}
	
	/** 
	* @Description: 绘制Y轴
	* @param canvas 
	* @return void
	*/
	protected void drawYAxis(Canvas canvas) {

		float length = super.getHeight() - axisXTitleQuadrantHeight
				- borderWidth;
		float postX;
		if (axisYPosition == AXIS_Y_POSITION_LEFT) {
			postX = borderWidth + axisYTitleQuadrantWidth + axisWidth / 2;
		} else {
			postX = super.getWidth() - borderWidth - axisYTitleQuadrantWidth
					- axisWidth / 2;
		}

		Paint mPaint = new Paint();
		mPaint.setColor(axisYColor);
		mPaint.setStrokeWidth(axisWidth);

		canvas.drawLine(postX, borderWidth, postX, length, mPaint);
	}
	
	/** 
	 * @Description: 绘制栅格边框
	 * @param canvas 
	 * @return void
	 */
	protected void drawBorder(Canvas canvas) {
		Paint mPaint = new Paint();
		mPaint.setColor(borderColor);
		mPaint.setStrokeWidth(borderWidth);
		mPaint.setStyle(Style.STROKE);
		// draw a rectangle
		canvas.drawRect(borderWidth / 2, borderWidth / 2, super.getWidth()
				- borderWidth / 2, super.getHeight() - borderWidth / 2, mPaint);
	}
	
	/** 
	 * @Description: 绘制经线
	 * @param canvas 
	 * @return void
	 */
	protected void drawLongitudeLine(Canvas canvas) {
		if (null == longitudeTitles) {
			return;
		}
		if (!displayLongitude) {
			return;
		}
		int counts = longitudeTitles.size();
		float length = getDataQuadrantHeight();

		Paint mPaintLine = new Paint();
		mPaintLine.setStyle(Style.STROKE);
		mPaintLine.setColor(longitudeColor);
		mPaintLine.setStrokeWidth(longitudeWidth);
		mPaintLine.setAntiAlias(true);
		if (dashLongitude) {
			mPaintLine.setPathEffect(dashEffect);
		}
		if (counts > 1) {
			float postOffset = longitudePostOffset();
			float offset = longitudeOffset();

			for (int i = 0; i < counts; i++) {
				Path path = new Path();
				path.moveTo(offset + i * postOffset, borderWidth);
				path.lineTo(offset + i * postOffset, length);
				canvas.drawPath(path, mPaintLine);
			}
		}
	}
	
	/** 
	 * @Description: 绘制纬线刻度
	 * @param canvas 
	 * @return void
	 */
	protected void drawLongitudeTitle(Canvas canvas) {

		if (null == longitudeTitles) {
			return;
		}
		if (!displayLongitude) {
			return;
		}
		if (!displayLongitudeTitle) {
			return;
		}
		if (longitudeTitles.size() <= 1) {
			return;
		}

		Paint mPaintFont = new Paint();
		mPaintFont.setColor(longitudeFontColor);
		mPaintFont.setTextSize(longitudeFontSize);
		mPaintFont.setAntiAlias(true);

		float postOffset = longitudePostOffset();

		float offset = longitudeOffset();
		for (int i = 0; i < longitudeTitles.size(); i++) {
			if (0 == i) {
				canvas.drawText(longitudeTitles.get(i), offset + 2f,
						super.getHeight() - axisXTitleQuadrantHeight
								+ longitudeFontSize, mPaintFont);
			} else {
//				canvas.drawText(longitudeTitles.get(i), offset + i * postOffset
//						- (longitudeTitles.get(i).length()) * longitudeFontSize
//						/ 2f, super.getHeight() - axisXTitleQuadrantHeight
//						+ longitudeFontSize, mPaintFont);
				
				canvas.drawText(
                        longitudeTitles.get(i),
                        offset + i * postOffset - getTextWidth(mPaintFont, longitudeTitles.get(i)) / 2f,
                        super.getHeight() - axisXTitleQuadrantHeight + longitudeFontSize, mPaintFont);
			}
		}
	}
	
	/** 
	 * @Description: 绘制经线
	 * @param canvas 
	 * @return void
	 */
	protected void drawLatitudeLine(Canvas canvas) {

		if (null == latitudeTitles) {
			return;
		}
		if (!displayLatitude) {
			return;
		}
		if (!displayLatitudeTitle) {
			return;
		}
		if (latitudeTitles.size() <= 1) {
			return;
		}

		float length = getDataQuadrantWidth();
		
		Paint mPaintLine = new Paint();
		mPaintLine.setStyle(Style.STROKE);
		mPaintLine.setColor(latitudeColor);
		mPaintLine.setStrokeWidth(latitudeWidth);
		mPaintLine.setAntiAlias(true);
		if (dashLatitude) {
			mPaintLine.setPathEffect(dashEffect);
		}

		Paint mPaintFont = new Paint();
		mPaintFont.setColor(latitudeFontColor);
		mPaintFont.setTextSize(latitudeFontSize);
		mPaintFont.setAntiAlias(true);

		float postOffset = this.getDataQuadrantPaddingHeight()
				/ (latitudeTitles.size() - 1);

		float offset = super.getHeight() - borderWidth
				- axisXTitleQuadrantHeight - axisWidth
				- dataQuadrantPaddingBottom;

		if (axisYPosition == AXIS_Y_POSITION_LEFT) {
			float startFrom = borderWidth + axisYTitleQuadrantWidth + axisWidth;
			for (int i = 0; i < latitudeTitles.size(); i++) {
				Path path = new Path();
				path.moveTo(startFrom, offset - i * postOffset);
				path.lineTo(startFrom + length, offset - i * postOffset);
				canvas.drawPath(path, mPaintLine);
			}
		} else {
			float startFrom = borderWidth;
			for (int i = 0; i < latitudeTitles.size(); i++) {
				Path path = new Path();
				path.moveTo(startFrom, offset - i * postOffset);
				path.lineTo(startFrom + length, offset - i * postOffset);
				canvas.drawPath(path, mPaintLine);
			}
		}
	}
	
	/** 
	 * @Description: 绘制经线刻度
	 * @param canvas 
	 * @return void
	 */
	protected void drawLatitudeTitle(Canvas canvas) {
		if (null == latitudeTitles) {
			return;
		}
		if (!displayLatitudeTitle) {
			return;
		}
		if (latitudeTitles.size() <= 1) {
			return;
		}
		Paint mPaintFont = new Paint();
		mPaintFont.setColor(latitudeFontColor);
		mPaintFont.setTextSize(latitudeFontSize);
		mPaintFont.setAntiAlias(true);

		float postOffset = this.getDataQuadrantPaddingHeight()
				/ (latitudeTitles.size() - 1);

		float offset = super.getHeight() - borderWidth
				- axisXTitleQuadrantHeight - axisWidth
				- dataQuadrantPaddingBottom;

		if (axisYPosition == AXIS_Y_POSITION_LEFT) {
			float startFrom = borderWidth;
			for (int i = 0; i < latitudeTitles.size(); i++) {
				if (0 == i) {
					canvas.drawText(latitudeTitles.get(i), startFrom,
							super.getHeight() - this.axisXTitleQuadrantHeight
									- borderWidth - axisWidth - 2f, mPaintFont);
				} else {
					canvas.drawText(latitudeTitles.get(i), startFrom, offset
							- i * postOffset + latitudeFontSize / 2f,
							mPaintFont);
				}
			}
		} else {
			float startFrom = super.getWidth() - borderWidth
					- axisYTitleQuadrantWidth;
			for (int i = 0; i < latitudeTitles.size(); i++) {

				if (0 == i) {
					canvas.drawText(latitudeTitles.get(i), startFrom,
							super.getHeight() - this.axisXTitleQuadrantHeight
									- borderWidth - axisWidth - 2f, mPaintFont);
				} else {
					canvas.drawText(latitudeTitles.get(i), startFrom, offset
							- i * postOffset + latitudeFontSize / 2f,
							mPaintFont);
				}
			}
		}

	}
	
	/** 
	 * @Description: 绘制触点水平线
	 * @param canvas 
	 * @return void
	 */
	protected void drawHorizontalLine(Canvas canvas) {

		if (!displayLatitudeTitle) {
			return;
		}
		if (!displayCrossYOnTouch) {
			return;
		}
		if (touchPoint == null) {
			return;
		}
		if (touchPoint.y <= 0) {
			return;
		}

		Paint mPaint = new Paint();
		mPaint.setColor(crossLinesColor);

		float lineHLength = getDataQuadrantWidth() + axisWidth;

		if (axisYPosition == AXIS_Y_POSITION_LEFT) {
			PointF boxHS = new PointF(borderWidth, touchPoint.y
					- latitudeFontSize / 2f - 2);
			PointF boxHE = new PointF(borderWidth + axisYTitleQuadrantWidth,
					touchPoint.y + latitudeFontSize / 2f + 2);

			// draw text
			float graduate = Float.valueOf(getAxisYGraduate(touchPoint.y));
			drawAlphaTextBox(boxHS, boxHE,formatAxisYDegree(graduate * (maxValue - minValue)
					+ minValue),
					latitudeFontSize, canvas);

			canvas.drawLine(borderWidth + axisYTitleQuadrantWidth, touchPoint.y,
					borderWidth + axisYTitleQuadrantWidth + lineHLength,
					touchPoint.y, mPaint);
		} else {
			PointF boxHS = new PointF(super.getWidth() - borderWidth
					- axisYTitleQuadrantWidth, touchPoint.y - latitudeFontSize
					/ 2f - 2);
			PointF boxHE = new PointF(super.getWidth() - borderWidth,
					touchPoint.y + latitudeFontSize / 2f + 2);

			// draw text
			float graduate = Float.valueOf(getAxisYGraduate(touchPoint.y));
			drawAlphaTextBox(boxHS, boxHE, formatAxisYDegree(graduate * (maxValue - minValue)
					+ minValue),
					latitudeFontSize, canvas);

			canvas.drawLine(borderWidth, touchPoint.y, borderWidth + lineHLength,
					touchPoint.y, mPaint);
		}

	}
	
	protected void drawVerticalLine(Canvas canvas) {

		if (!displayLongitudeTitle) {
			return;
		}
		if (!displayCrossXOnTouch) {
			return;
		}
		if (touchPoint == null) {
			return;
		}
		if (touchPoint.x <= 0) {
			return;
		}

		Paint mPaint = new Paint();
		mPaint.setColor(crossLinesColor);

		float lineVLength = getDataQuadrantHeight() + axisWidth;

		// TODO calculate points to draw
		PointF boxVS = new PointF(touchPoint.x - longitudeFontSize * 6f / 2f,
				borderWidth + lineVLength);
		PointF boxVE = new PointF(touchPoint.x + longitudeFontSize * 6f / 2f,
				borderWidth + lineVLength + axisXTitleQuadrantHeight);

		// draw text
		float graduate = Float.valueOf(getAxisXGraduate(touchPoint.x));
		int index = (int) Math.floor(graduate * displayNumber);

		if (index >= displayNumber) {
			index = displayNumber - 1;
		} else if (index < 0) {
			index = 0;
		}
		index = index + displayFrom;
		drawAlphaTextBox(boxVS, boxVE, formatAxisXDegree(stickData.get(index).getDate()),
				longitudeFontSize, canvas);

		canvas.drawLine(touchPoint.x, borderWidth, touchPoint.x, lineVLength,
				mPaint);
	}
	
	/** 
	 * @Description: X轴刻度
	 * @param value
	 * @return 
	 * @return String
	 */
	public String getAxisXGraduate(Object value) {
		float valueLength = ((Float) value).floatValue()
				- getDataQuadrantPaddingStartX();
		return String.valueOf(valueLength / this.getDataQuadrantPaddingWidth());
	}
	
	/** 
	 * @Description: Y轴刻度
	 * @param value
	 * @return 
	 * @return String
	 */
	public String getAxisYGraduate(Object value) {
		float valueLength = ((Float) value).floatValue()
				- getDataQuadrantPaddingStartY();
		return String
				.valueOf(1f - valueLength / getDataQuadrantPaddingHeight());
	}
	
	/** 
	 * @Description: 触点刻度显示框
	 * @param ptStart
	 * @param ptEnd
	 * @param content
	 * @param fontSize
	 * @param canvas 
	 * @return void
	 */
	private void drawAlphaTextBox(PointF ptStart, PointF ptEnd, String content,
			int fontSize, Canvas canvas) {
		Paint mPaintBox = new Paint();
		mPaintBox.setColor(Color.WHITE);
		mPaintBox.setAlpha(80);
		mPaintBox.setStyle(Style.FILL);

		Paint mPaintBoxLine = new Paint();
		mPaintBoxLine.setColor(crossLinesColor);
		mPaintBoxLine.setAntiAlias(true);
		mPaintBoxLine.setTextSize(fontSize);

		// draw a rectangle
		canvas.drawRect(ptStart.x, ptStart.y, ptEnd.x, ptEnd.y, mPaintBox);

		// draw a rectangle' border
		canvas.drawLine(ptStart.x, ptStart.y, ptStart.x, ptEnd.y, mPaintBoxLine);
		canvas.drawLine(ptStart.x, ptEnd.y, ptEnd.x, ptEnd.y, mPaintBoxLine);
		canvas.drawLine(ptEnd.x, ptEnd.y, ptEnd.x, ptStart.y, mPaintBoxLine);
		canvas.drawLine(ptEnd.x, ptStart.y, ptStart.x, ptStart.y, mPaintBoxLine);

		mPaintBoxLine.setColor(crossLinesFontColor);
		// draw text
		canvas.drawText(content, ptStart.x, ptStart.y + fontSize, mPaintBoxLine);
	}
	
	/** 
	 * @Description: 获取象限的宽度
	 * @return 
	 * @return float
	 */
	protected float getDataQuadrantWidth() {
		return super.getWidth() - axisYTitleQuadrantWidth - 2 * borderWidth
				- axisWidth;
	}

	/** 
	 * @Description: 获取象限的高度
	 * @return 
	 * @return float
	 */
	protected float getDataQuadrantHeight() {
		return SlipStickChart.super.getHeight() - axisXTitleQuadrantHeight - 2 * borderWidth
				- axisWidth;
	}
	
	/** 
	 * @Description: 经线间距
	 * @return 
	 * @return float
	 */
	public float longitudePostOffset(){
		return this.getDataQuadrantPaddingWidth() / (longitudeTitles.size() - 1);
	}
	
	/** 
	 * @Description: 经线起始偏移
	 * @return 
	 * @return float
	 */
	public float longitudeOffset(){
		return getDataQuadrantPaddingStartX();
	}
	
	/** 
	 * @Description: 象限内边距X坐标
	 * @return 
	 * @return float
	 */
	protected float getDataQuadrantPaddingStartX() {
		return getDataQuadrantStartX() + dataQuadrantPaddingLeft;
	}
	
	/** 
	 * @Description: 象限起始X坐标
	 * @return 
	 * @return float
	 */
	protected float getDataQuadrantStartX() {
		if (axisYPosition == AXIS_Y_POSITION_LEFT) {
			return borderWidth + axisYTitleQuadrantWidth + axisWidth;
		} else {
			return borderWidth;
		}
	}
	
	/** 
	 * @Description: 象限内边距Y坐标
	 * @return 
	 * @return float
	 */
	protected float getDataQuadrantPaddingStartY() {
		return getDataQuadrantStartY() + dataQuadrantPaddingTop;
	}
	
	/** 
	 * @Description: 象限起始Y坐标
	 * @return 
	 * @return float
	 */
	protected float getDataQuadrantStartY() {
		return borderWidth;
	}
	
	/** 
	 * @Description: 象限内边距X坐标
	 * @return 
	 * @return float
	 */
	protected float getDataQuadrantPaddingEndX() {
		return getDataQuadrantEndX() - dataQuadrantPaddingRight;
	}
	
	/** 
	 * @Description: 象限终止X坐标
	 * @return 
	 * @return float
	 */
	protected float getDataQuadrantEndX() {
		if (axisYPosition == AXIS_Y_POSITION_LEFT) {
			return super.getWidth() - borderWidth;
		} else {
			return super.getWidth() - borderWidth - axisYTitleQuadrantWidth
					- axisWidth;
		}
	}
	
	/** 
	 * @Description: 象限终止X坐标
	 * @return 
	 * @return float
	 */
	protected float getDataQuadrantEndY() {
		return super.getHeight() - borderWidth - axisXTitleQuadrantHeight
				- axisWidth;
	}

	/** 
	 * @Description: 象限内边距Y坐标
	 * @return 
	 * @return float
	 */
	protected float getDataQuadrantPaddingEndY() {
		return getDataQuadrantEndY() - dataQuadrantPaddingBottom;
	}
	
	/** 
	 * @Description: 获取象限内边距padding
	 * @return 
	 * @return float
	 */
	protected float getDataQuadrantPaddingWidth() {
		return getDataQuadrantWidth() - dataQuadrantPaddingLeft
				- dataQuadrantPaddingRight;
	}
	
	
	/** 
	 * @Description: 获取象限内边距padding
	 * @return 
	 * @return float
	 */
	protected float getDataQuadrantPaddingHeight() {
		return getDataQuadrantHeight() - dataQuadrantPaddingTop
				- dataQuadrantPaddingBottom;
	}
	
	/** 
	 * @Description: 判断有效区域
	 * @param x
	 * @param y
	 * @return 
	 * @return boolean
	 */
	protected boolean isValidTouchPoint (float x , float y) {
		if (x < getDataQuadrantPaddingStartX()
				|| x > getDataQuadrantPaddingEndX()) {
			return false;
		}
		if (y < getDataQuadrantPaddingStartY()
				|| y > getDataQuadrantPaddingEndY()) {
			return false;
		}
		return true;
	}
	
	/** 
	 * @param moveXdistance 
	 * @Description: 触摸后移动
	 * @return void
	 */
	public void moveRight() {
		int dataSize = stickData.size();
		if (displayFrom + displayNumber < dataSize - SLIP_STEP) {
			displayFrom = displayFrom + SLIP_STEP;
		} else {
			displayFrom = dataSize - displayNumber;
		}
	
		// 处理displayFrom越界
		if (displayFrom + displayNumber >= dataSize) {
			displayFrom = dataSize - displayNumber;
		}
	}

	
	/** 
	 * @Description: 触摸后移动
	 * @return void
	 */
	public void moveLeft() {
		int dataSize = stickData.size();
		
		if (displayFrom <= SLIP_STEP) {
			displayFrom = 0;
		} else if (displayFrom > SLIP_STEP) {
			displayFrom = displayFrom - SLIP_STEP;
		} else {

		}

		// 处理displayFrom越界
		if (displayFrom + displayNumber >= dataSize) {
			displayFrom = dataSize - displayNumber;
		}
	}

	/** 
	 * @Description: 移动的距离
	 * @param event
	 * @return 
	 * @return float
	 */
	protected float calcDistance(MotionEvent event) {
		if(event.getPointerCount() <= 1) {
			return 0f;
		}else{
			float x = event.getX(0) - event.getX(1);
			float y = event.getY(0) - event.getY(1);
			return FloatMath.sqrt(x * x + y * y);
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(measureWidth(widthMeasureSpec),
				measureHeight(heightMeasureSpec));
	}
	
	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else if (specMode == MeasureSpec.AT_MOST) {
			result = Math.min(result, specSize);
		}
		return result;
	}
	
	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else if (specMode == MeasureSpec.AT_MOST) {
			result = Math.min(result, specSize);
		}
		return result;
	}
	
	public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }
	
	/** 
	 * @Description: 当前选中的柱条
	 * @return int
	 */
	public int getSelectedIndex() {
		if (null == touchPoint) {
			return 0;
		}
		float graduate = Float.valueOf(getAxisXGraduate(getTouchPoint().x));
		int index = (int) Math.floor(graduate * displayNumber);

		if (index >= displayNumber) {
			index = displayNumber - 1;
		} else if (index < 0) {
			index = 0;
		}

		return index;
	}
	
	
	/**
	 * @return the touchPoint
	 */
	public PointF getTouchPoint() {
		return touchPoint;
	}

	/**
	 * @param touchPoint
	 *            the touchPoint to set
	 */
	public void setTouchPoint(PointF touchPoint) {
		this.touchPoint = touchPoint;
	}

	/**
	 * @return the axisXPosition
	 */
	public int getAxisXPosition() {
		return axisXPosition;
	}

	/**
	 * @param axisXPosition
	 *            the axisXPosition to set
	 */
	public void setAxisXPosition(int axisXPosition) {
		this.axisXPosition = axisXPosition;
	}

	/**
	 * @return the axisYPosition
	 */
	public int getAxisYPosition() {
		return axisYPosition;
	}

	/**
	 * @param axisYPosition
	 *            the axisYPosition to set
	 */
	public void setAxisYPosition(int axisYPosition) {
		this.axisYPosition = axisYPosition;
	}
	
	/**
	 * @param axisXColor
	 *            the axisXColor to set
	 */
	public void setAxisXColor(int axisXColor) {
		this.axisXColor = axisXColor;
	}

	/**
	 * @return the axisYColor
	 */
	public int getAxisYColor() {
		return axisYColor;
	}

	/**
	 * @param axisYColor
	 *            the axisYColor to set
	 */
	public void setAxisYColor(int axisYColor) {
		this.axisYColor = axisYColor;
	}
	
	/**
	 * @return the crossLinesColor
	 */
	public int getCrossLinesColor() {
		return crossLinesColor;
	}

	/**
	 * @param crossLinesColor
	 *            the crossLinesColor to set
	 */
	public void setCrossLinesColor(int crossLinesColor) {
		this.crossLinesColor = crossLinesColor;
	}
	
	/**
	 * @param crossLinesFontColor
	 *            the crossLinesFontColor to set
	 */
	public void setCrossLinesFontColor(int crossLinesFontColor) {
		this.crossLinesFontColor = crossLinesFontColor;
	}
	
	/**
	 * @return the longitudeColor
	 */
	public int getLongitudeColor() {
		return longitudeColor;
	}

	/**
	 * @param longitudeColor
	 *            the longitudeColor to set
	 */
	public void setLongitudeColor(int longitudeColor) {
		this.longitudeColor = longitudeColor;
	}
	
	/**
	 * @return the latitudeColor
	 */
	public int getLatitudeColor() {
		return latitudeColor;
	}

	/**
	 * @param latitudeColor
	 *            the latitudeColor to set
	 */
	public void setLatitudeColor(int latitudeColor) {
		this.latitudeColor = latitudeColor;
	}
	
	/**
	 * @return the longitudeFontColor
	 */
	public int getLongitudeFontColor() {
		return longitudeFontColor;
	}

	/**
	 * @param longitudeFontColor
	 *            the longitudeFontColor to set
	 */
	public void setLongitudeFontColor(int longitudeFontColor) {
		this.longitudeFontColor = longitudeFontColor;
	}
	
	/**
	 * @param latitudeFontColor
	 *            the latitudeFontColor to set
	 */
	public void setLatitudeFontColor(int latitudeFontColor) {
		this.latitudeFontColor = latitudeFontColor;
	}
	
	/**
	 * @param displayLongitudeTitle
	 *            the displayLongitudeTitle to set
	 */
	public void setDisplayLongitudeTitle(boolean displayLongitudeTitle) {
		this.displayLongitudeTitle = displayLongitudeTitle;
	}
	
	/**
	 * @param displayLatitudeTitle
	 *            the displayLatitudeTitle to set
	 */
	public void setDisplayLatitudeTitle(boolean displayLatitudeTitle) {
		this.displayLatitudeTitle = displayLatitudeTitle;
	}
	
	/**
	 * @return the latitudeFontColor
	 */
	public int getLatitudeFontColor() {
		return latitudeFontColor;
	}

	/**
	 * @return the displayLongitudeTitle
	 */
	public boolean isDisplayLongitudeTitle() {
		return displayLongitudeTitle;
	}
	
	/**
	 * @return the displayAxisYTitle
	 */
	public boolean isDisplayLatitudeTitle() {
		return displayLatitudeTitle;
	}
	
	/**
	 * @return the displayLatitude
	 */
	public boolean isDisplayLatitude() {
		return displayLatitude;
	}

	/**
	 * @param displayLatitude
	 *            the displayLatitude to set
	 */
	public void setDisplayLatitude(boolean displayLatitude) {
		this.displayLatitude = displayLatitude;
	}
	
	/**
	 * @return the displayLongitude
	 */
	public boolean isDisplayLongitude() {
		return displayLongitude;
	}

	/**
	 * @param displayLongitude
	 *            the displayLongitude to set
	 */
	public void setDisplayLongitude(boolean displayLongitude) {
		this.displayLongitude = displayLongitude;
	}
	
	/**
	 * @return the longitudeFontSize
	 */
	public int getLongitudeFontSize() {
		return longitudeFontSize;
	}

	/**
	 * @param longitudeFontSize
	 *            the longitudeFontSize to set
	 */
	public void setLongitudeFontSize(int longitudeFontSize) {
		this.longitudeFontSize = longitudeFontSize;
	}
	
	/**
	 * @return the latitudeFontSize
	 */
	public int getLatitudeFontSize() {
		return latitudeFontSize;
	}

	/**
	 * @param latitudeFontSize
	 *            the latitudeFontSize to set
	 */
	public void setLatitudeFontSize(int latitudeFontSize) {
		this.latitudeFontSize = latitudeFontSize;
	}
	
	/**
	 * @return the crossLinesFontColor
	 */
	public int getCrossLinesFontColor() {
		return crossLinesFontColor;
	}
	
	/**
	 * @return the borderColor
	 */
	public int getBorderColor() {
		return borderColor;
	}

	/**
	 * @param borderColor
	 *            the borderColor to set
	 */
	public void setBorderColor(int borderColor) {
		this.borderColor = borderColor;
	}
	
	/**
	 * @return the displayCrossXOnTouch
	 */
	public boolean isDisplayCrossXOnTouch() {
		return displayCrossXOnTouch;
	}

	/**
	 * @param displayCrossXOnTouch
	 *            the displayCrossXOnTouch to set
	 */
	public void setDisplayCrossXOnTouch(boolean displayCrossXOnTouch) {
		this.displayCrossXOnTouch = displayCrossXOnTouch;
	}
	
	/**
	 * @return the displayCrossYOnTouch
	 */
	public boolean isDisplayCrossYOnTouch() {
		return displayCrossYOnTouch;
	}

	/**
	 * @param displayCrossYOnTouch
	 *            the displayCrossYOnTouch to set
	 */
	public void setDisplayCrossYOnTouch(boolean displayCrossYOnTouch) {
		this.displayCrossYOnTouch = displayCrossYOnTouch;
	}
	
	/**
	 * @return the latitudeNum
	 */
	public int getLatitudeNum() {
		return latitudeNum;
	}

	/**
	 * @param latitudeNum
	 *            the latitudeNum to set
	 */
	public void setLatitudeNum(int latitudeNum) {
		this.latitudeNum = latitudeNum;
	}

	/**
	 * @return the longitudeNum
	 */
	public int getLongitudeNum() {
		return longitudeNum;
	}

	/**
	 * @param longitudeNum
	 *            the longitudeNum to set
	 */
	public void setLongitudeNum(int longitudeNum) {
		this.longitudeNum = longitudeNum;
	}
	
	/**
	 * @return the longitudeTitles
	 */
	public List<String> getLongitudeTitles() {
		return longitudeTitles;
	}

	/**
	 * @param longitudeTitles
	 *            the longitudeTitles to set
	 */
	public void setLongitudeTitles(List<String> longitudeTitles) {
		this.longitudeTitles = longitudeTitles;
	}

	/**
	 * @return the latitudeTitles
	 */
	public List<String> getLatitudeTitles() {
		return latitudeTitles;
	}

	/**
	 * @param latitudeTitles
	 *            the latitudeTitles to set
	 */
	public void setLatitudeTitles(List<String> latitudeTitles) {
		this.latitudeTitles = latitudeTitles;
	}

	/**
	 * @return the latitudeMaxTitleLength
	 */
	public int getLatitudeMaxTitleLength() {
		return latitudeMaxTitleLength;
	}

	/**
	 * @param latitudeMaxTitleLength
	 *            the latitudeMaxTitleLength to set
	 */
	public void setLatitudeMaxTitleLength(int latitudeMaxTitleLength) {
		this.latitudeMaxTitleLength = latitudeMaxTitleLength;
	}
	
	/**
	 * @return the axisMarginLeft
	 */
	public float getAxisYTitleQuadrantWidth() {
		return axisYTitleQuadrantWidth;
	}

	/**
	 * @param axisYTitleQuadrantWidth
	 *            the axisYTitleQuadrantWidth to set
	 */
	public void setAxisYTitleQuadrantWidth(float axisYTitleQuadrantWidth) {
		this.axisYTitleQuadrantWidth = axisYTitleQuadrantWidth;
	}
	
	/**
	 * @return the axisXTitleQuadrantHeight
	 */
	public float getAxisXTitleQuadrantHeight() {
		return axisXTitleQuadrantHeight;
	}

	/**
	 * @param axisXTitleQuadrantHeight
	 *            the axisXTitleQuadrantHeight to set
	 */
	public void setAxisXTitleQuadrantHeight(float axisXTitleQuadrantHeight) {
		this.axisXTitleQuadrantHeight = axisXTitleQuadrantHeight;
	}
	
	/**
	 * @return the dataQuadrantPaddingTop
	 */
	public float getDataQuadrantPaddingTop() {
		return dataQuadrantPaddingTop;
	}

	/**
	 * @param dataQuadrantPaddingTop
	 *            the dataQuadrantPaddingTop to set
	 */
	public void setDataQuadrantPaddingTop(float dataQuadrantPaddingTop) {
		this.dataQuadrantPaddingTop = dataQuadrantPaddingTop;
	}

	/**
	 * @return the dataQuadrantPaddingLeft
	 */
	public float getDataQuadrantPaddingLeft() {
		return dataQuadrantPaddingLeft;
	}

	/**
	 * @param dataQuadrantPaddingLeft
	 *            the dataQuadrantPaddingLeft to set
	 */
	public void setDataQuadrantPaddingLeft(float dataQuadrantPaddingLeft) {
		this.dataQuadrantPaddingLeft = dataQuadrantPaddingLeft;
	}

	/**
	 * @return the dataQuadrantPaddingBottom
	 */
	public float getDataQuadrantPaddingBottom() {
		return dataQuadrantPaddingBottom;
	}

	/**
	 * @param dataQuadrantPaddingBottom
	 *            the dataQuadrantPaddingBottom to set
	 */
	public void setDataQuadrantPaddingBottom(float dataQuadrantPaddingBottom) {
		this.dataQuadrantPaddingBottom = dataQuadrantPaddingBottom;
	}

	/**
	 * @return the dataQuadrantPaddingRight
	 */
	public float getDataQuadrantPaddingRight() {
		return dataQuadrantPaddingRight;
	}

	/**
	 * @param dataQuadrantPaddingRight
	 *            the dataQuadrantPaddingRight to set
	 */
	public void setDataQuadrantPaddingRight(float dataQuadrantPaddingRight) {
		this.dataQuadrantPaddingRight = dataQuadrantPaddingRight;
	}

	/**
	 * @param padding
	 *            the dataQuadrantPaddingTop dataQuadrantPaddingBottom
	 *            dataQuadrantPaddingLeft dataQuadrantPaddingRight to set
	 * 
	 */
	public void setDataQuadrantPadding(float padding) {
		this.dataQuadrantPaddingTop = padding;
		this.dataQuadrantPaddingLeft = padding;
		this.dataQuadrantPaddingBottom = padding;
		this.dataQuadrantPaddingRight = padding;
	}

	/**
	 * @param topnbottom
	 *            the dataQuadrantPaddingTop dataQuadrantPaddingBottom to set
	 * @param leftnright
	 *            the dataQuadrantPaddingLeft dataQuadrantPaddingRight to set
	 * 
	 */
	public void setDataQuadrantPadding(float topnbottom, float leftnright) {
		this.dataQuadrantPaddingTop = topnbottom;
		this.dataQuadrantPaddingLeft = leftnright;
		this.dataQuadrantPaddingBottom = topnbottom;
		this.dataQuadrantPaddingRight = leftnright;
	}

	/**
	 * @param top
	 *            the dataQuadrantPaddingTop to set
	 * @param right
	 *            the dataQuadrantPaddingLeft to set
	 * @param bottom
	 *            the dataQuadrantPaddingBottom to set
	 * @param left
	 *            the dataQuadrantPaddingRight to set
	 * 
	 */
	public void setDataQuadrantPadding(float top, float right, float bottom,
			float left) {
		this.dataQuadrantPaddingTop = top;
		this.dataQuadrantPaddingLeft = right;
		this.dataQuadrantPaddingBottom = bottom;
		this.dataQuadrantPaddingRight = left;
	}

	class OnDrawThread extends Thread{
		private boolean isInterrupt = false;
		@Override
		public void run() {
			while(!isInterrupt){
				Draw();
			}
		}
		
		public void exit() {
	        // 将done设置为true 使线程中的while循环结束。
			onDrawThread.isInterrupt = true;;
	        try {
	        	//当主线程处理完其他的事务后，需要用到子线程的处理结果，这个时候就要用到join()
	            join();
	        } catch (Exception e) {

	        }
	    }
		
	}

	private void Draw() {
		Canvas canvas = null;
		synchronized(surfaceHolder){
			try {
					canvas = surfaceHolder.lockCanvas();
					canvas.save();
					canvas.drawColor(Color.BLACK, Mode.CLEAR);
					if (autoCalcValueRange) {
						calcValueRange();
					}
					initAxisY();
					initAxisX();

					drawXAxis(canvas);
					drawYAxis(canvas);
					if (this.displayBorder) {
						drawBorder(canvas);
					}

					if (displayLongitude || displayLongitudeTitle) {
						drawLongitudeLine(canvas);
						drawLongitudeTitle(canvas);
					}
					if (displayLatitude || displayLatitudeTitle) {
						drawLatitudeLine(canvas);
						drawLatitudeTitle(canvas);
					}

					if (displayCrossXOnTouch || displayCrossYOnTouch) {
						drawHorizontalLine(canvas);
						drawVerticalLine(canvas);

					}
					
					drawSticks(canvas);
					canvas.restore();
				} catch (Exception e) {
					e.printStackTrace();
					Log.d("GridChart", "GridChart OnDrawThread error");
				}finally{
					if(canvas != null)
						surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		onDrawThread = new  OnDrawThread();
		onDrawThread.start();
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if(onDrawThread != null){
			onDrawThread.exit();
			onDrawThread = null;
		}
	}
	
//	public static final int DEFAULT_STICK_ALIGN_TYPE = ALIGN_TYPE_CENTER;
	
	public static final int DEFAULT_DISPLAY_FROM = 0;
	public static final int DEFAULT_DISPLAY_NUMBER = 50;
	public static final int DEFAULT_MIN_DISPLAY_NUMBER = 20;
//	public static final int DEFAULT_ZOOM_BASE_LINE = ZOOM_BASE_LINE_CENTER;
	public static final boolean DEFAULT_AUTO_CALC_VALUE_RANGE = true;

	public static final int DEFAULT_STICK_SPACING = 1;

	public static final int DEFAULT_DATA_MULTIPLE = 1;
	public static final String DEFAULT_AXIS_Y_DECIMAL_FORMAT = "#,##0";
	public static final String DEFAULT_AXIS_X_DATE_TARGET_FORMAT = "yyyy/MM/dd";
	public static final String DEFAULT_AXIS_X_DATE_SOURCE_FORMAT = "yyyyMMdd";
	
	protected int displayFrom = DEFAULT_DISPLAY_FROM;
	protected int displayNumber = DEFAULT_DISPLAY_NUMBER;
	protected int minDisplayNumber = DEFAULT_MIN_DISPLAY_NUMBER;
//	protected int zoomBaseLine = DEFAULT_ZOOM_BASE_LINE;
	protected boolean autoCalcValueRange = DEFAULT_AUTO_CALC_VALUE_RANGE;
	protected int stickSpacing = DEFAULT_STICK_SPACING;
	
	protected int dataMultiple =  DEFAULT_DATA_MULTIPLE;
	protected String axisYDecimalFormat = DEFAULT_AXIS_Y_DECIMAL_FORMAT;
	protected String axisXDateTargetFormat = DEFAULT_AXIS_X_DATE_TARGET_FORMAT;
	protected String axisXDateSourceFormat = DEFAULT_AXIS_X_DATE_SOURCE_FORMAT;
	
	protected double maxValue,minValue;
	protected IChartData<IStickEntity> stickData;
	
	public boolean isAutoCalcValueRange() {
		return autoCalcValueRange;
	}
	
	protected void calcValueRangePaddingZero() {
		double maxValue = SlipStickChart.this.maxValue;
		double minValue = SlipStickChart.this.minValue;

		if ((long) maxValue > (long) minValue) {
			if ((maxValue - minValue) < 10 && minValue > 1) {
				SlipStickChart.this.maxValue = (long) (maxValue + 1);
				SlipStickChart.this.minValue = (long) (minValue - 1);
			} else {
				SlipStickChart.this.maxValue = (long) (maxValue + (maxValue - minValue) * 0.1);
				SlipStickChart.this.minValue = (long) (minValue - (maxValue - minValue) * 0.1);
				if (SlipStickChart.this.minValue < 0) {
					SlipStickChart.this.minValue = 0;
				}
			}
		} else if ((long) maxValue == (long) minValue) {
			if (maxValue <= 10 && maxValue > 1) {
				SlipStickChart.this.maxValue = maxValue + 1;
				SlipStickChart.this.minValue = minValue - 1;
			} else if (maxValue <= 100 && maxValue > 10) {
				SlipStickChart.this.maxValue = maxValue + 10;
				SlipStickChart.this.minValue = minValue - 10;
			} else if (maxValue <= 1000 && maxValue > 100) {
				SlipStickChart.this.maxValue = maxValue + 100;
				SlipStickChart.this.minValue = minValue - 100;
			} else if (maxValue <= 10000 && maxValue > 1000) {
				SlipStickChart.this.maxValue = maxValue + 1000;
				SlipStickChart.this.minValue = minValue - 1000;
			} else if (maxValue <= 100000 && maxValue > 10000) {
				SlipStickChart.this.maxValue = maxValue + 10000;
				SlipStickChart.this.minValue = minValue - 10000;
			} else if (maxValue <= 1000000 && maxValue > 100000) {
				SlipStickChart.this.maxValue = maxValue + 100000;
				SlipStickChart.this.minValue = minValue - 100000;
			} else if (maxValue <= 10000000 && maxValue > 1000000) {
				SlipStickChart.this.maxValue = maxValue + 1000000;
				SlipStickChart.this.minValue = minValue - 1000000;
			} else if (maxValue <= 100000000 && maxValue > 10000000) {
				SlipStickChart.this.maxValue = maxValue + 10000000;
				SlipStickChart.this.minValue = minValue - 10000000;
			}
		} else {
			SlipStickChart.this.maxValue = 0;
			SlipStickChart.this.minValue = 0;
		}

	}
	
	protected void calcValueRange() {
		if (null == SlipStickChart.this.stickData) {
			SlipStickChart.this.maxValue = 0;
			SlipStickChart.this.minValue = 0;
			return;
		}

		if (SlipStickChart.this.stickData.size() > 0) {
			SlipStickChart.this.calcDataValueRange();
			SlipStickChart.this.calcValueRangePaddingZero();
		} else {
			SlipStickChart.this.maxValue = 0;
			SlipStickChart.this.minValue = 0;
		}
		SlipStickChart.this.calcValueRangeFormatForAxis();
	}
	
	protected void calcDataValueRange() {

		double maxValue = Double.MIN_VALUE;
		double minValue = Double.MAX_VALUE;

		IMeasurable first = (IMeasurable) SlipStickChart.this.stickData.get(0);
		// 第一个stick为停盘的情况
		if (first.getHigh() == 0 && first.getLow() == 0) {

		} else {
			maxValue = first.getHigh();
			minValue = first.getLow();
		}

		// 判断显示为方柱或显示为线条
		for (int i = SlipStickChart.this.displayFrom; i < SlipStickChart.this.displayFrom
				+ this.displayNumber; i++) {
			IMeasurable stick = (IMeasurable) SlipStickChart.this.stickData.get(i);
			if (stick.getLow() < minValue) {
				minValue = stick.getLow();
			}

			if (stick.getHigh() > maxValue) {
				maxValue = stick.getHigh();
			}

		}

		SlipStickChart.this.maxValue = maxValue;
		SlipStickChart.this.minValue = minValue;
	}
	
	protected void calcValueRangeFormatForAxis() {
		// 修正最大值和最小值
		long rate = (long) (SlipStickChart.this.maxValue - SlipStickChart.this.minValue) / (SlipStickChart.this.latitudeNum);
		String strRate = String.valueOf(rate);
		float first = Integer.parseInt(String.valueOf(strRate.charAt(0))) + 1.0f;
		if (first > 0 && strRate.length() > 1) {
			float second = Integer.parseInt(String.valueOf(strRate.charAt(1)));
			if (second < 5) {
				first = first - 0.5f;
			}
			rate = (long) (first * Math.pow(10, strRate.length() - 1));
		} else {
			rate = 1;
		}
		// 等分轴修正
		if (SlipStickChart.this.latitudeNum > 0
				&& (long) (SlipStickChart.this.maxValue - SlipStickChart.this.minValue)
						% (SlipStickChart.this.latitudeNum * rate) != 0) {
			// 最大值加上轴差
			SlipStickChart.this.maxValue = (long) SlipStickChart.this.maxValue
					+ (SlipStickChart.this.latitudeNum * rate)
					- ((long) (SlipStickChart.this.maxValue - SlipStickChart.this.minValue) % (SlipStickChart.this.latitudeNum * rate));
		}
	}
	
	protected void initAxisY() {
		this.calcValueRange();
		List<String> titleY = new ArrayList<String>();
		double average = (maxValue - minValue) / getLatitudeNum();
		
		// calculate degrees on Y axis
		for (int i = 0; i < getLatitudeNum(); i++) {
			String value = formatAxisYDegree(minValue + i * average);
			// padding space
			if (value.length() < getLatitudeMaxTitleLength()) {
				while (value.length() < getLatitudeMaxTitleLength()) {
					value = " " + value;
				}
			}
			titleY.add(value);
		}
		// calculate last degrees by use max value
		String value = formatAxisYDegree(maxValue);
		// padding space
		if (value.length() < getLatitudeMaxTitleLength()) {
			while (value.length() < getLatitudeMaxTitleLength()) {
				value = " " + value;
			}
		}
		titleY.add(value);

		setLatitudeTitles(titleY);
	}
	
	public String formatAxisYDegree(double value) {
		return new DecimalFormat(axisYDecimalFormat).format(Math.floor(value)/dataMultiple);
	}
	
	protected void initAxisX() {
		List<String> titleX = new ArrayList<String>();
		if (null != stickData && stickData.size() > 0) {
			float average = displayNumber /getLongitudeNum();
			for (int i = 0; i < getLongitudeNum(); i++) {
				int index = (int) Math.floor(i * average);
				if (index > displayNumber - 1) {
					index = displayNumber - 1;
				}
				index = index + displayFrom;
				titleX.add(formatAxisXDegree(stickData.get(index).getDate()));
			}
			titleX.add(formatAxisXDegree(stickData.get(displayFrom + displayNumber - 1).getDate()));
		}
		setLongitudeTitles(titleX);
	}
	
	public String formatAxisXDegree(int date) {
		try {
			Date dt = new SimpleDateFormat(axisXDateSourceFormat).parse(String
					.valueOf(date));
			return new SimpleDateFormat(axisXDateTargetFormat).format(dt);
		} catch (ParseException e) {
			return "";
		}
	}
	
	protected void drawSticks(Canvas canvas) {
		if (null == stickData) {
			return;
		}
		if (stickData.size() == 0) {
			return;
		}

		Paint mPaintStick = new Paint();
		mPaintStick.setColor(stickFillColor);

		float stickWidth = getDataQuadrantPaddingWidth() / displayNumber
				- stickSpacing;
		float stickX = getDataQuadrantPaddingStartX();

		for (int i = displayFrom; i < displayFrom + displayNumber; i++) {
			IMeasurable stick = (IMeasurable) stickData.get(i);
			float highY = (float) ((1f - (stick.getHigh() - minValue)
					/ (maxValue - minValue))
					* (getDataQuadrantPaddingHeight()) + getDataQuadrantPaddingStartY());
			float lowY = (float) ((1f - (stick.getLow() - minValue)
					/ (maxValue - minValue))
					* (getDataQuadrantPaddingHeight()) + getDataQuadrantPaddingStartY());

			// stick or line?
			if (stickWidth >= 2f) {
				canvas.drawRect(stickX, highY, stickX + stickWidth, lowY,
						mPaintStick);
			} else {
				canvas.drawLine(stickX, highY, stickX, lowY, mPaintStick);
			}

			// next x
			stickX = stickX + stickSpacing + stickWidth;
		}
	}
	
	/**
	 * @param stickBorderColor
	 *            the stickBorderColor to set
	 */
	public void setStickBorderColor(int stickBorderColor) {
		this.stickBorderColor = stickBorderColor;
	}

	/**
	 * @return the stickFillColor
	 */
	public int getStickFillColor() {
		return stickFillColor;
	}

	/**
	 * @param stickFillColor
	 *            the stickFillColor to set
	 */
	public void setStickFillColor(int stickFillColor) {
		this.stickFillColor = stickFillColor;
	}
	
	/**
	 * @return the maxValue
	 */
	public double getMaxValue() {
		return maxValue;
	}

	/**
	 * @param maxValue
	 *            the maxValue to set
	 */
	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * @return the minValue
	 */
	public double getMinValue() {
		return minValue;
	}

	/**
	 * @param minValue
	 *            the minValue to set
	 */
	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}
	
	/**
	 * @param displayFrom
	 *            the displayFrom to set
	 */
	public void setDisplayFrom(int displayFrom) {
		this.displayFrom = displayFrom;
	}

	/**
	 * @return the displayNumber
	 */
	public int getDisplayNumber() {
		return displayNumber;
	}

	/**
	 * @param displayNumber
	 *            the displayNumber to set
	 */
	public void setDisplayNumber(int displayNumber) {
		this.displayNumber = displayNumber;
	}

	/**
	 * @return the minDisplayNumber
	 */
	public int getMinDisplayNumber() {
		return minDisplayNumber;
	}

	/**
	 * @param minDisplayNumber
	 *            the minDisplayNumber to set
	 */
	public void setMinDisplayNumber(int minDisplayNumber) {
		this.minDisplayNumber = minDisplayNumber;
	}
	
	/**
	 * @return the dataMultiple
	 */
	public int getDataMultiple() {
		return dataMultiple;
	}

	/**
	 * @param dataMultiple the dataMultiple to set
	 */
	public void setDataMultiple(int dataMultiple) {
		this.dataMultiple = dataMultiple;
	}
	
	/**
	 * @param axisYDecimalFormat the axisYDecimalFormat to set
	 */
	public void setAxisYDecimalFormat(String axisYDecimalFormat) {
		this.axisYDecimalFormat = axisYDecimalFormat;
	}

	/**
	 * @return the axisXDateTargetFormat
	 */
	public String getAxisXDateTargetFormat() {
		return axisXDateTargetFormat;
	}

	/**
	 * @param axisXDateTargetFormat the axisXDateTargetFormat to set
	 */
	public void setAxisXDateTargetFormat(String axisXDateTargetFormat) {
		this.axisXDateTargetFormat = axisXDateTargetFormat;
	}

	/**
	 * @return the axisXDateSourceFormat
	 */
	public String getAxisXDateSourceFormat() {
		return axisXDateSourceFormat;
	}

	/**
	 * @param axisXDateSourceFormat the axisXDateSourceFormat to set
	 */
	public void setAxisXDateSourceFormat(String axisXDateSourceFormat) {
		this.axisXDateSourceFormat = axisXDateSourceFormat;
	}

	public void setStickData(IChartData<IStickEntity> stickData) {
		this.stickData = stickData;
	}
}
