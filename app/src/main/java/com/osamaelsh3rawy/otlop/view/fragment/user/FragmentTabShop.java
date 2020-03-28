package com.osamaelsh3rawy.otlop.view.fragment.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.TabPagerAdapter;
import com.osamaelsh3rawy.otlop.data.model.userListOfRestourante.RestourantesData;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentTabShop extends BaseFragment {


    public RestourantesData restourantesData;
    @BindView(R.id.fragment_tab_shop_tab)
    TabLayout fragmentTabShopTab;
    @BindView(R.id.fragment_tab_shop_bagger)
    ViewPager fragmentTabShopBagger;
    TabPagerAdapter tabPagerAdapter;
    private FragmentUserShopMenu fragmentUserShopMenu;
    private FragmentUserShopComment fragmentUserShopComment;
    private FragmentUserShopInfo fragmentUserShopInfo;

    public FragmentTabShop() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_shop, container, false);
        ButterKnife.bind(this, view);

        fragmentTabShopBagger.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(fragmentTabShopTab));

        tabPagerAdapter = new TabPagerAdapter(getChildFragmentManager());

        fragmentUserShopMenu = new FragmentUserShopMenu();
        fragmentUserShopMenu.restourantesData = restourantesData;

        FragmentUserOrderConfirm fragmentUserOrderDetaiels = new FragmentUserOrderConfirm();
        fragmentUserOrderDetaiels.restourantesData = restourantesData;

        fragmentUserShopComment = new FragmentUserShopComment();
        fragmentUserShopComment.restourantesData = restourantesData;

        fragmentUserShopInfo = new FragmentUserShopInfo();
        fragmentUserShopInfo.restourantesData = restourantesData;

        tabPagerAdapter.addTab(fragmentUserShopMenu, "menu");
        tabPagerAdapter.addTab(fragmentUserShopComment, "comment");
        tabPagerAdapter.addTab(fragmentUserShopInfo, "info");

        fragmentTabShopBagger.setAdapter(tabPagerAdapter);
        fragmentTabShopTab.setupWithViewPager(fragmentTabShopBagger);


        return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
