package com.example.subSubAdmin.adpater;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gameapplication.R;
import com.example.gameapplication.userApp.utlis.BaseURL;
import com.example.gameapplication.userApp.utlis.CommonMethod;
import com.example.subSubAdmin.activity.SubDashboardActivity;
import com.example.subSubAdmin.model.AddDebitResponse;
import com.example.subSubAdmin.model.StatementResponse;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddDebitAdpater extends RecyclerView.Adapter<AddDebitAdpater.MyHolder> implements View.OnClickListener {

    Context context;
    List<AddDebitResponse.AddDebitDetailsResponse> mList;
    int possion;
    int clickPossion;
    EditText et_AddChips;

    public AddDebitAdpater(Context context, List<AddDebitResponse.AddDebitDetailsResponse> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public AddDebitAdpater.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.custome_listing_add_debit, viewGroup, false);

        return new AddDebitAdpater.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        possion = i;

        try {
            myHolder.tvaddUserName.setText(mList.get(i).getUser_id());
            myHolder.tvValue.setText("Aviable Balance (" + mList.get(i).getChip_point() + ")");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAddSubmit:
                if (!et_AddChips.getText().toString().isEmpty()) {

                    int num = Integer.parseInt(et_AddChips.getText().toString().trim());
                    int sum = Integer.parseInt(CommonMethod.getChipsPoints(context));
                    if (num <= sum) {
                        String value = "credit";
                        addChipApiCall(value);
                    } else {
                        Toast.makeText(context, "your available Balance is low", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Enter Add Chips Number", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tvDebitSubmit:
                if (!et_AddChips.getText().toString().isEmpty()) {
                    int num = Integer.parseInt(et_AddChips.getText().toString().trim());
                    int sum = Integer.parseInt(mList.get(clickPossion).getChip_point());
                    if (num <= sum) {
                        String value = "debit";
                        addChipApiCall(value);
                    } else {
                        Toast.makeText(context, "your available Balance low", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Enter Add Chips Number", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvaddUserName, tvValue;
        ImageView ivRightArrow;
        LinearLayout llAddDebit;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvaddUserName = itemView.findViewById(R.id.tvaddUserName);
            tvValue = itemView.findViewById(R.id.tvValue);
            ivRightArrow = itemView.findViewById(R.id.ivRightArrow);
            llAddDebit = itemView.findViewById(R.id.llAddDebit);
            llAddDebit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.llAddDebit:
                    //  gotoFragment(new Add)
                    clickPossion = getAdapterPosition();
                    popUp(clickPossion);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void popUp(final int value) {

        LayoutInflater li = LayoutInflater.from(context);
        final View promptsView = li.inflate(R.layout.custom__dailog_box, null);
        TextView tvUserName = promptsView.findViewById(R.id.tvUserName);
        tvUserName.setText(mList.get(value).getUser_id());
        TextView tvAviablePoint = promptsView.findViewById(R.id.tvAviablePoint);
        tvAviablePoint.setText("Available Balance( " + mList.get(value).getChip_point() + ")");
        et_AddChips = promptsView.findViewById(R.id.et_AddChips);
        ImageView ivCancle = promptsView.findViewById(R.id.ivCancle);
        TextView tvDebitSubmit = promptsView.findViewById(R.id.tvDebitSubmit);
        TextView tvAddSubmit = promptsView.findViewById(R.id.tvAddSubmit);
        tvAddSubmit.setOnClickListener(this);
        tvDebitSubmit.setOnClickListener(this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setView(promptsView);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        ivCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void addChipApiCall(final String submitValue) {
        final ProgressDialog progress = ProgressDialog.show(context, "", "Please wait...", true);
        progress.setCancelable(false);

        final String url = BaseURL.ADDDEBITVALUE;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        System.out.println("Response===" + response);

                        try {
                            AddDebitResponse mAddDebitList = new Gson().fromJson(response, AddDebitResponse.class);

                            if (mAddDebitList.getStatus().equals("1")) {
                                Toast.makeText(context, "" + mAddDebitList.getMessage(), Toast.LENGTH_LONG).show();

                                Intent sendDashboard = new Intent(context, SubDashboardActivity.class);
                                context.startActivity(sendDashboard);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        System.out.println("Error" + error.toString());
                        Toast.makeText(context, "Server Error!!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", mList.get(clickPossion).getId());
                params.put("ava_chips", mList.get(clickPossion).getChip_point());
                //   if ((mList.get(clickPossion).getChip_point())<= (et_AddChips.getText().toString().trim()))
                params.put("chips", et_AddChips.getText().toString().trim());
                params.put("dl_id", CommonMethod.getUserId(context));
                params.put("dl_chips", CommonMethod.getChipsPoints(context));
                params.put("submit", submitValue);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

}


