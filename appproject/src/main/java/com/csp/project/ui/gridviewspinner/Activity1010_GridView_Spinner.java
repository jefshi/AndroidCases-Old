package com.project.day1010.gridviewspinner;

import java.util.List;

import com.project.R;
import com.project.day1008.player.provider.Music;
import com.project.day1008.player.provider.MusicProvider;
import com.project.day1008.player.template.Adapter_SongList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;

public class Activity1010_GridView_Spinner extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_1010_gridview_spinner);
		
		// setGridViewAdapter01();
		setGridViewAdapter02();
		
		
		setSpinnerAdapter01();
		
		Button btn = (Button) findViewById(R.id.btnShowChoosed);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Spinner spn = (Spinner) findViewById(R.id.spnCity);
				// 获取选择的Item的文本
				String result = (String) spn.getSelectedItem().toString();
				Log.i("info", result + "");
			}
		});
		
		
		
	}

	private void setSpinnerAdapter01() {
		List<String> cities = DataProvider.getInstance().getSpinnerData();
		Spinner spn = (Spinner) findViewById(R.id.spnCity);
		
		BaseAdapter adapter = new ArrayAdapter<String>(
				this, R.layout.activity_1010_txt, cities);
		
		spn.setAdapter(adapter);
	}

	private void setGridViewAdapter01() {
		List<Integer> data = DataProvider.getInstance().getGridViewData();
		GridView grv = (GridView) findViewById(R.id.grvMV);
		
		ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(
				this, R.layout.activity_1010_txt, data) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ImageView view = null;

				// 1.item view, 饺子皮
				if (convertView == null) {
					view = (ImageView) View.inflate(getContext(), R.layout.activity_1010_txt, null);
				} else {
					view = (ImageView) convertView;
				}

				// 2.item data, 饺子馅
				Object object = getItem(position);

				// 3.bind view, 包饺子, 对号入座
				view.setImageResource((Integer) object);

				return view;
			}
		};
		
		grv.setAdapter(adapter);
	}
	
	private void setGridViewAdapter02() {
		List<Music> musics = MusicProvider.getInstance() .getMusicsBySD("Music");
		GridView grv = (GridView) findViewById(R.id.grvMV);
		
		BaseAdapter adapter = new Adapter_SongList(
				this, R.layout.activity_day1008_player_song_list_music,
				musics,
				new int[] { R.id.imgHead, R.id.txtTitle, R.id.txtArtist });
		
		grv.setAdapter(adapter);
	}
}
