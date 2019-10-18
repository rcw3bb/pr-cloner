package xyz.ronella.git.pr.cloner.desktop.common;

public interface IVersion {
    Integer getMajor();
    Integer getMinor();
    String getMaintenance();
    String getBuild();
}
