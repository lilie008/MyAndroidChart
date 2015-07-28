package com.example.entity;


public interface IStickEntity extends IMeasurable, IHasDate {
	/**
	 * @return the date
	 */
	int getDate();

	/**
	 * @param date
	 *            the date to set
	 */
	void setDate(int date);
}
