package com.example.alarmclock.viewmodel;

import android.os.Build;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<String> timerText;
    private long timerLeftInMilliseconds = 600000;
    private CountDownTimer countDownTimer;
    private MutableLiveData<Boolean> timerRunning;
    private MutableLiveData<Integer> seconds, minutes;
    private Vibrator vibrator;

    public void init() {
        if (timerText == null) {
            timerText = new MutableLiveData<>();
            timerText.setValue("");
        }
        if (timerRunning == null) {
            timerRunning = new MutableLiveData<>();
            timerRunning.setValue(false);
        }

        if (minutes == null) {
            minutes = new MutableLiveData<>();
            seconds = new MutableLiveData<>();
            minutes.setValue(0);
            seconds.setValue(0);

        }
    }

    public Vibrator getVibrator() {
        return vibrator;
    }

    public void setVibrator(Vibrator vibrator) {
        this.vibrator = vibrator;
    }

    public long getTimerLeftInMilliseconds() {
        return timerLeftInMilliseconds;
    }

    public void setTimerLeftInMilliseconds(long timerLeftInMilliseconds) {
        this.timerLeftInMilliseconds = timerLeftInMilliseconds;
    }

    public MutableLiveData<Integer> getSeconds() {
        return seconds;
    }

    public void setSeconds(MutableLiveData<Integer> seconds) {
        this.seconds = seconds;
    }

    public MutableLiveData<Integer> getMinutes() {
        return minutes;
    }

    public void setMinutes(MutableLiveData<Integer> minutes) {
        this.minutes = minutes;
    }

    public MutableLiveData<Boolean> getTimerRunning() {
        return timerRunning;
    }

    public void setTimerRunning(MutableLiveData<Boolean> timerRunning) {
        this.timerRunning = timerRunning;
    }

    public MutableLiveData<String> getTimerText() {
        return timerText;
    }

    public void setTimerText(MutableLiveData<String> timerText) {
        this.timerText = timerText;
    }

    public void startStop() {
        if (timerRunning.getValue()) {
            stopTimer();
        } else {
            startTimer();
        }
    }

    public void startTimer() {
        timerRunning.setValue(true);
        countDownTimer = new CountDownTimer(timerLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long lnum) {
                timerLeftInMilliseconds = lnum;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timerText.setValue("0 : 00");

                if (Build.VERSION.SDK_INT >= 26) {
                    vibrator.vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(2000);
                }
            }
        }.start();

    }

    public void stopTimer() {
        countDownTimer.cancel();
        timerRunning.setValue(false);
    }

    public void updateTimer() {
        int mins = (int) timerLeftInMilliseconds / 60000;
        int secs = (int) timerLeftInMilliseconds % 60000 / 1000;

        timerText.setValue("");
        timerText.setValue(timerText.getValue() + mins);
        timerText.setValue(timerText.getValue() + " : ");
        if (secs < 10) {
            timerText.setValue(timerText.getValue() + "0");
        }
        timerText.setValue(timerText.getValue() + secs);

        if (minutes.getValue() != mins) {
            minutes.setValue(mins);
        }
        seconds.setValue(secs);
    }

}
