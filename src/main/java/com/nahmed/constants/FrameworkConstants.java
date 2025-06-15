package com.nahmed.constants;

import com.nahmed.enums.ConfigProperties;
import com.nahmed.utils.PropertyUtils;

public final class FrameworkConstants {

    private FrameworkConstants() {

    }

    private static final String RESOURCESPATH = System.getProperty("user.dir") + "/src/test/resources";
    private static final String CONFIGFILEPATH = RESOURCESPATH + "/config.properties";
    private static final String EXTENTREPORTFOLDERPATH = System.getProperty("user.dir") + "/reports/extent";

    private static String extentReportFilePath = "";

    public static String getExtentReportFilePath() {
        if (extentReportFilePath.isEmpty()) {
            extentReportFilePath = createReportPath();
        }
        return extentReportFilePath;
    }

    public static String createReportPath() {
        if (PropertyUtils.getValue(ConfigProperties.OVER_RIDE_REPORTS).equalsIgnoreCase("no")) {
            return EXTENTREPORTFOLDERPATH + "/" + System.currentTimeMillis() + "extent.html";
        } else {
            return EXTENTREPORTFOLDERPATH + "/" + "extent.html";
        }
    }

    public static String getConfigFilePath() {
        return CONFIGFILEPATH;
    }

}