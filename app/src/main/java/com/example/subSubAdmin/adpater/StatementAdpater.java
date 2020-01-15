package com.example.subSubAdmin.adpater;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gameapplication.R;
import com.example.gameapplication.userApp.adpter.BattListingAdpater;
import com.example.gameapplication.userApp.model.BattingHistoryResponse;
import com.example.subSubAdmin.model.StatementResponse;

import java.util.List;

public class StatementAdpater extends RecyclerView.Adapter<StatementAdpater.MyHolder> {

    Context context;
    List<StatementResponse.StatementDataResponse> mList;
    int possion;

    public StatementAdpater(Context context, List<StatementResponse.StatementDataResponse> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public StatementAdpater.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.custome_listing_statement, viewGroup, false);

        return new StatementAdpater.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        possion = i;

        try {
            myHolder.tvUserName.setText(mList.get(i).getUser());
            myHolder.tvChipsNumber.setText(mList.get(i).getDate_time());
            if (mList.get(possion).getUser_id().equals("0")) {
                if (mList.get(i).getType().equals("+")) {
                    myHolder.tvTotalValue.setTextColor(Color.parseColor("#00a241"));
                    myHolder.tvTotalValue.setText("+" + mList.get(i).getChips());
                } else {
                    myHolder.tvTotalValue.setTextColor(Color.parseColor("#ff4d00"));
                    myHolder.tvTotalValue.setText("-" + mList.get(i).getChips());
                }
            } else {
                if (mList.get(i).getType().equals("+")) {
                    myHolder.tvTotalValue.setTextColor(Color.parseColor("#ff4d00"));
                    myHolder.tvTotalValue.setText("-" + mList.get(i).getChips());
                } else {
                    myHolder.tvTotalValue.setTextColor(Color.parseColor("#00a241"));
                    myHolder.tvTotalValue.setText("+" + mList.get(i).getChips());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView tvUserName, tvChipsNumber, tvTotalValue;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvChipsNumber = itemView.findViewById(R.id.tvChipsNumber);
            tvTotalValue = itemView.findViewById(R.id.tvTotalValue);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

