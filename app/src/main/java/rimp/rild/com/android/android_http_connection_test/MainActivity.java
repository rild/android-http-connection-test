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

public class MainActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_main);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //create layout
        createLayout();
    }

    private void createLayout() {
        LinearLayout layout = new LinearLayout(this);
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

    }

    private void makeButton(String text, String tag) {
        Button button = new Button(this);
        button.setText(text);
        button.setTag(tag);
        button.setOnClickListener((View v) -> {

        });
    }
}
