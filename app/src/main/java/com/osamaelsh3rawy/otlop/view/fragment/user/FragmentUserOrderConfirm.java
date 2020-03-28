package com.osamaelsh3rawy.otlop.view.fragment.user;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.local.room.RoomDao;
import com.osamaelsh3rawy.otlop.data.local.room.RoomManager;
import com.osamaelsh3rawy.otlop.data.model.items.ItemsData;
import com.osamaelsh3rawy.otlop.data.model.userListOfRestourante.RestourantesData;
import com.osamaelsh3rawy.otlop.data.model.userMyOrder.MyOrder;
import com.osamaelsh3rawy.otlop.data.model.userMyOrder.MyOrderData;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_Api_Takin;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_Name;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_Phone;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.dismissProgressDialog;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.showProgressDialog;


public class FragmentUserOrderConfirm extends BaseFragment {


    @BindView(R.id.fragment_user_order_detaiels_ck_cash)
    RadioButton fragmentUserOrderDetaielsCkCash;
    @BindView(R.id.fragment_user_order_detaiels_ck_visa)
    RadioButton fragmentUserOrderDetaielsCkVisa;
    @BindView(R.id.fragment_complete_order_rg_pay_method)
    RadioGroup fragmentCompleteOrderRgPayMethod;
    @BindView(R.id.fragment_user_order_detaiels_et_detaiels)
    EditText fragmentUserOrderDetaielsEtDetaiels;
    @BindView(R.id.fragment_user_order_detaiels_et_address)
    EditText fragmentUserOrderDetaielsEtAddress;
    @BindView(R.id.fragment_user_order_detaiels_et_total_price)
    TextView fragmentUserOrderDetaielsEtTotalPrice;
    @BindView(R.id.fragment_user_order_detaiels_et_del_price)
    TextView fragmentUserOrderDetaielsEtDelPrice;
    @BindView(R.id.fragment_user_order_detaiels_et_total_price_total)
    TextView fragmentUserOrderDetaielsEtTotalPriceTotal;
    @BindView(R.id.fragment_user_order_detaiels_btn_confirm)
    Button fragmentUserOrderDetaielsBtnConfirm;
    public RestourantesData restourantesData;
    MyOrderData newOrder;
    public List<ItemsData> listitem = new ArrayList<>();
    private List<Integer> items = new ArrayList<>();
    private List<Integer> quantities = new ArrayList<>();
    private List<String> notes = new ArrayList<>();
    private int pay;
    private String name, api_tokin, phone;
    private ApiServies apiServies;
    private RoomDao roomDao;
    private double total;
    private int delevary_fee;
    private double totalAll;
    private  String TAG="FragmentUserOrderConfirm";

    public FragmentUserOrderConfirm() {
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
        View view = inflater.inflate(R.layout.fragment_user_order_confirm, container, false);
        ButterKnife.bind(this, view);
        intiFragment();

        apiServies = getClient();
        roomDao = RoomManager.getInstance(getActivity()).roomDao();

        setData();

        fragmentCompleteOrderRgPayMethod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.fragment_user_order_detaiels_ck_cash:
                        pay = 1;
                        break;
                    case R.id.fragment_user_order_detaiels_ck_visa:
                        pay = 2;
                        break;

                }
            }
        });

        return view;
    }

    private void setData() {
        api_tokin = SharedPreferencesManger.LoadData(getActivity(), User_Api_Takin);
        phone = SharedPreferencesManger.LoadData(getActivity(), User_Phone);
        name = SharedPreferencesManger.LoadData(getActivity(), User_Name);
//        delevary_fee = Integer.parseInt(restourantesData.getDeliveryCost());

        for (int i = 0; i < listitem.size(); i++) {
            total = listitem.get(i).getCount() * listitem.get(i).getPrice();
            quantities.add(listitem.get(i).getCount());
            items.add(listitem.get(i).getItemId());
            notes.add(listitem.get(i).getNote());
        }

//        fragmentUserOrderDetaielsEtDelPrice.setText(delevary_fee);
//        fragmentUserOrderDetaielsEtTotalPrice.setText(((int) total));
//        totalAll = total + delevary_fee;
//        fragmentUserOrderDetaielsEtTotalPriceTotal.setText((int) totalAll);
    }

    private void confirmRequest() {
        showProgressDialog(getActivity(), getString(R.string.please_wait));
        apiServies.addNewOrder(listitem.get(0).getRestaurantId(), fragmentUserOrderDetaielsEtDetaiels.getText().toString(),
                fragmentUserOrderDetaielsEtAddress.getText().toString(), pay, Integer.parseInt(phone), name,
                api_tokin, items, quantities, notes).enqueue(new Callback<MyOrder>() {
            @Override
            public void onResponse(Call<MyOrder> call, Response<MyOrder> response) {
                dismissProgressDialog();
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    } else {
                        Log.e(TAG,response.body().getMsg());
                    }
                } catch (Exception e) {
                    Log.e(TAG,e.getMessage());

                }
            }

            @Override
            public void onFailure(Call<MyOrder> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }


    @OnClick(R.id.fragment_user_order_detaiels_btn_confirm)
    public void onViewClicked() {
        confirmRequest();
    }
}
