package com.osamaelsh3rawy.otlop.view.fragment.restourant.moreRestourant;

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
import com.osamaelsh3rawy.otlop.data.model.userListOfRestourante.RestourantesData;
import com.osamaelsh3rawy.otlop.data.model.userRestourantReview.RestourantReview;
import com.osamaelsh3rawy.otlop.data.model.userRestourantReview.UserRestaurantReviewData;
import com.osamaelsh3rawy.otlop.helper.OnEndLess;
import com.osamaelsh3rawy.otlop.view.activity.BaseActivity;
import com.osamaelsh3rawy.otlop.view.folder.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.osamaelsh3rawy.otlop.data.api.ApiClient.getClient;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.Restourant_Api_Takin;
import static com.osamaelsh3rawy.otlop.data.local.Constanse.User_Api_Takin;


public class FragmentRestourantComment extends BaseFragment {


    @BindView(R.id.recycler_restourant_review)
    RecyclerView recyclerRestourantReview;
     private ReviewAdapter reviewAdapter;
    private ApiServies apiServies=getClient();
    private OnEndLess onEndLess;
    private int MaxPage;
    private List<UserRestaurantReviewData> listItem=new ArrayList<>();
    private RestourantesData restourantesData;

    public FragmentRestourantComment() {
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
        View view = inflater.inflate(R.layout.fragment_restourant_comment, container, false);
        ButterKnife.bind(this, view);
        intiFragment();


        return view;
    }

    private void inti() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerRestourantReview.setLayoutManager(linearLayoutManager);


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
        recyclerRestourantReview.setAdapter(reviewAdapter);

        getAllReview(1);
    }

    private void getAllReview(int page) {
        apiServies.Reviews(SharedPreferencesManger.LoadData(getActivity(), Restourant_Api_Takin), restourantesData.getId(), page).enqueue(new Callback<RestourantReview>() {
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
}
