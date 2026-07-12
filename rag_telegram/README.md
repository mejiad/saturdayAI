### Ejemplo completo de RAG

## Descripción del Módulo
La finalidad de este módulo es mostrar el uso de:

- Lectura de archivos PDF
- Convertir las páginas leídas a Documents de SpringAI
- Generar los documentos para subir a la base usando el splitter
- Generar los embeddings y guardar los textos en la base con sus embeddings
- Agregar memoria al modelo LLM por sesión
- Agregar advicers al modelo para 
  - la lectura de la base de vectores
  - para el sistema de Q&A
  - para moderar el contenido de las preguntas
- Mostrar el manejo de templates para la creación dinámica de prompts
- El uso de llamada de Tools 

## Requisitos

---

Para ejecutar e programa es necesario contar con:
- Docker
  - Ollama 
  - Qdrant
- Java 25
- SpringAI 2.0

El sistema de Ollama debe estar corriendo el model 'Gemma4'.

## Configuración del proyecto

---

El archivo de compose.yaml de Docker se define fuera del proyecto para poder compartir los servicios de Docker con diferentes proyecrtso.

---

Para compartir docker enter proyectos es necesario asignar las siguientes variables en application.properties

```
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.model.chat=ollama
spring.ai.ollama.chat.model=gemma4
spring.ai.chat.client.enabled=true
spring.ai.ollama.chat.temperature=0.6

spring.ai.model.embeddings=ollama
spring.ai.ollama.embedding.model=mxbai-embed-large

spring.ai.vectorstore.qdrant.host=localhost
spring.ai.vectorstore.qdrant.port=6334
spring.ai.vectorstore.qdrant.collection-name=vector_store
spring.ai.vectorstore.qdrant.content-field-name=doc_content
spring.ai.vectorstore.qdrant.use-tls=false
spring.ai.vectorstore.qdrant.initialize-schema=true

# application.properties de Proyecto B
spring.docker.compose.file=/home/mejiad/ai-101/compose.yaml
# Solo levanta el servicio
spring.docker.compose.lifecycle-management=start-only
```

