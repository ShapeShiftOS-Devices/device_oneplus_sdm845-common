# Nuke LiveDisplay - due to sepolicy errors
sudo rm -rf hardware/lineage/livedisplay
# Frameworks
cd frameworks/base
sudo git fetch https://github.com/ShapeShiftOS/android_frameworks_base android_11-ld
sudo git cherry-pick 8ec36a4b94ae3fdc010c8bb6818fb586f964a939
cd ../..
# Sepolicy
cd device/ssos/sepolicy
sudo git fetch https://github.com/ShapeShiftOS/android_device_ssos_sepolicy mmm
sudo git cherry-pick a3afe2efd4c5b628ea295c0176ab80f4b7a0f319
cd ../../..
# Settings
cd packages/apps/Settings
sudo git fetch https://github.com/ShapeShiftOS/android_packages_apps_Settings OOF
sudo git cherry-pick bdb7d30e137bf3b1b435b96a3fca5af825f4a564
cd ../../..
