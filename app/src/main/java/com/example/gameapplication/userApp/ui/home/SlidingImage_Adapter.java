package com.example.gameapplication.userApp.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gameapplication.R;
import com.example.gameapplication.userApp.model.DashboardResponseData;

import java.util.List;

public class SlidingImage_Adapter extends RecyclerView.Adapter<SlidingImage_Adapter.MyViewHolder> {
    Context activity;
    List<DashboardResponseData.WinBetting> mList;
    // List<Integer> mList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvValue, tvName, tvUserId, tvSessionId, tvTime, tvDate;
        LinearLayout llMainViewPager;

        public MyViewHolder(View view) {
            super(view);
            tvValue = view.findViewById(R.id.tvValue);
            tvName = view.findViewById(R.id.tvName);
            tvDate = view.findViewById(R.id.tvDate);
            tvTime = view.findViewById(R.id.tvTime);
            tvSessionId = view.findViewById(R.id.tvSessionId);
            llMainViewPager = view.findViewById(R.id.llMainViewPager);
        }
    }

    public SlidingImage_Adapter(Context activity, List<DashboardResponseData.WinBetting> mList) {
        this.activity = activity;
        this.mList = mList;
    }

    @Override
    public SlidingImage_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(activity).inflate(R.layout.home_slidindimage_pager, parent, false);
        return new SlidingImage_Adapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
      /*  if (position == 0) {
            holder.tvDate.setText(mList.get(position).date);
            holder.tvTime.setText(mList.get(position).time);
            holder.tvValue.setText("0" + mList.get(position).win_number);
            holder.tvSessionId.setText("Session Id : " + mList.get(position).id);
            holder.llMainViewPager.setBackgroundResource(R.drawable.image_bg_shap);
        } else {
            if (position == 1) {
                holder.tvDate.setText(mList.get(position).date);
                holder.tvTime.setText(mList.get(position).time);
                holder.tvValue.setText("0" + mList.get(position).win_number);
                holder.tvSessionId.setText("Session Id : " + mList.get(position).id);
                holder.llMainViewPager.setBackgroundResource(R.drawable.image_bg_shap_two);

            } else {
                if (position == 2) {
                    holder.tvDate.setText(mList.get(position).date);
                    holder.tvTime.setText(mList.get(position).time);
                    holder.tvValue.setText("0" + mList.get(position).win_number);
                    holder.tvSessionId.setText("Session Id : " + mList.get(position).id);
                    holder.llMainViewPager.setBackgroundResource(R.drawable.image_bg_shap_three);

                } else {*/
        if (position % 2 == 0) {
            holder.tvDate.setText(mList.get(position).date);
            holder.tvTime.setText(mList.get(position).time);
            holder.tvValue.setText("0" + mList.get(position).win_number);
            holder.tvSessionId.setText("Session Id : " + mList.get(position).id);
            holder.llMainViewPager.setBackgroundResource(R.drawable.image_bg_shap);

        } else {
            holder.tvDate.setText(mList.get(position).date);
            holder.tvTime.setText(mList.get(position).time);
            holder.tvValue.setText("0" + mList.get(position).win_number);
            holder.tvSessionId.setText("Session Id : " + mList.get(position).id);
            holder.llMainViewPager.setBackgroundResource(R.drawable.image_bg_shap_two);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}



