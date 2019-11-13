package com.project.day1008.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.common.android.control.util.LogUtil;
import com.project.R;
import com.project.day1008.player.provider.Music;
import com.project.day1008.player.provider.MusicProvider;
import com.project.day1008.player.template.Adapter_SongList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Loader;
import android.content.Loader.OnLoadCompleteListener;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("DefaultLocale")
public class Activity1008_Player extends Activity {
	private ListView			lsv;
	private ListView			lsvRightNav;
	private ViewStub			vsb;
	private Toast				toast;
	private Adapter_SongList	adapter;
	private ArrayAdapter<?>		rightNavAdapter;

	private List<Music> musics;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_day1008_player);

		lsv = (ListView) findViewById(R.id.lsvName);
		vsb = (ViewStub) findViewById(R.id.vsbChooseAll);

		lsvRightNav = (ListView) findViewById(R.id.lsvRightNav);
		LogUtil.logInfo(lsvRightNav.toString());

		toast = Toast.makeText(this, "", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setText("");
		toast.show();

		// 设置歌曲列表、导航列表
		setSongList();
		setRightNavi();

		setSongListEvent();
		setRightNaviEvent();

		// 设置自动补全
		AutoCompleteTextView act = (AutoCompleteTextView) findViewById(R.id.actSong);
		setActSong(act);

		// 设置检索
		SearchView srv = (SearchView) findViewById(R.id.srvSong);
		srv.onActionViewExpanded(); // 展开, 即光标锁定
		// srv.onActionViewCollapsed();

		srv.setOnQueryTextListener(getSrvSongListEnvent());
		
		MusicProvider.getInstance().runMusicLoadAsyncTask(getApplicationContext());
	}

	/**
	 * 
	 */
	public OnLoadCompleteListener<List<Music>> getOnLoadCompleteListener() {
		return new OnLoadCompleteListener<List<Music>>() {

			@Override
			public void onLoadComplete(Loader<List<Music>> loader, List<Music> data) {
				musics = data;
				adapter.setmObject(musics);
				adapter.notifyDataSetChanged();
				LogUtil.logInfo("监听成功");
			}
		};
	}

	// 自定义Adapter
	protected void setSongList() {
		// 1. 数据
		musics = MusicProvider.getInstance().getMusics();
		if (musics == null) {
			musics = new ArrayList<Music>();
			
			MusicProvider.getInstance().setOnLoadCompleteListener(getOnLoadCompleteListener());
		}

		// 2. listview
		ListView listView = (ListView) findViewById(R.id.lsvName);

		// 3.adapter
		adapter = new Adapter_SongList(
				this, R.layout.activity_day1008_player_song_list_music,
				musics,
				new int[] { R.id.imgHead, R.id.txtTitle, R.id.txtArtist });

		// 4. 关联适配器
		// 底层自动调用getCount()、getView()
		listView.setAdapter(adapter);
	}

	// 设置事件
	private void setSongListEvent() {
		// 单击事件
		//		View musicView = findViewById(R.layout.activity_day1008_adapter_music);
		//		ImageView imgHead = (ImageView) musicView.findViewById(R.id.imgHead);
		//		imgHead.setOnClickListener(new OnClickListener() {
		//			@Override
		//			public void onClick(View v) {
		//				Log.i("info", "头像单击成功");
		//			}
		//		});

		// 双击事件

		// 单击事件
		// lsv.setOnClickListener(this); // 这个不能用在ListView上
		lsv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.i("info", "单击成功" + position);
				Adapter_SongList adapter = (Adapter_SongList) parent.getAdapter();
				adapter.setSelectPosition(position);
				adapter.notifyDataSetChanged();
			}
		});

		// 长按事件
		// lsv.setOnLongClickListener(this); // 这个不能用在ListView上
		lsv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Log.i("info", "长按成功" + position);
				adapter.setCanChoose(true, position);
				adapter.notifyDataSetChanged();

				// 长按显示全选与删除功能
				View viewLayout = vsb.inflate();
				viewLayout.setVisibility(View.VISIBLE);
				new Day1010_ChooseViewStub().setEvent(viewLayout, adapter);
				return true;
			}
		});

		// 滚动事件
		lsv.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 滚动停止时
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					Music music = (Music) view.getItemAtPosition(view.getFirstVisiblePosition());
					char nameTop = music.getTitle().toUpperCase(Locale.CHINA).charAt(0);

					lsvRightNav.setItemChecked(nameTop - 'A', true);
					rightNavAdapter.notifyDataSetChanged();
					Log.i("info", "改变滚动了-------------");
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// Music music = (Music) view.getItemAtPosition(firstVisibleItem);
				// Log.i("info", "滚动了-------------");
			}
		});

	}

	// 点击回退键，运行此方法内容
	@Override
	public void onBackPressed() {
		if (adapter != null && adapter.isCanChoose()) {
			adapter.setCanChoose(false, -1);
			adapter.notifyDataSetChanged();

			vsb.setVisibility(View.GONE);
			return;
		}
		// 销毁该Activity
		super.onBackPressed();
	}

	// 初始化右侧导航
	private void setRightNavi() {
		List<Character> chs = new ArrayList<Character>();
		for (char i = 'A'; i <= 'Z'; i++) {
			chs.add(i);
		}

		// 选择模式
		lsvRightNav.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lsvRightNav.setItemChecked(0, true);
		rightNavAdapter = new ArrayAdapter<Character>(
				this, R.layout.activity_day1008_player_song_list_txt, chs) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView view = (TextView) super.getView(position, convertView, parent);

				// 改变颜色
				int clickPos = lsvRightNav.getCheckedItemPosition();
				if (clickPos == position) {
					view.setTextColor(Color.RED);
				} else {
					view.setTextColor(Color.BLACK);
				}
				return view;
			}
		};

		lsvRightNav.setAdapter(rightNavAdapter);
	}

	// 右侧导航事件
	private void setRightNaviEvent() {
		lsvRightNav.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.i("info", "右侧导航单击事件开始");
				rightNavAdapter = (ArrayAdapter<?>) parent.getAdapter();
				rightNavAdapter.notifyDataSetChanged();

				// 点击导航，设置主列表滚动
				Character ch = (Character) parent.getItemAtPosition(position);
				int minPos = adapter.getMinPosByTitle(ch);
				Log.i("info", "ch + minPos: " + ch + ":" + minPos);
				lsv.setSelectionFromTop(minPos, 0);

				Log.i("info", "右侧导航单击事件结束");
			}
		});
	}

	/**
	 * 添加自动补全
	 * @version 1.0.0
	 * @author tarena
	 * <p style='font-weight:bold'>Date:</p> 2016年10月12日 上午10:29:15
	 * <p style='font-weight:bold'>AlterDate:</p>
	 */
	private void setActSong(AutoCompleteTextView act) {
		List<String> musicTitle = MusicProvider.getInstance().getMusicTitleBySD();
		LogUtil.logInfo(musicTitle.toString());
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_1010_txt, musicTitle);
		act.setAdapter(adapter);
		act.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String item = (String) parent.getAdapter().getItem(position);
				Toast.makeText(Activity1008_Player.this, item, Toast.LENGTH_LONG).show();
				;
			}
		});
	}

	/**
	 * 检索框事件
	 * @version 1.0.0
	 * @return
	 * @author tarena
	 * <p style='font-weight:bold'>Date:</p> 2016年10月12日 上午10:42:34
	 * <p style='font-weight:bold'>AlterDate:</p>
	 */
	private OnQueryTextListener getSrvSongListEnvent() {
		return new OnQueryTextListener() {

			@Override
			// 软键盘输入法，提交按钮事件
			public boolean onQueryTextSubmit(String query) {
				LogUtil.logInfo("检索提交");
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			// 检索框输入文本变化
			public boolean onQueryTextChange(String newText) {
				Adapter_SongList adapter = (Adapter_SongList) lsv.getAdapter();

				// 获取检索结果数据
				List<Music> musics = MusicProvider.getInstance().getMusicsByFilter(newText);

				// 设置数据与刷新
				adapter.setmObject(musics);
				adapter.notifyDataSetChanged();
				return true;
			}
		};
	}
}
