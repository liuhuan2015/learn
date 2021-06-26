package com.liuh.learn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.liuh.lib_reflection.BindView;
import com.liuh.lib_reflection.Binding;

public class MainActivity2 extends AppCompatActivity {
    @BindView(R.id.textview)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Binding.bind(this);
        textView.setText("哈哈哈哈");
    }
}