package com.example.gameapplication.userApp.adpter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gameapplication.R;
import com.example.gameapplication.userApp.model.BattingHistoryResponse;

import java.util.List;

public class BattListingAdpater extends RecyclerView.Adapter<BattListingAdpater.MyHolder> {

    Context context;
    List<BattingHistoryResponse.ListData> mList;
    int possion;

    public BattListingAdpater(Context context, List<BattingHistoryResponse.ListData> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public BattListingAdpater.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.custome_listing_batting, viewGroup, false);

        return new BattListingAdpater.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BattListingAdpater.MyHolder myHolder, int i) {

        try {
            myHolder.tvChipsTime.setText(mList.get(i).getStart_time() + "  ");
            myHolder.tvChipsNumber.setText(mList.get(i).getNumber());
            myHolder.tvChipsDate.setText(mList.get(i).getDate());
            if (mList.get(i).getChip_status().equals("0")) {
                myHolder.tvNextDate.setText("" + mList.get(i).getChips());
                myHolder.tvNextDate.setTextColor(Color.parseColor("#000"));

            } else {
                if (mList.get(i).getChip_status().equals("-")) {
                    myHolder.tvNextDate.setText("-" + mList.get(i).getChips());
                    myHolder.tvNextDate.setTextColor(Color.parseColor("#ff4d00"));

                } else {
                    myHolder.tvNextDate.setText("+" + mList.get(i).getChips());
                    myHolder.tvNextDate.setTextColor(Color.parseColor("#00a241"));
                    myHolder.tvChipsWinner.setVisibility(View.VISIBLE);

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView tvNextDate, tvChipsTime, tvChipsDate, tvChipsNumber, tvChipsWinner;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvChipsNumber = itemView.findViewById(R.id.tvChipsNumber);
            tvNextDate = itemView.findViewById(R.id.tvNextDate);
            tvChipsTime = itemView.findViewById(R.id.tvChipsTime);
            tvChipsDate = itemView.findViewById(R.id.tvChipsDate);
            tvChipsWinner = itemView.findViewById(R.id.tvChipsWinner);

        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
