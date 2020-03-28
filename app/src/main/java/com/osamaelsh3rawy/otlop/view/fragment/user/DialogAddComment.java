package com.osamaelsh3rawy.otlop.view.fragment.user;

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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.ReviewAdapter;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.userListOfRestourante.RestourantesData;
import com.osamaelsh3rawy.otlop.data.model.userRestourantReview.RestourantReview;
import com.osamaelsh3rawy.otlop.data.model.userRestourantReview.UserRestaurantReviewData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_Api_Takin;

public class DialogAddComment extends Dialog {
    @BindView(R.id.dialog_add_et)
    EditText dialogAddEt;
    @BindView(R.id.dialo_add_img_rate_love)
    ImageView dialoAddImgRateLove;
    @BindView(R.id.dialo_add_img_rate_happy)
    ImageView dialoAddImgRateHappy;
    @BindView(R.id.dialo_add_img_rate_ordinary)
    ImageView dialoAddImgRateOrdinary;
    @BindView(R.id.dialo_add_img_rate_sad)
    ImageView dialoAddImgRateSad;
    @BindView(R.id.dialo_add_img_rate_angry)
    ImageView dialoAddImgRateAngry;
    @BindView(R.id.dialog_add_comment_btn)
    Button dialogAddCommentBtn;
    private Activity activity;
    private static int rate;
    private boolean Cancelable;
    private ApiServies apiServies;
    public RestourantesData restourantesData;
    private ReviewAdapter reviewAdapter;
    private String TAG="DialogAddComment";
    private ArrayList<UserRestaurantReviewData> DataList = new ArrayList<>();


    public DialogAddComment(@NonNull Context context) {
        super(context);
    }


    protected DialogAddComment(@NonNull Context context, boolean cancelable, ReviewAdapter reviewAdapter) {
        super(context);
        this.activity = activity;
        this.Cancelable = Cancelable;
        this.reviewAdapter = reviewAdapter;
        onCreate();
    }

    public void onCreate() {
        try {
            apiServies = getClient();
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_add_rate);
            ButterKnife.bind(this);
            DialogAddComment.this.setCancelable(Cancelable);

            if (restourantesData != (null)) {

            }

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            show();
        } catch (Exception e) {

        }


    }

    @OnClick({R.id.dialo_add_img_rate_love, R.id.dialo_add_img_rate_happy, R.id.dialo_add_img_rate_ordinary, R.id.dialo_add_img_rate_sad, R.id.dialo_add_img_rate_angry, R.id.dialog_add_comment_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialo_add_img_rate_love:
                rate = 5;
                dialoAddImgRateLove.setImageResource(R.drawable.ic_face_love_green);
                dialoAddImgRateHappy.setImageResource(R.drawable.ic_face_happy);
                dialoAddImgRateOrdinary.setImageResource(R.drawable.ic_face_normal);
                dialoAddImgRateSad.setImageResource(R.drawable.ic_face_sad);
                dialoAddImgRateAngry.setImageResource(R.drawable.ic_face_angry);
                break;
            case R.id.dialo_add_img_rate_happy:
                rate = 4;
                dialoAddImgRateHappy.setImageResource(R.drawable.ic_face_happy_green);
                dialoAddImgRateLove.setImageResource(R.drawable.ic_face_love);
                dialoAddImgRateOrdinary.setImageResource(R.drawable.ic_face_normal);
                dialoAddImgRateSad.setImageResource(R.drawable.ic_face_sad);
                dialoAddImgRateAngry.setImageResource(R.drawable.ic_face_angry);
                break;
            case R.id.dialo_add_img_rate_ordinary:
                rate = 3;
                dialoAddImgRateOrdinary.setImageResource(R.drawable.ic_face_normal_green);
                dialoAddImgRateLove.setImageResource(R.drawable.ic_face_love);
                dialoAddImgRateHappy.setImageResource(R.drawable.ic_face_happy);
                dialoAddImgRateSad.setImageResource(R.drawable.ic_face_sad);
                dialoAddImgRateAngry.setImageResource(R.drawable.ic_face_angry);
                break;
            case R.id.dialo_add_img_rate_sad:
                rate = 2;
                dialoAddImgRateSad.setImageResource(R.drawable.ic_face_sad_green);
                dialoAddImgRateLove.setImageResource(R.drawable.ic_face_love);
                dialoAddImgRateHappy.setImageResource(R.drawable.ic_face_happy);
                dialoAddImgRateOrdinary.setImageResource(R.drawable.ic_face_normal);
                dialoAddImgRateAngry.setImageResource(R.drawable.ic_face_angry);

                break;
            case R.id.dialo_add_img_rate_angry:
                rate = 1;
                dialoAddImgRateAngry.setImageResource(R.drawable.ic_face_angry_grean);
                dialoAddImgRateLove.setImageResource(R.drawable.ic_face_love);
                dialoAddImgRateHappy.setImageResource(R.drawable.ic_face_happy);
                dialoAddImgRateOrdinary.setImageResource(R.drawable.ic_face_normal);
                dialoAddImgRateSad.setImageResource(R.drawable.ic_face_sad);

                break;
            case R.id.dialog_add_comment_btn:
                String tokin = SharedPreferencesManger.LoadData(activity, User_Api_Takin);
                String comment = dialogAddEt.getText().toString().trim();
                apiServies.UserAddComment(rate, comment, restourantesData.getId(), tokin).enqueue(new Callback<RestourantReview>() {
                    @Override
                    public void onResponse(Call<RestourantReview> call, Response<RestourantReview> response) {
                       try {
                           if (response.body().getStatus() == 1) {
                            DataList.addAll(response.body().getData().getData());
                            reviewAdapter.notifyDataSetChanged();
                            Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();

                               dismiss();
                           }
                       else {
                               Log.e(TAG,response.body().getMsg());
                               Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                               dismiss();
                        }
                       }catch (Exception e){
                           Log.e(TAG,e.getMessage());
                       }
                    }

                    @Override
                    public void onFailure(Call<RestourantReview> call, Throwable t) {

                    }
                });
                break;
        }
    }
}
