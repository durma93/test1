package rs.org.amss.model;

public class ParkingCityNew {
    private String city;
    private String icon;
    private String zone;
    private String maxTime;
    private String fromUntil;
    private String Price;
    private String smsNumber;

    public ParkingCityNew() {
    }

    public ParkingCityNew(String city, String icon, String zone, String maxTime, String fromUntil, String price, String smsNumber) {
        this.city = city;
        this.icon = icon;
        this.zone = zone;
        this.maxTime = maxTime;
        this.fromUntil = fromUntil;
        Price = price;
        this.smsNumber = smsNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(String maxTime) {
        this.maxTime = maxTime;
    }

    public String getFromUntil() {
        return fromUntil;
    }

    public void setFromUntil(String fromUntil) {
        this.fromUntil = fromUntil;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getSmsNumber() {
        return smsNumber;
    }

    public void setSmsNumber(String smsNumber) {
        this.smsNumber = smsNumber;
    }
}
