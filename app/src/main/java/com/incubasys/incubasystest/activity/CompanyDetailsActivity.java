package com.incubasys.incubasystest.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.incubasys.incubasystest.R;
import com.incubasys.incubasystest.fragment.CompanyDetailsFragment;

public class CompanyDetailsActivity extends AppCompatActivity {

    private static String FRAGMENT_INSTANCE_NAME = "company_details_fragment";
    CompanyDetailsFragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        FragmentManager fm = getSupportFragmentManager();
        fragment = (CompanyDetailsFragment) fm.findFragmentByTag(FRAGMENT_INSTANCE_NAME);
    }
}
