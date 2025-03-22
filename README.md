[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/lSnKkmdT)

# Sistema de notificaciones

## Introducción

En la actualidad, los sistemas de notificaciones son una parte fundamental de cualquier aplicación, permitiendo a los usuarios recibir información relevante de distintas maneras y en diversos formatos. Un buen diseño de software debe garantizar **flexibilidad y escalabilidad** para soportar diferentes tipos de notificaciones, múltiples canales de comunicación y personalización según las preferencias del usuario.

El sistema debe contemplar los siguientes aspectos:

- **Tipos de notificaciones:** El sistema debe soportar distintos tipos de notificaciones, por ejemplo, notificaciones de alerta, recordatorios y promociones.
- **Medios de entrega:** Los usuarios pueden elegir recibir notificaciones a través de distintos canales, como **email, SMS o push notifications**. No son excluyentes, un usuario podría elegir más de una opción.
- **Preferencias del usuario:** Un usuario debe poder configurar qué tipos de notificaciones desea recibir y por qué medios.
- **Conversión de formatos:** Dependiendo del canal de comunicación, el formato de la notificación puede necesitar adaptaciones (ejemplo: emails en **HTML**, SMS en **texto plano**, push notifications con **JSON**).
- **Prioridades:** Las distintas notificaciones podrán clasificarse entre distintas prioridades, lo que permitirá que, según este dato, se puedan elegir distintas preferencias descritas antes.

## Consigna

Se les pide que diseñen una versión básica del sistema utilizando **UML** y los principios de la **POO**. El diseño debe permitir agregar fácilmente nuevos tipos de notificaciones y nuevos canales de comunicación sin modificar el código existente.

Es importante aclarar que **no se busca diseñar ninguna interfaz gráfica**, ni los componentes que lo componen, sólo las **clases internas** que representan la notificación y los caminos que puede seguir.
