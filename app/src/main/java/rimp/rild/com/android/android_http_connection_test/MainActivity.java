package rimp.rild.com.android.android_http_connection_test;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final static int WC = LinearLayout.LayoutParams.WRAP_CONTENT;
    private final static int MP = LinearLayout.LayoutParams.MATCH_PARENT;
    private final static String TAG_READ = "read";

    LinearLayout layout;
    EditText editText;

    private String text;
    //android.osのものであることに注意
    private Handler handler = new Handler();

    //アドレスに関する情報
    private final static String END_POINT = "http://npaka.net";
    private final static String PATH = "/android/test.txt";
    private final static String URL = END_POINT + PATH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //create layout
        createLayout();

        createEditText();

        createButton();
    }

    private void createLayout() {
        layout = new LinearLayout(this);
        layout.setBackgroundColor(Color.WHITE);
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);
    }

    private void createEditText() {
        editText = new EditText(this);
        editText.setText("");
        editText.setLayoutParams(new LinearLayout.LayoutParams(MP, WC));
        layout.addView(editText);
    }

    //したの makeButton メソッドを作ってからつくるように
    private void createButton() {
        makeButton("HTTP-Connection", TAG_READ);
    }

    private void makeButton(String text, String tag) {
        Button button = new Button(this);
        button.setText(text);
        button.setTag(tag);

        button.setOnClickListener(this);

//        button.setOnClickListener((View v) -> {
//            String viewTag = (String) v.getTag();
//            if (TAG_READ.equals(viewTag)) {
//                // create new thread
//                Thread thread = new Thread(() -> {
//                    String message;
//                    try {
//                        message = new String(http2data(URL));
//                    } catch (Exception e) {
//                        message = null;
//                    }
////
////                    handler.post(() -> {
////                       if (message != null) {
////                           editText.setText(message);
////                       } else {
////                           editText.setText("failed to load");
////                       }
////                    });
//                });
//                thread.start();
//            }
//        });

        layout.addView(button);
    }

    //http connection
    public  static byte[] http2data(String path) throws Exception {
        byte[] w = new byte[1024];
        HttpURLConnection c = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            //open http connection
            URL url = new URL(path);
            c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.connect();
            in = c.getInputStream();

            //load byte array
            out = new ByteArrayOutputStream();
            while (true) {
                int size = in.read(w);
                if (size <= 0) break;
                out.write(w, 0, size);
            }
            out.close();

            //close http connection
            in.close();
            c.disconnect();
            return out.toByteArray();
        } catch (Exception e) {
            try {
                if (c != null) c.disconnect();
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (Exception e2) {
            }
            throw e;
        }
    }

    @Override
    public void onClick(View v) {
        String viewTag = (String) v.getTag();
        if (TAG_READ.equals(viewTag)) {
            // create new thread
            Thread thread = new Thread(() -> {
                String url;
                try {
                    text = new String(http2data(URL));
                } catch (Exception e) {
                    url = null;
                    text = null;
                }

                handler.post(() -> {
                    if (text != null) {
                        editText.setText(text);
                    } else {
                        editText.setText("failed to load");
                    }
                });
            });
            thread.start();
        }
    }
}
