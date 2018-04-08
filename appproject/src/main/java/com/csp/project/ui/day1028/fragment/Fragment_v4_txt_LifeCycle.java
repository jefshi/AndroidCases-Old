package com.project.day1028.fragment;

import com.common.android.control.util.LogUtil;
import com.project.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_v4_txt_LifeCycle extends Fragment {
	private String frgName = getClass().getSimpleName();

	private int			count		= 0;
	private String[]	contents	= new String[] {
			"这个第1个是[Fragment], 访问成功！",
			"\n这个第2个是[Fragment], 访问成功！",
			"这个第3个是[Fragment], 访问成功！",
			"\n这个第4个是[Fragment], 访问成功！",
			"这个第5个是[Fragment], 访问成功！"
	};

	public Fragment_v4_txt_LifeCycle() {
	}

	public static Fragment_v4_txt_LifeCycle newInstance(Object... params) {
		Fragment_v4_txt_LifeCycle frg = new Fragment_v4_txt_LifeCycle();
		Bundle bundle = new Bundle();
		bundle.putInt("Count", (Integer) params[0]);
		frg.setArguments(bundle);
		return frg;
	}

	@Override
	public void onAttach(Activity activity) {
		Bundle bundle = getArguments();
		if (bundle != null) {
			this.count = bundle.getInt("Count");
		}
		
		LogUtil.logInfo(frgName + "." + count + ".onAttach()");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		LogUtil.logInfo(frgName + "." + count + ".onCreate()");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LogUtil.logInfo(frgName + "." + count + ".onCreateView()");
		View view = inflater.inflate(R.layout.template_txt, container, false);
		((TextView) view).setText(contents[count]);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		LogUtil.logInfo(frgName + "." + count + ".onActivityCreated()");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		LogUtil.logInfo(frgName + "." + count + ".onStart()");
		super.onStart();
	}

	@Override
	public void onResume() {
		LogUtil.logInfo(frgName + "." + count + ".onResume()");
		super.onResume();
	}

	@Override
	public void onPause() {
		LogUtil.logInfo(frgName + "." + count + ".onPause()");
		super.onPause();
	}

	@Override
	public void onStop() {
		LogUtil.logInfo(frgName + "." + count + ".onStop()");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		LogUtil.logInfo(frgName + "." + count + ".onDestroyView()");
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		LogUtil.logInfo(frgName + "." + count + ".onDestroy()");
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		LogUtil.logInfo(frgName + "." + count + ".onDetach()");
		super.onDetach();
	}
}
