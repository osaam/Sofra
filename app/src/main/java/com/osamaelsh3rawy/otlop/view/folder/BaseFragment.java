package com.osamaelsh3rawy.otlop.view.folder;

import androidx.fragment.app.Fragment;

import com.osamaelsh3rawy.otlop.adapter.ItemMealAdapter;
import com.osamaelsh3rawy.otlop.view.activity.BaseActivity;

public class BaseFragment extends Fragment {
    public BaseActivity baseActivity;

    public void intiFragment() {
        baseActivity = (BaseActivity) getActivity();
        baseActivity.baseFragment = this;
    }

    public void onBack() {
        baseActivity.superonBackPressed();
    }

}
