read -rp "Would you like to compile for an sdm845-common OnePlus device? [Y|N]: " option

case ${option} in
   Y | y)
   cd system/core
   sudo git revert a0257ed241d6952c162baaf2567278074e31f5df
   sudo git fetch https://github.com/SevralT/system_core twelve
   sudo git cherry-pick 87b724a43ec07752574e3051cb598dcc5a5f9816..7ed7e5c1b9c0ad40479b07fab76722b06758729c

   cd ../sepolicy
   sudo git fetch https://github.com/SevralT/system_sepolicy twelve
   sudo git cherry-pick 2b9c2b40c40cfc30e1faf131f04c3e80a5f00a1b
   ;;
   N | n)
   echo "Quitting vendorsetup for sdm845"
   ;;
esac
