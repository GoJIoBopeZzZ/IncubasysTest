package com.incubasys.incubasystest.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.incubasys.incubasystest.R;
import com.incubasys.incubasystest.adapter.RecyclerAdapter;
import com.incubasys.incubasystest.api.ApiService;
import com.incubasys.incubasystest.api.RetrofitClient;
import com.incubasys.incubasystest.model.CompanyData;
import com.incubasys.incubasystest.model.CompanyResponse;
import com.incubasys.incubasystest.model.User;
import com.incubasys.incubasystest.utils.UserNotification;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class CompanyListFragment extends Fragment {

    private List<CompanyData> companies = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ProgressDialog progressDialog;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LayoutManagerType mCurrentLayoutManagerType;
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";

    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(CompanyListFragment.this.getContext(),
                R.style.AppTheme_Dark_Dialog);
        setRetainInstance(true);
        loadCompanies(30, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_company_list, container, false);
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }

        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        mAdapter = new RecyclerAdapter(rootView.getContext(), companies);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    private void loadCompanies(int limit, int offset) {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        ApiService api = RetrofitClient.getApiService();
        Call<CompanyResponse> call = api.getCompanies(User.getToken(), limit, offset);
        System.out.println(User.getToken());

        call.enqueue(new Callback<CompanyResponse>() {
            @Override
            public void onResponse(Response<CompanyResponse> response, Retrofit retrofit) {
                Log.d("Response","Success");
                if (response.body().getSuccess()) {
                    companies = response.body().getData();
                    mAdapter = new RecyclerAdapter(CompanyListFragment.this.getContext(), companies);
                    mRecyclerView.setAdapter(mAdapter);
                    progressDialog.dismiss();
                }
                else {
                    progressDialog.dismiss();
                    UserNotification.showMessage(CompanyListFragment.this.getContext(), response.body().getError().getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                Log.d("Response","Failure " + t.toString());
                UserNotification.showMessage(CompanyListFragment.this.getContext(), "Failure");
            }
        });
    }
}
