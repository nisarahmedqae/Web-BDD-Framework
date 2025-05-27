package com.nahmed.cucumber.constants;

import com.nahmed.cucumber.enums.ConfigProperties;
import com.nahmed.cucumber.utils.PropertyUtils;

public final class FrameworkConstants {

	private FrameworkConstants() {

	}

	private static final String RESOURCESPATH = System.getProperty("user.dir") + "/src/test/resources";
	private static final String CONFIGFILEPATH = RESOURCESPATH + "/configurations/config.properties";
	private static final String EXTENTREPORTFOLDERPATH = System.getProperty("user.dir") + "/reports/extent-test-output";
	private static final String RUNMANAGERDATASHEET = "RUNMANAGER";

	private static final int EXPLICITWAIT = 10;
	private static String extentReportFilePath = "";

	public static String getExtentReportFilePath() {
		if (extentReportFilePath.isEmpty()) {
			extentReportFilePath = createReportPath();
		}
		return extentReportFilePath;
	}

	public static String createReportPath() {
		if (PropertyUtils.getValue(ConfigProperties.OVER_RIDE_REPORTS).equalsIgnoreCase("no")) {
			return EXTENTREPORTFOLDERPATH + "/" + System.currentTimeMillis() + "index.html";
		} else {
			return EXTENTREPORTFOLDERPATH + "/" + "index.html";
		}
	}

	public static String getRunmanagerDatasheet() {
		return RUNMANAGERDATASHEET;
	}

	public static int getExplicitWait() {
		return EXPLICITWAIT;
	}

	public static String getConfigFilePath() {
		return CONFIGFILEPATH;
	}

}