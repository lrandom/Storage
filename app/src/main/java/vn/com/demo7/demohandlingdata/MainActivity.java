package vn.com.demo7.demohandlingdata;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DownloadTask("http://www.killingmycareer.com/wp-content/uploads/2014/07/sunshine-500536_600x4001.jpg").execute();
    }

    void writeFile(){
        String content = "abcdef gh";
        try {
            FileOutputStream fos = openFileOutput("demo.txt",MODE_APPEND);
            fos.write(content.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void readFile(){
        try {
            FileInputStream fis = openFileInput("demo.txt");
            byte[] data = new byte[fis.available()];
            if(fis.read(data)!=-1){
                Log.e("FILE CONTENT",new String(data));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void checkExternalWriteFile(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                writeExternalFile();
            }else{
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},105);
            }
        }else{
            writeExternalFile();
        }
    }

    Boolean writeExternalFile(){
        if(Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            String x = "abcdef";
            try {
                FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "hihi.txt"));
                fos.write(x.getBytes());
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            return false;
        }
        return true;
    }

    void readExternalFile(){
        if(Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) || Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED_READ_ONLY)){
           try {
               FileInputStream fis = new FileInputStream(new File(Environment.getExternalStorageDirectory(), "hihi.txt"));
               byte[] data = new byte[fis.available()];
               if(fis.read(data)!=-1){
                   Log.e("FILE CONTENT",new String(data));
               }
           }catch (Exception e){
               e.printStackTrace();
           }

        }
    }

    class DownloadTask extends AsyncTask<Void,Void,Void>{
        String url;
        InputStream is;
        FileOutputStream ous;
        int count;

        public DownloadTask(String url) {
            this.url=url;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(this.url);
                URLConnection urlConnection = url.openConnection();
                is = urlConnection.getInputStream();
                ous = new FileOutputStream(new File(Environment.getExternalStorageDirectory(),"test.jpg"));
                byte[] data = new byte[1024*4];
                while ((count=is.read(data))!=-1){
                    Log.e("DOWLOADING IMAGE","DOWLOADING IMAGE");
                    ous.write(data,0,count);
                }
                ous.close();
                is.close();
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }


    }
}
