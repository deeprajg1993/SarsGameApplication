package com.example.gameapplication.userApp.ui.battinglist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gameapplication.R;
import com.example.gameapplication.userApp.activity.MainActivity;
import com.example.gameapplication.userApp.model.BattingChipsResponse;
import com.example.gameapplication.userApp.model.BattingHistoryResponse;
import com.example.gameapplication.userApp.utlis.BaseURL;
import com.example.gameapplication.userApp.utlis.CommonMethod;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattingListFragment extends Fragment {

    BattingListAdpater mAdpater;
    RecyclerView rvBattingList;
    List<BattingListViewModel> mList;

//    private BattingListViewModel battingListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*battingListViewModel =
                ViewModelProviders.of(this).get(BattingListViewModel.class);*/
        View view = inflater.inflate(R.layout.fragment_batting_list, container, false);
       /* final TextView textView = root.findViewById(R.id.text_share);
        battingListViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        rvBattingList = view.findViewById(R.id.rvBattingList);

        /*mList = new ArrayList<>();

        mList.add(new BattingListViewModel("fdjsakjfdnsakjfnjdksan", "11:10", "564"));
        mList.add(new BattingListViewModel("fdjsakjfdnsakjfnjdksan", "11:10", "564"));

        mList.add(new BattingListViewModel("fdjsakjfdnsakjfnjdksan", "11:10", "564"));

        mList.add(new BattingListViewModel("fdjsakjfdnsakjfnjdksan", "11:10", "564"));

        mList.add(new BattingListViewModel("fdjsakjfdnsakjfnjdksan", "11:10", "564"));

        mAdpater = new BattingListAdpater(getContext(), mList);
        rvBattingList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBattingList.setAdapter(mAdpater);*/
        if (CommonMethod.isOnline(getActivity())) {
            battingChipsApiCall();
        } else {
            Toast.makeText(getActivity(), "Please Connect To Internet", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    public void battingChipsApiCall() {
        final ProgressDialog progress = ProgressDialog.show(getContext(), "", "Please wait...", true);
        progress.setCancelable(false);

        final String url = BaseURL.CHIPSHISTORY;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        System.out.println("Response===" + response);

                        try {
                            BattingChipsResponse mBattingList = new Gson().fromJson(response, BattingChipsResponse.class);

                            if (mBattingList.getStatus().equals("1")) {
                                mAdpater = new BattingListAdpater(getContext(), mBattingList.getData());
                                rvBattingList.setLayoutManager(new LinearLayoutManager(getContext()));
                                rvBattingList.setAdapter(mAdpater);
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