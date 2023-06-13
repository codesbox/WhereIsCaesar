package com.example.whereiscaesarv2.presentation.ui.fragments;

import static com.example.whereiscaesarv2.presentation.ui.fragments.MainMapFragment.bottomSheetBehavior;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.whereiscaesarv2.R;
import com.example.whereiscaesarv2.databinding.FragmentAddRestaurantBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRestaurantFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddRestaurantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRestaurantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRestaurantFragment newInstance(String param1, String param2) {
        AddRestaurantFragment fragment = new AddRestaurantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_restaurant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentAddRestaurantBinding binding = FragmentAddRestaurantBinding.bind(view);

        String id = getArguments().getString("id");

        binding.signUpButton.setOnClickListener(v -> {

            String restaurantName = binding.firstNameRes.getText().toString();
            Double latitude = Double.valueOf(binding.lati.getText().toString());
            Double longitude = Double.valueOf(binding.longi.getText().toString());
            String address = binding.adress.getText().toString();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference restaurantsRef = db.collection("Restaurants");
            String restaurantId = restaurantsRef.document().getId();

            // Создание данных для добавления
            Map<String, Object> restaurantData = new HashMap<>();
            restaurantData.put("allSum", 0);
            restaurantData.put("allCount", 0);
            restaurantData.put("dishes", new HashMap<>());
            restaurantData.put("dishesNames", new ArrayList<>());
            restaurantData.put("latitude", latitude);
            restaurantData.put("longitude", longitude);
            restaurantData.put("name", restaurantName);
            restaurantData.put("mainPoint", true);
            restaurantData.put("address", true);
            restaurantData.put("userId", id);

            GeoPoint location = new GeoPoint(latitude, longitude);
            restaurantData.put("geoPoint", location);

            // Добавление документа в коллекцию Restaurants
            restaurantsRef.document(restaurantName)
                    .set(restaurantData, SetOptions.merge())
                    .addOnSuccessListener(aVoid -> {
                        // Успешное добавление
                        Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                        // Ошибка при добавлении
                    });
        });
    }
}