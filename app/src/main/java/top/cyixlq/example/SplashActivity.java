package top.cyixlq.example;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import top.cyixlq.annotation.Route;
import top.cyixlq.crouter.CRouter;

@Route("main")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CRouter.go("login", SplashActivity.this);
                        finish();
                    }
                });
            }
        };
        timer.schedule(task, 3000);
    };
}
