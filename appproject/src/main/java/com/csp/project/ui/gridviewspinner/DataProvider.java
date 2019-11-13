package com.project.day1010.gridviewspinner;

import java.util.ArrayList;
import java.util.List;

import com.project.R;

public class DataProvider {
	private static DataProvider dtp;
	
	private DataProvider() {
	}
	
	public static DataProvider getInstance() {
		if (dtp == null) {
			dtp =  new DataProvider();
		}
		return dtp;
	}
	
	public List<Integer> getGridViewData() {
		List<Integer> lsImag = new ArrayList<Integer>();
		
		lsImag.add(R.drawable.png_01);
		lsImag.add(R.drawable.png_02);
		lsImag.add(R.drawable.png_03);
		lsImag.add(R.drawable.png_04);
		lsImag.add(R.drawable.png_05);
		lsImag.add(R.drawable.png_06);
		lsImag.add(R.drawable.png_07);
		lsImag.add(R.drawable.png_08);
		lsImag.add(R.drawable.png_09);
		lsImag.add(R.drawable.png_10);
		lsImag.add(R.drawable.png_11);
		lsImag.add(R.drawable.png_12);
		
        return lsImag;
	}
	
	public List<String> getSpinnerData() {
		List<String> cities = new ArrayList<String>();
		
		cities.add("天津");
		cities.add("上海");
		cities.add("北京");
		cities.add("深圳");
		cities.add("福州");
		cities.add("杭州");
		cities.add("广州");
		cities.add("日照");
		
		return cities;
	}
}
