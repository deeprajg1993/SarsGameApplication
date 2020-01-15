package com.example.gameapplication.userApp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gameapplication.R;
import com.example.gameapplication.userApp.adpter.BattListingAdpater;
import com.example.gameapplication.userApp.model.BattingHistoryResponse;
import com.example.gameapplication.userApp.model.BattingListingViewModel;
import com.example.gameapplication.userApp.utlis.BaseURL;
import com.example.gameapplication.userApp.utlis.CommonMethod;
import com.example.gameapplication.userApp.utlis.IsConnected;
import com.example.gameapplication.userApp.utlis.SessionManager;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BettingListingFragment extends Fragment {
    RecyclerView rcBettingListing;
    BattListingAdpater mAdpater;
    List<BattingListingViewModel> mList;
    SessionManager sessionManager;
    HashMap<String, String> user;
    String chips_points;
    TextView tvTotalePoint;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_betting_history, container, false);

        sessionManager = new SessionManager(getContext());

        rcBettingListing = view.findViewById(R.id.rcBettingListing);
        tvTotalePoint = view.findViewById(R.id.tvTotalePoint);

        if (sessionManager.getUserDetails() != null) {
            user = sessionManager.getUserDetails();

            chips_points = user.get(SessionManager.CHIPS_POINT);
            tvTotalePoint.setText(CommonMethod.getChipsPoints(getContext()));
        }
        if (IsConnected.isInternet_connected(getContext())) {
            battingListApiCall();
        } else {
            Toast.makeText(getContext(), "Please Connect To Internet", Toast.LENGTH_SHORT).show();
        }
        return view;
    }


    public void battingListApiCall() {
        final ProgressDialog progress = ProgressDialog.show(getContext(), "", "Please wait...", true);
        progress.setCancelable(false);

        final String url = BaseURL.BETTINGLIST;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        System.out.println("Response===" + response);

                        try {
                            BattingHistoryResponse mBattingList = new Gson().fromJson(response, BattingHistoryResponse.class);

                            if (mBattingList.getStatus().equals("1")) {
                                mAdpater = new BattListingAdpater(getContext(), mBattingList.getData());
                                rcBettingListing.setLayoutManager(new LinearLayoutManager(getContext()));
                                rcBettingListing.setAdapter(mAdpater);
                                mAdpater.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "" + mBattingList.getMessage(), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getContext(), "Server Error!!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //   params.put("req", "BettingHistry");
                params.put("user_id", CommonMethod.getUserId(getContext()));
                //  params.put("user_id", "24");


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }


    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    // handle back button
                   /* getActivity().finish();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();
*/
                    Intent back = new Intent(getContext(), MainActivity.class);
                    back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(back);
                    return true;

                }

                return false;
            }
        });
    }
}