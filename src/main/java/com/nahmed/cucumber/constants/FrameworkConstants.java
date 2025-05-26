package com.nahmed.cucumber.constants;

import com.nahmed.cucumber.enums.ConfigProperties;
import com.tmb.utils.PropertyUtils;

public final class FrameworkConstants {

	private FrameworkConstants() {

	}

	private static final String RESOURCESPATH = System.getProperty("user.dir") + "/src/test/resources";
	private static final String CONFIGFILEPATH = RESOURCESPATH + "/config/config.properties";
	private static final String EXTENTREPORTFOLDERPATH = System.getProperty("user.dir") + "/extent-test-output";
	private static final String EXCELPATH = RESOURCESPATH + "/excel/testdata.xlsx";
	private static final String RUNMANAGERDATASHEET = "RUNMANAGER";

	private static final String ITERATIONDATASHEET = "DATA";

	private static final int EXPLICITWAIT = 10;
	private static String extentReportFilePath = "";

	public static String getExtentReportFilePath() {
		if (extentReportFilePath.isEmpty()) {
			extentReportFilePath = createReportPath();
		}
		return extentReportFilePath;
	}

	public static String createReportPath() {
		if (PropertyUtils.getValue(ConfigProperties.OVERRIDEREPORTS).equalsIgnoreCase("no")) {
			return EXTENTREPORTFOLDERPATH + "/" + System.currentTimeMillis() + "index.html";
		} else {
			return EXTENTREPORTFOLDERPATH + "/" + "index.html";
		}
	}

	public static String getRunmanagerDatasheet() {
		return RUNMANAGERDATASHEET;
	}

	public static String getIterationDatasheet() {
		return ITERATIONDATASHEET;
	}

	public static String getExcelPath() {
		return EXCELPATH;
	}

	public static int getExplicitWait() {
		return EXPLICITWAIT;
	}

	public static String getConfigFilePath() {
		return CONFIGFILEPATH;
	}

}