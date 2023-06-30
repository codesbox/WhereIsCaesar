package com.example.whereiscaesarv2.presentation.ui.fragments.account;

import static com.example.whereiscaesarv2.presentation.app.App.isAuto;
import static com.example.whereiscaesarv2.presentation.ui.fragments.MainMapFragment.bottomSheetBehavior;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.domain.models.AccountModelDomain;
import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentUserProfileBSBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfileBSFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile_b_s, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setPeekHeight(0);
        bottomSheetBehavior.setDraggable(true);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        isAuto = false;

        FragmentUserProfileBSBinding binding = FragmentUserProfileBSBinding.bind(view);
        AccountModelDomain accountModelDomain = (AccountModelDomain) getArguments().getSerializable("account");

        binding.userName.setText(String.format("Здравствуйте, %s", accountModelDomain.firstName));
        binding.userCount.setText(String.format("Критик еды %s уровня", accountModelDomain.feedBackCount.toString()));
        binding.signOut.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Navigation.findNavController(requireActivity(), R.id.containerBottomSheet).popBackStack(R.id.account_graph, false);
        });
        binding.myFeedbacks.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.mainFragmentContainerView).navigate(R.id.action_mainMapFragment_to_myFeedbacksFragment2);
        });

        binding.getRestaurator.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setTitle("Подтверждение")
                    .setMessage("Вы точно хотите стать ресторатором? Процесс необратим")
                    .setPositiveButton("Стать ресторатором", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            FirebaseFirestore db = FirebaseFirestore.getInstance();


                            CollectionReference collectionRef = db.collection("Users");
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                            FirebaseUser currentUser = mAuth.getCurrentUser();



                            DocumentReference documentRef = collectionRef.document(currentUser.getUid());


                            documentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            // Документ найден, меняем значение поля "q" на "hhfbhdbc"
                                            documentRef.update("type", "r")
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                NavHostFragment.findNavController(UserProfileBSFragment.this).navigate(R.id.splashAccountBSFragment);
                                                            } else {
                                                                Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        } else {
                                            Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        }
                    })
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();



        });
    }
}