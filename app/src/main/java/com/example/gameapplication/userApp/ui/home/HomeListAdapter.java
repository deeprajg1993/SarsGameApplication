package com.example.gameapplication.userApp.ui.home;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gameapplication.R;
import com.example.gameapplication.userApp.model.DashboardResponseData;
import com.example.gameapplication.userApp.ui.battingclose.BattingClosefragment;

import java.util.List;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.MyViewHolder> {
    //   Context activity;
    List<DashboardResponseData.BettingTime> mList;
    FragmentActivity activity;
    String endAm_Pm, end_Time;

    // List<Integer> mList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvBattingClose, tvAm_Pm, tvTime, tvLeftTime;
        ImageView imageview, favorite;
        LinearLayout llHeader;


        public MyViewHolder(View view) {
            super(view);
            tvAm_Pm = view.findViewById(R.id.tvAm_Pm);
            tvBattingClose = view.findViewById(R.id.tvBattingClose);
            tvTime = view.findViewById(R.id.tvTime);
            tvLeftTime = view.findViewById(R.id.tvLeftTime);

            llHeader = view.findViewById(R.id.llHeader);

            llHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new BattingClosefragment();
                    //    endAm_Pm = mList.get(getAdapterPosition()).getEnd_am_pm();
                    //  end_Time = mList.get(getAdapterPosition()).getBetting_end_time();

                    Bundle args = new Bundle();

                    args.putString("endAmPm", mList.get(getAdapterPosition()).endAmPm);
                    args.putString("endTime", mList.get(getAdapterPosition()).bettingEndTime);
                    args.putString("left_time", mList.get(getAdapterPosition()).leftTime);
                    args.putString("betting_id", mList.get(getAdapterPosition()).id);

                    fragment.setArguments(args);
                    gotoFragment(fragment, "BattingFragment");

                }
            });

        }
    }

    public HomeListAdapter(FragmentActivity activity, List<DashboardResponseData.BettingTime> mList) {
        this.activity = activity;
        this.mList = mList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(activity).inflate(R.layout.home_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        /* if (position == 0) {*/
        if (!mList.get(position).leftTime.equals("")) {
            holder.tvAm_Pm.setText(mList.get(position).startAmPm);
            holder.tvAm_Pm.setTextColor(Color.WHITE);
            holder.tvBattingClose.setVisibility(View.VISIBLE);
            holder.tvBattingClose.setText("Betting Close Soon");
            holder.tvLeftTime.setText("Time Left:" + mList.get(position).leftTime);
            holder.tvLeftTime.setTextColor(Color.YELLOW);
            holder.tvTime.setText(mList.get(position).bettingStartTime);
            holder.tvTime.setTextColor(Color.WHITE);
            holder.llHeader.setBackgroundResource(R.drawable.shap_home);


        } else {
            holder.tvAm_Pm.setText(mList.get(position).startAmPm);
            holder.tvAm_Pm.setTextColor(Color.BLACK);
            holder.tvLeftTime.setText("Start Time : " + mList.get(position).startTime);
            holder.tvLeftTime.setTextColor(Color.parseColor("#138d00"));
            holder.tvTime.setText(mList.get(position).bettingStartTime);
            holder.tvTime.setTextColor(Color.BLACK);
            holder.llHeader.setBackgroundResource(R.drawable.shap_rounded);

        }
        /*holder.llHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new BattingClosefragment();
                endAm_Pm = mList.get(position).getEnd_am_pm();
                end_Time = mList.get(position).getBetting_end_time();

                Bundle args = new Bundle();

                args.putString("endAmPm", endAm_Pm);
                args.putString("endTime", end_Time);
                fragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, fragment)
                        .commit();
               // gotoFragment(new BattingClosefragment(), "BattingFragment");


            }
        });

*/
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    private void gotoFragment(Fragment fragment, final String constant) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content_framgment, fragment, constant)
                //.addToBackStack(constant)
                .commit();
    }
}


