package com.project.day1008.player;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import com.project.R;
import com.project.day1008.player.provider.Music;
import com.project.day1008.player.template.Adapter_SongList;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Day1010_ChooseViewStub {
	
	public void setEvent(final View viewLayout, final Adapter_SongList adapter) {
		CheckBox chk = (CheckBox) viewLayout.findViewById(R.id.chkChooseAll);
		chk.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					adapter.setAllChoose();
				} else {
					adapter.clearChoose();
				}
				adapter.notifyDataSetChanged();
				
			}
		});
		
		Button btn = (Button) viewLayout.findViewById(R.id.btnSongDel);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("info", "删除开始---------------------");
				
				
				Set<String> chooseSet = adapter.getChooseSet();
				Log.i("info", chooseSet.toString());
//				List<String> delFilesPath = new ArrayList<String>();
				
				for (Iterator<String> iterator = chooseSet.iterator(); iterator.hasNext();) {
					String position = (String) iterator.next();
					Music music = (Music) adapter.getItem(Integer.parseInt(position));
					
					Log.i("info", "删除文件路径" + music.getPath());
					boolean success = new File(music.getPath()).delete();
					Log.i("info", "删除结果：" + success);
				}
				Log.i("info", "删除结束---------------------");
				adapter.setCanChoose(false, -1);
				adapter.notifyDataSetChanged();
			}
		});
	}
}
