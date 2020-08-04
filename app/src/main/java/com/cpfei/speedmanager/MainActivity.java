package com.cpfei.speedmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.cpfei.speedmanager.speed.SpeedManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        findViewById(R.id.loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        ArrayList<String> urls = new ArrayList<>();

                        urls.add("https://t9.baidu.com/it/u=3363001160,1163944807&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1596769702&t=824bf9c126fe0a3b2182e397ab0a3a02");
                        urls.add("https://t9.baidu.com/it/u=3363001160,1163944807&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1596770452&t=07cabd3b3138fa862357c9b817df4cd0");
                        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596175732982&di=e9c741ac2c6d5d033a29cc8e08219c33&imgtype=0&src=http%3A%2F%2Ft9.baidu.com%2Fit%2Fu%3D583874135%2C70653437%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D3607%26h%3D2408");
                        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596175732981&di=4fb82c1df1ff7324be0a2b619fbec265&imgtype=0&src=http%3A%2F%2Ft9.baidu.com%2Fit%2Fu%3D1307125826%2C3433407105%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D5760%26h%3D3240");
                        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596175732981&di=b3f4f2688ba57b59341355fac98892d5&imgtype=0&src=http%3A%2F%2Ft9.baidu.com%2Fit%2Fu%3D2268908537%2C2815455140%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D1280%26h%3D719");
                        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596175732981&di=11643cf26d9054b05f5897c849e4464b&imgtype=0&src=http%3A%2F%2Ft9.baidu.com%2Fit%2Fu%3D4169540006%2C4220376401%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D1000%26h%3D1500");
                        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596175732981&di=8a4248abbe0f6f490c13e55aed657b1d&imgtype=0&src=http%3A%2F%2Ft9.baidu.com%2Fit%2Fu%3D86853839%2C3576305254%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D750%26h%3D390");

                        for (int i = 0; i < urls.size(); i++) {
                            String url = urls.get(i);
                            SpeedManager speedManager = new SpeedManager();
                            speedManager.getSpeedParams(url);

                        }
                    }
                }).start();

            }
        });



    }
}
