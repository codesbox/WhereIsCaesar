package com.example.data.storages.firebase;

import static com.example.data.util.LogTags.DATABASE_ERROR;

import android.util.Log;

import com.example.data.storages.models.DishModelData;
import com.example.domain.listeners.DishesListener;
import com.example.domain.models.DishModelDomain;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class DishesStorageImpl implements  DishesStorage{

    private final String IMAGE_URL = "image";
    private final String IS_CATEGORY = "isc";


    @Override
    public void getDishes(String tag, DishesListener listener) {


        List<DishModelData> dishesDataList = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final String COLLECTION = "Категории и блюда";
        CollectionReference collectionRef = db.collection(COLLECTION);

        final String MAIN_TAG = "maintag";
        Query query = collectionRef.whereEqualTo(MAIN_TAG, tag);

        query.get().addOnCompleteListener(task -> {

            if (task.isSuccessful()){

                List<DocumentSnapshot> documents = task.getResult().getDocuments();

                for (DocumentSnapshot document : documents){
                    dishesDataList.add(new DishModelData(
                            document.getId(), document.getString(IMAGE_URL), document.getBoolean(IS_CATEGORY)));
                }

                List<DishModelDomain> dishesDomainList = new ArrayList<>();

                for (DishModelData document : dishesDataList){
                    dishesDomainList.add(new DishModelDomain(
                            document.title, document.imageUrl, document.IsCategory)
                    );
                }

                listener.getDishes(dishesDomainList);

            }
            else {
                Log.w(DATABASE_ERROR, "DishesStorageImpl");
            }

        });

    }

}
