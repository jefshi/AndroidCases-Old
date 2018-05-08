package com.csp.cases.activity.intent;

import java.io.Serializable;

public class OptionBySer implements Serializable {
	private static final long serialVersionUID = 1L;

	public int x;
	public int y;

	public OptionBySer(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
