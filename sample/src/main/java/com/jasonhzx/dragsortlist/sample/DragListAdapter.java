package com.jasonhzx.dragsortlist.sample;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jasonhzx.dragsortlist.component.DragSortItemLayout;
import com.jasonhzx.dragsortlist.component.ItemTouchHelperViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DragListAdapter extends RecyclerView.Adapter<DragListAdapter.DragViewHolder>{

    private ArrayList<String> list = new ArrayList<>();

    public void updateList(List<String> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<String> getList() {
        return this.list;
    }

    private DragSortItemLayout.OnItemSortListener mOnItemSortListener;

    public void setOnItemSortListener(DragSortItemLayout.OnItemSortListener onItemSortListener) {
        mOnItemSortListener = onItemSortListener;
    }

    @NonNull
    @Override
    public DragListAdapter.DragViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drag_list_item, parent, false);
        return new DragViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final DragListAdapter.DragViewHolder holder, int position) {
        holder.textView.setText(list.get(position));
        holder.sortImg.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mOnItemSortListener.onStartDrags(holder);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class DragViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        public TextView textView;
        public FrameLayout mask;
        public ImageView sortImg;
        public DragViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_name);
            mask = itemView.findViewById(R.id.mask);
            sortImg = itemView.findViewById(R.id.sort_img);
        }

        @Override
        public void onItemSelected() {
            mask.setVisibility(View.VISIBLE);
        }

        @Override
        public void onItemClear() {
            mask.setVisibility(View.GONE);
        }
    }
}
