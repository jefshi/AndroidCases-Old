package com.csp.cases.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import com.csp.cases.R;
import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 动画案例
 * <p>Create Date: 2017/7/18
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
@SuppressWarnings({"unused", "UnusedAssignment"})
public class AnimationActivity extends BaseListActivity {
	@Override
	public List<ItemInfo> getItemInfos() {
		List<ItemInfo> items = new ArrayList<>();
		items.add(new ItemInfo("-- Frame Animation --", "onFrameAnimation", "帧动画：不会改变控件的位置(可以点[ImageView]做测试)"));
		items.add(new ItemInfo("-- Tween Animation --", "", "补间动画：不会改变控件的位置(可以点[ImageView]做测试)"));
		items.add(new ItemInfo("XML 方式实现补间动画(SET)", "onTweenByXml", "与下述使用[Java]实现效果相同"));
		items.add(new ItemInfo("Java 方式实现补间动画(SET)", "onTweenByJava", "与上述使用[XML]实现效果相同"));
		items.add(new ItemInfo("-- Property Animation --", "", "属性动画：改变控件的位置(可以点[ImageView]做测试)"));
		items.add(new ItemInfo("XML 方式实现属性动画(SET)", "onPropertyByXml", "与下述使用[Java]实现效果相同"));
		items.add(new ItemInfo("Java 方式实现属性动画(SET)", "onPropertyByJava", "与上述使用[XML]实现效果相同"));
		return items;
	}

	@Override
	public void initViewContent() {
		setDescription("Alpha: 淡入淡出, Rotate: 旋转, Scale: 缩放, Translate: 平移");
		imgItem.setImageResource(R.mipmap.item_card);

		super.initViewContent();
	}

	@Override
	public void initViewEvent() {
		super.initViewEvent();
		imgItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(AnimationActivity.this, "点在[ImageView]上", Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * 补间动画/帧动画监听器
	 */
	private Animation.AnimationListener newAnimationListener() {
		return new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				logError("newAnimationListener: onAnimationStart");
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				logError("newAnimationListener: onAnimationEnd");
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				logError("newAnimationListener: onAnimationRepeat");
			}
		};
	}

	/**
	 * 帧动画
	 */
	@SuppressWarnings({"ConstantConditions", "deprecation"})
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void onFrameAnimation() {
		Drawable drawable = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			drawable = getDrawable(R.drawable.aniamtion_frame);
		else
			drawable = getResources().getDrawable(R.drawable.aniamtion_frame);

		imgItem.setImageDrawable(drawable);
		final AnimationDrawable animDraw = (AnimationDrawable) drawable;
		animDraw.start();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				animDraw.stop();
			}
		}, 2000);
	}

	/**
	 * XML 方式实现补间动画(SET)
	 */
	private void onTweenByXml() {
		// int resId = R.anim.animation_alpha;
		// int resId = R.anim.animation_rotate;
		// int resId = R.anim.animation_scale;
		// int resId = R.anim.animation_translate;
		int resId = R.anim.animation_set;
		Animation anim = AnimationUtils.loadAnimation(this, resId);
		anim.setAnimationListener(newAnimationListener());
		imgItem.startAnimation(anim);
	}

	/**
	 * Java 方式实现补间动画(SET)
	 */
	private void onTweenByJava() {
		// Scale 缩放
		Animation scale = new ScaleAnimation(1, 2, 1, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scale.setInterpolator(new AccelerateInterpolator());
		scale.setDuration(2000);
		scale.setRepeatCount(1);
		scale.setRepeatMode(Animation.REVERSE);

		// Alpha 淡入淡出
		Animation alpha = new AlphaAnimation(1f, 0.5f);
		alpha.setStartOffset(2000);

		// Rotate 旋转
		RotateAnimation rotate = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotate.setInterpolator(new AccelerateDecelerateInterpolator());
		rotate.setStartOffset(4000);

		// Translate 平移
		TranslateAnimation translate = new TranslateAnimation(0, 200, 0, 200);
		translate.setInterpolator(new DecelerateInterpolator());
		translate.setStartOffset(4000);

		// Set 集合
		AnimationSet anim = new AnimationSet(false);
		anim.addAnimation(scale);
		anim.addAnimation(alpha);
		anim.addAnimation(rotate);
		anim.addAnimation(translate);
		anim.setAnimationListener(newAnimationListener());
		anim.setDuration(2000);
		imgItem.startAnimation(anim);
	}

	/**
	 * 属性动画监听器
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private Animator.AnimatorListener newAnimatorListener() {
		return new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
				logError("onAnimationStart");
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				logError("onAnimationEnd");
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				logError("onAnimationCancel");
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				logError("onAnimationRepeat");
			}
		};

		// return new AnimatorListenerAdapter() {
		// 	@Override
		// 	public void onAnimationStart(Animator animation) {
		// 		super.onAnimationStart(animation);
		// 	}
		// };
	}

	/**
	 * XML 方式实现属性动画(SET)
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void onPropertyByXml() {
		// int resId = R.animator.animator;
		int resId = R.animator.animator_set;
		Animator animator = AnimatorInflater.loadAnimator(this, resId);
		animator.setTarget(imgItem);
		animator.addListener(newAnimatorListener());
		animator.start();
	}

	/**
	 * Java 方式实现属性动画(SET)
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void onPropertyByJava() {
		// 缩放
		ObjectAnimator scale = ObjectAnimator.ofFloat(imgItem, "scaleX", 1f, 2f);
		scale.setDuration(2000);
		scale.setRepeatCount(1);
		scale.setRepeatMode(ObjectAnimator.REVERSE);

		// 淡入淡出
		ObjectAnimator alpha = ObjectAnimator.ofFloat(imgItem, "alpha", 1f, 0.5f);

		// 旋转
		ObjectAnimator rotation = ObjectAnimator.ofFloat(imgItem, "rotation", 0f, 360f, 0f);

		// 平移
		ObjectAnimator translation = ObjectAnimator.ofFloat(imgItem, "translationY", 0f, 100f);

		// Set 集合
		AnimatorSet set = new AnimatorSet();
		set.play(alpha)
				.with(rotation)
				.before(translation)
				.after(scale);
		set.setDuration(2000);
		set.addListener(newAnimatorListener());
		set.start();
	}
}
