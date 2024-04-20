package ru.netology;

import TestsData.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class Tests {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    String citySelector = "[data-test-id='city'] input";
    String dateSelector = "[data-test-id='date'] input";
    String nameSelector = "[data-test-id='name'] input";
    String phoneSelector = "[data-test-id='phone'] input";

    String agreementSelector = "[data-test-id='agreement']";
    String successSelector = "[data-test-id='success-notification'] .notification__content";
    String buttonSelector = ".form-field .button";
    String rePlanSelector = "[data-test-id='replan-notification'] .notification__title";
    String buttonRePlanSelector = ".notification__content .button";

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $(citySelector).setValue(validUser.getCity());
        $(dateSelector).doubleClick().sendKeys(Keys.BACK_SPACE);
        $(dateSelector).setValue(firstMeetingDate);
        $(nameSelector).setValue(validUser.getName());
        $(phoneSelector).setValue(validUser.getPhone());
        $(agreementSelector).click();
        $(buttonSelector).click();
        $(successSelector).shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate));
        $(dateSelector).doubleClick().sendKeys(Keys.BACK_SPACE);
        $(dateSelector).setValue(secondMeetingDate);
        $(buttonSelector).click();
        $(rePlanSelector).shouldBe(visible).shouldHave(text("Необходимо подтверждение"));
        $(buttonRePlanSelector).click();
        $(successSelector).shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate));
    }

}