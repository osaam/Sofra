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
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.Settings.System.getString;
import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Api_Takin;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.convertFileToMultipart;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.convertToRequestBody;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.showProgressDialog;

public class DialogAddCategory extends Dialog {

    private TypeFoodAdapter typeFoodAdapter;
    @BindView(R.id.dialo_add_img)
    CircleImageView dialoAddImg;
    @BindView(R.id.dialog_add_et)
    EditText dialogAddEt;
    @BindView(R.id.dialog_add_btn)
    Button dialogAddBtn;
    List<RestourantCategoryData> categoryDataList = new ArrayList<>();
    private Activity activity;
    private Context context;
    private boolean Cancelable;
    private String path;
    public RestourantCategoryData categoryData;
    RequestBody name, api_takin;
    MultipartBody.Part photo;
    private String TAG="DialogAddCategory";
    private ApiServies apiServies;


    public DialogAddCategory(@NonNull Context context) {
        super(context);
    }

    public DialogAddCategory(Context context, Activity activity, boolean Cancelable, TypeFoodAdapter typeFoodAdapter, List<RestourantCategoryData> categoryDataList) {
        super(context);
        this.activity = activity;
        this.context = context;
        this.Cancelable = Cancelable;
        this.typeFoodAdapter = typeFoodAdapter;
        this.categoryDataList = categoryDataList;
        onCreate();
    }


    public void onCreate() {
        try {
            apiServies = getClient();
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_add_categories);
            ButterKnife.bind(this);
            DialogAddCategory.this.setCancelable(Cancelable);

            if (categoryData != (null)) {

            }

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            show();
        } catch (Exception e) {

        }
    }

    @OnClick({R.id.dialo_add_img, R.id.dialog_add_et, R.id.dialog_add_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialo_add_img:
                HelperMethods.initImage3(dialoAddImg,getContext());
                break;
            case R.id.dialog_add_et:
                break;
            case R.id.dialog_add_btn:
                getNewCategory();
                break;
        }
    }

    private void getNewCategory() {
        //        if (name != (null)&& photo != (null)) {
//            Toast.makeText(activity, "Please Enter your Category Name & photo", Toast.LENGTH_SHORT).show();
//        }
//        else {
        // }

        name = convertToRequestBody(dialogAddEt.getText().toString().trim());
        photo = convertFileToMultipart(path, "photo");
        api_takin = convertToRequestBody(SharedPreferencesManger.LoadData(activity, Restourant_Api_Takin));

        apiServies.RestourantAddCategory(name, photo, api_takin).enqueue(new Callback<RestourantOneCategory>() {
            @Override
            public void onResponse(Call<RestourantOneCategory> call, Response<RestourantOneCategory> response) {
              try{
                if (response.body().getStatus() == 1) {
                    categoryDataList.add(response.body().getData());
                    typeFoodAdapter.notifyDataSetChanged();
                    dismiss();
                }else{
                    Log.e(TAG,response.body().getMsg());
                }
              }catch (Exception e) {
                  Log.e(TAG,e.getMessage());
              }
              }



            @Override
            public void onFailure(Call<RestourantOneCategory> call, Throwable t) {

            }
        });
    }


}