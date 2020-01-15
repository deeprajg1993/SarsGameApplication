package com.example.gameapplication.userApp.ui.battinglist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gameapplication.R;
import com.example.gameapplication.userApp.model.BattingChipsResponse;

import java.util.List;

public class BattingListAdpater extends RecyclerView.Adapter<BattingListAdpater.MyHolder> {

    Context context;
    List<BattingChipsResponse.BettingChipsResponse> mList;
    int possion;

    public BattingListAdpater(Context context, List<BattingChipsResponse.BettingChipsResponse> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public BattingListAdpater.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.custome_list_batting, viewGroup, false);

        return new BattingListAdpater.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BattingListAdpater.MyHolder myHolder, int i) {
        possion = i;

        try {

            myHolder.tvListName.setText(mList.get(possion).getDate());
            myHolder.tvTime.setText(mList.get(possion).getTime());
            if (mList.get(possion).getType().equals("-")) {
                myHolder.tvCountValue.setText("-" + mList.get(possion).getChips());
                myHolder.tvCountValue.setTextColor(Color.parseColor("#ff4d00"));
            }else {
                if (mList.get(possion).getType().equals("+")){
                    myHolder.tvCountValue.setText("+" + mList.get(possion).getChips());
                    myHolder.tvCountValue.setTextColor(Color.parseColor("#00a241"));

                }else {
                    myHolder.tvCountValue.setText(mList.get(possion).getChips());
                }
            }
            /*myHolder.attendanceTitle.setText(notification.get(i).getActivity());
//            String createdIn = notification.get(i).getDate().substring(0, notification.get(i).getTime().length() - 3);
            if (notification.get(i).getNotification_status().equals("1")) {
                myHolder.tvAttendanceTime.setText(notification.get(i).getDate() + " ," + notification.get(i).getTime());
                myHolder.tv_notification.setText(notification.get(i).getNotification_message());
            } else {
                myHolder.tvAttendanceTime.setText(notification.get(i).getDate() + " ," + notification.get(i).getTime());
                myHolder.tv_notification.setText(notification.get(i).getNotification_message());
            }
*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView tvListName, tvTime, tvCountValue, tvRate;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvListName = itemView.findViewById(R.id.tvListName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvCountValue = itemView.findViewById(R.id.tvCountValue);
            tvRate = itemView.findViewById(R.id.tvRate);

        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}