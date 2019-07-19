package com.autohubtraining.autohub.data.model.user_cameras;


import java.io.Serializable;
import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class UserCameraResponse implements Serializable {

    private String cameraBrand;

    private String cameraBrandId;

    private String cameraModelId;

    private List<String> cameraAccessories;


    private String cameraModel;

    public void setCameraBrand(String cameraBrand) {
        this.cameraBrand = cameraBrand;
    }

    public String getCameraBrand() {
        return cameraBrand;
    }

    public void setCameraBrandId(String cameraBrandId) {
        this.cameraBrandId = cameraBrandId;
    }

    public String getCameraBrandId() {
        return cameraBrandId;
    }

    public void setCameraModelId(String cameraModelId) {
        this.cameraModelId = cameraModelId;
    }

    public String getCameraModelId() {
        return cameraModelId;
    }

    public void setCameraAccessories(List<String> cameraAccessories) {
        this.cameraAccessories = cameraAccessories;
    }

    public List<String> getCameraAccessories() {
        return cameraAccessories;
    }

    public void setCameraModel(String cameraModel) {
        this.cameraModel = cameraModel;
    }

    public String getCameraModel() {
        return cameraModel;
    }
}