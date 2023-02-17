package com.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

enum Download{
    INSTANCE;

    Semaphore semaphore = new Semaphore(3,true);

    public void download(){
        try {
            semaphore.acquire();
            downloadData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            semaphore.release();
        }
    }

    private void downloadData() {
        System.out.println("Downloading data from web");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
public class App {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0;i<12;i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Download.INSTANCE.download();
                }
            });
        }
    }
}
