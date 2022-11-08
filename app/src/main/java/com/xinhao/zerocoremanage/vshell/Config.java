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

import com.xinhao.zerocoremanage.utils.LogUtils;

/**
 * Application build-time configuration entries.
 */
@SuppressWarnings("WeakerAccess")
public class Config {
    public static String TAG = "Config";
    /**
     * Name of CD-ROM image file.
     * Must be a name of file located in assets directory.
     */
    public static final String CDROM_IMAGE_NAME = "operating-system.iso";

    /**
     * Name of HDD image file.
     * Must be a name of file located in assets directory.
     */
    public static final String HDD_IMAGE_NAME = "userdata.qcow2";

    /**
     * Name of zip archive with QEMU firmware.
     * Must be a name of file located in assets directory.
     */
    public static final String QEMU_DATA_PACKAGE = "qemu-runtime-data.bin";

    /**
     * Upstream DNS server used by QEMU DNS resolver.
     */
    public static final String QEMU_UPSTREAM_DNS = "8.8.8.8";

    /**
     * A tag used for general logging.
     */
    public static final String APP_LOG_TAG = "virt-shell:app";

    /**
     * A tag used for input (ime) logging.
     */
    public static final String INPUT_LOG_TAG = "virt-shell:input";

    /**
     * A tag used for installer logging.
     */
    public static final String INSTALLER_LOG_TAG = "virt-shell:installer";

    /**
     * A tag used for wakelock logging.
     */
    public static final String WAKELOCK_LOG_TAG = "virt-shell:wakelock";

    /**
     * Returns path to runtime environment directory.
     */
    public static String getDataDirectory(final Context context) {
        LogUtils.d(TAG, "getDataDirectory context:" + context);
        return context.getFilesDir().getAbsolutePath();
    }
}
