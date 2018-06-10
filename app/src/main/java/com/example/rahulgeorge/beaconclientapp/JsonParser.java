package com.example.rahulgeorge.beaconclientapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class JsonParser {
/*{"blufiId":"18685","eventUuid":"87a7184e-d8f9-4178-75d1-8bfe194075c5","metricType":"PRESENCE","namespace":"BEACON",
"newState":"VIOLATING","oldState":"OK","policyId":12345,"projectId":4221,
"shopperTrackEventId":0,"timestamp":1527061858234,"timestampCleared":1527061858234,"uniqueDeviceId":"15867524323877569123",
"value":"No blufi data"}
* */

    public Beacon policyDataParser(String data)
    {
        Log.e("Parsing data",data+"data");
        Beacon policy=new Beacon();
        try {
            JSONObject obj=new JSONObject(data);
            if(!obj.getString("newState").equalsIgnoreCase("VIOLATING"))
                policy.setActive(true);
            else
                policy.setActive(false);
            policy.setBeaconId(obj.getString("uniqueDeviceId"));
            policy.setBlufiId(obj.getString("blufiId"));
            return policy;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String deviceNameParser(String data)
        {
            String out=data;
            Log.e("datarecieved",data);
            try {
                JSONArray ob=new JSONArray(data);
                JSONObject obb=(JSONObject)ob.get(0);
                return(obb.getString("name"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return out;

        }

        public HashMap<String,String> getDeviceIdUserMap(String data)
        {
            HashMap<String,String> map=new HashMap<>();
            try {
                JSONArray obj=new JSONArray(data);
                int length=obj.length();
                for(int i=0;i<length;i++)
                {
                    JSONObject obb=(JSONObject)obj.get(i);
                    String deviceid=obb.getString("deviceId");
                    String name=obb.getString("name");
                    map.put(deviceid,name);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return map;
        }


        public HashMap<String,Beacon> getActiveDeviceList(String data)
        {
            Beacon beacon=new Beacon();
            HashMap<String,Beacon> map=new HashMap<>();
            try{
                JSONArray obb=new JSONArray(data);
                for(int i=0;i<obb.length();i++) {
                 JSONObject obj=(JSONObject) obb.get(i);
                    if(obj.getString("deviceType").equals("BEACON"))
                    {
                        String beaconid = obj.getString("deviceId");
                        String status=obj.getString("status");
                        beacon.setBeaconId(beaconid);
                        beacon.setActive(status);
                        map.put(beaconid,beacon);
                    }

                }

            }
            catch(JSONException e)
            {e.printStackTrace();}

            return map;
        }

}
