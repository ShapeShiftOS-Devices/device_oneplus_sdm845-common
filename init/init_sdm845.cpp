/*
   Copyright (c) 2014, The Linux Foundation. All rights reserved.

   Redistribution and use in source and binary forms, with or without
   modification, are permitted provided that the following conditions are
   met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above
      copyright notice, this list of conditions and the following
      disclaimer in the documentation and/or other materials provided
      with the distribution.
    * Neither the name of The Linux Foundation nor the names of its
      contributors may be used to endorse or promote products derived
      from this software without specific prior written permission.

   THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED
   WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
   MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT
   ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
   BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
   BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
   WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
   OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
   IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#include <stdlib.h>
#define _REALLY_INCLUDE_SYS__SYSTEM_PROPERTIES_H_
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/_system_properties.h>

#include <android-base/properties.h>
#include "property_service.h"
#include "vendor_init.h"
#include <sys/sysinfo.h>

int property_set(const char *key, const char *value) {
    return __system_property_set(key, value);
}

void property_override(char const prop[], char const value[])
{
    prop_info *pi;

    pi = (prop_info*) __system_property_find(prop);
    if (pi)
        __system_property_update(pi, value, strlen(value));
    else
        __system_property_add(prop, strlen(prop), value, strlen(value));
}

void property_override_dual(char const system_prop[], char const vendor_prop[],
    char const value[])
{
    property_override(system_prop, value);
    property_override(vendor_prop, value);
}

          /* From Magisk@jni/magiskhide/hide_utils.c */
static const char *snet_prop_key[] = {
    "ro.boot.vbmeta.device_state",
    "ro.boot.verifiedbootstate",
    "ro.boot.flash.locked",
    "ro.boot.selinux",
    "ro.boot.veritymode",
    "ro.boot.warranty_bit",
    "ro.warranty_bit",
    "ro.debuggable",
    "ro.secure",
    "ro.build.type",
    "ro.build.tags",
    "ro.build.selinux",
    NULL
};

 static const char *snet_prop_value[] = {
    "locked",
    "green",
    "1",
    "enforcing",
    "enforcing",
    "0",
    "0",
    "0",
    "1",
    "user",
    "release-keys",
    "1",
    NULL
};

 static void workaround_snet_properties() {

     // Hide all sensitive props
    for (int i = 0; snet_prop_key[i]; ++i) {
        property_override(snet_prop_key[i], snet_prop_value[i]);
    }

     chmod("/sys/fs/selinux/enforce", 0640);
    chmod("/sys/fs/selinux/policy", 0440);
}

void load_dalvikvm_properties()
{
	struct sysinfo sys;

	sysinfo(&sys);
	if (sys.totalram < 7000ull * 1024 * 1024)
	{
		// 6GB RAM
		property_override_dual("dalvik.vm.heapstartsize", "dalvik.vm.heapstartsize", "16m");
		property_override_dual("dalvik.vm.heaptargetutilization", "dalvik.vm.heaptargetutilization", "0.5");
		property_override_dual("dalvik.vm.heapmaxfree", "dalvik.vm.heapmaxfree", "32m");
	}
	else
	{
		// 8/10GB RAM
		property_override_dual("dalvik.vm.heapstartsize", "dalvik.vm.heapstartsize", "24m");
		property_override_dual("dalvik.vm.heaptargetutilization", "dalvik.vm.heaptargetutilization", "0.46");
		property_override_dual("dalvik.vm.heapmaxfree", "dalvik.vm.heapmaxfree", "48m");
	}

	property_override_dual("dalvik.vm.heapgrowthlimit", "dalvik.vm.heapgrowthlimit", "256m");
	property_override_dual("dalvik.vm.heapsize", "dalvik.vm.heapsize", "512m");
	property_override_dual("dalvik.vm.heapminfree", "dalvik.vm.heapminfree", "8m");
}

void vendor_load_properties()
{

    // Load dalvik config
    load_dalvikvm_properties();

    // Property Overrides
    property_override("ro.control_privapp_permissions", "log");

    // Processor strings in About Phone
    property_override("ro.processor.model", "SDM845");

    // Disable speaker protect in vendor
    property_override("vendor.audio.feature.spkr_prot.enable", "false");

    // Workaround SafetyNet
    workaround_snet_properties();
}
