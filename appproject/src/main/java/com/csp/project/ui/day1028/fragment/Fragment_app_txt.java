package com.project.day1028.fragment;

import com.project.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_app_txt extends Fragment {
	public Fragment_app_txt() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.template_txt, container, false);
		((TextView) view).setText("这个是[Fragment], 访问成功！");
		return view;
	}
}
