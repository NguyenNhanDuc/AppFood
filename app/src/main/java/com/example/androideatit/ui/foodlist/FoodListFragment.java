package com.example.androideatit.ui.foodlist;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androideatit.Adapter.MyFoodListAdapter;
import com.example.androideatit.Common.Common;
import com.example.androideatit.EventBus.MenuItemBack;
import com.example.androideatit.Model.CategoryModel;
import com.example.androideatit.Model.FoodModel;
import com.example.androideatit.R;
import com.example.androideatit.databinding.FragmentFoodListBinding;
import com.example.androideatit.databinding.FragmentSlideshowBinding;
import com.example.androideatit.ui.slideshow.SlideshowViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FoodListFragment extends Fragment {

    private FragmentFoodListBinding binding;

    private FoodListViewModel foodListViewModel;

    Unbinder unbinder;
    @BindView(R.id.recycler_food_list)
    RecyclerView recycler_food_list;

    LayoutAnimationController layoutAnimationController;
    MyFoodListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        foodListViewModel =
                new ViewModelProvider(this).get(FoodListViewModel.class);

        View root  = inflater.inflate(R.layout.fragment_food_list, container, false);

        unbinder = ButterKnife.bind(this, root);
        initView();
        System.out.println("");
        foodListViewModel.getMutableLiveDataFoodList().observe(getViewLifecycleOwner(), new Observer<List<FoodModel>>() {
            @Override
            public void onChanged(List<FoodModel> foodModels) {
                if(foodModels != null) {
                    adapter = new MyFoodListAdapter(getContext(), foodModels);
                    recycler_food_list.setAdapter(adapter);
                    recycler_food_list.setLayoutAnimation(layoutAnimationController);
                }
            }
        });
        return root;
    }

    private void initView() {
        setHasOptionsMenu(true);

        ((AppCompatActivity)getActivity())
                .getSupportActionBar()
                .setTitle(Common.categorySelected.getName());

        recycler_food_list.setHasFixedSize(true);
        recycler_food_list.setLayoutManager(new LinearLayoutManager(getContext()));

        layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_item_from_left);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        //Event
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //Clear text when click to clear button on search view
        ImageView closeButton = (ImageView) searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        closeButton.setOnClickListener(view -> {
            EditText ed = (EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
            //Clear text
            ed.setText("");
            //Clear query
            searchView.setQuery("", false);
            //Collapse the action view
            searchView.onActionViewCollapsed();
            //Collapse the search widget
            menuItem.collapseActionView();
            //Restore result to original
            foodListViewModel.getMutableLiveDataFoodList();
        });
    }

    private void startSearch(String s) {
        List<FoodModel> resultList = new ArrayList<>();
        for(int i=0; i<Common.categorySelected.getFoods().size(); i++) {
            FoodModel foodModel = Common.categorySelected.getFoods().get(i);
            if(foodModel.getName().toLowerCase().contains(s)) {
                resultList.add(foodModel);
            }
        }

        foodListViewModel.getMutableLiveDataFoodList().setValue(resultList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().postSticky(new MenuItemBack());
        super.onDestroy();
    }
}