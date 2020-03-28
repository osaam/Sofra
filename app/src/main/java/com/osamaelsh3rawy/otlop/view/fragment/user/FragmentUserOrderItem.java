package com.osamaelsh3rawy.otlop.view.fragment.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.local.room.RoomDao;
import com.osamaelsh3rawy.otlop.data.model.items.ItemsData;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.osamaelsh3rawy.otlop.data.local.room.RoomManager.getInstance;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;


public class FragmentUserOrderItem extends BaseFragment {

    @BindView(R.id.fragment_order_item_txt_number_of_order)
    TextView fragmentOrderItemTxtNumberOfOrder;
    @BindView(R.id.item_recycler_shops_item_order_img)
    ImageView itemRecyclerShopsItemOrderImg;
    @BindView(R.id.item_recycler_shops_item_order_txt1_title)
    TextView itemRecyclerShopsItemOrderTxt1Title;
    @BindView(R.id.item_recycler_shops_item_order_txt2_detaiels)
    TextView itemRecyclerShopsItemOrderTxt2Detaiels;
    @BindView(R.id.item_recycler_shops_item_order_txt3_price)
    TextView itemRecyclerShopsItemOrderTxt3Price;
    @BindView(R.id.item_recycler_shops_item_order_et_note)
    EditText itemRecyclerShopsItemOrderEtNote;

    public ItemsData itemsData;
    public static int num = 1;
    private String note;
    private RoomDao roomDao;

    public FragmentUserOrderItem() {
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
        View view = inflater.inflate(R.layout.fragment_user_shops_orders_item, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        setData();
        roomDao = getInstance(getActivity()).roomDao();


        return view;
    }

    private void setData() {
        fragmentOrderItemTxtNumberOfOrder.setText(String.valueOf(num));
        itemRecyclerShopsItemOrderTxt1Title.setText(itemsData.getName());
        itemRecyclerShopsItemOrderTxt2Detaiels.setText(itemsData.getDescription());
        itemRecyclerShopsItemOrderTxt3Price.setText(String.valueOf(itemsData.getPrice()));
        Glide.with(getContext()).load(itemsData.getPhotoUrl()).into(itemRecyclerShopsItemOrderImg);
    }


    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    public void numberOfOrder(int num) {
        fragmentOrderItemTxtNumberOfOrder.setText(String.valueOf(num));
    }

    @OnClick({R.id.increase, R.id.decreas, R.id.item_order_btn_shopping})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.increase:
                if (num == 100) {
                    Toast.makeText(getActivity(), "you cant buy more than 100 cup in the siNgle order", Toast.LENGTH_LONG).show();
                } else {
                    num += 1;
                    fragmentOrderItemTxtNumberOfOrder.setText(String.valueOf(num));
                }
                break;
            case R.id.decreas:
                if (num == 1) {
                    Toast.makeText(getActivity(), "you cant chooes less than 1", Toast.LENGTH_SHORT).show();
                } else {
                    num -= 1;
                    fragmentOrderItemTxtNumberOfOrder.setText(String.valueOf(num));
                }
                break;
            case R.id.item_order_btn_shopping:

                note = itemRecyclerShopsItemOrderEtNote.getText().toString();

                itemsData.setCount(num);
                itemsData.setNote(note);

                roomDao.add(itemsData);
                Toast.makeText(getActivity(), "your order added", Toast.LENGTH_SHORT).show();

              //  replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_container, new FragmentUserOrderItemCart());

                break;
        }
    }
}
