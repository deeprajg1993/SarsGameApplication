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
import com.example.subSubAdmin.model.ProfitLossResponse;

import java.util.List;

public class ProfitLossAdapter extends RecyclerView.Adapter<ProfitLossAdapter.MyHolder> {

    Context context;
    List<ProfitLossResponse.ProfitLossData> mList;
    int possion;

    public ProfitLossAdapter(Context context, List<ProfitLossResponse.ProfitLossData> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ProfitLossAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.custome_profite_loss_listing, viewGroup, false);

        return new ProfitLossAdapter.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        possion = i;

        try {
            myHolder.tvProfitLossNumber.setText(mList.get(i).getNumber());
            myHolder.tvProfitLossDay.setText(mList.get(i).getDate());
            myHolder.tvProfitLossDate.setText(mList.get(i).getStart_time() + "-" + mList.get(i).getEnd_time());
            if (mList.get(possion).getProfit_type().equals("+")) {
                myHolder.tvProfitLossValue.setText("+ " + mList.get(i).getChips());

            } else {
                if (mList.get(possion).getProfit_type().equals("-")) {
                    myHolder.tvProfitLossValue.setTextColor(Color.parseColor("#ff4d00"));
                    myHolder.tvProfitLossValue.setText("- " + mList.get(i).getChips());

                } else {
                    myHolder.tvProfitLossValue.setText(mList.get(i).getChips());
                    myHolder.tvProfitLossValue.setTextColor(Color.parseColor("#00574B"));
                    myHolder.tvWin.setVisibility(View.VISIBLE);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView tvProfitLossDate, tvProfitLossValue, tvProfitLossDay, tvProfitLossNumber, tvWin;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvProfitLossNumber = itemView.findViewById(R.id.tvProfitLossNumber);
            tvProfitLossDate = itemView.findViewById(R.id.tvProfitLossDate);
            tvProfitLossValue = itemView.findViewById(R.id.tvProfitLossValue);
            tvProfitLossDay = itemView.findViewById(R.id.tvProfitLossDay);
            tvWin = itemView.findViewById(R.id.tvWin);

        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

