package utils;

import java.time.Duration;

/**
 * Централизованные константы сценариев сайта https://www.saucedemo.com (Swag Labs):
 * базовый URL, единый таймаут явных ожиданий и демонстрационные учётные данные.
 */
public final class SauceDemoConfig {

    public static final String BASE_URL = "https://www.saucedemo.com";

    /** Единый таймаут явных ожиданий (WebDriverWait) для всех Page Object. */
    public static final Duration EXPLICIT_TIMEOUT = Duration.ofSeconds(10);

    // Демонстрационные учётные записи Swag Labs, пароль общий для всех
    public static final String STANDARD_USER = "standard_user";
    public static final String LOCKED_OUT_USER = "locked_out_user";
    public static final String PASSWORD = "secret_sauce";

    private SauceDemoConfig() {
    }
}
