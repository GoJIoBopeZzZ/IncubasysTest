package com.incubasys.incubasystest.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.incubasys.incubasystest.R;
import com.incubasys.incubasystest.fragment.CompanyListFragment;

public class CompanyListActivity extends AppCompatActivity {

    private static String FRAGMENT_INSTANCE_NAME = "companies_fragment";
    CompanyListFragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);

        FragmentManager fm = getSupportFragmentManager();
        fragment = (CompanyListFragment) fm.findFragmentByTag(FRAGMENT_INSTANCE_NAME);
    }
}
