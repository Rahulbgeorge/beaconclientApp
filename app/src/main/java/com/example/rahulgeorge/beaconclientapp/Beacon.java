package com.example.rahulgeorge.beaconclientapp;

public class Beacon {
    /*{"blufiId":"18685","eventUuid":"87a7184e-d8f9-4178-75d1-8bfe194075c5","metricType":"PRESENCE","namespace":"BEACON",
"newState":"VIOLATING","oldState":"OK","policyId":12345,"projectId":4221,
"shopperTrackEventId":0,"timestamp":1527061858234,"timestampCleared":1527061858234,"uniqueDeviceId":"15867524323877569123",
"value":"No blufi data"}
* */

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setActive(String active)
    {
        if(active.equals("ACTIVE"))
        {this.isActive=true;}
        else
        {this.isActive=false;}
    }

    private Boolean isActive;


    public String getBlufiId() {
        return blufiId;
    }

    public void setBlufiId(String blufiId) {
        this.blufiId = blufiId;
    }

    public String getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(String beaconId) {
        this.beaconId = beaconId;
    }

    public String blufiId;
    public String beaconId;

    public String getBeaconName() {
        return beaconName;
    }

    public void setBeaconName(String beaconName) {
        this.beaconName = beaconName;
    }

    public String getBlufiName() {
        return blufiName;
    }

    public void setBlufiName(String blufiName) {
        this.blufiName = blufiName;
    }

    public String beaconName;
    public String blufiName;

    public Beacon()
    {
        isActive=false;
    }
}
