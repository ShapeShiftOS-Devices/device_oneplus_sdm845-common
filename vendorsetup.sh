echo "deleting useless HALs"
sudo rm -rf hardware/qcom-caf/msm8998
sudo rm -rf hardware/qcom-caf/msm8996
sudo rm -rf hardware/qcom-caf/sm8150
sudo rm -rf hardware/qcom-caf/sm8250
sudo rm -rf hardware/qcom-caf/sdm845

echo "cloning sdm845 HALs"
sudo git clone https://github.com/ShapeShiftOS/android_hardware_qcom_audio -b android_11-caf-sdm845 hardware/qcom-caf/sdm845/audio
sudo git clone https://github.com/ShapeShiftOS/android_hardware_qcom_media -b android_11-caf-sdm845 hardware/qcom-caf/sdm845/media
sudo git clone https://github.com/ShapeShiftOS/android_hardware_qcom_display -b android_11-caf-sdm845 hardware/qcom-caf/sdm845/display


