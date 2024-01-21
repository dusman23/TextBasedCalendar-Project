/**
 * Class Name: Events
 * Programmer: Danial Usman
 * Date: 12/11/2020
 * Description: Creates an event according to date.
 */
package calendar;


public class Events {
    //fields
    private String eventName;
    private String month;
    private int day;
    
    //arg constructor
    public Events(String eN, String m, int d){
        eventName = eN;
        month = m;
        day = d;
    }
    
    //no arg constructor
    public Events(){
        eventName = "";
        month = "";
        day = 1;
    }
    
    //Accessors
    public String getEventName(){
        return eventName;
    }
    public String getMonth(){
        return month;
    }
    public int getDay(){
        return day;
    }
    
    //Mutators
    public void setEventName(String eN){
        eventName = eN;
    }
    public void setMonth(String m){
        month = m;
    }
    public void setDay(int d){
        day = d;
    }
    
    /**
     * Method Name: toString
     * Method Description: Displays current state of object
     * @return state of object
     */
    public String toString(){
        return eventName + "," + month
                + "," + day;
    }
}

