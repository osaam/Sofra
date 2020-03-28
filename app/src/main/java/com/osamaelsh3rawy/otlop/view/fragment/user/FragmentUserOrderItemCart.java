package com.osamaelsh3rawy.otlop.view.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.OrderCartAdapter;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.local.room.RoomDao;
import com.osamaelsh3rawy.otlop.data.model.items.ItemsData;
import com.osamaelsh3rawy.otlop.view.activity.ActivityUserLoginCycle;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_Api_Takin;
import static com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger.loadUserData;
import static com.osamaelsh3rawy.otlop.data.local.room.RoomManager.getInstance;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;


public class FragmentUserOrderItemCart extends BaseFragment {


    @BindView(R.id.fragment_user_shops_item_order_confirm_recycler)
    RecyclerView fragmentUserShopsItemOrderConfirmRecycler;
    @BindView(R.id.fragment_user_shops_item_order_confirm_txt_all)
    TextView fragmentUserShopsItemOrderConfirmTxtAll;
    public List<ItemsData> listitem = new ArrayList<>();
    private RoomDao roomDao;
    private boolean goLogin = false;
    private LinearLayoutManager linearLayoutManager;
    private OrderCartAdapter orderConfirmAdapter;
    FragmentUserOrderItemCart fragmentUserOrderItemConfirm;
    FragmentUserOrderConfirm fragmentUserOrderDetaiels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_shops_orders_item_cart, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        roomDao = getInstance(getActivity()).roomDao();


        linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentUserShopsItemOrderConfirmRecycler.setLayoutManager(linearLayoutManager);
        listitem = getInstance(getActivity()).roomDao().getAll();
        orderConfirmAdapter = new OrderCartAdapter(baseActivity, getContext(), listitem, this);
        fragmentUserShopsItemOrderConfirmRecycler.setAdapter(orderConfirmAdapter);


        orderConfirmAdapter.notifyDataSetChanged();

        setTotal();
        return view;
    }

    public void setTotal() {
        double total = 0;
        for (int i = 0; i < listitem.size(); i++) {
            total = (int) (total + (listitem.get(i).getCount() * listitem.get(i).getPrice()));
        }

        fragmentUserShopsItemOrderConfirmTxtAll.setText(String.valueOf(total));

    }


    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (goLogin && loadUserData(getActivity()) != null) {
            goLogin = false;
            FragmentUserOrderConfirm fragmentUserOrderDetaiels = new FragmentUserOrderConfirm();
            fragmentUserOrderDetaiels.listitem = listitem;
            replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_container, fragmentUserOrderDetaiels);
        }

    }

    @OnClick({R.id.fragment_user_shops_item_order_confirm_btn_conf, R.id.fragment_user_shops_item_order_confirm_btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_user_shops_item_order_confirm_btn_conf:

                String apiTakin = SharedPreferencesManger.LoadData(getActivity(), User_Api_Takin);

                if ((apiTakin == null)) {

                    Intent intent = new Intent(getActivity(), ActivityUserLoginCycle.class);
                    intent.putExtra("type", "finish");
                    getActivity().startActivity(intent);

                    goLogin = true;

                } else {
                    FragmentUserOrderConfirm fragmentUserOrderDetaiels = new FragmentUserOrderConfirm();
                    fragmentUserOrderDetaiels.listitem = listitem;
                    replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_container, fragmentUserOrderDetaiels);
                }


                break;
            case R.id.fragment_user_shops_item_order_confirm_btn_add:
                super.onBack();
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_container, new FragmentUserShops());

                break;

        }
    }
}
