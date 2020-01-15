package com.example.gameapplication.userApp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gameapplication.R;
import com.example.gameapplication.userApp.activity.LoginActivity;
import com.example.gameapplication.userApp.model.DashboardResponseData;
import com.example.gameapplication.userApp.utlis.BaseURL;
import com.example.gameapplication.userApp.utlis.CommonMethod;
import com.example.subSubAdmin.activity.SubDashboardActivity;
import com.example.subSubAdmin.model.ChipsPointResponseParser;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    //    public static AutoScrollViewPager mViewPager;
    List<BannerModel> mBannerList = new ArrayList<>();
    RecyclerView rvTimeListing, rvBattingListing, rcViewPager;
    List<HomeDashboardModel> mlist = new ArrayList<>();
    List<HomeDataListing> mDataListing = new ArrayList<>();
    String currentDate;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        findview(view);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = df.format(c.getTime());
        if (CommonMethod.isOnline(getActivity())) {
            battingTimeSlotApi();

        } else {
            Toast.makeText(getActivity(), "Please Connect to internet", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void findview(View view) {
        rcViewPager = view.findViewById(R.id.rcViewPager);
        rvTimeListing = view.findViewById(R.id.rvTimeListing);
        rvBattingListing = view.findViewById(R.id.rvBattingListing);
    }


    public void battingTimeSlotApi() {
        /*final ProgressDialog progress = ProgressDialog.show(getContext(), "", "Please wait...", true);
        progress.setCancelable(false);*/

        final String url = BaseURL.DASHBOARDDATA;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //              progress.dismiss();
                        System.out.println("Response===" + response);

                        try {
                            DashboardResponseData mDashboard = new Gson().fromJson(response, DashboardResponseData.class);

                            if (mDashboard.status.equals("1")) {
                                //last_betting

                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                rvBattingListing.setLayoutManager(layoutManager);
                                rvBattingListing.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                                //  rvTimeListing.setLayoutManager(new GridLayoutManager(getContext(), 2));
                                HomeDataAdapter mLastBetting = new HomeDataAdapter(getActivity(), mDashboard.lastBetting);

                                rvBattingListing.setAdapter(mLastBetting);

                                //betting_time
                                HomeListAdapter mAdpater = new HomeListAdapter(getActivity(), mDashboard.bettingTime);
                                rvTimeListing.setLayoutManager(new GridLayoutManager(getContext(), 2));
                                rvTimeListing.setAdapter(mAdpater);


                                //  mAdpater.notifyDataSetChanged();

                                RecyclerView.LayoutManager layout = new LinearLayoutManager(getActivity());
                                rcViewPager.setLayoutManager(layout);
                                rcViewPager.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                                //  rvTimeListing.setLayoutManager(new GridLayoutManager(getContext(), 2));
                                SlidingImage_Adapter mSlidingImage = new SlidingImage_Adapter(getActivity(), mDashboard.winBetting);

                                rcViewPager.setAdapter(mSlidingImage);

                            } else {
                                Toast.makeText(getContext(), "" + mDashboard.message, Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //            progress.dismiss();
                        System.out.println("Error" + error.toString());
                        Toast.makeText(getContext(), "Server Error!!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("date", currentDate);
                params.put("user_id", CommonMethod.getUserId(getContext()));


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




}