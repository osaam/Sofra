package com.osamaelsh3rawy.otlop.view.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.adapter.ReviewAdapter;
import com.osamaelsh3rawy.otlop.data.api.ApiServies;
import com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger;
import com.osamaelsh3rawy.otlop.data.model.items.ItemsData;
import com.osamaelsh3rawy.otlop.data.model.userListOfRestourante.RestourantesData;
import com.osamaelsh3rawy.otlop.data.model.userRestourantReview.RestourantReview;
import com.osamaelsh3rawy.otlop.data.model.userRestourantReview.UserRestaurantReviewData;
import com.osamaelsh3rawy.otlop.helper.HelperMethods;
import com.osamaelsh3rawy.otlop.helper.OnEndLess;
import com.osamaelsh3rawy.otlop.view.activity.ActivityUserLoginCycle;
import com.osamaelsh3rawy.otlop.view.activity.BaseActivity;
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
import static com.osamaelsh3rawy.otlop.data.local.SharedPreferencesManger.loadUserData;
import static com.osamaelsh3rawy.otlop.helper.HelperMethods.replaceFragment;


public class FragmentUserShopComment extends BaseFragment {


    public RestourantesData restourantesData;
    @BindView(R.id.recycler_review)
    RecyclerView recyclerReview;
    private OnEndLess onEndLess;
    private int MaxPage;
    private ReviewAdapter reviewAdapter;
    private List<UserRestaurantReviewData> listItem = new ArrayList<>();
    private ApiServies apiServies;
    ItemsData itemsData;
    private String tokin;
    private boolean goLogin = false;

    public FragmentUserShopComment() {
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
        View view = inflater.inflate(R.layout.fragment_user_shop_comment, container, false);
        ButterKnife.bind(this, view);
        intiFragment();
        apiServies = getClient();
        inti();


        return view;
    }

    private void inti() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerReview.setLayoutManager(linearLayoutManager);


        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= MaxPage) {
                    if (MaxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        getAllReview(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }
            }
        };

        reviewAdapter = new ReviewAdapter((BaseActivity) getActivity(), getActivity(), listItem);
        recyclerReview.setAdapter(reviewAdapter);

        recyclerReview.addOnScrollListener(onEndLess);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (goLogin && loadUserData(getActivity()) != null) {
            goLogin = false;

            DialogAddComment dialogAddComment = new DialogAddComment(getActivity(), true, reviewAdapter);
            dialogAddComment.restourantesData = restourantesData;
            dialogAddComment.show();
            reviewAdapter.notifyDataSetChanged();
        }
    }

    private void getAllReview(int page) {
        tokin = SharedPreferencesManger.LoadData(getActivity(), User_Api_Takin);

        apiServies.Reviews(tokin, restourantesData.getId(), page).enqueue(new Callback<RestourantReview>() {
            @Override
            public void onResponse(Call<RestourantReview> call, Response<RestourantReview> response) {

                if (response.body().getStatus() == 1) {
                    listItem.addAll(response.body().getData().getData());
                    reviewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RestourantReview> call, Throwable t) {

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

    @OnClick(R.id.fragment_user_comment_btn_add)
    public void onViewClicked() {
        tokin = SharedPreferencesManger.LoadData(getActivity(), User_Api_Takin);
        if (tokin == (null)) {
            Intent intent = new Intent(getActivity(), ActivityUserLoginCycle.class);
            getActivity().startActivity(intent);
        } else {
            DialogAddComment dialogAddComment = new DialogAddComment(getActivity(), true, reviewAdapter);
            dialogAddComment.restourantesData = restourantesData;
            dialogAddComment.show();
            reviewAdapter.notifyDataSetChanged();

            //  }
        }
    }
}
