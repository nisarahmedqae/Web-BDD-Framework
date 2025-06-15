package com.nahmed.constants;

import com.nahmed.enums.ConfigProperties;
import com.nahmed.utils.PropertyUtils;

public final class FrameworkConstants {

    private FrameworkConstants() {

    }

    private static final String RESOURCES_FOLDER_PATH = System.getProperty("user.dir") + "\\src\\test\\resources";
    private static final String CONFIG_FILE_PATH = RESOURCES_FOLDER_PATH + "\\config.properties";
    private static final String EXTENT_REPORT_FOLDER_PATH = System.getProperty("user.dir") + "\\reports\\extent";

    private static String extentReportFilePath = "";

    public static String getExtentReportFilePath() {
        if (extentReportFilePath.isEmpty()) {
            extentReportFilePath = createReportPath();
        }
        return extentReportFilePath;
    }

    public static String createReportPath() {
        if (PropertyUtils.getValue(ConfigProperties.OVER_RIDE_REPORTS).equalsIgnoreCase("no")) {
            return EXTENT_REPORT_FOLDER_PATH + "/" + System.currentTimeMillis() + "extent_report.html";
        } else {
            return EXTENT_REPORT_FOLDER_PATH + "/" + "extent_report.html";
        }
    }

    public static String getConfigFilePath() {
        return CONFIG_FILE_PATH;
    }

}