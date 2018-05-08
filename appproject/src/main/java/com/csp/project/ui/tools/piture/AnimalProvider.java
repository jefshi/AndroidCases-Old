package com.project.tools.piture;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

public class AnimalProvider {
	private List<Animal> animals = new ArrayList<Animal>();

	public AnimalProvider() {
		File sdcard = Environment.getExternalStorageDirectory();
		String dirPath = sdcard.getAbsoluteFile() + "/DCIM/Camera/";
		
		animals.add(new Animal("01我", dirPath + "/BigPicture.jpg"));
		animals.add(new Animal("02鼠", dirPath + "/png_01.png"));
		animals.add(new Animal("03牛", dirPath + "/png_02.png"));
		animals.add(new Animal("04虎", dirPath + "/png_03.png"));
		animals.add(new Animal("05兔", dirPath + "/png_04.png"));
		animals.add(new Animal("06龙", dirPath + "/png_05.png"));
		animals.add(new Animal("07蛇", dirPath + "/png_06.png"));
		animals.add(new Animal("08马", dirPath + "/png_07.png"));
		animals.add(new Animal("09羊", dirPath + "/png_08.png"));
		animals.add(new Animal("10猴", dirPath + "/png_09.png"));
		animals.add(new Animal("11鸡", dirPath + "/png_10.png"));
		animals.add(new Animal("12狗", dirPath + "/png_11.png"));
		animals.add(new Animal("13猪", dirPath + "/png_12.png"));
		animals.add(new Animal("14猪", dirPath + "/png_14.png"));
		animals.add(new Animal("15猪", dirPath + "/png_15.png"));
		animals.add(new Animal("16猪", dirPath + "/png_16.png"));
		animals.add(new Animal("17猪", dirPath + "/png_17.png"));
		animals.add(new Animal("18猪", dirPath + "/png_18.png"));
		animals.add(new Animal("19猪", dirPath + "/png_19.png"));
		animals.add(new Animal("20猪", dirPath + "/png_20.png"));
		animals.add(new Animal("21猪", dirPath + "/png_21.png"));
		animals.add(new Animal("22猪", dirPath + "/png_22.png"));
		animals.add(new Animal("23猪", dirPath + "/png_23.png"));
	}

	public List<Animal> getAnimals() {
		return animals;
	}
}
