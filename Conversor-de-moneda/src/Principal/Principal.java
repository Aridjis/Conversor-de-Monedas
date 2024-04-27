package Principal;

import com.google.gson.*;
import modelos.RespuestaApi;
import modelos.RespuestaPersonalizada;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner lectura = new Scanner(System.in);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        RespuestaPersonalizada respuestaPersonalizada;
        var apiKey = "0377f81278167cdafe9ae98b";
        boolean salir = false;

        while (!salir) {
            System.out.println("*****************************************");
            System.out.println("¡Bienvenido al Conversor de monedas de Alura!");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Tipo de cambio entre 2 monedas.");
            System.out.println("2. Equivalencia entre montos.");
            System.out.println("3. Monedas disponibles.");
            System.out.println("0. Salir");
            System.out.println("*****************************************");
            var opcion = lectura.nextLine();

            switch (opcion) {
                case "1": {
                    System.out.println("Elija una moneda base:");
                    System.out.println("ARS - Peso argentino\n" +
                            "BOB - Boliviano boliviano\n" +
                            "BRL - Real brasileño\n" +
                            "CLP - Peso chileno\n" +
                            "COP - Peso colombiano\n" +
                            "USD - Dólar estadounidense");
                    var monedaBase = lectura.nextLine();

                    System.out.println("Indique la moneda a la cual quiere convertir.");
                    System.out.println("ARS - Peso argentino\n" +
                            "BOB - Boliviano boliviano\n" +
                            "BRL - Real brasileño\n" +
                            "CLP - Peso chileno\n" +
                            "COP - Peso colombiano\n" +
                            "USD - Dólar estadounidense");
                    var monedaDeseada = lectura.nextLine();

                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + monedaBase + "/" + monedaDeseada + "/"))
                            .build();
                    HttpResponse<String> response = client
                            .send(request, HttpResponse.BodyHandlers.ofString());

                    String json = response.body();
                    RespuestaApi respuestaApi = gson.fromJson(json, RespuestaApi.class);

                    respuestaPersonalizada = new RespuestaPersonalizada(
                            respuestaApi.base_code(),
                            respuestaApi.target_code(),
                            respuestaApi.conversion_rate(),
                            respuestaApi.conversion_result()
                    );

                    System.out.println(respuestaPersonalizada.toString());
                    break;
                }
                case "2": {
                    System.out.println("Elija de las opciones el tipo de moneda que quiere convertir");
                    System.out.println("ARS - Peso argentino\n" +
                            "BOB - Boliviano boliviano\n" +
                            "BRL - Real brasileño\n" +
                            "CLP - Peso chileno\n" +
                            "COP - Peso colombiano\n" +
                            "USD - Dólar estadounidense");
                    var monedaBase = lectura.nextLine();

                    System.out.println("Indique la moneda a la cual quiere convertir.");
                    System.out.println("ARS - Peso argentino\n" +
                            "BOB - Boliviano boliviano\n" +
                            "BRL - Real brasileño\n" +
                            "CLP - Peso chileno\n" +
                            "COP - Peso colombiano\n" +
                            "USD - Dólar estadounidense");
                    var monedaDeseada = lectura.nextLine();

                    System.out.println("Indique la cantidad que desea convertir");
                    var cantidadMoneda = Double.parseDouble(lectura.nextLine()); // Parsea la entrada a double

                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + monedaBase + "/" + monedaDeseada + "/" + cantidadMoneda))
                            .build();
                    HttpResponse<String> response = client
                            .send(request, HttpResponse.BodyHandlers.ofString());

                    String json = response.body();
                    RespuestaApi respuestaApi = gson.fromJson(json, RespuestaApi.class);

                    respuestaPersonalizada = new RespuestaPersonalizada(
                            respuestaApi.base_code(),
                            respuestaApi.target_code(),
                            respuestaApi.conversion_rate(),
                            respuestaApi.conversion_result()
                    );

                    System.out.println(respuestaPersonalizada.toString());
                    System.out.println(cantidadMoneda + " " + monedaBase + " equivalen a " + respuestaPersonalizada.getConversion_result()+ " " + monedaDeseada);
                    break;
                }
                case "3": {
                    paginacionMonedasDisponibles(apiKey);
                    break;
                }
                case "0": {
                    System.out.println("Saliendo del programa...");
                    salir = true;
                    break;
                }
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        }
    }

    private static void paginacionMonedasDisponibles(String apiKey) throws IOException, InterruptedException {
        Scanner lectura = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();
        int pageSize = 10;
        int currentPage = 0;
        boolean salirPaginacion = false;

        while (!salirPaginacion) {
            HttpRequest monedasRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/USD"))
                    .build();
            HttpResponse<String> monedasResponse = client
                    .send(monedasRequest, HttpResponse.BodyHandlers.ofString());
            String monedasJson = monedasResponse.body();

            List<String> monedasDisponibles = new ArrayList<>();
            JsonElement root = JsonParser.parseString(monedasJson);
            if (root.isJsonObject()) {
                JsonObject jsonObject = root.getAsJsonObject();
                JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");
                if (conversionRates != null) {
                    for (String moneda : conversionRates.keySet()) {
                        monedasDisponibles.add(moneda);
                    }
                }
            }

            int totalPages = (int) Math.ceil((double) monedasDisponibles.size() / pageSize);
            int startIndex = currentPage * pageSize;
            int endIndex = Math.min((currentPage + 1) * pageSize, monedasDisponibles.size());

            System.out.println("Página " + (currentPage + 1) + " de " + totalPages);
            for (int i = startIndex; i < endIndex; i++) {
                System.out.println(monedasDisponibles.get(i));
            }

            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Página Anterior");
            System.out.println("2. Página Siguiente");
            System.out.println("0. Volver al menú principal");

            String opcionPaginacion = lectura.nextLine();

            switch (opcionPaginacion) {
                case "1":
                    if (currentPage > 0) {
                        currentPage--;
                    } else {
                        System.out.println("Ya estás en la primera página.");
                    }
                    break;
                case "2":
                    if (currentPage < totalPages - 1) {
                        currentPage++;
                    } else {
                        System.out.println("Ya estás en la última página.");
                    }
                    break;
                case "0":
                    salirPaginacion = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                    break;
            }
        }
    }
}
