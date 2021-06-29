package com.liuh.learn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.tools.r8.s.b.P;
import com.android.tools.r8.s.b.W;
import com.liuh.lib_reflection.BindView;
import com.liuh.lib_reflection.Binding;

import java.lang.ref.WeakReference;
import java.util.Arrays;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity2";

    @BindView(R.id.textview)
    TextView textView;

    Button bubbleSort;
    Button quickSort;
    Button binarySearch;
    Button handlerClick;

    MyHandler mHandler = new MyHandler(MainActivity2.this);

    //解决可能会出现的内存泄漏风险
    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity2> mWeakReference;

        public MyHandler(MainActivity2 activity) {
            //开始的时候有疑惑，认为网上的方案，在这个地方，都没有调super，明明super里面是有代码逻辑的。
            //后来上网搜发现了这个：如果子类的构造方法中没有显示地调用基类构造方法，则系统默认调用基类无参数的构造方法。
            //并且使用Person类和Student类做了一下验证，发现这个代码是对的。
//            super();
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            MainActivity2 mainActivity2 = mWeakReference.get();
            switch (msg.what) {
                case 1:
                    if (mainActivity2 != null) {
                        mainActivity2.textView.setText("heihei");
                    }
                    break;
                case 2:
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Binding.bind(this);
        bubbleSort = findViewById(R.id.btn_bubble_sort);
        quickSort = findViewById(R.id.btn_quick_sort);
        binarySearch = findViewById(R.id.btn_binary_search);
        handlerClick = findViewById(R.id.btn_handler);
        textView.setText("哈哈哈哈");

        Student p = new Student("zhangsan");
        Log.i(TAG, "onCreate, name: " + p.name);

        mHandler.post(new Runnable() {
            @Override
            public void run() {

            }
        });


        bubbleSort.setOnClickListener(this);
        quickSort.setOnClickListener(this);
        binarySearch.setOnClickListener(this);
        handlerClick.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解决内存泄漏风险
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_bubble_sort:
                //冒泡排序
                bubbleSort();
                break;
            case R.id.btn_quick_sort:
                int[] arr = {2, 5, 1, 3, 8, 5, 7, 4, 3};
                int[] arrSorted = quickSort(arr, 0, arr.length - 1);
                Log.i(TAG, "quickSort: " + Arrays.toString(arrSorted));
                break;
            case R.id.btn_binary_search:
                int[] source = {1, 4, 5, 8, 9, 13, 55, 62, 77, 89, 111, 323};
                int index = binarySearch(source, 4);
                Log.i(TAG, "binarySearch, index: " + index);
                break;
            case R.id.btn_handler:
                Message m = mHandler.obtainMessage();
                m.what = 1;
                mHandler.sendMessage(m);
                break;
        }
    }

    /**
     * 冒泡排序
     * 时间复杂度：O(n^2)
     */
    private void bubbleSort() {
        int[] arr = {2, 5, 1, 3, 8, 5, 7, 4, 3};
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    //进行冒泡
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        Log.i(TAG, "bubbleSort: " + Arrays.toString(arr));
    }


    /**
     * 快速排序
     * 用到了分治的思想，通过递归来实现，时间复杂度O(nlogn)
     *
     * @param start 开始的下标
     * @param end   结束的下标
     */
    public int[] quickSort(int arr[], int start, int end) {
        int pivot = arr[start];
        int i = start;
        int j = end;
        while (i < j) {
            while ((i < j) && (arr[j] > pivot)) {
                j--;
            }
            while ((i < j) && (arr[i] < pivot)) {
                i++;
            }
            if ((arr[i] == arr[j]) && (i < j)) {
                i++;
            } else {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        if (i - 1 > start) arr = quickSort(arr, start, i - 1);
        if (j + 1 < end) arr = quickSort(arr, j + 1, end);
        return (arr);
    }

    /**
     * 二分查找
     * 前置条件：数组是有序的
     * 时间复杂度：O(log2n)
     * 推导过程：
     * 被查找区间的数据量大小变化：
     * n、n/2,n/4 ... n/2^k
     * 当 n/2^k 为 1 时，就找了目标数据，此时查找次数是：log2n，即时间复杂度是O(log2n)
     */
    public int binarySearch(int[] arr, int target) {
        int start = 0;
        int end = arr.length;

        while (start <= end) {

            int middle = (start + end) / 2;
            if (target == arr[middle]) {
                return middle;
            } else if (target < arr[middle]) {
                end = middle - 1;
            } else {
                start = middle + 1;
            }
        }
        return -1;
    }

}