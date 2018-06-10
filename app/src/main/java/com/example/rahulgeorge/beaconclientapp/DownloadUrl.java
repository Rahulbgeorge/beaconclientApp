package com.example.rahulgeorge.beaconclientapp;

/**
 * Created by Rahul study on 03-05-2018.
 */


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Priyanka on 7/11/17.
 */

public class DownloadUrl {

    public String readUrl(String myUrl) throws IOException {
        String result = "";
        String ApiKey = "9kcyvUk1KWkVFjHHSpAni6AJjgSskRNQLibguo7WQqIsCWNP0A";
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

            URL BeaconEndPoint = null;
            HttpsURLConnection myconnection = null;
            try {
                BeaconEndPoint = new URL(myUrl);
                myconnection = (HttpsURLConnection) BeaconEndPoint.openConnection();
                myconnection.setRequestProperty("BZID", ApiKey);

                if (myconnection.getResponseCode() == 200) {
                    //successfully recieved response
                    InputStream response = myconnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(response));
                    result = br.readLine();
                    // InputStreamReader responseBody=new InputStreamReader(response,"utf-8");
                    //result=responseBody.toString();
                    myconnection.disconnect();
                }



            } catch (Exception e) {
                result = "exception";
                Log.i("rest ", "exception ");
            }
            // All your networking logic
            // should be here


            Log.i("Rest", result);

            return result;
        }
        }

