Dado que estás usando Spring Boot, el enfoque más limpio será crear un servicio dentro de tu aplicación que encapsule las llamadas a esta API externa.

Aquí te presento una guía paso a paso y los ejemplos de código utilizando `WebClient` (la forma moderna y recomendada de hacer peticiones HTTP en Spring).

---

## 🚀 Guía de Integración Spring Boot $\to$ Telegram

### 🎯 Paso 0: Requisitos Previos (¡Fundamental!)

1.  **Crear el Bot:** Ve a [@BotFather](https://t.me/BotFather) en Telegram y sigue los pasos para crear un nuevo bot.
2.  **Obtener el Token:** BotFather te proporcionará un **Token API**. Este token es tu clave secreta; ¡trátalo como una contraseña! (Ej: `123456789:ABC-DEF1234`).
3.  **Conocer el Chat ID:** Necesitas saber a qué chat enviar el mensaje. Si vas a enviarlo a ti mismo, tu propio User ID será el `chat_id`. Un truco es pedirle ayuda a otro bot (como `@get_id_bot`) para obtener tu ID.

### ⚙️ Paso 1: Configuración en Spring Boot

Es crucial no hardcodear el token ni el chat ID. Usaremos `application.properties` o `application.yml`.

**`src/main/resources/application.yml`**
```yaml
telegram:
  bot-token: TU_TOKEN_OBTENIDO_DE_BOTFATHER # Ejemplo: 123456789:ABC...
  chat-id: ID_DEL_DESTINO # Tu User ID o el de un grupo.
```

### 🌐 Paso 2: Crear la Clase de Servicio (El Core)

Vamos a crear una clase `TelegramService` que será responsable de construir y enviar la petición HTTP al endpoint de Telegram.

**Dependencia:** Asegúrate de tener la dependencia `spring-boot-starter-webflux` en tu `pom.xml` o `build.gradle`, ya que es donde reside `WebClient`.

```java
package com.ejemplo.servicio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TelegramService {

    // 1. Inyectamos el WebClient para hacer peticiones HTTP
    private final WebClient webClient;

    // 2. Inyectamos las propiedades del archivo de configuración
    @Value("${telegram.bot-token}")
    private String botToken;

    @Value("${telegram.chat-id}")
    private String chatId;

    /**
     * Constructor para inicializar WebClient con la URL base del API
     */
    public TelegramService(WebClient.Builder webClientBuilder) {
        // La URL base es el endpoint de nuestro bot
        this.webClient = webClientBuilder.baseUrl("https://api.telegram.org/bot").build();
    }

    /**
     * Método principal para enviar un mensaje a Telegram.
     * @param message El texto que se desea enviar.
     */
    public void sendMessage(String message) {
        // Construye el cuerpo JSON de la petición (payload)
        var payload = new SendMessageRequest(chatId, message);

        System.out.println("Intentando enviar mensaje a Telegram: " + message);

        webClient.post()
                // Endpoint específico: /sendMessage
                .uri("/sendMessage") 
                // Definimos el cuerpo de la petición (el payload)
                .bodyValue(payload)
                // Enviamos la petición y esperamos la respuesta (Mono<Void> o Mono<T>)
                .retrieve()
                // Manejo básico de errores: si Telegram devuelve un error HTTP, lanza una excepción.
                .onStatus(status -> status.isError(), response -> 
                    response.createException().flatMap(ex -> Mono.error(new RuntimeException("Error de Telegram API: " + ex.getMessage())))
                )
                // Ejecutamos la petición de forma bloqueante para simplificar el ejemplo (si no necesitas reactividad pura).
                .bodyToMono(Void.class) 
                .doOnError(e -> System.err.println("FALLÓ EL ENVÍO DE TELEGRAM: " + e.getMessage()))
                .block(); // Usamos block() para que la llamada sea síncrona en este ejemplo de servicio
    }

    /**
     * Clase auxiliar (Record o simple POJO) para mapear el cuerpo JSON enviado a Telegram.
     */
    public static class SendMessageRequest {
        private String chat_id;
        private String text; // El texto del mensaje

        public SendMessageRequest(String chatId, String text) {
            this.chat_id = chatId;
            this.text = text;
        }

        // Getters y Setters (Spring/Jackson los maneja automáticamente si usas Lombok o record)
    }
}
```

### 🖥️ Paso 3: Usar el Servicio (El Controlador)

Ahora puedes inyectar este servicio en cualquier parte de tu aplicación, como un `RestController`.

```java
package com.ejemplo.controller;

import com.ejemplo.servicio.TelegramService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trigger")
public class MessageController {

    private final TelegramService telegramService;

    // Inyección de dependencia
    public MessageController(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    /**
     * Endpoint llamado para enviar un mensaje automáticamente al activar el servicio.
     */
    @GetMapping("/send-message")
    public String sendMessageTrigger(@RequestParam(defaultValue = "¡Hola desde Spring!") String message) {
        try {
            // 1. Llamamos al servicio que maneja la lógica de Telegram
            telegramService.sendMessage(message);
            return "✅ Mensaje enviado exitosamente a Telegram.";
        } catch (Exception e) {
            // Manejo general de errores si falla el envío
            e.printStackTrace();
            return "❌ Error al intentar enviar mensaje: " + e.getMessage();
        }
    }
}
```

---

## ✨ Resumen y Conceptos Clave

| Componente | Propósito | Notas Importantes |
| :--- | :--- | :--- |
| **`@Value`** | Inyectar el Token y Chat ID desde `application.yml`. | ¡Nunca hardcodear credenciales! |
| **`WebClient`** | Cliente HTTP reactivo de Spring WebFlux. | Es más moderno y eficiente que `RestTemplate` para peticiones externas. |
| **Payload (JSON)** | El cuerpo de datos (`chat_id`, `text`) que se envía a Telegram. | Debe seguir la estructura requerida por la API de Telegram. |
| **`.onStatus(...)`** | Manejo de errores HTTP. | Esto te permite saber si el fallo fue por un error de red o por una respuesta específica de Telegram (ej: "Bot no encontrado"). |
| **`@Service`** | Capa de lógica de negocio. | Separa la lógica de comunicación externa del controlador REST, haciendo tu código más limpio y testeable. |

### 💡 Consejo Adicional: Tipos de Mensajes

La API de Telegram es muy potente. Si en el futuro quieres enviar algo más complejo que solo texto (como fotos, archivos PDF o documentos), tendrás que modificar la estructura del `payload` para incluir los parámetros adicionales (`photo`, `document`, etc.) y ajustar tu servicio con la lógica necesaria para subir esos recursos primero a Telegram.

¡Espero que esta guía te sea de gran utilidad! Si tienes algún error específico o necesitas incorporar un tipo de mensaje avanzado, no dudes en preguntar.