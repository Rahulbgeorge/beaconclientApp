package com.example.rahulgeorge.beaconclientapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MainActivity extends AppCompatActivity {
    public static List<String> viewed;
    final ArrayList<String> projectVisited =new ArrayList<String>();
    ArrayList<String> flag;
    private ArrayAdapter listAdapter;
    private ListView plv;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    public TextView output;
    ListView lv;
    HashMap<String,Beacon> devices;
    HashMap<String,String> deviceList;
    TextView headCount;
    private Beacon policy;
    JsonParser parser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewed=new ArrayList<String>();
        lv=findViewById(R.id.employeelist);
        devices=new HashMap<>();
        output=findViewById(R.id.output);
        parser=new JsonParser();
        plv=findViewById(R.id.projectlist);
        listAdapter=new ArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_1,projectVisited);
        //String input=" {\"beaconName\":\"\",\"blufiId\":\"18685\",\"blufiName\":\"\",\"eventUuid\":\"4bff9b2c-2146-4e2d-6027-79e7075e5b4f\",\"metricType\":\"PROXIMITY_ALERT\",\"namespace\":\"BEACON\",\"newState\":\"OK\",\"oldState\":\"VIOLATING\",\"policyId\":12346,\"policyName\":\"\",\"projectId\":4221,\"shopperTrackEventId\":0,\"timestamp\":1527071772787,\"timestampCleared\":1527071772787,\"uniqueDeviceId\":\"15867524323877569123\",\"value\":\"\"}";
        //Beacon policy=parser.policyDataParser(input);
        //Log.e("policy got",policy.getBeaconId());
        //new DownloadData().execute(new String[]{policy.getBeaconId(),policy.getBlufiId()});
        getDeviceNames();
        plv.setAdapter(listAdapter);
        headCount=findViewById(R.id.employeecount);
        start();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }


    public void getDeviceNames()
    {
        new DownloadDeviceList().execute();
    }
    OkHttpClient client;
    public void start() {
        output.setText("Listening for the device");
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, null); // will use java's default key and trust store which is sufficient unless you deal with self-signed certificates
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        client = new OkHttpClient();
        Request request = new Request.Builder().url("wss://bluzone.io/portal/consumer/policy")
                .addHeader("BZID","9kcyvUk1KWkVFjHHSpAni6AJjgSskRNQLibguo7WQqIsCWNP0A")
                .build();


        EchoWebSocketListener listener = new EchoWebSocketListener(this);
        WebSocket ws = client.newWebSocket(request, listener);
        client.sslSocketFactory();
        client.dispatcher().executorService().shutdown();


    }

    public void createList()
    {
        int count=0;
        //to find number of active devices
        for(String key:devices.keySet())
        {
            if(!devices.get(key).getActive())
            {count++;

            Log.e("device",devices.get(key).getBeaconId());}
            else
            {
                devices.remove(key);
            }
            headCount.setText(count+"");


        }
       // headCount.setText(devices.size());
        customListAdapter adapter=new customListAdapter(devices,deviceList,getBaseContext());
        lv.setAdapter(adapter);
    }

    public void setOutput(String text)
    {
        output.setText(text) ;
    }





     final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;
        private Context parentcontext;
MainActivity context;
        public EchoWebSocketListener(MainActivity context)
        {
            this.context=context;
        }
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
        output("Websocket opened Successfully, Waiting for the message");
        }
        @Override
        public void onMessage(WebSocket webSocket, String text) {
            final WebSocket socket=webSocket;
            output("message recieved");
          decodeData(text);
          Log.e("Recieved Blufi id",policy.blufiId);
          myRef = database.getReference("projects").child(policy.blufiId);
          myRef.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot dataSnapshot) {
                     Log.e("Beacon","Data change detected");
                     Log.e("Output value",dataSnapshot.hasChild("Visited")+"");
                     Map<String, Object> td=null;
                     if(dataSnapshot.getValue()!=null) {
                          td = (HashMap<String, Object>) dataSnapshot.getValue();
                         Log.e("found values", td.containsKey("Name") + "");
                     }
                     else{
                         Log.e("recieved value","Null value recieved");
                     }
                    // String visited = td.get("Visited").toString();
                     //Log.e("Visited value is ",visited);
                     if(!viewed.contains(policy.blufiId)) {
                         Log.e("Beacon","Triggered");
                         projectVisited.add(td.get("Name").toString());
                         listAdapter.notifyDataSetChanged();
                         Intent intent = new Intent(MainActivity.this, Advertisement.class);
                         intent.putExtra("imageurl", td.get("imageurl").toString());
                         intent.putExtra("description", td.get("description").toString());
                         intent.putExtra("Like", td.get("Like").toString());
                         intent.putExtra("disLike", td.get("Dislike").toString());
                         intent.putExtra("Blufid", policy.blufiId);
                         intent.putExtra("Visited", td.get("Visited").toString());
                         socket.close(NORMAL_CLOSURE_STATUS, null);
                         viewed.add(policy.blufiId);
                         startActivity(intent);

                     }

                 }

                 @Override
                 public void onCancelled(DatabaseError error) {
                     // Failed to read value
                     Log.w("Failed", "Failed to read value.", error.toException());
                 }
             });

        }
        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {

        }
        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {

            output("Closing : " + code + " / " + reason);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    start();
                }
            });

        }
        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            output("Error : " + t.getMessage());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

start();                }
            });

        }



        public void decodeData(String input)
        {
            Log.e("Violation detect",input+" rec");
            policy=parser.policyDataParser(input);
            if(policy!=null) {
                devices.put(policy.beaconId, policy);

                }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    createList();
                }
            });

        }

    }

    public void output(final String input)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                output.setText(input);
            }
        });
    }

    public void outputData(final String input)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                output.setText(input);
            }
        });
    }



    class DownloadDeviceList extends  AsyncTask<Void,Void,Void>
    {


        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String data=new DownloadUrl().readUrl("https://bluzone.io/portal/papis/v1/projects/4221/registry/devices");
                deviceList=parser.getDeviceIdUserMap(data);
                devices=parser.getActiveDeviceList(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

                    createList();



        }
    }

}
