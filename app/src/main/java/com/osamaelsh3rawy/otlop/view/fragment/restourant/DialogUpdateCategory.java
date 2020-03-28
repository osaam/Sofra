package com.osamaelsh3rawy.otlop.view.fragment.restourant;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.TypeFoodAdapter;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.restourantCategory.RestourantCategory;
import com.osamaelsh3rawy.otlop.data.model.restourantCategory.RestourantCategoryData;
import com.osamaelsh3rawy.otlop.data.model.restourantCategory.RestourantOneCategory;
import com.osamaelsh3rawy.otlop.helper.HelperMethods;
import com.osamaelsh3rawy.otlop.helper.MediaLoader;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Api_Takin;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.convertFileToMultipart;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.convertToRequestBody;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.initImage3;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.showProgressDialog;

public class DialogUpdateCategory extends Dialog {

    private TypeFoodAdapter typeFoodAdapter;
    @BindView(R.id.dialo_update_img)
    CircleImageView dialoUpdateImg;
    @BindView(R.id.dialog_update_et)
    EditText dialogUpdateEt;
    @BindView(R.id.dialog_update_btn)
    Button dialogUpdateBtn;
    private Activity activity;
    private Context context;
    private boolean Cancelable;
    public int positionUpdate;
    private String path;
    private String TAG = "DialogUpdateCategory";
    public RestourantCategoryData categoryData;
    RequestBody name, api_takin, category_id;
    MultipartBody.Part photo;
    private ApiServies apiServies;
    private ArrayList<RestourantCategoryData> categoryDataList = new ArrayList<>();


    public DialogUpdateCategory(@NonNull Context context) {
        super(context);
    }

    public DialogUpdateCategory(Context context, Activity activity, boolean Cancelable, TypeFoodAdapter typeFoodAdapter) {
        super(context);
        this.activity = activity;
        this.context = context;
        this.Cancelable = Cancelable;
        this.typeFoodAdapter = typeFoodAdapter;
        onCreate();
    }


    public void onCreate() {
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_update_categories);
            ButterKnife.bind(this);
            DialogUpdateCategory.this.setCancelable(Cancelable);

            if (categoryData != (null)) {
            }

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            show();
            intialize();
        } catch (Exception e) {

        }


    }

    private void intialize() {
        dialogUpdateEt.setText(categoryData.getName());
        Glide.with(context).load(categoryData.getPhotoUrl()).into(dialoUpdateImg);
    }

    @OnClick({R.id.dialo_update_img, R.id.dialog_update_et, R.id.dialog_update_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialo_update_img:
                initImage3(dialoUpdateImg,getContext());
                break;
            case R.id.dialog_update_btn:
                getNewCategory();
                break;
        }
    }

    private void getNewCategory() {
//        if (name.equals(0)) {
//            Toast.makeText(activity, "Please Enter Category Name", Toast.LENGTH_SHORT).show();
//        } else if (photo.equals(0)) {
//            Toast.makeText(activity, "Please Enter Category Photo", Toast.LENGTH_SHORT).show();
//        } else {
        //}


        name = convertToRequestBody(dialogUpdateEt.getText().toString().trim());
        photo = convertFileToMultipart(path, "photo");
        api_takin = convertToRequestBody(SharedPreferencesManger.LoadData(activity, Restourant_Api_Takin));
        category_id = convertToRequestBody(categoryData.getId().toString().trim());

        apiServies = getClient();
        apiServies.RestourantUpdateCategory(name, photo, api_takin, category_id).enqueue(new Callback<RestourantOneCategory>() {
            @Override
            public void onResponse(Call<RestourantOneCategory> call, Response<RestourantOneCategory> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        categoryDataList.remove(categoryData);
                        categoryDataList.add(response.body().getData());
                        dismiss();
                    } else {
                        Log.e(TAG, response.body().getMsg());
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RestourantOneCategory> call, Throwable t) {

            }
        });
    }


}
