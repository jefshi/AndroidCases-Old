package com.csp.cases.activity.component.camera;


import android.support.v4.util.ArrayMap;

import com.csp.utils.android.log.LogCat;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author 郭翰林
 * @date 2019/3/4 0004 11:13
 * 注释:
 */
public class SizeMap {

    private final ArrayMap<AspectRatio, SortedSet<Size>> mRatios = new ArrayMap<>();

    /**
     * Add a new {@link Size} to this collection.
     *
     * @param size The size to add.
     * @return {@code true} if it is added, {@code false} if it already exists and is not added.
     */
    public boolean add(Size size) {
        for (AspectRatio ratio : mRatios.keySet()) {
            if (ratio.matches(size)) {
                final SortedSet<Size> sizes = mRatios.get(ratio);
                if (sizes.contains(size)) {
                    return false;
                } else {
                    sizes.add(size);
                    return true;
                }
            }
        }
        // None of the existing ratio matches the provided size; add a new key
        SortedSet<Size> sizes = new TreeSet<>();
        sizes.add(size);
        LogCat.i("预览尺寸== "+ AspectRatio.of(size.getWidth(), size.getHeight())+"比例==" +sizes);
        mRatios.put(AspectRatio.of(size.getWidth(), size.getHeight()), sizes);
        return true;
    }

    /**
     * Removes the specified aspect ratio and all sizes associated with it.
     *
     * @param ratio The aspect ratio to be removed.
     */
    public void remove(AspectRatio ratio) {
        mRatios.remove(ratio);
    }

    Set<AspectRatio> ratios() {
        return mRatios.keySet();
    }

    SortedSet<Size> sizes(AspectRatio ratio) {
        return mRatios.get(ratio);
    }

    void clear() {
        mRatios.clear();
    }

    boolean isEmpty() {
        return mRatios.isEmpty();
    }

}
