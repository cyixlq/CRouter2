package top.cyixlq.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import top.cyixlq.annotation.Route;
import top.cyixlq.crouter.CRouter;

@Route("home")
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.btnLogOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRouter.get().go("login", HomeActivity.this);
                finish();
            }
        });
    }
}
