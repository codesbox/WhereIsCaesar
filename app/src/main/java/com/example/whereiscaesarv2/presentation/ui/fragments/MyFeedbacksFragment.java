package com.example.whereiscaesarv2.presentation.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.domain.listeners.GetMyFeedbacksListener;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentMyFeedbacksBinding;
import com.example.whereiscaesarv2.presentation.ui.recycler.MyFeedbackAdapter;
import com.example.domain.models.MyFeedback;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MyFeedbacksViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MyFeedbacksViewModelFactory;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MyRestaurantsViewModel;
import com.example.whereiscaesarv2.presentation.viewModels.viewmodels.MyRestaurantsViewModelFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MyFeedbacksFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_feedbacks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentMyFeedbacksBinding binding = FragmentMyFeedbacksBinding.bind(view);
        binding.goBack.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).popBackStack();
        });

        String userId = FirebaseAuth.getInstance().getUid();

        MyFeedbackAdapter adapter = new MyFeedbackAdapter(getContext());
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        GetMyFeedbacksListener listener = new GetMyFeedbacksListener() {
            @Override
            public void onSuccess(List<MyFeedback> myFeedbackList) {
                adapter.setItemList(myFeedbackList);
            }

            @Override
            public void onFailure() {
                Toast.makeText(requireContext(), "У вас нет ни одного отзыва", Toast.LENGTH_SHORT).show();
            }
        };

        MyFeedbacksViewModel viewModel = new ViewModelProvider(this, new MyFeedbacksViewModelFactory()).get(MyFeedbacksViewModel.class);
        viewModel.getMyFeedbacks(userId, listener);
    }
}