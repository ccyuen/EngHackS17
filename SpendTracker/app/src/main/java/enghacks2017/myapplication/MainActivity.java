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
        
    private double extractMoneyFromString(String s) {
        if (!s.contains("."))
            return -1;
        try {
            if (s.charAt(0) == '$')
                return Double.parseDouble(s.substring(1));
            else
                return Double.parseDouble(s);
        }
        catch (IndexOutOfBoundsException | NumberFormatException ex) {
            return -1.;
        }
    }

    private String convertResponseToString(BatchAnnotateImagesResponse response) {
        List<EntityAnnotation> labels = response.getResponses().get(0).getTextAnnotations();

        double total = 0.;
        String location = "";
        if (labels != null) {
            // try to detect total as either the last number on screen or the first number after the last occurrence of word, "total"
            int foundTotal = 0;
            String[] allText = labels.get(0).getDescription().split("\n");  // if text was detected, labels[0] always stores all the text, separated by line breaks
            for (String word: allText) {
                double money = extractMoneyFromString(word);
                if (money > 0. && foundTotal != 2)
                    total = money;
                if (word.toLowerCase().contains("total"))
                    foundTotal = 1;
                else if (money > 0. && foundTotal == 1)
                    foundTotal = 2;
            }
            // try to detect the restaurant location as the first two lines with at least 2 letters
            for (String word: allText) {
                int acc = 0;
                for (char e: word.toCharArray())
                    if (('a' <= e && e <= 'z') || ('A' <= e && e <= 'Z'))
                        acc++;
                if (acc > 1 && location == "")
                    location = word;
                else if (acc > 1) {
                    location += "\n" + word;
                    break;
                }
            }
        }
        String message = "";
        if (labels == null || total < 0. || location.equals(""))
            message += "database not updated; receipt information could not be detected";
        else
            message += String.format("database updated:\n\tlocation: %s\n\ttotal: %.2f", location, total);

//        try {
//            message += response.toPrettyString();
//        }
//        catch (IOException ex) {}

        // update database
        //database.add(location, new Date(), total);

        return message;
    }
        
    }
}
