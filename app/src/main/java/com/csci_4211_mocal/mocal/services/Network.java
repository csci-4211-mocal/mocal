package com.csci_4211_mocal.mocal.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.csci_4211_mocal.mocal.R;
import com.csci_4211_mocal.mocal.models.AccountInfo;
import com.csci_4211_mocal.mocal.models.Event;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Network {
    Context context;

    public void healthCheck(HealthCallback callback) {
        String endpoint = context.getString(R.string.api_url);
        String params = "/health";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                endpoint + params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onHealthResponse(null, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onHealthResponse(error, null);
                    }
                }
        );

        requestQueue.add(stringRequest);
    }

    public void login(String username, String password, LoginCallback callback) throws JSONException {
        String endpoint = context.getString(R.string.api_url);
        String params = "/accounts/login";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        final String requestBody = jsonObject.toString();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                endpoint + params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onLoginResponse(null, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("info", "Error: " + error.toString());
                        callback.onLoginResponse(error, null);
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };

        requestQueue.add(stringRequest);
    }

    public void getAccountInfo(String token, AccountInfoCallback callback) throws JSONException {
        String endpoint = context.getString(R.string.api_url);
        String params = "/accounts/info";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        final String requestBody = jsonObject.toString();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                endpoint + params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onAccountInfoResponse(null, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onAccountInfoResponse(error, null);
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };

        requestQueue.add(stringRequest);
    }

    public void shareEvent(String token, String username, Event event, ShareCallback callback) throws JSONException {
        String endpoint = context.getString(R.string.api_url);
        String params = "/events/new";

        Gson gson = new Gson();
        String payload = gson.toJson(event);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("to_account", username);
        jsonObject.put("payload", payload);
        final String requestBody = jsonObject.toString();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                endpoint + params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onShareResponse(null, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onShareResponse(error, null);
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };

        requestQueue.add(stringRequest);
    }

    public void getReceivedEvents(String token, ReceivedEventsCallback callback) throws JSONException {
        String endpoint = context.getString(R.string.api_url);
        String params = "/events/all";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        final String requestBody = jsonObject.toString();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                endpoint + params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onReceivedEventsResponse(null, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onReceivedEventsResponse(error, null);
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };

        requestQueue.add(stringRequest);
    }

    public void deleteEvent(String token, String eventId, DeleteEventCallback callback) throws JSONException {
        String endpoint = context.getString(R.string.api_url);
        String params = "/events/delete";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        final String requestBody = jsonObject.toString();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                endpoint + params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onDeletedResponse(null, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onDeletedResponse(error, null);
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };

        requestQueue.add(stringRequest);
    }

    public void getWeather (double lat, double lon, WeatherCallback callback) {
        String endpoint = context.getString(R.string.weather_api_url);
        String params = "?lat=" + lat + "&lon=" + lon + "&apikey=" + context.getString(R.string.weather_api_token);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest (
                Request.Method.GET,
                endpoint + params,
                new Response.Listener<String>() {
                    public void onResponse (String response) {
                        callback.onWeatherResponse(null, response);
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse (VolleyError error) {
                        Log.i("info", "Error: " + error.toString());
                        callback.onWeatherResponse(error, null);
                    }
                }
        );

        requestQueue.add(stringRequest);
    }

    public interface HealthCallback {
        void onHealthResponse(VolleyError error, String res);
    }

    public interface LoginCallback {
        void onLoginResponse(VolleyError error, String res);
    }

    public interface AccountInfoCallback {
        void onAccountInfoResponse(VolleyError error, String res);
    }

    public interface WeatherCallback {
        void onWeatherResponse(VolleyError error, String res);
    }

    public interface ShareCallback {
        void onShareResponse(VolleyError error, String res);
    }

    public interface ReceivedEventsCallback {
        void onReceivedEventsResponse(VolleyError error, String res);
    }

    public interface DeleteEventCallback {
        void onDeletedResponse(VolleyError error, String res);
    }

    public Network(Context context) {
        this.context = context;
    }
}
