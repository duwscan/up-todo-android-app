package com.app.todoapp.calendar.cmp.model;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class DayItemDecorator extends RecyclerView.ItemDecoration {
    private final int spacing;

    public DayItemDecorator(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();
        if (position == 0) {
            outRect.left = spacing;
        }
        if (position == itemCount - 1) {
            outRect.right = spacing;
        }
    }
}
