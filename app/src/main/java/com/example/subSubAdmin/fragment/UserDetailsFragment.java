package com.example.subSubAdmin.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gameapplication.R;
import com.example.gameapplication.userApp.adpter.BattListingAdpater;
import com.example.gameapplication.userApp.model.BattingHistoryResponse;
import com.example.gameapplication.userApp.utlis.BaseURL;
import com.example.gameapplication.userApp.utlis.CommonMethod;
import com.example.subSubAdmin.adpater.StatementAdpater;
import com.example.subSubAdmin.adpater.UserDetailsAdpater;
import com.example.subSubAdmin.model.StatementResponse;
import com.example.subSubAdmin.model.UserDetailsResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDetailsFragment extends Fragment {
    RecyclerView rcUserDetails;
    UserDetailsAdpater mAdpater;
    List<UserDetailsResponse> mList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_details_fragment, container, false);

        rcUserDetails = view.findViewById(R.id.rcUserDetails);

        if (CommonMethod.isOnline(getActivity())) {
            userDetailsListApiCall();
        } else {
            Toast.makeText(getActivity(), "Please Connect To internet", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    public void userDetailsListApiCall() {
        final ProgressDialog progress = ProgressDialog.show(getContext(), "", "Please wait...", true);
        progress.setCancelable(false);

        final String url = BaseURL.ADDUSERLIST;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        System.out.println("Response===" + response);

                        try {
                            UserDetailsResponse mUserList = new Gson().fromJson(response, UserDetailsResponse.class);

                            if (mUserList.getStatus().equals("1")) {
                                mAdpater = new UserDetailsAdpater(getActivity(), mUserList.getData());
                                rcUserDetails.setLayoutManager(new LinearLayoutManager(getContext()));
                                rcUserDetails.setAdapter(mAdpater);
                                mAdpater.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "" + mUserList.getMessage(), Toast.LENGTH_LONG).show();
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
                params.put("dl_id", CommonMethod.getUserId(getContext()));


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
