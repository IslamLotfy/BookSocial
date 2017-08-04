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

/**
 * A placeholder fragment containing a simple view.
 */
public class SignupActivityFragment extends Fragment {


    private EditText emailField;
    private EditText passField;
    private Button signupButton;
    private EditText nameField1;
    private EditText nameField2;
    private ProgressDialog progressDialog;
    private String mail;
    private String pass;
    private String firstName;
    private String secondName;
    private Authenticator authenticator;
    public SignupActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_signup, container, false);
        emailField = (EditText) view.findViewById(R.id.emailfield);
        passField = (EditText) view.findViewById(R.id.passfield);
        nameField1 = (EditText) view.findViewById(R.id.firstname);
        nameField2 = (EditText) view.findViewById(R.id.secondname);
        signupButton = (Button) view.findViewById(R.id.signupbtn);
        progressDialog = new ProgressDialog(getActivity());
        authenticator = Authenticator.getInstance();


        signupButton.setOnClickListener(view1 -> {
            progressDialog.setTitle(getResources().getString(R.string.loading));
            progressDialog.setMessage(getResources().getString(R.string.wait_message));
            progressDialog.show();
            signup();
        });
        return view;
    }

    private void signup() {
        mail = emailField.getText().toString();
        pass = passField.getText().toString();
        if(mail==null||pass==null||pass.length()<6){
            Toast.makeText(getActivity(),getResources().getString(R.string.write_pass_email),Toast.LENGTH_SHORT).show();
            return;
        }
        firstName = nameField1.getText().toString();
        secondName = nameField2.getText().toString();
        authenticator.createUser(getActivity(), mail, pass).subscribe(o -> {
            progressDialog.dismiss();
            Toast.makeText(getActivity(), getResources().getString(R.string.log_in),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
    }
}
