package ru.netology;

import TestsData.DataGenerator;
import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import dev.failsafe.internal.util.Durations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
        $(dateSelector).doubleClick().setValue(firstMeetingDate);
        $(nameSelector).setValue(validUser.getName());
        $(phoneSelector).setValue(validUser.getPhone());
        $(agreementSelector).click();
        $(buttonSelector).click();
        $("[data-test-id='success-notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(15));
        $(successSelector).shouldBe(visible).shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate));
        $(dateSelector).doubleClick().setValue(secondMeetingDate);
        $(buttonSelector).click();
        $(rePlanSelector).shouldBe(visible).shouldHave(text("Необходимо подтверждение"));
        $(buttonRePlanSelector).click();
        $(successSelector).shouldBe(visible, Duration.ofSeconds(5)).shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate));
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе
    }

}