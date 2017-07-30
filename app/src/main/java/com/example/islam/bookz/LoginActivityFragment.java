package com.example.islam.bookz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {

    private Button logingButton;
    private Button signupButton;
    private EditText emailField;
    private EditText passwordField;
    private String email;
    private String password;
    private ProgressDialog progressDialog;
    private Authenticator authenticator;

    public LoginActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        checkSession();

        logingButton = (Button) view.findViewById(R.id.signinbtn);
        signupButton = (Button) view.findViewById(R.id.signup);
        emailField = (EditText) view.findViewById(R.id.mailfield);
        passwordField = (EditText) view.findViewById(R.id.passwordfield);
        progressDialog = new ProgressDialog(getActivity());
        authenticator = Authenticator.getInstance();

        logingButton.setOnClickListener(view1 -> {
            progressDialog.setMessage("please wait ...");
            progressDialog.setTitle("loading");
            progressDialog.show();
            login();
        });

        signupButton.setOnClickListener(view1 -> {
            signup();
        });
        return view;
    }

    private void checkSession() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void signup() {
        Intent intent = new Intent(getActivity(), SignupActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void login() {
        email = emailField.getText().toString();
        password = passwordField.getText().toString();
        authenticator.signIn(getActivity(), email, password, task -> {
            progressDialog.dismiss();
            if (task.isSuccessful()) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            } else {
                Toast.makeText(getActivity(), "an error occured \n please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
