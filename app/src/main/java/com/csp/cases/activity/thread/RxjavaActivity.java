package com.csp.cases.activity.thread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.log.LogCat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Description:
 * <p>Create Date: 2017/12/15
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class RxjavaActivity extends BaseListActivity {
	@Override
	public List<ItemInfo> getItemInfos() {
		List<ItemInfo> itemInfos = new ArrayList<>();
		itemInfos.add(new ItemInfo("简单的Demo", "sampleDemo", ""));
		return itemInfos;
	}

	private void sampleDemo() {
		Observable
				.create(new ObservableOnSubscribe<File>() {
					@Override
					public void subscribe(@NonNull ObservableEmitter<File> emitter) throws Exception {
						LogCat.e("subscribe");
						File pictureDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
						LogCat.e(pictureDir);
						File[] pictures = pictureDir.listFiles();
						LogCat.e("", pictures);
						for (File picture : pictures) {
							if (picture.getName().endsWith("png")) {
								emitter.onNext(picture);
								TimeUnit.SECONDS.sleep(2);
							}
						}
						emitter.onComplete();
					}
				}).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.map(new Function<File, Bitmap>() {
					@Override
					public Bitmap apply(@NonNull File file) throws Exception {
						LogCat.e("map");
						return BitmapFactory.decodeFile(file.getAbsolutePath());
					}
				})
				.subscribe(new Observer<Bitmap>() {
					private Disposable disposable;

					@Override
					public void onSubscribe(@NonNull Disposable disposable) {
						LogCat.e("onSubscribe");
						this.disposable = disposable;
					}

					@Override
					public void onNext(@NonNull Bitmap bitmap) {
						LogCat.e("onNext");
						imgItem.setImageBitmap(bitmap);
					}

					@Override
					public void onError(@NonNull Throwable throwable) {
						LogCat.e("onError");
					}

					@Override
					public void onComplete() {
						LogCat.e("onComplete");
						disposable.dispose();
					}
				});
	}
}
