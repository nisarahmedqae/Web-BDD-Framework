package com.nahmed.tests;

import java.util.Map;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.nahmed.annotations.FrameworkAnnotation;
import com.nahmed.enums.CategoryType;
import com.nahmed.pageobjects.AmazonHomePage;

public final class AmazonDemoTests extends BaseTest {

	private AmazonDemoTests() {
	}

	@FrameworkAnnotation(author = { "Amuthan", "Sachin" }, category = { CategoryType.REGRESSION, CategoryType.SMOKE })
	@Test
	public void amazonTest(Map<String, String> data) {
		String title = new AmazonHomePage().clickHamburger().clickComputer().clickOnSubMenuItem(data.get("menutext"))
				.getPageTitle();
		Assertions.assertThat(title).isNotNull().isNotBlank();
	}
}
