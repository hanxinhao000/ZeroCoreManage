package com.xinhao.zerocoremanage.vshell;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.xinhao.zerocoremanage.utils.LogUtils;
import com.xinhao.zerocoremanage.utils.UUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class VShellManage {
    public static String TAG = "VShellManage";

    public static ArrayList<String> getEnvironment() {
        ArrayList<String> environment = new ArrayList<>();
        Context appContext = UUtils.getContext();
        String runtimeDataPath = Config.getDataDirectory(appContext);
        environment.add("ANDROID_ROOT=" + System.getenv("ANDROID_ROOT"));
        environment.add("ANDROID_DATA=" + System.getenv("ANDROID_DATA"));
        environment.add("APP_RUNTIME_DIR=" + runtimeDataPath);
        environment.add("LANG=en_US.UTF-8");
        environment.add("HOME=" + runtimeDataPath);
        environment.add("PATH=/system/bin");
        environment.add("TMPDIR=" + appContext.getCacheDir().getAbsolutePath());
        // Used by QEMU internal DNS.
        environment.add("CONFIG_QEMU_DNS=" + Config.QEMU_UPSTREAM_DNS);
        // Variables present on Android 10 or higher.
        String[] androidExtra = {
                "ANDROID_ART_ROOT",
                "ANDROID_I18N_ROOT",
                "ANDROID_RUNTIME_ROOT",
                "ANDROID_TZDATA_ROOT"
        };
        for (String var : androidExtra) {
            String value = System.getenv(var);
            if (value != null) {
                environment.add(var + "=" + value);
            }
        }
        LogUtils.d(TAG, "initiating QEMU session with following environment:" +  environment);
        return environment;
    }

    public static ArrayList<String> getProcessArgs() {
        Context appContext = UUtils.getContext();
        String runtimeDataPath = Config.getDataDirectory(appContext);
        // QEMU is loaded as shared library, however options are being provided as
        // command line arguments.
        ArrayList<String> processArgs = new ArrayList<>();

        // Fake argument to provide argv[0].
        processArgs.add("qemu-system-x86_64");

        // Path to directory with firmware & keymap files.
        processArgs.addAll(Arrays.asList("-L", runtimeDataPath));

        // Emulate CPU with max feature set.
        processArgs.addAll(Arrays.asList("-cpu", "max"));

        // Determine safe values for VM RAM allocation.
        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        if (am != null) {
            am.getMemoryInfo(memInfo);
            // 32% of host memory will be used for QEMU emulated RAM.
            int safeRam = (int) (memInfo.totalMem * 0.32 / 1048576);
            // 8% of host memory will be used for QEMU TCG buffer.
            int safeTcg = (int) (memInfo.totalMem * 0.08 / 1048576);
            processArgs.addAll(Arrays.asList("-m", safeRam + "M", "-accel", "tcg,tb-size=" + safeTcg));
        } else {
            // Fallback.
            Log.e(Config.APP_LOG_TAG, "failed to determine size of host memory");
            processArgs.addAll(Arrays.asList("-m", "256M", "-accel", "tcg,tb-size=64"));
        }

        // Do not create default devices.
        processArgs.add("-nodefaults");

        // SCSI CD-ROM(s) and HDD(s).
        processArgs.addAll(Arrays.asList("-drive", "file=" + runtimeDataPath + "/"
                + Config.CDROM_IMAGE_NAME + ",if=none,media=cdrom,index=0,id=cd0"));
        processArgs.addAll(Arrays.asList("-drive", "file=" + runtimeDataPath + "/"
                + Config.HDD_IMAGE_NAME
                + ",if=none,index=2,discard=unmap,detect-zeroes=unmap,cache=writeback,id=hd0"));
        processArgs.addAll(Arrays.asList("-device", "virtio-scsi-pci,id=virtio-scsi-pci0"));
        processArgs.addAll(Arrays.asList("-device",
                "scsi-cd,bus=virtio-scsi-pci0.0,id=scsi-cd0,drive=cd0"));
        processArgs.addAll(Arrays.asList("-device",
                "scsi-hd,bus=virtio-scsi-pci0.0,id=scsi-hd0,drive=hd0"));

        // Try to boot from HDD.
        // Default HDD setup has a valid MBR allowing to try next drive in case if OS not
        // installed, so CD-ROM is going to be actually booted.
        processArgs.addAll(Arrays.asList("-boot", "c,menu=on"));

        // Setup random number generator.
        processArgs.addAll(Arrays.asList("-object", "rng-random,filename=/dev/urandom,id=rng0"));
        processArgs.addAll(Arrays.asList("-device", "virtio-rng-pci,rng=rng0,id=virtio-rng-pci0"));

        // Networking.
        processArgs.addAll(Arrays.asList("-netdev", "user,id=vmnic0"));
        processArgs.addAll(Arrays.asList("-device", "virtio-net-pci,netdev=vmnic0,id=virtio-net-pci0"));

        // Access to shared storage.
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            processArgs.addAll(Arrays.asList("-fsdev",
                    "local,security_model=none,id=fsdev0,multidevs=remap,path=/storage/self/primary"));
            processArgs.addAll(Arrays.asList("-device",
                    "virtio-9p-pci,fsdev=fsdev0,mount_tag=host_storage,id=virtio-9p-pci0"));
        }

        // We need only monitor & serial consoles.
        processArgs.add("-nographic");

        // Disable parallel port.
        processArgs.addAll(Arrays.asList("-parallel", "none"));

        // Serial console.
        processArgs.addAll(Arrays.asList("-chardev", "stdio,id=serial0,mux=off,signal=off"));
        processArgs.addAll(Arrays.asList("-serial", "chardev:serial0"));

        LogUtils.d(TAG, "initiating QEMU session with following arguments:" +  processArgs);
        return processArgs;
    }


}
