package enghacks2017.myapplication;

// google cloud client services
import com.google.cloud.vision.spi.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import org.apache.commons.codec.binary.Base64;

public class MainActivity extends AppCompatActivity {

    TextView test;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test = (TextView) findViewById(R.id.test);
        //File f = new File(getExternalFilesDir("Pictures"), "Output.txt");
        String filePath = "/data/enghacks2017.myapplication/files/Pictures/pic.jpg";
        test.setText(filePath);
        String receiptStringData = "";
        ConnectToService cTS = new ConnectToService();

        try {
            receiptStringData = cTS.execute(filePath).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        test.setText(receiptStringData);
    }
}
