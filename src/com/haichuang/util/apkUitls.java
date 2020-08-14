package com.haichuang.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import net.dongliu.apk.parser.bean.UseFeature;

public class apkUitls {
	 public static Map<String, Object> readAPK(File apkUrl) {
	        Map<String, Object> resMap = new HashMap<String, Object>();
	        try (ApkFile apkFile = new ApkFile(apkUrl)) {
	            ApkMeta apkMeta = apkFile.getApkMeta();
	            resMap.put("filename", apkMeta.getName());
	            resMap.put("pkgname", apkMeta.getPackageName());
	            resMap.put("versioncode", apkMeta.getVersionCode());
	            resMap.put("versionname", apkMeta.getVersionName());
	            for (UseFeature feature : apkMeta.getUsesFeatures()) {
	                System.out.println(feature.getName());
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return resMap;

	    }
}
