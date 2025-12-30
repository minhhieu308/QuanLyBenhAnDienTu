package com.example.quanlybenhandientu;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String ho, ten, userEmail;
    private ImageView userPicture;
    private TextView profileName, profileEmail;
    private Button suaThongTin, dangXuat;


    private String mParam1;
    private String mParam2;

    public PatientProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientProfileFragment newInstance(String param1, String param2) {
        PatientProfileFragment fragment = new PatientProfileFragment();
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
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_profile, container, false);
        // Inflate the layout for this fragment
        profileName = view.findViewById(R.id.name_patient_profile);
        profileEmail = view.findViewById(R.id.email_patient_profile);
        userPicture = view.findViewById(R.id.user_image_patient_profile);
        suaThongTin = view.findViewById(R.id.suaThongTin_patient_profile);
        dangXuat = view.findViewById(R.id.dangXuat_patient_profile);
        getInfo();
        dangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance().signOut(requireContext()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(getActivity(), Login.class));
                        getActivity().finish();
                    }
                });
            }
        });
        return view;
    }
    private void getInfo(){
        StringBuilder stringBuilder = new StringBuilder();
        String email = mAuth.getCurrentUser().getEmail().toString();
        db.collection("users").document(email).collection("info").document("info").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if(document.exists()){
                    ten = document.getString("ten");
                    ho = document.getString("ho");
                    userEmail = document.getString("email");
                    stringBuilder.append(ho);
                    stringBuilder.append(" ");
                    stringBuilder.append(ten);
                    profileName.setText(stringBuilder);
                    profileEmail.setText(userEmail);
                }
            }
        });
    }
}