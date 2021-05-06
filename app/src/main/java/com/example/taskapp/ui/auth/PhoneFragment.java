package com.example.taskapp.ui.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.taskapp.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class PhoneFragment extends Fragment {
    private EditText editPhone;
    private Button btnContinue;
    private EditText smsPlace;
    private Button btnConfirm;
    private LinearLayout phoneConst, smsConst;
    private String smsCodeFromInet;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editPhone = view.findViewById(R.id.editPhone);
        btnContinue = view.findViewById(R.id.btnContinue);
        smsPlace = view.findViewById(R.id.smsPlase);
        btnConfirm = view.findViewById(R.id.btnConfirm);
        phoneConst = view.findViewById(R.id.phone_cont);
        smsConst = view.findViewById(R.id.sms_cont);

        setOnClickListeners();
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("Phone", "onVerificationCompleted");
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("Phone", "onVerificationFailed" + e.getMessage());
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                smsCodeFromInet = s;
                smsPlace.setText("000000");
            }
        };
    }

    private void setOnClickListeners() {
        btnContinue.setOnClickListener(v -> {
            phoneConst.setVisibility(View.GONE);
            smsConst.setVisibility(View.VISIBLE);
            requestSMS();
        });
        btnConfirm.setOnClickListener(v -> {
            String codeFromSmsPlace = smsPlace.getText().toString().trim();
            verifyPhoneNumCode(smsCodeFromInet, codeFromSmsPlace);
        });
    }

    public void verifyPhoneNumCode(String smsCode, String code) {
        if (TextUtils.isEmpty(code)) {
            smsPlace.setError("Error input text");
            return;
        }
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(smsCode, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithCredential:success");

                        FirebaseUser user = task.getResult().getUser();
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                        navController.navigate(R.id.action_phoneFragment_to_navigation_home);
                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w("TAG", "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                        }
                    }
                });
    }

    private void requestSMS() {
        String phone = editPhone.getText().toString().trim();
        FirebaseAuthSettings firebaseAuthSettings = mAuth.getFirebaseAuthSettings();

        firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phone, smsCodeFromInet);

        PhoneAuthOptions options = PhoneAuthOptions
                .newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(requireActivity())
                .setCallbacks(callbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}