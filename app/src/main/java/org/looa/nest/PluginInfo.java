package org.looa.nest;

/**
 * 插件信息
 * <p>
 * Created by ran on 2017/8/18.
 */

public class PluginInfo {
    private int pluginAppIcon;
    private String pluginAppName;
    private String pluginAppIntroduce;
    private String pluginPath;
    private String pluginName;
    private String packageName;
    private String versionName;
    private int versionCode;

    public int getPluginAppIcon() {
        return pluginAppIcon;
    }

    public void setPluginAppIcon(int pluginAppIcon) {
        this.pluginAppIcon = pluginAppIcon;
    }

    public String getPluginAppName() {
        return pluginAppName;
    }

    public void setPluginAppName(String pluginAppName) {
        this.pluginAppName = pluginAppName;
    }

    public String getPluginAppIntroduce() {
        return pluginAppIntroduce;
    }

    public void setPluginAppIntroduce(String pluginAppIntroduce) {
        this.pluginAppIntroduce = pluginAppIntroduce;
    }

    public String getPluginPath() {
        return pluginPath;
    }

    public void setPluginPath(String pluginPath) {
        this.pluginPath = pluginPath;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    @Override
    public String toString() {
        return "PluginInfo{" +
                "pluginPath='" + pluginPath + '\'' +
                ", packageName='" + packageName + '\'' +
                ", versionName='" + versionName + '\'' +
                ", versionCode='" + versionCode + '\'' +
                '}';
    }
}
