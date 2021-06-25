package com.liuh.learn

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.functions.Function

class MainActivity : AppCompatActivity() {
    var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvContent = findViewById<TextView>(R.id.tv_content)

        val single: Single<Int> = Single.just(1)
        val singleString: Single<String> =
            single.map(object : Function<Int, String> {
                override fun apply(t: Int?): String {
                    return t.toString()
                }
            })
        singleString.subscribe(object : SingleObserver<String> {
            override fun onSuccess(t: String?) {
                tvContent.text = t
            }

            override fun onSubscribe(d: Disposable?) {
            }

            override fun onError(e: Throwable?) {
            }
        })

//        Observable.just("a", "b", "c")
//            .map(object : Function<String, String> {
//                override fun apply(t: String): String {
//                    var name: String = t
//                    Log.i("MainActivity", "apply: map--1----$name")
//                    return name
//                }
//            })
//            .subscribe(object : Consumer<String> {
//                override fun accept(t: String) {
//                    Log.i("MainActivity", "accept: map--2----$t")
//                }
//            })


        Observable.just("a", "b", "c")
            .flatMap(object : Function<String, Observable<String>> {
                override fun apply(t: String?): Observable<String> {
                    Log.i("MainActivity", "apply: flatMap--1----$t")
                    return Observable.just(t)
                }
            })
            .doOnSubscribe(object :Consumer<Disposable> {
                override fun accept(t: Disposable?) {
                    Log.i("MainActivity", "accept: ")
                }

            })
            .doOnNext(object : Consumer<String> {
                override fun accept(t: String?) {
                    Log.i("MainActivity", "accept: ---$t")
                }
            })
            .subscribe(object : Consumer<String> {
                override fun accept(t: String?) {
                    Log.i("MainActivity", "accept: flatMap--2----$t")
                }
            })


//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://api.github.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//            .build()
//
//        val service = retrofit.create(GitHubService::class.java)
//        service.listRepos("octocat")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : SingleObserver<MutableList<Repo>> {
//                override fun onSuccess(t: MutableList<Repo>?) {
//                    println("response: ${t!![0].name}")
//                    tvContent.text = t[0].name
//                }
//
//                override fun onSubscribe(d: Disposable) {
//                    tvContent.text = "正在请求..."
//                    disposable = d
//                }
//
//                override fun onError(e: Throwable) {
//                    tvContent.text = e.message ?: e.javaClass.name
//                }
//            })
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}


