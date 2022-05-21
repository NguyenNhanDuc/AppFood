package com.example.androideatit.ui.view_orders;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androideatit.Adapter.MyOrdersAdapter;
import com.example.androideatit.Callback.ILoadOrderCallbackListener;
import com.example.androideatit.Common.Common;
import com.example.androideatit.Model.Order;
import com.example.androideatit.R;
import com.example.androideatit.databinding.FragmentSlideshowBinding;
import com.example.androideatit.databinding.FragmentViewOrderBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class ViewOrdersFragment extends Fragment implements ILoadOrderCallbackListener {

    private FragmentViewOrderBinding binding;
    @BindView(R.id.recycler_orders)
    RecyclerView recycler_orders;

    AlertDialog dialog;
    private Unbinder unbinder;

    private ViewOrdersViewModel viewOrdersViewModel;

    private ILoadOrderCallbackListener listener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewOrdersViewModel =
                new ViewModelProvider(this).get(ViewOrdersViewModel.class);

        binding = FragmentViewOrderBinding .inflate(inflater, container, false);
        View root = binding.getRoot();
        unbinder = ButterKnife.bind(this, root);

        initViews(root);
        loadOrderFromFirebase();

        viewOrdersViewModel.getMutableLiveDataOrderList().observe(getViewLifecycleOwner(), orderList -> {
            MyOrdersAdapter adapter = new MyOrdersAdapter(getContext(), orderList);
            recycler_orders.setAdapter(adapter);
        });
        return root;
    }

    private void loadOrderFromFirebase() {
        List<Order> orderList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference(Common.ORDER_REF)
                .orderByChild("userId")
                .equalTo(Common.currentUser.getUid())
                .limitToLast(100)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot orderSnapshot:snapshot.getChildren()) {
                            Order order = orderSnapshot.getValue(Order.class);
                            order.setOrderNumber(orderSnapshot.getKey()); // Remember set it
                            orderList.add(order);
                        }
                        listener.onLoadOrderSuccess(orderList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onLoadOrderFailed(error.getMessage());
                    }
                });
    }

    private void initViews(View root) {
        listener = this;
        dialog = new SpotsDialog.Builder().setCancelable(false).setContext(getContext()).build();

        recycler_orders.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler_orders.setLayoutManager(layoutManager);
        recycler_orders.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onLoadOrderSuccess(List<Order> orderList) {
        dialog.dismiss();
        viewOrdersViewModel.setMutableLiveDataOrderList(orderList);
    }

    @Override
    public void onLoadOrderFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}