package com.autohubtraining.autohub.scene.viewmore;

import com.autohubtraining.autohub.scene.base.BaseView;

public class ViewMoreContract {


    public interface ViewMorePresenter {
        void onCreate();

        void onDestroy();


        void onNextButtonClicked();
    }

    public interface ViewMoreView extends BaseView {
        void addTabLayout();
        void  isFavouriteSuccessfully(boolean isFavourite);
    }
}
