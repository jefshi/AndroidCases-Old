package com.project.day1008.player.provider;

import java.io.File;
import java.io.FileFilter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.common.android.control.util.LogUtil;
import com.common.android.model.util.CursorUtil;
import com.project.R;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Loader.OnLoadCompleteListener;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;

public class MusicProvider {
	// ========================================
	// 单例模式
	// ========================================
	private MusicProvider() {
	}

	public static MusicProvider getInstance() {
		return Inner.instance;
	}

	static class Inner {
		public static MusicProvider instance = new MusicProvider();
	}

	// ========================================
	// 自定义监听器
	// ========================================
	private OnLoadCompleteListener<List<Music>> loadCompleteListener;

	public void setOnLoadCompleteListener(OnLoadCompleteListener<List<Music>> listener) {
		loadCompleteListener = listener;
	}

	// ========================================
	// 异步加载
	// ========================================
	private List<Music> musics;

	public List<Music> getMusics() {
		return musics;
	}

	public void runMusicLoadAsyncTask(Context context) {
		LogUtil.logInfo("runMusicLoadAsyncTask");
		MusicLoaderAsyncTask musicTask = new MusicLoaderAsyncTask(context);
		musicTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, musics);
	}

	/**
	 * 异步任务
	 */
	private class MusicLoaderAsyncTask extends AsyncTask<Object, Integer, List<Music>> {
		private WeakReference<Context> wContext;

		public MusicLoaderAsyncTask(Context context) {
			wContext = new WeakReference<Context>(context);
		}

		@Override
		protected List<Music> doInBackground(Object... params) {
			LogUtil.logInfo("sleep 5000 start: --------------");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LogUtil.logInfo("sleep 5000 end: --------------");

			// ContentResolver
			ContentResolver cr = wContext.get().getContentResolver();
			Cursor cursor = cr.query(
					Media.EXTERNAL_CONTENT_URI,
					null,
					Media.IS_MUSIC + " = ? ",
					new String[] { "1" },
					Media.DATE_ADDED + " desc ");

			// 获取查询数据并封装
			if (!cursor.moveToFirst()) {
				LogUtil.logInfo("检索结果为空: --------------");
				return null;
			}

			List<Music> musics = new ArrayList<Music>();
			do {
				String title = CursorUtil.getString(cursor, Media.TITLE);
				String artist = CursorUtil.getString(cursor, Media.ARTIST);
				Music music = new Music(title, artist);
				musics.add(music);
			} while (cursor.moveToNext());
			cursor.close();
			
			LogUtil.logInfo("检索成功------------------------------");
			return musics;
		}

		@Override
		protected void onPostExecute(List<Music> result) {
			sortMusicByName(result);
			if (loadCompleteListener != null) {
				loadCompleteListener.onLoadComplete(null, result);
			}
			musics = result;
		}
	}

	// ========================================
	// 其他
	// ========================================
	// 获取音乐数据
	public List<Music> getMusicsBySD(String path) {
		// 文件路径
		File sdFile = Environment.getExternalStorageDirectory();
		// Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
		File srcFile = new File(sdFile, path);

		// 获取音乐列表
		File[] musics = srcFile.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				return file.isFile() && file.getName().endsWith(".mp3");
			}
		});

		// 生成Music对象
		List<Music> list = new ArrayList<Music>();
		if (musics != null) {
			for (File file : musics) {
				Music music = new Music(file.getName(), file.getName());
				music.setPath(file.getAbsolutePath());
				music.setHead(R.drawable.png_01);
				list.add(music);
			}
		} else {
			Log.i("info", "找不到文件了");
			return null;
		}

		// Music排序
		this.musics = list;
		sortMusicByName(this.musics);
		return list;
	}

	public List<String> getMusicTitleBySD() {
		if (musics == null) {
			return new ArrayList<String>();
		}
		
		List<String> musicTitle = new ArrayList<String>();
		for (int i = 0; i < musics.size(); i++) {
			musicTitle.add(musics.get(i).getTitle());
		}
		return musicTitle;
	}

	// 排序
	private void sortMusicByName(List<Music> musics) {
		Collections.sort(musics, new Comparator<Music>() {
			@Override
			public int compare(Music lhs, Music rhs) {
				return lhs.getTitle().toUpperCase().compareTo(
						rhs.getTitle().toUpperCase());
			}
		});
	}

	// 检索
	public List<Music> getMusicsByFilter(String search) {
		List<Music> result = new ArrayList<Music>();
		for (int i = 0; i < musics.size(); i++) {
			if (musics.get(i).getTitle().toUpperCase().startsWith(search.toUpperCase())) {
				result.add(musics.get(i));
			}
		}
		return result;
	}
}
