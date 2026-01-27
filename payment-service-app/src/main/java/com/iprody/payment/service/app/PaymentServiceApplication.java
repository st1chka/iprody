package com.iprody.payment.service.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения Payment Service.
 * <p>
 * Этот класс содержит точку входа в приложение и конфигурацию Spring Boot. Используется для запуска
 * микросервиса обработки платежей.
 * </p>
 */
@SpringBootApplication
public class PaymentServiceApplication {

    /**
     * Точка входа в приложение.
     * <p>
     * Запускает Spring Boot приложение с конфигурацией по умолчанию. Этот метод инициализирует
     * Spring контекст и запускает встроенный сервер.
     * </p>
     *
     * @param args аргументы командной строки, передаваемые при запуске приложения. Могут содержать
     *             параметры конфигурации Spring Boot.
     */
    public static void main(final String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}
