Readme

Este programa contiene los ejemplos de las pláticas de AI de los sábados.

Requerimientos para ejecutarlo.

Antes de compilar y ejecutar el programa se debe contar con lo siguiente instalado en la máquina:


- IDE de IntelliJ para modificar y compilar el programa
- Ollama
- Seleccionar algún modelo para ser ejecutado por Ollama

```shell
$ ollama pull gemma4
```
- Modificar el model en las propiedades dentro de resources/epplication.properties
Las propiedades debe tener las siguiente propiedades minimamente:


```shell
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.model.chat=ollama
spring.ai.ollama.chat.model=qwen3
spring.ai.chat.client.enabled=true
spring.ai.ollama.chat.temperature=0.6
```

El modelo ejecutado por Ollama debe ser el mismo en el archivo de propiedades de programa application.properties del programa. 
Para ver que modelo estamos ejecutando en Ollama teclear en el sistema operativo:

```shell
$ ollama ps
```

- Ejecutar Ollama en mode serve desde la línea de comandos del sistema operativo

```shell
$ ollama serve
```

- compilar el programa
- Usar algún programa para realizar las llamadas de HTTP pude ser curl, httpie, wget.


```shell
  curl -X POST localhost:8080/ai/template -d "sport=chess" -d "question=como se mueve la torre"
```

