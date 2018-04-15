package com.munch.browser.history.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.munch.browser.R;

import javax.annotation.Nullable;

abstract class BaseSwipeCallback extends ItemTouchHelper.SimpleCallback {
    @NonNull
    private final Context mContext;
    @NonNull
    private final Paint mClearPaint;
    @NonNull
    private final Drawable mBackground;
    @NonNull
    private final Drawable mDeleteIcon;
    @NonNull
    private final String mRedColor = "#f44336";

    private int mIntrinsicWidth;
    private int mIntrinsicHeight;

    public BaseSwipeCallback(Context context, int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
        mContext = context;
        mClearPaint = new Paint();
        mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        mBackground = new ColorDrawable(Color.parseColor(mRedColor));

        mDeleteIcon = ContextCompat.getDrawable(mContext, R.drawable.ic_delete_white_24);
        mIntrinsicWidth = mDeleteIcon.getIntrinsicWidth();
        mIntrinsicHeight = mDeleteIcon.getIntrinsicHeight();
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags = ItemTouchHelper.LEFT;
        return viewHolder.getItemViewType() == HistoryAdapter.DATE_HOLDER
                ? 0
                : makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;
        float itemHeight = itemView.getBottom() - itemView.getTop();
        boolean isCanceled = dX == 0f && !isCurrentlyActive;

        if (isCanceled) {
            clearCanvas(c, itemView.getRight() + dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            return;
        }

        // Draw the red delete background
        mBackground.setBounds(
                itemView.getRight() + (int) dX,
                itemView.getTop(),
                itemView.getRight(),
                itemView.getBottom()
        );
        mBackground.draw(c);

        // Calculate position of delete icon
        int iconTop = itemView.getTop() + (int) (itemHeight - mIntrinsicHeight) / 2;
        int iconMargin = (int) (itemHeight - mIntrinsicHeight) / 2;
        int iconLeft = itemView.getRight() - iconMargin - mIntrinsicWidth;
        int iconRight = itemView.getRight() - iconMargin;
        int iconBottom = iconTop + mIntrinsicHeight;

        // Draw the delete icon
        mDeleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
        mDeleteIcon.draw(c);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    private void clearCanvas(@Nullable Canvas c, float left, float top, float right, float bottom) {
        if (c != null) {
            c.drawRect(left, top, right, bottom, mClearPaint);
        }
    }
}
