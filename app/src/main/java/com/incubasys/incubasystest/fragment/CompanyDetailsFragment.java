package com.incubasys.incubasystest.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.incubasys.incubasystest.R;
import com.incubasys.incubasystest.api.ApiService;
import com.incubasys.incubasystest.api.RetrofitClient;
import com.incubasys.incubasystest.model.CompanyDetailsData;
import com.incubasys.incubasystest.model.User;
import com.incubasys.incubasystest.utils.UserNotification;
import com.squareup.picasso.Picasso;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class CompanyDetailsFragment extends Fragment {

    private TextView mCompanyName;
    private TextView mCompanyDescription;
    private ImageView mCompanyLogo;
    private ProgressDialog progressDialog;
    private String name;
    private String description;
    private Drawable logo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        progressDialog = new ProgressDialog(CompanyDetailsFragment.this.getContext(),
                R.style.AppTheme_Dark_Dialog);
        String id = getActivity().getIntent().getExtras().getString("ID");
        Log.d("ID", String.valueOf(id));
        if (id != null && !id.isEmpty()) {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            loadCompanyById(Integer.valueOf(id));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_company_details, container, false);

        mCompanyName = rootView.findViewById(R.id.tvName);
        if (name != null) mCompanyName.setText(name);
        mCompanyLogo = rootView.findViewById(R.id.ivLogo);
        if (logo != null) mCompanyLogo.setImageDrawable(logo);
        mCompanyDescription = rootView.findViewById(R.id.tvDescription);
        if (description != null) mCompanyDescription.setText(description);

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

    private void loadCompanyById(int id) {
        ApiService api = RetrofitClient.getApiService();
        Call<CompanyDetailsData> call = api.getCompanyDetails(User.getToken(), id);
        System.out.println(User.getToken());

        call.enqueue(new Callback<CompanyDetailsData>() {
            @Override
            public void onResponse(Response<CompanyDetailsData> response, Retrofit retrofit) {
                Log.d("Response","Success");
                if (response.body().getSuccess()) {
                    mCompanyName.setText(response.body().getData().getName());
                    name = response.body().getData().getName();
                    mCompanyDescription.setText(response.body().getData().getDescription().toString());
                    description = response.body().getData().getDescription().toString();
                    Picasso.with(CompanyDetailsFragment.this.getContext())
                            .load(response.body().getData().getLink())
                            .placeholder(R.mipmap.ic_launcher_round).into(mCompanyLogo);

                    logo = mCompanyLogo.getDrawable();
                    progressDialog.dismiss();
                }
                else {
                    UserNotification.showMessage(CompanyDetailsFragment.this.getContext(), response.body().getError().getMessage());
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("Response","Failure " + t.toString());
                UserNotification.showMessage(CompanyDetailsFragment.this.getContext(), "Failure");
                progressDialog.dismiss();
            }
        });
    }
}
