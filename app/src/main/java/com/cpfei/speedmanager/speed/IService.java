package com.cpfei.speedmanager.speed;

import android.content.Intent;

public interface IService {
    public void doWhenCreate();
    public boolean checkBeforeStart(Intent intent, int startId);
    public void doWhenStart(Intent intent, int startId);
    public void doWhenDestroy();
}
