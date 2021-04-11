# Nuke LiveDisplay - due to sepolicy errors
sudo rm -rf hardware/lineage/livedisplay
# Frameworks
cd frameworks/base
sudo git fetch https://github.com/ShapeShiftOS/android_frameworks_base android_11-ld
sudo git cherry-pick 2ebb2daff72d0c2392d0af9a96ef729e3f0f2a20
cd ../..
# Sepolicy
cd device/ssos/sepolicy
sudo git fetch https://github.com/ShapeShiftOS/android_device_ssos_sepolicy mmm
sudo git cherry-pick 35ea4a73ee7053158caf66c96d2d64ce5b65f366
cd ../../..
# Settings
cd packages/apps/Settings
sudo git fetch https://github.com/ShapeShiftOS/android_packages_apps_Settings OOF
sudo git cherry-pick 05d4b95ca33879c917d65d6215ba1adadbddb7e7
cd ../../..

# Proton Clang
sudo rm -rf prebuilts/clang/host/linux-x86/clang-proton
sudo git clone https://github.com/kdrag0n/proton-clang -b master prebuilts/clang/host/linux-x86/clang-proton --depth=1
