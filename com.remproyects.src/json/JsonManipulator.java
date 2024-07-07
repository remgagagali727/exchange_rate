package json;

import com.google.gson.Gson;
import modelos.Moneda;

public class JsonManipulator {

    Gson gson;

    public JsonManipulator() {
        gson = new Gson();
    }

    public Moneda jsonToMoneda(String json){
        Moneda moneda;
        moneda = gson.fromJson(json, Moneda.class);
        return moneda;
    }

}
