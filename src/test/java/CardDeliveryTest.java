import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeEach
    public void openUrl() {
        open("http://localhost:9999");
    }

    public String setDate(int shift) {
        LocalDate localDate = LocalDate.now().plusDays(shift);
        return DateTimeFormatter.ofPattern("dd.MM.yyyy").format(localDate);
    }

    @Test
    public void shouldPassIfOrderInThreeDays() {
        String date = setDate(3);
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван-Иван");
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
    public void shouldPassIfOrderInFourDays() {
        String date = setDate(4);
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Владимиров Владимир");
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
    public void shouldPassIfOrderInTenDays() {
        String date = setDate(10);
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван-Иван");
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
    public void shouldFailIfOrderInTwoDays() {
        String date = setDate(2);
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван-Иван");
        $("[data-test-id='phone'] input").setValue("+79990009900");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("span.input__sub").should(appear, Duration.ofSeconds(15));
        $(withText("Заказ на выбранную дату невозможен")).should(appear, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']").shouldNot(appear, Duration.ofSeconds(15));
    }

    @Test
    public void shouldFailIfDateInWrongFormat() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue("12.31.2022");
        $("[data-test-id='name'] input").setValue("Иванов Иван-Иван");
        $("[data-test-id='phone'] input").setValue("+79990009900");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='date'] .input_invalid .input__sub").should(appear, Duration.ofSeconds(15));
        $(withText("Неверно введена дата")).should(appear, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']").shouldNot(appear, Duration.ofSeconds(15));
    }

    @Test
    public void shouldFailIfNoDate() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='name'] input").setValue("Иванов Иван-Иван");
        $("[data-test-id='phone'] input").setValue("+79990009900");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='date'] .input_invalid .input__sub").should(appear, Duration.ofSeconds(15));
        $(withText("Неверно введена дата")).should(appear, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']").shouldNot(appear, Duration.ofSeconds(15));
    }

    @Test
    public void shouldFailIfCityNotFromList() {
        String date = setDate(3);
        $("[data-test-id='city'] input").setValue("Сестрорецк");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван-Иван");
        $("[data-test-id='phone'] input").setValue("+79990009900");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").should(appear, Duration.ofSeconds(15));
        $(withText("Доставка в выбранный город недоступна")).should(appear, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']").shouldNot(appear, Duration.ofSeconds(15));
    }

    @Test
    public void shouldFailIfCityInRoman() {
        String date = setDate(3);
        $("[data-test-id='city'] input").setValue("Moscow");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван-Иван");
        $("[data-test-id='phone'] input").setValue("+79990009900");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").should(appear, Duration.ofSeconds(15));
        $(withText("Доставка в выбранный город недоступна")).should(appear, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']").shouldNot(appear, Duration.ofSeconds(15));
    }

    @Test
    public void shouldFailIfNoCity() {
        String date = setDate(3);
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван-Иван");
        $("[data-test-id='phone'] input").setValue("+79990009900");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").should(appear, Duration.ofSeconds(15));
        $(withText("Поле обязательно для заполнения")).should(appear, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']").shouldNot(appear, Duration.ofSeconds(15));
    }

    @Test
    public void shouldFailIfNameInRoman() {
        String date = setDate(3);
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Ivan Ivanov");
        $("[data-test-id='phone'] input").setValue("+79990009900");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub");
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']").shouldNot(appear, Duration.ofSeconds(15));
    }

    @Test
    public void shouldFailIfNameWithNumber() {
        String date = setDate(3);
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("1234567890");
        $("[data-test-id='phone'] input").setValue("+79990009900");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub");
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']").shouldNot(appear, Duration.ofSeconds(15));
    }

    @Test
    public void shouldFailIfNameWithSymbol() {
        String date = setDate(3);
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("!@#$%^&*()");
        $("[data-test-id='phone'] input").setValue("+79990009900");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub");
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']").shouldNot(appear, Duration.ofSeconds(15));
    }

    @Test
    public void shouldFailIfNoName() {
        String date = setDate(3);
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='phone'] input").setValue("+79990009900");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub");
        $(withText("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']").shouldNot(appear, Duration.ofSeconds(15));
    }

    @Test
    public void shouldFailIfNoPhone() {
        String date = setDate(3);
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван-Иван");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").should(appear, Duration.ofSeconds(15));
        $(withText("Поле обязательно для заполнения")).should(appear, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']").shouldNot(appear, Duration.ofSeconds(15));
    }

    @Test
    public void shouldFailIfInvalidCheckbox() {
        String date = setDate(3);
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick();
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван-Иван");
        $("[data-test-id='phone'] input").setValue("+79990009900");
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='agreement'].input_invalid");
        $("[data-test-id='success-notification']").shouldNot(appear, Duration.ofSeconds(15));
    }
}