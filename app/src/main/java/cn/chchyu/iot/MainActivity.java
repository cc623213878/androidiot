package cn.chchyu.iot;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import cn.chchyu.iot.GetPostUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView=(TextView)findViewById(R.id.ttt);
        final Handler h = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==0x123){
                   textView.setText(msg.obj.toString());
                }
            }
        };
        new Thread(new AccessNetwork("GET", "http://192.168.24.2:8080/hello", null, h)).start();
    }
    class AccessNetwork implements Runnable{
        private String op ;
        private String url;
        private String params;
        private Handler h;

        public AccessNetwork(String op, String url, String params,Handler h) {
            super();
            this.op = op;
            this.url = url;
            this.params = params;
            this.h = h;
        }

        @Override
        public void run() {
            Message m = new Message();
            m.what = 0x123;
            if(op.equals("GET")){
               // Log.i("iiiiiii","发送GET请求");
                m.obj = GetPostUtil.sendGet(url, params);
                //Log.i("iiiiiii",">>>>>>>>>>>>"+m.obj);
            }
            if(op.equals("POST")){
                Log.i("iiiiiii","发送POST请求");
                m.obj = GetPostUtil.sendPost(url, params);
                Log.i("gggggggg",">>>>>>>>>>>>"+m.obj);
            }
            h.sendMessage(m);
        }
    }
 }
