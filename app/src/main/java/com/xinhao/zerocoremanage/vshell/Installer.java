/*
*************************************************************************
vShell - x86 Linux virtual shell application powered by QEMU.
Copyright (C) 2019-2021  Leonid Pliushch <leonid.pliushch@gmail.com>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*************************************************************************
*/
package com.xinhao.zerocoremanage.vshell;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;

import com.xinhao.zerocoremanage.utils.LogUtils;
import com.xinhao.zerocoremanage.utils.UUtils;
import com.xinhao.zerocoremanage.zeroeg.ZeroEngineManage;

import java.io.File;
import java.io.IOException;

/**
 * Runtime data installer for assets embedded into APK.
 */
@SuppressWarnings("WeakerAccess")
public class Installer {
    public static String TAG = "Installer";
    /**
     * Performs installation of runtime data if necessary.
     */
    public static void setupIfNeeded(final Context mContext, Context mEngineContext, Handler mHandler) {
        // List of files to extract.
        final String[] runtimeDataFiles = {
                "alpine/bios-256k.bin",
                "alpine/efi-virtio.rom",
                "alpine/kvmvapic.bin",
            Config.CDROM_IMAGE_NAME,
            Config.HDD_IMAGE_NAME,
        };

        mHandler.sendEmptyMessage(ZeroEngineManage.INSTALLING);
        AssetManager assetManager = mEngineContext.getAssets();
        for (String dataFile : runtimeDataFiles) {
            File outputFile = new File(Config.getDataDirectory(mContext), dataFile);
            if (!outputFile.exists()) {
                try {
                    UUtils.writerFileRawInput(outputFile, assetManager.open(dataFile));
                    LogUtils.d(TAG, "writerFileRawInput file path: " + outputFile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtils.d(TAG, "install error: " + e);
                }
            }
        }
        mHandler.sendEmptyMessage(ZeroEngineManage.INSTALL_COMPLETE);
    }
}
