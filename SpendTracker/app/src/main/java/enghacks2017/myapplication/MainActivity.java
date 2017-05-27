package enghacks2017.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import org.apache.commons.codec.binary.Base64;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String fileName = "./resources/wakeupcat.jpg";

        // Reads the image file into memory
        Path path = Paths.get(fileName);
        byte[] data = Files.readAllBytes(path);
        ByteString imgBytes = ByteString.copyFrom(data);

        // Builds the image annotation request
        List<AnnotateImageRequest> requests = new ArrayList<>();
        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                .addFeatures(feat)
                .setImage(img)
                .build();
        requests.add(request);
//        private URL url;
//        private HttpURLConnection client = null;
//
//        // request server service
//        try {
//
//            try {
//                url = new URL("https://vision.googleapis.com/v1/images:annotate?key=AIzaSyAD-I0sD-7YVYtj05WkYIqtUYQpZaOmC4I");
//                client = (HttpURLConnection) url.openConnection();
//
//                client.setRequestMethod("POST");
//                client.setRequestProperty("KEY","VALUE");
//                client.setDoOutput(true);
//
//                OutputStream outputPost = new BufferedOutputStream(client.getOutputStream());
//                writeStream.(outputPost);
//                outputPost.flush();
//                outputPost.close();
//            }
//        }
//        catch(MalformedURLException error) {
//            //Handles an incorrectly entered URL
//        }
//        catch(SocketTimeoutException error) {
//        //Handles URL access timeout.
//        }
//        catch (IOException error) {
//        //Handles input and output errors
//        }
//        catch(Exception e) {
//            Log.wtf("Server request", "Error in server request");
//        }
//        finally {
//            if(client != null) // Make sure the connection is not null.
//                client.disconnect();
//        }
    }
}
