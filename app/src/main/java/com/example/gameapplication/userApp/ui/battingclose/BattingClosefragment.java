package com.example.gameapplication.userApp.ui.battingclose;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gameapplication.userApp.activity.MainActivity;
import com.example.gameapplication.R;
import com.example.gameapplication.userApp.model.AddBettingResponse;
import com.example.gameapplication.userApp.utlis.BaseURL;
import com.example.gameapplication.userApp.utlis.CommonMethod;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class BattingClosefragment extends Fragment implements View.OnClickListener {

    // private BattingCloseFragmentViewModel battingCloseFragmentViewModel;
    TextView tvOneValue, tvTwoValue, tvThreeValue, tvFourValue, tvSevenValue, tvFiveValue, tvEightValue,
            tvZeroValue, tvSixValue, tvNineValue, tvTime, tvAmPm, tvLeftTimeClose;
    EditText etEnterValue, etEnterValueData;
    Button btnConfirm;
    String betting_id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_batting_close, container, false);

        findView(view);
        clickButton(view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String endAmPm = bundle.getString("endAmPm");
            String endTime = bundle.getString("endTime");
            String left_time = bundle.getString("left_time");
            tvLeftTimeClose.setText("Left Time " + left_time);

            betting_id = bundle.getString("betting_id");
            tvTime.setText(endTime);
            tvAmPm.setText(endAmPm);
        }


        return view;
    }

    private void clickButton(View view) {
        tvOneValue.setOnClickListener(this);
        tvTwoValue.setOnClickListener(this);
        tvThreeValue.setOnClickListener(this);
        tvFourValue.setOnClickListener(this);
        tvFiveValue.setOnClickListener(this);
        tvSixValue.setOnClickListener(this);
        tvSevenValue.setOnClickListener(this);
        tvEightValue.setOnClickListener(this);
        tvNineValue.setOnClickListener(this);
        tvZeroValue.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);

    }

    private void findView(View view) {
        tvOneValue = view.findViewById(R.id.tvOneValue);
        tvTwoValue = view.findViewById(R.id.tvTwoValue);
        tvThreeValue = view.findViewById(R.id.tvThreeValue);
        tvFourValue = view.findViewById(R.id.tvFourValue);
        tvFiveValue = view.findViewById(R.id.tvFiveValue);
        tvSixValue = view.findViewById(R.id.tvSixValue);
        tvSevenValue = view.findViewById(R.id.tvSevenValue);
        tvEightValue = view.findViewById(R.id.tvEightValue);
        tvNineValue = view.findViewById(R.id.tvNineValue);
        tvZeroValue = view.findViewById(R.id.tvZeroValue);
        etEnterValue = view.findViewById(R.id.etEnterValue);
        etEnterValueData = view.findViewById(R.id.etEnterValueData);
        btnConfirm = view.findViewById(R.id.btnConfirm);
        tvTime = view.findViewById(R.id.tvTime);
        tvAmPm = view.findViewById(R.id.tvAmPm);

        tvLeftTimeClose = view.findViewById(R.id.tvLeftTimeClose);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvOneValue:
                etEnterValue.setText("1");
                tvOneValue.setBackgroundResource(R.drawable.shapes);
                tvTwoValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvThreeValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvFourValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvSevenValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvFiveValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvSixValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvNineValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvEightValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvZeroValue.setBackgroundResource(R.drawable.bg_shap_round);
                openKeyWord();
                //tvOneValue.setBackgroundColor(Color.parseColor("#rrggbb"));
                break;
            case R.id.tvTwoValue:
                etEnterValue.setText("2");
                tvTwoValue.setBackgroundResource(R.drawable.shapes);
                tvOneValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvThreeValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvFourValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvSevenValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvFiveValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvSixValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvNineValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvEightValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvZeroValue.setBackgroundResource(R.drawable.bg_shap_round);
                openKeyWord();
                break;
            case R.id.tvThreeValue:
                etEnterValue.setText("3");
                tvThreeValue.setBackgroundResource(R.drawable.shapes);
                tvTwoValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvOneValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvFourValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvSevenValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvFiveValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvSixValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvNineValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvEightValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvZeroValue.setBackgroundResource(R.drawable.bg_shap_round);
                openKeyWord();
                break;
            case R.id.tvFourValue:
                etEnterValue.setText("4");
                tvFourValue.setBackgroundResource(R.drawable.shapes);
                tvTwoValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvThreeValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvOneValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvSevenValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvFiveValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvSixValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvNineValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvEightValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvZeroValue.setBackgroundResource(R.drawable.bg_shap_round);
                openKeyWord();
                break;
            case R.id.tvFiveValue:
                tvFiveValue.setBackgroundResource(R.drawable.shapes);
                tvTwoValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvThreeValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvFourValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvSevenValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvOneValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvSixValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvNineValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvEightValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvZeroValue.setBackgroundResource(R.drawable.bg_shap_round);
                etEnterValue.setText("5");
                openKeyWord();
                break;
            case R.id.tvSixValue:
                tvSixValue.setBackgroundResource(R.drawable.shapes);
                tvTwoValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvThreeValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvFourValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvSevenValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvFiveValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvOneValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvNineValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvEightValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvZeroValue.setBackgroundResource(R.drawable.bg_shap_round);
                etEnterValue.setText("6");
                openKeyWord();
                break;
            case R.id.tvSevenValue:
                tvSevenValue.setBackgroundResource(R.drawable.shapes);
                tvTwoValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvThreeValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvFourValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvOneValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvFiveValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvSixValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvNineValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvEightValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvZeroValue.setBackgroundResource(R.drawable.bg_shap_round);
                etEnterValue.setText("7");
                openKeyWord();
                break;
            case R.id.tvEightValue:
                tvEightValue.setBackgroundResource(R.drawable.shapes);
                tvTwoValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvThreeValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvFourValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvSevenValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvFiveValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvSixValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvNineValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvOneValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvZeroValue.setBackgroundResource(R.drawable.bg_shap_round);
                etEnterValue.setText("8");
                openKeyWord();
                break;
            case R.id.tvNineValue:
                tvNineValue.setBackgroundResource(R.drawable.shapes);
                tvTwoValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvThreeValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvFourValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvSevenValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvFiveValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvSixValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvOneValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvEightValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvZeroValue.setBackgroundResource(R.drawable.bg_shap_round);
                etEnterValue.setText("9");
                openKeyWord();
                break;
            case R.id.tvZeroValue:
                tvZeroValue.setBackgroundResource(R.drawable.shapes);
                tvTwoValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvThreeValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvFourValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvSevenValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvFiveValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvSixValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvNineValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvEightValue.setBackgroundResource(R.drawable.bg_shap_round);
                tvOneValue.setBackgroundResource(R.drawable.bg_shap_round);
                etEnterValue.setText("0");
                openKeyWord();
                break;
            case R.id.btnConfirm:
                if (etEnterValue.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please Select any one value", Toast.LENGTH_SHORT).show();
                } else if (etEnterValueData.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please Enter Chips Number", Toast.LENGTH_SHORT).show();
                } else {
                    if (CommonMethod.isOnline(getContext())) {
                        int totalvalue = Integer.parseInt(etEnterValueData.getText().toString());
                        int value = Integer.parseInt(CommonMethod.getChipsPoints(getContext()));
                        if (totalvalue < value) {
                            addBettingApi();
                        } else {
                            Toast.makeText(getContext(), "you have not sufficient chips", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Please Connect to internet", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void openKeyWord() {
        etEnterValueData.setText("");
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(etEnterValueData.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        etEnterValueData.requestFocus();
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

                   /* FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();*/
                    Intent dashboard = new Intent(getContext(), MainActivity.class);
                    startActivity(dashboard);
                    getActivity().finish();
                    return true;

                }

                return false;
            }
        });
    }

    private void addBettingApi() {
        final ProgressDialog progress = ProgressDialog.show(getContext(), "", "Please wait...", true);
        progress.setCancelable(false);

        final String url = BaseURL.ADDBETTING;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        System.out.println("Response===" + response);

                        try {
                            AddBettingResponse mAddBetting = new Gson().fromJson(response, AddBettingResponse.class);

                            if (mAddBetting.getStatus().equals("1")) {
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                startActivity(intent);

                                Toast.makeText(getContext(), mAddBetting.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "" + mAddBetting.getMessage(), Toast.LENGTH_LONG).show();
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
                //  params.put("req", "index");
                params.put("user_id", CommonMethod.getUserId(getContext()));
                params.put("betting_id", betting_id);
                params.put("number", etEnterValue.getText().toString().trim());
                params.put("chips", etEnterValueData.getText().toString());
                params.put("user_chips", CommonMethod.getChipsPoints(getContext()));

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