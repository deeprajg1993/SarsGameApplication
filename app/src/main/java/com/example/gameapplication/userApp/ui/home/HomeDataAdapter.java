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

public class HomeDataAdapter extends RecyclerView.Adapter<HomeDataAdapter.MyViewHolder> {
    Context activity;
    List<DashboardResponseData.LastBetting> mList;
    // List<Integer> mList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvChipsNumber, tvChipsTime, tvChipsValue, tvChipsDate, tvBettingId;

        public MyViewHolder(View view) {
            super(view);
            tvChipsNumber = view.findViewById(R.id.tvChipsNumber);
            tvChipsTime = view.findViewById(R.id.tvChipsTime);
            tvChipsValue = view.findViewById(R.id.tvChipsValue);
            tvChipsDate = view.findViewById(R.id.tvChipsDate);
            tvBettingId = view.findViewById(R.id.tvBettingId);

        }
    }

    public HomeDataAdapter(Context activity, List<DashboardResponseData.LastBetting> mList) {
        this.activity = activity;
        this.mList = mList;
    }

    @Override
    public HomeDataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(activity).inflate(R.layout.home_data_list_item, parent, false);
        return new HomeDataAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HomeDataAdapter.MyViewHolder holder, final int position) {


        holder.tvChipsNumber.setText("0" + mList.get(position).number);
        holder.tvChipsTime.setText(mList.get(position).startTime);
        holder.tvChipsDate.setText(mList.get(position).date);
        holder.tvBettingId.setText("Session : " + mList.get(position).betting_id);
        if (mList.get(position).bettingStatus.equals("-")) {

            holder.tvChipsValue.setText(mList.get(position).chips);
            holder.tvChipsValue.setBackgroundColor(Color.parseColor("#ff0101"));
        } else {
            if (mList.get(position).bettingStatus.equals("+")) {
                holder.tvChipsValue.setText(mList.get(position).chips);
                holder.tvChipsValue.setBackgroundColor(Color.parseColor("#008346"));
            } else {
                holder.tvChipsValue.setText(mList.get(position).chips);
                holder.tvChipsValue.setBackgroundColor(Color.parseColor("#fda800"));
            }
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}



