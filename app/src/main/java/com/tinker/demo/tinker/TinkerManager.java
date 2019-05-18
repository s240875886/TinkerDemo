package com.tinker.demo.tinker;

import android.content.Context;

import com.tencent.tinker.entry.ApplicationLike;
import com.tencent.tinker.lib.library.TinkerLoadLibrary;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

/**
 * 功能   ：Tinker管理类
 */

public class TinkerManager {

    private static boolean isInstalled = false;//是否已经初始化标志位
    private static ApplicationLike mApplicationLike;

    /**
     * 完成Tinker初始化
     *
     * @param applicationLike
     */
    public static void installedTinker(ApplicationLike applicationLike) {
        mApplicationLike = applicationLike;
        if (isInstalled) {
            return;
        }
        TinkerInstaller.install(mApplicationLike);
        isInstalled = true;
    }

    /**
     * 完成patch文件的加载
     *
     * @param path 补丁文件路径
     */
    public static void loadPatch(String path) {
        if (Tinker.isTinkerInstalled()) {//是否已经安装过
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), path);
        }
    }

    /**
     * @param libName 修改类加载器库路径
     */
    public static void loadLibrary(String libName){
        TinkerLoadLibrary.installNavitveLibraryABI(getApplicationContext(), libName);
    }

    /**
     * 对于lib/armeabi，只需使用TinkerInstaller.loadLibrary
     * @param libName
     */
    public static void loadArmLibrary(String libName){
        TinkerLoadLibrary.loadArmLibrary(getApplicationContext(), libName);
    }

    /**
     * @param relativePath 比如lib / armeabi
     * @param libName   对于lib libTest。因此，您可以通过Test或libTest，或libTest.so
     */
    public static void loadLibraryFromTinker(String relativePath,String libName){
        TinkerLoadLibrary.loadLibraryFromTinker(getApplicationContext(),relativePath, libName);
    }

    /**
     *
     * 清除patch文件
     */
    public static void cleanPatch() {
        Tinker.with(getApplicationContext()).cleanPatch();
    }
    /**
     * 利用Tinker代理Application 获取应用全局的上下文
     * @return 全局的上下文
     */
    private static Context getApplicationContext() {
        if (mApplicationLike != null)
            return mApplicationLike.getApplication().getApplicationContext();
        return null;
    }


}

