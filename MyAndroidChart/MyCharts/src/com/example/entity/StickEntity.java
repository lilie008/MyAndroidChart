package com.example.entity;


public class StickEntity implements IStickEntity {

	/**
	 * <p>
	 * High Value
	 * </p>
	 * <p>
	 * 高値
	 * </p>
	 * <p>
	 * 最高值
	 * </p>
	 * 
	 */
	private double high;

	/**
	 * <p>
	 * Low Value
	 * </p>
	 * <p>
	 * 低値
	 * </p>
	 * <p>
	 * 最低值
	 * </p>
	 * 
	 */
	private double low;

	/**
	 * <p>
	 * Date
	 * </p>
	 * <p>
	 * 日付
	 * </p>
	 * <p>
	 * 日期
	 * </p>
	 * 
	 */
	private int date;

	/**
	 * 
	 * <p>
	 * Constructor of StickEntity
	 * </p>
	 * <p>
	 * StickEntity类对象的构造函数
	 * </p>
	 * <p>
	 * StickEntityのコンストラクター
	 * </p>
	 * 
	 * @param high
	 *            <p>
	 *            High Value
	 *            </p>
	 *            <p>
	 *            高値
	 *            </p>
	 *            <p>
	 *            最高价
	 *            </p>
	 * @param low
	 *            <p>
	 *            Low Value
	 *            </p>
	 *            <p>
	 *            低値
	 *            </p>
	 *            <p>
	 *            最低值
	 *            </p>
	 * @param date
	 *            <p>
	 *            Date
	 *            </p>
	 *            <p>
	 *            日付
	 *            </p>
	 *            <p>
	 *            日期
	 *            </p>
	 */
	public StickEntity(double high, double low, int date) {
		super();
		this.high = high;
		this.low = low;
		this.date = date;
	}

	/**
	 * 
	 * <p>
	 * Constructor of StickEntity
	 * </p>
	 * <p>
	 * StickEntity类对象的构造函数
	 * </p>
	 * <p>
	 * StickEntityのコンストラクター
	 * </p>
	 * 
	 */
	public StickEntity() {
		super();
	}

	/**
	 * @return the high
	 */
	public double getHigh() {
		return high;
	}

	/**
	 * @param high
	 *            the high to set
	 */
	public void setHigh(double high) {
		this.high = high;
	}

	/**
	 * @return the low
	 */
	public double getLow() {
		return low;
	}

	/**
	 * @param low
	 *            the low to set
	 */
	public void setLow(double low) {
		this.low = low;
	}

	/**
	 * @return the date
	 */
	public int getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(int date) {
		this.date = date;
	}
}
