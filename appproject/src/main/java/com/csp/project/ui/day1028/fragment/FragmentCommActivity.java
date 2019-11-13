package com.project.day1028.fragment;

import com.project.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Activity -> Fragment 通讯
 * 1) 提供静态有参实例化方法(传递数据)
 *   a) 目的: 实例化对象的同时, 绑定数据到对象上
 *   b) 方法: static Fragment newInstance(Object... params)
 *   c) 绑定: Fragment.setArguments(Bundle args)
 * 2) Fragment 中获取数据
 *   a) 方法: Bundle bundle = getArguments();
 * 
 * Fragment -> Activity 通讯
 * 1) 添加并初始化观察者对象(添加自定义监听器)
 * 2) [Fragment]中控件事件回调监听事件
 * 
 * @version 1.0
 * @author tarena
 * <p style='font-weight:bold'>Date:</p> 2016年11月1日 上午8:51:23
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class FragmentCommActivity extends Fragment {
	protected View contentView; // 当前内容

	// 当前内容的控件
	protected ImageView	img;
	protected TextView	txt;
	protected Button	btn;

	public FragmentCommActivity() {
	}

	/**
	 * 封装数据用到的Key
	 */
	public static final String	ONE_KEY	= "oneKey";
	public static final String	TWO_KEY	= "twoKey";

	/**
	 * 静态有参实例化方法(传递数据)
	 * @param params 数据
	 */
	public static FragmentCommActivity newInstance(Object... params) {
		// 封装数据
		Bundle bundle = new Bundle();
		bundle.putString(ONE_KEY, (String) params[0]);
		bundle.putInt(TWO_KEY, (Integer) params[1]);

		// 绑定数据
		FragmentCommActivity frg = new FragmentCommActivity();
		frg.setArguments(bundle);
		return frg;
	}

	/**
	 * 初始化UI
	 * 1) 获取从Activity中传入的数据
	 * 2) [Fragment]中控件事件回调监听事件
	 */
	private void initialUi() {
		// 获取控件
		img = (ImageView) contentView.findViewById(R.id.imgTempImgTxtBtn);
		txt = (TextView) contentView.findViewById(R.id.txtTempImgTxtBtn);
		btn = (Button) contentView.findViewById(R.id.btnTempImgTxtBtn);

		// 获取数据
		Bundle bundle = getArguments();
		if (bundle != null) {
			txt.setText(bundle.getString(ONE_KEY));
			img.setImageResource(bundle.getInt(TWO_KEY));
		}

		// Fragment -> Activity 通讯
		btn.setText("点击按钮向[Activity]通讯");
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString(ONE_KEY, "信息与图片由[Fragment]提供");
				bundle.putInt(TWO_KEY, R.drawable.ic_launcher);
				listener.onFragmentInteraction(v, bundle);
			}
		});
	}

	/**
	 * 自定义监听器
	 */
	public interface OnFragmentInteractionListener {
		public void onFragmentInteraction(View view, Bundle bundle);
	}

	/**
	 * 添加观察者对象
	 */
	private OnFragmentInteractionListener listener;

	/**
	 * 初始化观察者对象
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.template_lay_txt_img_btn, container, false);
		initialUi();
		return contentView;
	}

}
