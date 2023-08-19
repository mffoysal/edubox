package com.edubox.admin.tab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.edubox.admin.R;
import com.edubox.admin.adapter.TabPagerAdapter;
import com.edubox.admin.oneFrag;
import com.edubox.admin.threeFrag;
import com.edubox.admin.twoFrag;
import com.google.android.material.tabs.TabLayout;

public class Tab extends AppCompatActivity {

    private TabPagerAdapter tabPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        tabLayout = findViewById(R.id.tabLayoutId);
        viewPager = findViewById(R.id.tabPagerId);

        tabLayout.setupWithViewPager(viewPager);
        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tabPagerAdapter.addFragment(new oneFrag(), "Home");
        tabPagerAdapter.addFragment(new twoFrag(), "Phone");
        tabPagerAdapter.addFragment(new threeFrag(), "School");
        viewPager.setAdapter(tabPagerAdapter);

    }

}