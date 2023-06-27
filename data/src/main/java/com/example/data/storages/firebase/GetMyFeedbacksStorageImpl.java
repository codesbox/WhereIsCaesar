package com.example.data.storages.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.domain.listeners.GetMyFeedbacksListener;
import com.example.domain.models.MyFeedback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GetMyFeedbacksStorageImpl implements GetMyFeedbacksStorage{
    @Override
    public void getMyFeedbacks(String userId, GetMyFeedbacksListener listener) {

        List<MyFeedback> myFeedbackList = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference feedbacksRef = db.collection("Feedbacks");

        feedbacksRef.whereEqualTo("userId", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                List<DocumentSnapshot> documentSnapshots = querySnapshot.getDocuments();
                                for(DocumentSnapshot documentSnapshot: documentSnapshots){
                                    myFeedbackList.add(new MyFeedback(documentSnapshot.getString("restaurantName"), documentSnapshot.getString("dishName"), documentSnapshot.getString("feedbackText"), documentSnapshot.getDouble("estimation").intValue()));
                                }
                                listener.onSuccess(myFeedbackList);

                            } else {
                                listener.onFailure();
                            }
                        } else {
                            Log.e("FirestoreExample", "Ошибка при получении документа: ", task.getException());
                        }
                    }
                });

    }
}
