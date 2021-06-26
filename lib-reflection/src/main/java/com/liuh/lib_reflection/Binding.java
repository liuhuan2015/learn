package com.liuh.lib_reflection;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;

public class Binding {
    public static void bind(AppCompatActivity activity) {
//        activity.textView = activity.findViewById(R.id.textview);
        for (Field field : activity.getClass().getDeclaredFields()) {
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView != null) {
                try {
                    field.setAccessible(true);
                    field.set(activity, activity.findViewById(bindView.value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
