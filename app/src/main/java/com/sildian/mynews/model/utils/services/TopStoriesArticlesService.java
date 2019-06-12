package com.sildian.mynews.model.utils.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import io.reactivex.disposables.Disposable;

public class TopStoriesArticlesService extends Service {

    private Disposable disposable;

    public TopStoriesArticlesService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void runTopStoriesArticlesRequest(){

    }
}
