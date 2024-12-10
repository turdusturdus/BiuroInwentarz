package com.example.biuroinwentarz.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.biuroinwentarz.database.AppDatabase;
import com.example.biuroinwentarz.model.Inwentarz;
import com.example.biuroinwentarz.utils.NotificationUtils;

import java.util.List;

public class InwentarzCheckWorker extends Worker {

    public InwentarzCheckWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        List<Inwentarz> allItems = db.inwentarzDao().getAllInwentarz().getValue();
        if (allItems == null) {
            allItems = fetchAllItems(db);
        }
        if (allItems != null) {
            for (Inwentarz item : allItems) {
                if (item.getIlosc_obecna() < item.getIlosc_min()) {
                    NotificationUtils.sendNotification(getApplicationContext(), "Ilość mniejsza niż minimalna: " + item.getNazwa());
                }
            }
        }
        return Result.success();
    }

    private List<Inwentarz> fetchAllItems(AppDatabase db) {
        return db.inwentarzDao().getRawAll();
    }
}