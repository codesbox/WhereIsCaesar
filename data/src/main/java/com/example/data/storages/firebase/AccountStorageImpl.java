package com.example.data.storages.firebase;

import static android.content.ContentValues.TAG;
import static com.example.data.util.LogTags.DATABASE_ERROR;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.data.storages.models.AccountModelData;
import com.example.data.storages.models.AddUserModelData;
import com.example.domain.listeners.AccountListener;
import com.example.domain.listeners.AddUserListener;
import com.example.domain.models.AccountModelDomain;
import com.example.domain.models.AddUserModelDomain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AccountStorageImpl implements AccountStorage{
    @Override
    public void getAccountData(AccountListener listener, String id) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference usersCollection = db.collection("Users");

        usersCollection.whereEqualTo("id", id)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        QuerySnapshot querySnapshot = task.getResult();
                        if (!querySnapshot.isEmpty()) {

                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                            String firstName = documentSnapshot.getString("firstName");
                            String lastName = documentSnapshot.getString("lastName");
                            String userId = documentSnapshot.getString("id");
                            String phoneNumber = documentSnapshot.getString("phoneNumber");
                            String type = documentSnapshot.getString("type");
                            Integer feedbackCount = Objects.requireNonNull(documentSnapshot.getLong("feedbackCount")).intValue();

                            AccountModelData accountModelData = new AccountModelData(firstName, lastName, phoneNumber, userId, type, feedbackCount);

                            AccountModelDomain accountModelDomain = new AccountModelDomain(accountModelData.firstName, accountModelData.lastName, accountModelData.phoneNumber,
                                    accountModelData.id, accountModelData.type, accountModelData.feedBackCount);

                            listener.getAccountData(accountModelDomain);

                        }
                        else{
                            Log.w(DATABASE_ERROR, "AccountStorageImplEmpty");
                        }

                    }
                    else{
                        Log.w(DATABASE_ERROR, "AccountStorageImpl");
                    }
                });

    }

    @Override
    public void addUser(AddUserListener listener, AddUserModelDomain addUserModelDomain) {

        AddUserModelData addUserModelData = new AddUserModelData(addUserModelDomain.firstName,
                addUserModelDomain.lastName, addUserModelDomain.email, addUserModelDomain.password);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(addUserModelData.email, addUserModelData.password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()){
                        String userId = task.getResult().getUser().getUid();

                        Map<String, Object> userData = new HashMap<>();
                        userData.put("id", userId);
                        userData.put("firstName", addUserModelData.firstName);
                        userData.put("lastName", addUserModelData.lastName);
                        userData.put("feedbackCount", 0);
                        userData.put("type", "u");
                        userData.put("phoneNumber", "");

                        FirebaseFirestore.getInstance().collection("Users").document(userId)
                                .set(userData)
                                .addOnSuccessListener(aVoid -> {
                                    listener.onSuccess();
                                })
                                .addOnFailureListener(e -> {
                                    Log.e(TAG, "Error adding user document", e);
                                    listener.onFailure();
                                });
                    }
                    else{
                        listener.onFailure();
                    }
                });


    }
}
