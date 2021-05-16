package com.example.taskapp.ui.auth;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.taskapp.R;
import com.example.taskapp.databinding.FragmentPhoneBinding;
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
    private FragmentPhoneBinding binding;
    private NavController navController;
    private String smsCodeFromInet;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPhoneBinding.inflate(inflater, container, false);
        mAuth.getFirebaseAuthSettings().forceRecaptchaFlowForTesting(true);
        btnContinueVerifi();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(requireContext(), "Авторизация успешна", Toast.LENGTH_SHORT).show();
                Log.e("Phone", "onVerificationCompleted");
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(requireContext(), "Авторизация не завершилась", Toast.LENGTH_SHORT).show();
                Log.e("Phone", "onVerificationFailed" + e.getMessage());
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Log.e("TAG", "onCodeAutoRetrievalTimeOut: " + s);
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                smsCodeFromInet = s;
            }
        };
    }

    private void btnContinueVerifi() {
        binding.btnContinue.setOnClickListener(v -> {
            binding.phoneCont.setVisibility(View.GONE);
            binding.smsCont.setVisibility(View.VISIBLE);
            requestSMS();
        });
        binding.btnConfirm.setOnClickListener(v -> {
            String codeFromSmsPlace = binding.smsPlase.getText().toString().trim();
            verifyPhoneNumCode(smsCodeFromInet, codeFromSmsPlace);
        });
    }

    public void verifyPhoneNumCode(String smsCodeFromInet, String codeFromSmsPlace) {
        if (TextUtils.isEmpty(codeFromSmsPlace)) {
            binding.editPhone.setError("Error input text");
        } else {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(smsCodeFromInet, codeFromSmsPlace);
            signIn(credential);
        }
    }

    private void signIn(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithCredential:success");
                        Toast.makeText(requireContext(), "Успешная регистрация", Toast.LENGTH_SHORT).show();

                        FirebaseUser user = task.getResult().getUser();
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
        String phone = binding.editPhone.getText().toString().trim();
        FirebaseAuthSettings firebaseAuthSettings = mAuth.getFirebaseAuthSettings();

        firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phone, smsCodeFromInet);

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(requireActivity())
                .setCallbacks(callbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        timerOut();
    }

    private void timerOut() {
        binding.timer.setVisibility(View.VISIBLE);
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timer.setText("Осталось: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                if (isAdded()) Toast.makeText(requireActivity(), "Время запроса истекло, введите номер еще раз!", Toast.LENGTH_LONG).show();
                binding.phoneCont.setVisibility(View.VISIBLE);
                binding.smsCont.setVisibility(View.GONE);
                binding.editPhone.setText("");
            }
        }.start();
    }
}