package com.project.day1008.player.template;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.common.android.control.util.LogUtil;
import com.project.R;
import com.project.day1008.player.provider.Music;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_SongList extends BaseAdapter {

	// Adapter 基础数据
	private Context					mContext;	// 上下文对象
	private List<? extends Music>	mObject;	// 数据源
	private int						mResource;	// 模板选择
	private int[]					mTargetId;
	private LayoutInflater			mInflater;	// 解析XML(一般是Layout), 并初始化对象

	// 根据模板功能追加
	private boolean		canChoose;		// 是否能被选择
	private Set<String>	chooseSet;		// 被选择的item(复数)的序号
	private int			selectPosition;	// 当前被选中(激活)的item
	
	{
		selectPosition = -1;
	}

	public Adapter_SongList(Context context, int resource, List<? extends Music> musics, int[] targetId) {
		mContext = context;
		mObject = musics;
		mResource = resource;
		mTargetId = targetId;
		chooseSet = new HashSet<String>();

		// 解析XML文件
		// 方法01
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 方法02
		// mInflater = LayoutInflater.from(context);
	}

	public void setSelectPosition(int position) {
		selectPosition = position;
	}

	public List<? extends Music> getmObject() {
		return mObject;
	}

	public void setmObject(List<? extends Music> mMusics) {
		this.mObject = mMusics;
	}

	public Set<String> getChooseSet() {
		return chooseSet;
	}

	public void setAllChoose() {
		for (int i = 0, count = getCount(); i < count; i++) {
			chooseSet.add(i + "");
		}
	}

	public void clearChoose() {
		chooseSet.removeAll(chooseSet);
	}

	public boolean isCanChoose() {
		return canChoose;
	}

	public void setCanChoose(boolean canChoose, int position) {
		this.canChoose = canChoose;
		if (!canChoose) {
			chooseSet.removeAll(chooseSet);
		} else {
			chooseSet.add(position + "");
		}
	}

	@Override
	public int getCount() {
		return mObject.size();
	}

	@Override
	public Object getItem(int position) {
		LogUtil.logInfo(mObject.size());
		return mObject.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return createViewFromResource(position, convertView, parent, mResource);
	}

	// @param convertView 推出去的Item的View
	public View createViewFromResource(int position, View convertView, ViewGroup parent,
			int resource) {
		View view = null;
		ViewHolder vHolder = null;
		
		LogUtil.logInfo("pos: " + position);

		// 1.item view, 饺子皮
		if (convertView == null) {
			// 非常耗时与麻烦
			// 方法01
			view = mInflater.inflate(resource, parent, false);
			
			// 方法02
			// view = View.inflate(mContext, resource, parent);
			
			vHolder = new ViewHolder(view);
			view.setTag(vHolder);
		} else {
			view = convertView;
			vHolder = (ViewHolder) view.getTag();
		}
		vHolder.setPosition(position);

		// 2.item data, 饺子馅
		Music music = (Music) getItem(position);

		// 3.bind view, 包饺子, 对号入座
		vHolder.setViewContent(music);
		if (selectPosition == position) {
			view.setBackgroundColor(Color.BLUE);
		} else {
			view.setBackgroundColor(Color.WHITE);
		}

		return view;
	}

	// 通过记录饺子皮的各个点的位置来优化适配器
	// 缺点: 内存占用加大，用空间换时间
	class ViewHolder {
		private int			position;
		private ImageView	imageHead;
		private TextView	txtTitle;
		private TextView	txtArtist;
		private CheckBox	chkChoose;

		public ViewHolder(View view) {
			try {
				position = 0;
				imageHead = (ImageView) view.findViewById(mTargetId[0]);
				txtTitle = (TextView) view.findViewById(mTargetId[1]);
				txtArtist = (TextView) view.findViewById(mTargetId[2]);
				chkChoose = (CheckBox) view.findViewById(R.id.chkChoose);
				chkChoose.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						boolean isChecked = ((CheckBox) v).isChecked();
						if (isChecked) {
							chooseSet.add(position + "");
						} else {
							chooseSet.remove(position + "");
						}
						Log.i("info", "position: " + position);
						Log.i("info", "List: " + chooseSet);
					}
				});
			} catch (NullPointerException e) {
				// TODO: handle exception
				Log.e("error", "失败---------------------------------");
				Log.e("error", e.getMessage());
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("error", "---------------------------------");
			}
		}

		public void setPosition(int position) {
			this.position = position;
		}

		public void setViewContent(Music music) {
			imageHead.setImageResource(music.getHead());
			txtTitle.setText(music.getTitle());
			txtArtist.setText(music.getArtist());
			chkChoose.setVisibility(isCanChoose() ? View.VISIBLE : View.GONE);
			chkChoose.setChecked(chooseSet.contains(position + ""));
		}
	}

	// 根据输入的字符，在列表数据中查找匹配该字符(或匹配更大的字符)的最小序号
	public int getMinPosByTitle(char ch) {
		int position = getCount();
		for (int i = 0, count = position; i < count; i++) {
			char chTop = ((Music) getItem(i)).getTitle().toUpperCase().charAt(0);
			if (chTop >= ch) {
				position = i;
				break;
			}
		}
		return position;
	}
}
