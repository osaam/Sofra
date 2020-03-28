package com.osamaelsh3rawy.otlop.view.fragment.restourant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.TabPagerAdapter;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;
import com.osamaelsh3rawy.otlop.view.fragment.user.FragmentUserList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentRestourantTabList extends BaseFragment {

    @BindView(R.id.fragment_tab_restourant_list_tab)
    TabLayout fragmentTabRestourantListTab;
    @BindView(R.id.fragment_tab_restourant_list_bagger)
    ViewPager fragmentTabRestourantListBagger;

    FragmentRestourantList fragmentRestourantListNew, fragmentRestourantListCurrent, fragmentRestourantListPreviose;
    TabPagerAdapter tabPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_list_restourant, container, false);
        ButterKnife.bind(this, view);

//        fragmentTabListBagger.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(fragmentTabListTab));

        tabPagerAdapter = new TabPagerAdapter(getChildFragmentManager());

        fragmentRestourantListNew = new FragmentRestourantList();
        fragmentRestourantListNew.state = "pending";

        fragmentRestourantListCurrent = new FragmentRestourantList();
        fragmentRestourantListCurrent.state = "current";

        fragmentRestourantListPreviose = new FragmentRestourantList();
        fragmentRestourantListPreviose.state = "completed";

        tabPagerAdapter.addTab(fragmentRestourantListNew, "new");
        tabPagerAdapter.addTab(fragmentRestourantListCurrent, "current");
        tabPagerAdapter.addTab(fragmentRestourantListPreviose, "previous");

        //set adapter
        fragmentTabRestourantListBagger.setAdapter(tabPagerAdapter);
        //set up
        fragmentTabRestourantListTab.setupWithViewPager(fragmentTabRestourantListBagger);


        return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }

}
