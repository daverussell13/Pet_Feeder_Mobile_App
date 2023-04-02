package com.damskuy.petfeedermobileapp.helpers;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

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

    public static class TextChangeEvent implements TextWatcher {

        public interface Before {
            void callback(CharSequence s, int start, int count, int after);
        }

        public interface On {
            void callback(CharSequence s, int start, int before, int count);
        }

        public interface After {
            void callback(Editable s);
        }

        private Before beforeCallback;

        private On onCallback;

        private After afterCallback;

        public TextChangeEvent(TextChangeEvent.Before callback) { beforeCallback = callback; }

        public TextChangeEvent(TextChangeEvent.On callback) { onCallback = callback; }

        public TextChangeEvent(TextChangeEvent.After callback) { afterCallback = callback; }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            beforeCallback.callback(s, start, count, after);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            onCallback.callback(s, start, before, count);
        }

        @Override
        public void afterTextChanged(Editable s) {
            afterCallback.callback(s);
        }
    }
}
