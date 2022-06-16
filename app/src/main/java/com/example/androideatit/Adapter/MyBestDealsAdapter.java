package com.example.androideatit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.asksira.loopingviewpager.LoopingViewPager;
import com.bumptech.glide.Glide;
import com.example.androideatit.EventBus.BestDealItemClick;
import com.example.androideatit.Model.BestDealModel;
import com.example.androideatit.R;
import com.example.androideatit.databinding.FragmentHomeBinding;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyBestDealsAdapter extends LoopingPagerAdapter<BestDealModel> {

    Context context;
    @BindView(R.id.img_best_deal)
    ImageView img_best_deal;
    @BindView(R.id.txt_best_deal)
    TextView txt_best_deal;

    Unbinder unbinder;

    public MyBestDealsAdapter(@NonNull List<? extends BestDealModel> itemList, boolean isInfinite) {
        super(itemList, isInfinite);
    }

    public MyBestDealsAdapter(Context context, @NonNull List<? extends BestDealModel> itemList, boolean isInfinite) {
        super(itemList, isInfinite);
        this.context = context;
    }

    @Override
    protected void bindView(@NonNull View view, int i, int i1) {
        unbinder = ButterKnife.bind(this, view);

        Glide.with(view).load(getItemList().get(i).getImage()).into(img_best_deal);
        txt_best_deal.setText(getItemList().get(i).getName());

        view.setOnClickListener(view1 -> {
            EventBus.getDefault().postSticky(new BestDealItemClick(getItemList().get(i)));
        });
    }

    @NonNull
    @Override
    protected View inflateView(int i, @NonNull ViewGroup viewGroup, int i1) {
        return LayoutInflater.from(context).inflate(R.layout.layout_best_deal_item, viewGroup, false);
    }
}

