package main;

import json.JsonManipulator;
import modelos.Moneda;
import online.HttpConsultor;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static String key = "00b3ef610d9968fb5901a09a";

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        HttpConsultor consultor = new HttpConsultor();
        JsonManipulator manipulator = new JsonManipulator();
        List<String> currency = currencyTypes();
        List<String> consultas = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        int base, consulta;
        double cantidad;
        while(true) {
            System.out.println("Bienvenido al programa de conversion de monedas :]");
            System.out.println("--------------------------------------------------");
            System.out.println("Para utilizar un tipo de monedas utilize el siguiente formato");
            System.out.println("(numero de moneda base) - (numero de moneda a consultar) - (cantidad) Ejemplo: [2 - 1 - 300]");
            System.out.println("Para salir ingrese: 0 - 0 - 0");
            for(int i = 0;i < currency.size();i++) System.out.println(currency.get(i) + " = " + (i + 1));
            base = in.nextInt() - 1;
            in.next();
            consulta = in.nextInt() - 1;
            in.next();
            cantidad = in.nextDouble();
            if(base == -1) break;
            URI uri = new URI("https://v6.exchangerate-api.com/v6/" + key +  "/latest/" + currency.get(base));
            try {
                Moneda moneda = manipulator.jsonToMoneda(consultor.consult(uri));
                System.out.println("El resultado de su consulta es");
                String stringConsulta = cantidad + " " + currency.get(base)
                        + " equivale" + (cantidad > 1?"n":"") + " a " + (moneda.conversion_rates().get(currency.get(consulta)) * cantidad)
                        + " " + currency.get(consulta);
                consultas.add(stringConsulta);
                System.out.println(stringConsulta);
            } catch (ConnectException e) {
                System.out.println("Parece que no se ha podido conectar con la API, intente mas tarde");
                System.out.println(e.getMessage());
                break;
            }
            if(!consultas.isEmpty())
                System.out.println("Las consultas realizadas son las siguientes");
            for(String c : consultas) {
                System.out.println(c);
            }
        }
    }

    public static List<String> currencyTypes() {
        List<String> currency = new ArrayList<>();
        currency.add("ARS");
        currency.add("BOB");
        currency.add("BRL");
        currency.add("CLP");
        currency.add("COP");
        currency.add("USD");
        currency.add("MXN");
        return currency;
    }

}

