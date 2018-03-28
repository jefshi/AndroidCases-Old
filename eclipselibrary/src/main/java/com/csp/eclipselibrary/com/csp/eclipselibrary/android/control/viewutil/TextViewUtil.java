package com.csp.eclipselibrary.com.csp.eclipselibrary.android.control.viewutil;

import android.widget.TextView;

public class TextViewUtil {
	public static int compare(TextView txt01, TextView txt02) {
		return txt01.getText().toString().compareTo(txt02.getText().toString());
	}
}
