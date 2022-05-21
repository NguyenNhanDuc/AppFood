package com.example.androideatit.ui.comments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androideatit.Adapter.MyCommentAdapter;
import com.example.androideatit.Callback.ICommentCallbackListener;
import com.example.androideatit.Common.Common;
import com.example.androideatit.Model.CommentModel;
import com.example.androideatit.R;
import com.example.androideatit.databinding.FragmentSlideshowBinding;
import com.example.androideatit.ui.fooddetail.FoodDetailViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
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

public class CommentFragment extends BottomSheetDialogFragment implements ICommentCallbackListener {

    //private Fragment
    //private FragmentCommentBinding binding;

    private CommentViewModel commentViewModel;

    private Unbinder unbinder;

    @BindView(R.id.recycler_comment)
    RecyclerView recycler_comment;

    AlertDialog dialog;
    ICommentCallbackListener listener;

    public CommentFragment() {
        listener = this;
    }

    private static CommentFragment instance;

    public static CommentFragment getInstance() {
        if ( instance == null) {
            instance = new CommentFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        commentViewModel =
                new ViewModelProvider(this).get(CommentViewModel.class);

        View root = inflater.inflate(R.layout.bottom_sheet_comment_fragment, container, false);
        unbinder = ButterKnife.bind(this, root);

        initView();
        loadCommentsFromFirebase();
        commentViewModel.getMutableLiveDataFoodList().observe(this , commentModels -> {
            MyCommentAdapter adapter = new MyCommentAdapter(getContext(), commentModels);
            recycler_comment.setAdapter(adapter);
        });
        return root;
    }

    private void loadCommentsFromFirebase() {
        dialog.show();
        List<CommentModel> commentModels = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference(Common.COMMENT_REF)
                .child(Common.selectedFood.getId())
                .orderByChild("commentTimeStamp")
                .limitToLast(100)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot commentSnapshop: snapshot.getChildren()) {
                            CommentModel commentModel = commentSnapshop.getValue(CommentModel.class);
                            commentModels.add(commentModel);
                        }
                        listener.onCommentLoadSuccess(commentModels);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onCommentLoadFailed(error.getMessage());
                    }
                });
    }

    private void initView() {

        dialog = new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();

        recycler_comment.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recycler_comment.setLayoutManager(layoutManager);
        recycler_comment.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
    }

    @Override
    public void onCommentLoadSuccess(List<CommentModel> commentModels) {
        dialog.dismiss();
        commentViewModel.setCommentList(commentModels);
    }

    @Override
    public void onCommentLoadFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
