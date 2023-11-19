package com.example.application.views.main.queuingSystem.project;

public enum EventType {
    REQUEST_GENERATION, // заявка сгенерирована
    REQUEST_BUFFER, // заявка отправлена в буфер
    REQUEST_REFUSE, // отказ заявке
    REQUEST_CHOICE_BUF, // выбор заявки из буфера
    REQUEST_CHOICE_DEVICE, // выбор прибора
    REQUEST_FINISH_DEVICE // Освобождение прибора (готовность взять заявку на обслуживание)
}
