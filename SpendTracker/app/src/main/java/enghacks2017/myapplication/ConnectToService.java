package enghacks2017.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.gson.Gson;

import org.apache.commons.codec.binary.Base64;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

class ConnectToService extends AsyncTask<String, Void, String> {

    private static final Gson gson = new Gson();
    private static final String TARGET_URL = "https://vision.googleapis.com/v1/images:annotate?key=AIzaSyAD-I0sD-7YVYtj05WkYIqtUYQpZaOmC4I";
    private static final String API_KEY = "AIzaSyAD-I0sD-7YVYtj05WkYIqtUYQpZaOmC4I";

    protected String doInBackground(String... filePath) {
        InputStream is = null;
        byte[] imageData = null;
        AnnotateImageRequest imgRequest;


        imageData = Base64.encodeBase64(filePath[0].getBytes());

        // set http protocol and request URLs
        URL serverUrl = null;
        try {
            serverUrl = new URL(TARGET_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        URLConnection urlConnection = null;
        try {
            urlConnection = serverUrl.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpURLConnection httpConnection = (HttpURLConnection)urlConnection;
        try {
            httpConnection.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        httpConnection.setRequestProperty("Content-Type", "application/json");
        httpConnection.setDoOutput(true);


        BufferedWriter httpRequestBodyWriter = null;
        try {
            httpRequestBodyWriter = new BufferedWriter(new OutputStreamWriter(httpConnection.getOutputStream()));
            Log.wtf("hello","" + (httpConnection.getOutputStream()));
            httpRequestBodyWriter.write
                    ("{\"requests\":  [{ \"content\":\"" + imageData + "\" },"
                            +" \"features\":[{ \"type\":\"LABEL_DETECTION\"}]}]}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (httpRequestBodyWriter != null)
                    httpRequestBodyWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String response = "";
        try {
            response = httpConnection.getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (httpConnection.getInputStream() == null)
                return "No input stream";
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.wtf("hello", "response: " + response);
        return response;
    }

//    protected void onPostExecute(MainActivity m) {
//    }
}
