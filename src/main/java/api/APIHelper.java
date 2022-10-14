package api;

import com.squareup.okhttp.*;
import model.Endpoint;

import java.io.IOException;

public class APIHelper {


    private String baseURL = "https://backend-interview.tools.gcp.viesure.io/weather";

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public boolean updateTemperature(int fahrenheit) {
        String body = String.format("{\n  \"tempInFahrenheit\": %s\n}", fahrenheit);
        return executePutRequestWithBody(body, Endpoint.TEMP);
    }

    public boolean updateCondition(int condition) {
        String body = String.format("{\n  \"condition\": %s\n}", condition);
        return executePutRequestWithBody(body, Endpoint.CONDITION);
    }

    private boolean executePutRequestWithBody(String stringBody, Endpoint endpoint) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, stringBody);
        Request request = new Request.Builder()
                .url(baseURL + "/" + endpoint.getPath())
                .method("PUT", body)
                .addHeader("accept", "*/*")
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            return false;
        }
    }
}
