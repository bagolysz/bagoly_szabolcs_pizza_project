package com.sdproject.szabi.pizzaclient.ui;

/**
 * Created by Szabi on 5/26/2017.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * A decoration that draws a simple horizontal divider at the bottom of each item from the list.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private final Drawable mDivider;
    private final boolean mDrawLastDivider;

    /**
     * Creates an instance of <code>DividerItemDecoration</code>.
     * </p>
     * @param context The context the decoration will be used in.
     * @param drawLastDivider TRUE if the divider for the list item should be drawn, FALSE otherwise.
     */
    public DividerItemDecoration(Context context, boolean drawLastDivider) {
        TypedArray attributes = context.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        mDivider = attributes.getDrawable(0);
        attributes.recycle();
        mDrawLastDivider = drawLastDivider;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int childPosition = 0; childPosition < childCount; childPosition++) {
            if (shouldDecorate(childPosition, parent)) {
                View child = parent.getChildAt(childPosition);

                int top = child.getBottom() + ((RecyclerView.LayoutParams) child.getLayoutParams()).bottomMargin;

                mDivider.setBounds(left, top, right, top + mDivider.getIntrinsicHeight());
                mDivider.draw(c);
            }
        }
    }

    /**
     * Overriding this method is needed in order to be able to draw the divider in the <code>onDraw()</code>
     * method. Otherwise it can only be drawn within the <code>onDrawOver()</code> method.
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = mDivider.getIntrinsicHeight();
    }

    /**
     * Check if the list item at the given position should be decorated or not.
     * </p>
     * @param position The position of the item within the list.
     * @param parent The <code>RecyclerView</code> holding the item.
     * @return TRUE if item at the given position should have a divider, FALSE otherwise.
     */
    private boolean shouldDecorate(int position, RecyclerView parent) {
        return mDrawLastDivider || position != parent.getChildCount() - 1;
    }
}
