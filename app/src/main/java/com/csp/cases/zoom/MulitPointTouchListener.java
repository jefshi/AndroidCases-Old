package com.csp.cases.zoom;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class MulitPointTouchListener implements OnTouchListener {
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    float windowWidth;
    float widnowHeigth;
    int mode = 0;
    boolean canTranslate = false;
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    private boolean isMultiPoint = false;
    private ZoomImageView zoomImageView;

    public MulitPointTouchListener(ZoomImageView zoomImageView) {
        this.zoomImageView = zoomImageView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ImageView view = (ImageView) v;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                isMultiPoint = false;
                matrix.set(view.getImageMatrix());
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = 1;

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                isMultiPoint = true;
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = 2;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (mode == 1) {
                    if (canTranslate) {
                        matrix.set(savedMatrix);
                        matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
                    }
                } else if (mode == 2) {
                    canTranslate = true;
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode = 3;
                break;
            case MotionEvent.ACTION_UP:
                if (!isMultiPoint) {
                    zoomImageView.finishActivity();
                }
                mode = 0;

                break;
        }

        view.setImageMatrix(matrix);
        return true;
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}
// imageView.setOnTouchListener(new MulitPointTouchListener ());
