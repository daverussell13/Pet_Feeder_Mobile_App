package com.damskuy.petfeedermobileapp.helpers;

import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.damskuy.petfeedermobileapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ViewHelper {

    public static void hideTextInputHint(
            boolean focus,
            TextInputEditText edt,
            TextInputLayout edtLayout,
            String hint
    ) {
        if (focus) edtLayout.setHint(null);
        else {
            if (TextUtils.isEmpty(edt.getText())) edtLayout.setHint(hint);
            else edtLayout.setHint(null);
        }
    }

    public static String getEdtText(EditText edt) {
        return Objects.requireNonNull(edt.getText()).toString();
    }

    public static void vibratePhone(Context context, Vibrator service, View parent, int duration) {
        Animation shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake_animation);
        parent.startAnimation(shakeAnimation);
        service.vibrate(duration);
        new Handler().postDelayed(service::cancel, duration);
    }
}
