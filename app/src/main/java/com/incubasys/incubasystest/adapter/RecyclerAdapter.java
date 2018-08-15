package com.incubasys.incubasystest.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.incubasys.incubasystest.R;
import com.incubasys.incubasystest.activity.CompanyDetailsActivity;
import com.incubasys.incubasystest.api.ApiService;
import com.incubasys.incubasystest.api.RetrofitClient;
import com.incubasys.incubasystest.model.CompanyData;
import com.incubasys.incubasystest.model.CompanyResponse;
import com.incubasys.incubasystest.model.User;
import com.incubasys.incubasystest.utils.UserNotification;
import com.squareup.picasso.Picasso;
import java.util.List;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CompanyHolder> {
    private List<CompanyData> entries;
    private Context context;

    public RecyclerAdapter(Context context, List<CompanyData> entries) {
        this.context = context;
        this.entries = entries;
    }

    @Override
    public CompanyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_item, parent, false);
        return new CompanyHolder(view);
    }

    @Override
    public void onBindViewHolder(CompanyHolder holder, int position) {
        holder.bindData(entries.get(position));
        int layoutPosition = holder.getLayoutPosition();
        if (layoutPosition == getItemCount() / 2 - 1) {
            load(entries, 30, entries.size());
        }
    }

    private void update() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    private void load(final List<CompanyData> list, int limit, int offset) {
        ApiService api = RetrofitClient.getApiService();
        Call<CompanyResponse> call = api.getCompanies(User.getToken(), limit, offset);

        call.enqueue(new Callback<CompanyResponse>() {
            @Override
            public void onResponse(Response<CompanyResponse> response, Retrofit retrofit) {
                Log.d("Response","Success");
                if (response.body().getSuccess()) {
                    list.addAll(response.body().getData());
                    update();
                }
                else UserNotification.showMessage(context, response.body().getError().getMessage());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("Response","Failure " + t.toString());
                UserNotification.showMessage(context, "Failure");
            }
        });
    }

    class CompanyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mCompanyName;
        private LinearLayout mLayout;
        private TextView mCompanyId;
        private ImageView mCompanyLogo;
        private int id;

        CompanyHolder(View v) {
            super(v);
            mCompanyName = v.findViewById(R.id.tvCompanyName);
            mCompanyId = v.findViewById(R.id.tvCompanyId);
            mCompanyLogo = v.findViewById(R.id.ivCompanyLogo);
            mLayout = v.findViewById(R.id.cardLayout);
            mLayout.setOnClickListener(this);
        }

        void bindData(CompanyData entry) {
            id = entry.getId();
            mCompanyName.setText(entry.getName());
            mCompanyId.setText(String.valueOf(id) + ".");
            if (!entry.getLink().equals("") && entry.getLink() != null)
                Picasso.with(context).load(entry.getLink()).placeholder(R.mipmap.ic_launcher_round).into(mCompanyLogo);
            else mCompanyLogo.setImageResource(R.mipmap.ic_launcher_round);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cardLayout:
                    Intent intent = new Intent(view.getContext(), CompanyDetailsActivity.class);
                    intent.putExtra("ID", String.valueOf(id));
                    context.startActivity(intent);
            }
        }
    }
}
