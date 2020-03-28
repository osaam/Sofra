package com.osamaelsh3rawy.otlop.view.fragment.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.TabPagerAdapter;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentTabList extends BaseFragment {

    @BindView(R.id.fragment_tab_list_tab)
    TabLayout fragmentTabListTab;
    @BindView(R.id.fragment_tab_list_bagger)
    ViewPager fragmentTabListBagger;

    private FragmentUserList fragmentUserListNew, fragmentUserListCurrent, fragmentUserListPreviose;
    private TabPagerAdapter tabPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_list, container, false);
        ButterKnife.bind(this, view);

//        fragmentTabListBagger.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(fragmentTabListTab));

        fragmentUserListNew = new FragmentUserList();
        fragmentUserListNew.status = "pending";

        fragmentUserListCurrent = new FragmentUserList();
        fragmentUserListCurrent.status = "current";

        fragmentUserListPreviose = new FragmentUserList();
        fragmentUserListPreviose.status = "completed";

        tabPagerAdapter = new TabPagerAdapter(getChildFragmentManager());
        tabPagerAdapter.addTab(fragmentUserListNew, "new");
        tabPagerAdapter.addTab(fragmentUserListCurrent, "current");
        tabPagerAdapter.addTab(fragmentUserListPreviose, "previous");

        //set adapter
        fragmentTabListBagger.setAdapter(tabPagerAdapter);
        //set up
        fragmentTabListTab.setupWithViewPager(fragmentTabListBagger);

        return view;
    }

}
