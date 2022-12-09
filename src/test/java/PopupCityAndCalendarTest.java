import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class PopupCityAndCalendarTest {

    @BeforeEach
    public void setup() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldPassIfPickUpCityFromList() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input").setValue("Мо");
        $$(".popup .menu .menu-item .menu-item__control").find(exactText("Москва")).click();
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Петров Петр");
        $("[data-test-id='phone'] input").setValue("+79990009900");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='success-notification']").should(appear, Duration.ofSeconds(15));
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + date), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
    @Test
    public void shouldPassIfPickUpDateFromCalendar() {
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").click();
        LocalDate defaultDay = LocalDate.now().plusDays(3);
        LocalDate chooseDay = LocalDate.now().plusDays(7);
        String date = String.format(String.valueOf(chooseDay.getDayOfMonth()), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        if (chooseDay.getMonthValue() > defaultDay.getMonthValue()) {
            $(".calendar__arrow_direction_right[data-step='1']").click();
        }
        if (chooseDay.getYear() > defaultDay.getYear()) {
            $(".calendar__arrow_double[data-step='12']").click();
        }
        $$("td.calendar__day").find(text(date)).click();
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79990009900");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='success-notification']").should(appear, Duration.ofSeconds(15));
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + date), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}