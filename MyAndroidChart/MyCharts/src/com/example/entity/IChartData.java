package com.example.entity;

public interface IChartData<T> {
	int size();

	T get(int index);

	boolean hasData();

	boolean hasNoData();

	void add(T data);
}
