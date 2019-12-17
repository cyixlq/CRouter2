package top.cyixlq.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import top.cyixlq.annotation.Route;
import top.cyixlq.crouter.CRouter;

@Route("login")
public class LoginActivity extends AppCompatActivity {

    private EditText etPersonName, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etPersonName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassWord);
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String personName = etPersonName.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(personName) && !TextUtils.isEmpty(password)) {
                    CRouter.get().go("home", LoginActivity.this);
                    finish();
                }
            }
        });
    }
}
