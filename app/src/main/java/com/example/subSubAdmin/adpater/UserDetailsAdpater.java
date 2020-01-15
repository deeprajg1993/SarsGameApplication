package com.example.subSubAdmin.adpater;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gameapplication.R;
import com.example.gameapplication.userApp.ui.battingclose.BattingClosefragment;
import com.example.subSubAdmin.fragment.UserDetailsSubFragment;
import com.example.subSubAdmin.model.StatementResponse;
import com.example.subSubAdmin.model.UserDetailsResponse;

import java.util.List;

public class UserDetailsAdpater extends RecyclerView.Adapter<UserDetailsAdpater.MyHolder> {

    List<UserDetailsResponse.UserDataResponse> mList;
    int possion;
    FragmentActivity activity;

    public UserDetailsAdpater(FragmentActivity activity, List<UserDetailsResponse.UserDataResponse> mList) {
        this.activity = activity;
        this.mList = mList;
    }

    @NonNull
    @Override
    public UserDetailsAdpater.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.custome_listing_user_details, viewGroup, false);

        return new UserDetailsAdpater.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        possion = i;

        try {
            myHolder.tvUserDetailsName.setText(mList.get(i).getUser_id());
            myHolder.tvDetailsValue.setText("Aviable Balance (" + mList.get(i).getChip_point() + ")");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvUserDetailsName, tvDetailsValue;
        LinearLayout llVistProfile;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvUserDetailsName = itemView.findViewById(R.id.tvUserDetailsName);
            tvDetailsValue = itemView.findViewById(R.id.tvDetailsValue);
            llVistProfile = itemView.findViewById(R.id.llVistProfile);
            llVistProfile.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.llVistProfile:


                    Fragment fragment = new UserDetailsSubFragment();
                    //    endAm_Pm = mList.get(getAdapterPosition()).getEnd_am_pm();
                    //  end_Time = mList.get(getAdapterPosition()).getBetting_end_time();

                    Bundle args = new Bundle();

                    args.putString("UserName", mList.get(getAdapterPosition()).getUser_id());
                    args.putString("ChipsPoint", mList.get(getAdapterPosition()).getChip_point());
                    args.putString("UB", mList.get(getAdapterPosition()).getUB());
                    args.putString("BB", mList.get(getAdapterPosition()).getBB());
                    args.putString("AD", mList.get(getAdapterPosition()).getAD());
                    args.putString("User_Id", mList.get(getAdapterPosition()).getId());
                    fragment.setArguments(args);
                    gotoFragment(fragment, "BattingFragment");

                    //  gotoFragment(new UserDetailsSubFragment(), "UserDetailsSubFragment");
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void gotoFragment(Fragment fragment, final String constant) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, fragment, constant)
                .addToBackStack(constant)
                .commit();
    }

}

