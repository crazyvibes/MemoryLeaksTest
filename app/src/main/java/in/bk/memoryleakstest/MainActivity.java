package in.bk.memoryleakstest;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private MyAsyncTask myAsyncTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt_ml).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myAsyncTask!=null){
                    finish();
                }
                myAsyncTask=new MyAsyncTask(MainActivity.this);
                myAsyncTask.execute();
            }
        });
    }

    private static class MyAsyncTask extends AsyncTask<Void,Void,Void>{

        //way2 by using weak reference we can avoid ML
        private WeakReference<Context> mContext;

       // private Context mContext;
        public MyAsyncTask(Context context) {
          //  mContext=context;
            mContext=new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(Void... voids) {
          //  Bitmap bitmap= BitmapFactory.decodeResource(mContext.getResources(),R.drawable.ic_launcher_background);
            Bitmap bitmap= BitmapFactory.decodeResource(mContext.get().getResources(),R.drawable.ic_launcher_background);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onDestroy() {

     //   myAsyncTask.cancel(true); //fixing memory leaks(way1)

        super.onDestroy();
        Log.d("MainActivity","onDestroy: activity is destroyed");
    }
}