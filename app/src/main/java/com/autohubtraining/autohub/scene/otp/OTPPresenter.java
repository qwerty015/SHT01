package com.autohubtraining.autohub.scene.otp;

public class OTPPresenter implements OTPContract.Presenter, PhoneAuthDataListener {
    private OTPContract.View view;
    private OTPContract.Repository repository;
    private String verificationId;

    public OTPPresenter(OTPContract.View view) {
        this.view = view;
        this.repository = new OTPRepository(this);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onCodeSent(String verificationId) {
        this.verificationId = verificationId;
        this.view.requestOTP();
    }

    @Override
    public void onVerificationFailure(String errorMessage) {
        this.view.showError(errorMessage);
    }

    @Override
    public void onVerificationSuccess() {
        view.navigateToNextScreen();
    }

    //actions
    @Override
    public void onNextBtnClicked() {
        view.navigateToNextScreen();
    }

    @Override
    public void submitPhoneNumberForVerification(String phoneNumber) {
        this.repository.requestPhoneVerification(phoneNumber, this.view.getContext());
    }

    @Override
    public void submitOTP(String otp) {
        this.repository.verifyOTP(verificationId, otp);
    }
}
