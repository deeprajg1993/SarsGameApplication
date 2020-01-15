package com.example.subSubAdmin.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.example.gameapplication.userApp.utlis.BaseURL;
import com.example.gameapplication.userApp.utlis.CommonMethod;
import com.example.subSubAdmin.adpater.ProfitLossAdapter;
import com.example.subSubAdmin.model.AddDebitDataResponse;
import com.example.subSubAdmin.model.ProfitLossResponse;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDetailsSubFragment extends Fragment implements View.OnClickListener {
    ImageView ivRightArrow, ivArrow;
    ProfitLossAdapter mAdapter;
    List<ProfitLossResponse> mList;
    ImageView ivCancle, ivProfitLoss;
    RecyclerView rcProfitLoss;
    AlertDialog changepswDialog, ProfitLossDialog;
    EditText et_confrim_password, et_new_password;
    TextView tvChangePassword, tvChipsPoint, tvUserName;
    String AD, BB, UB, user_Id;
    Switch bettingBlock, UserBlock, AccountDeactivate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_details_sub_fragment, container, false);

        ivRightArrow = view.findViewById(R.id.ivRightArrow);
        ivArrow = view.findViewById(R.id.ivArrow);
        bettingBlock = view.findViewById(R.id.bettingBlock);
        UserBlock = view.findViewById(R.id.UserBlock);
        AccountDeactivate = view.findViewById(R.id.AccountDeactivate);

        UserBlock.setOnClickListener(this);
        AccountDeactivate.setOnClickListener(this);
        bettingBlock.setOnClickListener(this);
        ivRightArrow.setOnClickListener(this);
        ivArrow.setOnClickListener(this);
        tvChipsPoint = view.findViewById(R.id.tvChipsPoint);
        tvUserName = view.findViewById(R.id.tvUserName);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String UserName = bundle.getString("UserName");
            String Name = UserName.substring(0, 1).toUpperCase() + UserName.substring(1);

            String ChipsPoint = bundle.getString("ChipsPoint");
            BB = bundle.getString("BB");
            UB = bundle.getString("UB");
            AD = bundle.getString("AD");
            user_Id = bundle.getString("User_Id");
            tvChipsPoint.setText("Aviable Balance (" + ChipsPoint + ")");
            tvUserName.setText(Name);

        }
        if (BB.equals("1")) {
            bettingBlock.setChecked(true);
        } else {
            bettingBlock.setChecked(false);
        }
        if (UB.equals("1")) {
            UserBlock.setChecked(true);
        } else {
            UserBlock.setChecked(false);
        }
        if (AD.equals("1")) {
            AccountDeactivate.setChecked(true);
        } else {
            AccountDeactivate.setChecked(false);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bettingBlock:
                if (BB.equals("0")) {
                    String statusValue = "1";
                    String type = "BB";
                    userChangeStatusSwitch(type, statusValue);

                } else {
                    String statusValue = "0";
                    String type = "BB";
                    userChangeStatusSwitch(type, statusValue);

                }
                break;

            case R.id.AccountDeactivate:
                if (AD.equals("0")) {
                    String statusValue = "1";
                    String type = "AD";
                    userChangeStatusSwitch(type, statusValue);

                } else {
                    String statusValue = "0";
                    String type = "AD";
                    userChangeStatusSwitch(type, statusValue);
                }
                break;
            case R.id.UserBlock:
                if (UB.equals("0")) {
                    String statusValue = "1";
                    String type = "UB";
                    userChangeStatusSwitch(type, statusValue);

                } else {
                    String statusValue = "0";
                    String type = "UB";
                    userChangeStatusSwitch(type, statusValue);
                }
                break;
            case R.id.ivRightArrow:
                popUp();
                break;
            case R.id.ivArrow:
                profitLossPopup();
                break;
            case R.id.tvChangePassword:
                if (et_new_password.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Please Enter New Password Password", Toast.LENGTH_SHORT).show();
                } else if (et_confrim_password.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
                } else {
                    if (et_new_password.getText().toString().trim().equals(et_confrim_password.getText().toString().trim())) {
                        if (CommonMethod.isOnline(getContext())) {
                            userChangePassword();
                        } else {
                            Toast.makeText(getContext(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "submit", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void popUp() {

        LayoutInflater li = LayoutInflater.from(getContext());
        final View promptsView = li.inflate(R.layout.custom__change_password, null);
        ivCancle = promptsView.findViewById(R.id.ivCancle);
        et_confrim_password = promptsView.findViewById(R.id.et_confrim_password);
        et_new_password = promptsView.findViewById(R.id.et_new_password);
        tvChangePassword = promptsView.findViewById(R.id.tvChangePassword);
        tvChangePassword.setOnClickListener(this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());
        alertDialogBuilder.setView(promptsView);
        changepswDialog = alertDialogBuilder.create();

        ivCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changepswDialog.dismiss();
            }
        });
        changepswDialog.setCancelable(false);
        changepswDialog.show();
    }

    private void profitLossPopup() {

        LayoutInflater li = LayoutInflater.from(getContext());
        final View promptsView = li.inflate(R.layout.custom__profit_loss_popup, null);
        rcProfitLoss = promptsView.findViewById(R.id.rcProfitLoss);
        ivProfitLoss = promptsView.findViewById(R.id.ivProfitLoss);
        if (CommonMethod.isOnline(getContext())) {
            userProfitListApiCall();
        } else {
            Toast.makeText(getContext(), "Please Connect To Internet", Toast.LENGTH_SHORT).show();
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());
        alertDialogBuilder.setView(promptsView);
        ProfitLossDialog = alertDialogBuilder.create();

        ivProfitLoss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfitLossDialog.dismiss();
            }
        });
        ProfitLossDialog.setCancelable(false);
        ProfitLossDialog.show();
    }

    public void userProfitListApiCall() {
        final ProgressDialog progress = ProgressDialog.show(getContext(), "", "Please wait...", true);
        progress.setCancelable(false);

        final String url = BaseURL.PROFITLOSS;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        System.out.println("Response===" + response);

                        try {
                            ProfitLossResponse mAddDebitList = new Gson().fromJson(response, ProfitLossResponse.class);

                            if (mAddDebitList.getStatus().equals("1")) {

                                mAdapter = new ProfitLossAdapter(getContext(), mAddDebitList.getData());
                                rcProfitLoss.setLayoutManager(new LinearLayoutManager(getContext()));
                                rcProfitLoss.setAdapter(mAdapter);
                                String totalUser = String.valueOf(mAddDebitList.getData().size());
                                mAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "" + mAddDebitList.getMessage(), Toast.LENGTH_LONG).show();
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
                params.put("user_id", user_Id);


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

    private void userChangePassword() {
        final ProgressDialog progress = ProgressDialog.show(getContext(), "", "Please wait...", true);
        progress.setCancelable(false);

        final String url = BaseURL.CHANGEPASSWORD;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        System.out.println("Response===" + response);

                        try {
                            AddDebitDataResponse mChangePassword = new Gson().fromJson(response, AddDebitDataResponse.class);

                            if (mChangePassword.getStatus().equals("1")) {
                                changepswDialog.dismiss();
                                Toast.makeText(getContext(), mChangePassword.getMessage(), Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(getContext(), "" + mChangePassword.getMessage(), Toast.LENGTH_LONG).show();
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
                params.put("user_id", CommonMethod.getUserId(getContext()));
                params.put("pass", et_new_password.getText().toString().trim());


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

    private void userChangeStatusSwitch(final String type, final String statusValue) {
        final ProgressDialog progress = ProgressDialog.show(getContext(), "", "Please wait...", true);
        progress.setCancelable(false);

        final String url = BaseURL.CHANGESTATUS;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        System.out.println("Response===" + response);

                        try {
                            AddDebitDataResponse mChangePassword = new Gson().fromJson(response, AddDebitDataResponse.class);

                            if (mChangePassword.getStatus().equals("1")) {
                                Toast.makeText(getContext(), mChangePassword.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "" + mChangePassword.getMessage(), Toast.LENGTH_LONG).show();
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
                params.put("id", user_Id);
                params.put("type", type);
                params.put("status", statusValue);


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
