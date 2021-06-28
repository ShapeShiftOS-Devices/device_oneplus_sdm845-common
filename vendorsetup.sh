# Nuke LiveDisplay - due to sepolicy errors
sudo rm -rf hardware/lineage/livedisplay
# Frameworks
cd frameworks/base
sudo git fetch https://github.com/ShapeShiftOS/android_frameworks_base android_11-ld
sudo git cherry-pick 5c997251a2938c155422ff6826517f7e2e4d019f
cd ../..
# Sepolicy
cd device/ssos/sepolicy
sudo git fetch https://github.com/ShapeShiftOS/android_device_ssos_sepolicy mmm
sudo git cherry-pick aaafbd2be8d8cda537bd44a7953838c081172a0f
cd ../../..
# Settings
cd packages/apps/Settings
sudo git fetch https://github.com/ShapeShiftOS/android_packages_apps_Settings OOF
sudo git cherry-pick 9fc128a4d705128943ea321cebdd5a48e041dd8f
cd ../../..
