LOCAL_PATH := $(call my-dir)

#GoogleCamera
include $(CLEAR_VARS)
LOCAL_MODULE := GoogleCamera
LOCAL_SRC_FILES := priv-app/GoogleCamera/GoogleCamera.apk
LOCAL_MODULE_CLASS := APPS
LOCAL_PRIVILEGED_MODULE := true
LOCAL_OVERRIDES_PACKAGES := Snap Camera2
LOCAL_CERTIFICATE := PRESIGNED
LOCAL_DEX_PREOPT := false
include $(BUILD_PREBUILT) 
