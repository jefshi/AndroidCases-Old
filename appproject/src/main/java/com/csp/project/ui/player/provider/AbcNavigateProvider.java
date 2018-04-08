package com.project.day1008.player.provider;

import java.util.ArrayList;
import java.util.List;

public class AbcNavigateProvider {

	public List<Character> getAbcChs() {
		List<Character> chs = new ArrayList<Character>();
		for (char i = 'A'; i <= 'Z'; i++) {
			chs.add(i);
		}
		return chs;
	}
	
}
