# Conversor de Monedas Alura

Este proyecto es un conversor de monedas desarrollado en Java utilizando la API de ExchangeRate-API para obtener tasas de cambio en tiempo real.

## Requisitos

- Java 11 o superior
- Dependencia de GSON para manejar la serialización y deserialización de objetos JSON
- Conexión a Internet para obtener las tasas de cambio de la API

## Uso

1. Clona el repositorio o descarga el código fuente en tu máquina local.
2. Abre el proyecto en tu IDE de preferencia.
3. Configura tu API Key de ExchangeRate-API en la variable `apiKey` en la clase `Principal`.
4. Ejecuta la clase `Principal` para iniciar la aplicación.

### Funcionalidades

- **Tipo de cambio entre 2 monedas**: Ingresa las monedas base y deseada para obtener la tasa de cambio y la conversión.
- **Equivalencia entre montos**: Convierte un monto de una moneda a otra seleccionada.
- **Monedas disponibles**: Explora las monedas disponibles con paginación.

## API Key

Para obtener tu propia API Key de ExchangeRate-API, visita [ExchangeRate-API](https://www.exchangerate-api.com/) y regístrate para obtener acceso a la API.

## Autor

Este proyecto fue creado por Joaquin Valenzuela y forma parte del curso de Java Orientado a Objetos de Alura.
