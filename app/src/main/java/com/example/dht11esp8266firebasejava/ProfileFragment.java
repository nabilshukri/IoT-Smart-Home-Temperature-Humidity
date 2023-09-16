package com.example.dht11esp8266firebasejava;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.profile_fragment,container,false);

        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        TextView txtName = view.findViewById(R.id.name);
        TextView txtEmail = view.findViewById(R.id.email);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getEmail();
        String username = email.substring(0, email.indexOf("@"));
        txtEmail.setText(email);
        txtName.setText(username);



        return view;
    }
}