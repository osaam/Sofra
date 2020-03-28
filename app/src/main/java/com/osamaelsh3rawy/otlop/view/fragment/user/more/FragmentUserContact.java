package com.osamaelsh3rawy.otlop.view.fragment.user.more;

import android.os.Bundle;
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
import com.osamaelsh3rawy.otlop.data.model.contactUs.ContactUs;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.dismissProgressDialog;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.showProgressDialog;


public class FragmentUserContact extends BaseFragment {

    ApiServies apiServies;
    @BindView(R.id.fragment_user_contact_txt_name)
    EditText fragmentUserContactTxtName;
    @BindView(R.id.fragment_user_contact_txt_email)
    EditText fragmentUserContactTxtEmail;
    @BindView(R.id.fragment_user_contact_txt_phone)
    EditText fragmentUserContactTxtPhone;

    @BindView(R.id.fragment_contact_us_rb_complain)
    RadioButton fragmentContactUsRbComplain;
    @BindView(R.id.fragment_contact_us_rb_suggestion)
    RadioButton fragmentContactUsRbSuggestion;
    @BindView(R.id.fragment_contact_us_rb_enquiry)
    RadioButton fragmentContactUsRbEnquiry;
    @BindView(R.id.fragment_contact_us_rg_message_type)
    RadioGroup fragmentContactUsRgMessageType;
    @BindView(R.id.fragment_user_contact_txt)
    TextView fragmentUserContactTxt;
    @BindView(R.id.fragment_user_contact_us_et_description)
    EditText fragmentUserContactUsEtDescription;
    @BindView(R.id.fragment_user_contact_us_btn_send)
    Button fragmentUserContactUsBtnSend;
    private String type;

    public FragmentUserContact() {
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
        View view = inflater.inflate(R.layout.fragment_user_contact, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        apiServies = getClient();
        fragmentContactUsRgMessageType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.fragment_contact_us_rb_complain:
                        type = "complaint";
                        break;
                    case R.id.fragment_contact_us_rb_suggestion:
                        type = "suggestion";
                        break;
                    case R.id.fragment_contact_us_rb_enquiry:
                        type = "inquiry";
                        break;
                }
            }
        });

        return view;
    }

    private void sendMessage() {
        showProgressDialog(getActivity(), getString(R.string.please_wait));
        apiServies.sendMessage(fragmentUserContactTxtName.getText().toString(),
                fragmentUserContactTxtEmail.getText().toString(),
                fragmentUserContactTxtPhone.getText().toString(),
                type, fragmentUserContactUsEtDescription.getText().toString()).enqueue(new Callback<ContactUs>() {
            @Override
            public void onResponse(Call<ContactUs> call, Response<ContactUs> response) {
                if (response.body().getStatus()==1) {
                    dismissProgressDialog();
                    Toast.makeText(getActivity(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                replaceFragment(getActivity().getSupportFragmentManager(),R.id.activity_user_container,new FragmentUserMore());}
            }

            @Override
            public void onFailure(Call<ContactUs> call, Throwable t) {

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

    @OnClick(R.id.fragment_user_contact_us_btn_send)
    public void onViewClicked() {
        sendMessage();
    }
}
