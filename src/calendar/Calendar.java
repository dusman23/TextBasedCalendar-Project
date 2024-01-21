/**
 * Project Name: Calendar
 * Programmer: Danial Usman
 * Date: 12/11/2020
 * Description: Simulates a calendar which keeps track of important events.
 */
package calendar;

import java.util.Scanner;//imports scanners from util package
import java.util.ArrayList;//imports ArrayLists from util package
import java.io.*;//imports everything from io package

/**
 * Class Name: Calendar
 * Class Description: Main Class for Calendar program.
 */
public class Calendar {

    public static void main(String[] args) throws IOException {
        Scanner scanS = new Scanner(System.in);//Creates Scanner
        Scanner scanN = new Scanner(System.in);

        File myFile = new File("Dates.txt");//Creates File called Dates.txt
        FileWriter fWriter = new FileWriter(myFile, true);//Allows appending
        PrintWriter pWriter = new PrintWriter(fWriter);//Allows file input

        File myFile1 = new File("Events.txt");//Creates File called Events.txt
        FileWriter fWriter1 = new FileWriter(myFile1, true);
        PrintWriter pWriter1 = new PrintWriter(fWriter1);

        Scanner readFile = new Scanner(myFile);//Scanner for Dates.txt
        Scanner readFile1 = new Scanner(myFile1);//Scanner for Events.txt

        ArrayList<String> months = new ArrayList<>();//creates String ArrayList
        ArrayList<Events> events = new ArrayList<>();//creates Events ArrayList
        int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};//days
        String month = "";
        String passedDay;
        String line;
        int day = 0;

        System.out.println("\tWELCOME TO THE CALENDAR\n");
        fillMonths(months);//calls fillMonths method

        if (!readFile.hasNext()) {//calls method if Dates file is empty
            changeDate(events, months, days, myFile);
        }

        if (readFile.hasNext()) {//conditional for if Dates file is not empty
            System.out.println("The current date is " + readFile.next());
            System.out.println("Has a day passed since you last"
                    + " used the calendar?");
            passedDay = scanS.nextLine();

            while (!passedDay.equalsIgnoreCase("yes")//error traps user input
                    && !passedDay.equalsIgnoreCase("no")) {
                System.out.println("Please say yes or no.");
                passedDay = scanS.nextLine();
            }
            
            readFile.close();//closes readFile scanner
            readFile = new Scanner(myFile);//resets readFile scanner
            
            if (passedDay.equalsIgnoreCase("yes")) {
                while (readFile.hasNext()) {
                    line = readFile.nextLine();
                    String token[] = line.split(",");//tokenizes File lines
                    day = Integer.parseInt(token[1]) + 1;//Adds one to day
                    month = token[0];//Sets month to month in file
                }
                
                //Advances month to next month if previous month ends.
                for (int i = 0; i < days.length; i++) {
                    if (month.equalsIgnoreCase("December") && day > 31) {
                        month = months.get(0);
                        day = 1;
                    }
                    if (month.equalsIgnoreCase(months.get(i))
                            && day > days[i]) {
                        month = months.get(i + 1);
                        day = 1;
                    }
                }
                
                fWriter = new FileWriter(myFile, false);//deletes file info
                pWriter.println(month + "," + day);//adds new day to file
                System.out.println("The date has been set to " + month
                        + "," + day);

            }
        }
        fillList(events, myFile1);//calls fillList method
        menu(events, months, days, myFile1, myFile);//calls menu method

        pWriter.close();//closes PrintWriter for Dates file
    }
    
    /**
     * Method Name: fillMonths
     * Method Description: Fills months ArrayList with month names
     * @param months: ArrayList containing month names.
     */
    public static void fillMonths(ArrayList<String> months) {
        String[] monthArray = {"January", "February", "March", "April",
            "May", "June", "July", "August", "September", "October",
            "November", "December"};
        
        for (int i = 0; i < monthArray.length; i++) {
            months.add(monthArray[i]);//fills ArrayList with month names
        }
    }
    
    /**
     * Method Name: menu
     * Method Description: Menu for program used for navigation / serves as hub
     * @param events: ArrayList containing event information
     * @param months: ArrayList containing month names
     * @param days: Array containing number of days for each month
     * @param myFile1: File for events
     * @param myFile: File for date
     * @throws IOException 
     */
    public static void menu(ArrayList<Events> events, ArrayList<String> months,
            int[] days, File myFile1, File myFile) throws IOException {

        Scanner scanN = new Scanner(System.in);
        int userChoice;

        System.out.println("\n\tMAIN MENU"
                + "\n\nWhat would you like to do?(1,2,3..etc)");
        System.out.println("1. Add Event\n2. View Events\n3. Remove Event"
                + "\n4. Change Date\n5. Exit");
        userChoice = scanN.nextInt();
        
        while (userChoice > 5 || userChoice < 1) {//error traps user input
            System.out.println("Please enter a number from 1 - 5.");
            userChoice = scanN.nextInt();
        }
        
        if (userChoice == 1) {
            System.out.println("\n\tADD EVENT\n");
            //adds object created from getData to events ArrayList
            events.add(getData(events, months, days, myFile1));
            menu(events, months, days, myFile1, myFile);//navigates to menu
        }
        if (userChoice == 2) {
            viewEvents(events, months, days, myFile1);//calls viewEvents method
            menu(events, months, days, myFile1, myFile);
        }
        if (userChoice == 3) {
            System.out.println("\n\tEVENT REMOVAL\n");
            if (events.size() == 0) {//if there are no events does not call
                System.out.println("You have no events to remove.");
                menu(events, months, days, myFile1, myFile);
            } else if (events.size() > 0) {
                removeEvent(events, months, days, myFile1);
                menu(events, months, days, myFile1, myFile);
            }
        }
        if (userChoice == 4) {
            System.out.println("\n\tDATE CHANGE\n");
            changeDate(events, months, days, myFile);//calls changeDate method
            menu(events, months, days, myFile1, myFile);
        }
    }
    
    /**
     * Method Name: getData
     * Method Description: Gets Data for events ArrayList from user
     * @param events: ArrayList containing event info
     * @param months: ArrayList containing month names
     * @param days: Array containing number of days for each month
     * @param myFile1: File for events
     * @return Events - new Events object created through user input.
     * @throws IOException 
     */
    public static Events getData(ArrayList<Events> events,
            ArrayList<String> months, int[] days, File myFile1)
            throws IOException {
        FileWriter fWriter1 = new FileWriter(myFile1, true);
        PrintWriter pWriter1 = new PrintWriter(fWriter1);
        
        Scanner scanN = new Scanner(System.in);
        Scanner scanS = new Scanner(System.in);
        
        String eventName;
        String month;
        int day;

        System.out.println("What would you like to name this event?");
        eventName = scanS.nextLine();
        System.out.println("What month is the event taking place in?");
        month = scanS.nextLine();
        month = monthCheck(months, month);
        System.out.println("What day is the event taking place on");
        day = scanN.nextInt();
        day = dayCheck(months, days, month, day);
        
        Events newEvent = new Events(eventName, month, day);
        pWriter1.println((newEvent.toString().replace("[", "")
                .replace("]", "")));//removes square brackets (needed this)
        pWriter1.close();
        return new Events(eventName, month, day);//returns new object
    }
    
    /**
     * Method Name: viewEvents
     * Method Description: Allows the user to view events through different ways
     * @param event: ArrayList for all events
     * @param months: ArrayList for months
     * @param days: Array for number of days in each month
     * @param myFile1: File for events
     * @throws IOException 
     */
    public static void viewEvents(ArrayList<Events> event,
            ArrayList<String> months, int[] days, File myFile1)
            throws IOException {
        Scanner scanN = new Scanner(System.in);
        Scanner scanS = new Scanner(System.in);
        int minIndex;//used to keep track of index
        Events minValue = new Events("", "", 0);//creates new Events object
        int userChoice;
        String month;
        int i;
        int count = 0;
        
        System.out.println("\n\tVIEW EVENTS"
                + "\n\nWhich option would you like to choose?(1,2)");
        System.out.println("1. List of events for specific month\n"
                + "2. Search for an event on a specific date");
        userChoice = scanN.nextInt();
        
        while (userChoice > 2 || userChoice <= 0) {//error trap
            System.out.println("Please enter a number from 1 - 2");
            userChoice = scanN.nextInt();
        }
        
        if (userChoice == 1) {//Uses selection sort
            System.out.println("Which month would you like "
                    + "to view events for?");
            month = scanS.nextLine();
            month = monthCheck(months, month);
            System.out.println("\n\tEVENT LIST");
            
            for (i = 0; i < event.size(); i++) {
                minIndex = i;//sets minIndex to first index in ArrayList
                for (int j = i + 1; j < event.size(); j++) {
                    //compares day at index j to day at index i
                    if (event.get(j).getDay() < event.get(minIndex).getDay()) {
                        minIndex = j;//sets minIndex to j if j is smaller
                    }
                }
                //changes minValue to object at minIndex
                minValue = event.get(minIndex);
                //sets event at minIndex to event at i
                event.set(minIndex, event.get(i));
                //sets event at i to minValue
                event.set(i, minValue);
            }
            
            for (i = 0; i < event.size(); i++) {//prints sorted ArrayList
                if (event.get(i).getMonth().equalsIgnoreCase(month)) {
                    System.out.println(event.get(i).getEventName() + " "
                            + event.get(i).getMonth() + ", "
                            + event.get(i).getDay());
                }
            }
        }
        
        if (userChoice == 2) {
            FileWriter fWriter1 = new FileWriter(myFile1, true);
            PrintWriter pWriter1 = new PrintWriter(fWriter1);
            
            int day;
            count = 0;
            String makeEvent;
            String eventName;
            
            System.out.println("What month are you searching the event for?");
            month = scanS.nextLine();
            month = monthCheck(months, month);
            System.out.println("What day is the event taking place on?");
            day = scanN.nextInt();
            day = dayCheck(months, days, month, day);
            
            System.out.println("\n\tSEARCH RESULT");
            for (i = 0; i < event.size(); i++) {//prints specified event
                if (event.get(i).getMonth().equalsIgnoreCase(month)
                        && event.get(i).getDay() == day) {
                    System.out.println(event.get(i));
                    count++;
                }
            }
            if (count == 0) {//if event on date does not exist
                System.out.println("You do not have an event planned "
                        + "for this day. Would you like to set one?");
                makeEvent = scanS.nextLine();
                
                while (!makeEvent.equalsIgnoreCase("Yes")
                        && !makeEvent.equalsIgnoreCase("No")) {//error traps
                    System.out.println("Please say yes or no.");
                    makeEvent = scanS.nextLine();
                }
                
                if (makeEvent.equalsIgnoreCase("Yes")) {//creates new Event
                    System.out.println("What would you like to "
                            + "name this event?");
                    eventName = scanS.nextLine();
                    Events tempEvent = new Events(eventName, month, day);
                    event.add(tempEvent);
                    pWriter1.println(tempEvent);
                    pWriter1.close();
                    System.out.println("Event has been set.");
                }
            }
        }
    }
    
    /**
     * Method Name: removeEvent
     * Method Description: Removes event from file and from ArrayList
     * @param event: ArrayList for events
     * @param months: ArrayList for months
     * @param days: Array for number of days
     * @param myFile1: File for events
     * @throws IOException 
     */
    public static void removeEvent(ArrayList<Events> event,
            ArrayList<String> months, int[] days, File myFile1)
            throws IOException {
        Scanner scanN = new Scanner(System.in);
        int count = 0;
        int userchoice;
        
        System.out.println("Which event would you like to remove?(1,2,3..etc)");
        for (int i = 0; i < event.size(); i++) {//prints unsorted list of events
            System.out.println((i + 1) + ") " + event.get(i));
            count++;
        }
        userchoice = scanN.nextInt();
        
        while (userchoice - 1 > count - 1 || userchoice <= 0) {//error trap
            System.out.println("Please enter a number from " + 1
                    + "-" + count);
            userchoice = scanN.nextInt();
        }
        userchoice -= 1;//to prevent out of bounds exception
        event.remove(userchoice);//removes specified event
        
        //reprints info on file
        FileWriter fWriter1 = new FileWriter(myFile1, false);
        PrintWriter pWriter1 = new PrintWriter(fWriter1);
        for (int i = 0; i < event.size(); i++) {
            pWriter1.println(event.get(i));
        }
        pWriter1.close();
        System.out.println("The event has been removed.");
    }
    
    /**
     * Method Name: changeDate
     * Method Description: Changes date.
     * @param event: ArrayList for events
     * @param months: ArrayList for months
     * @param days: Array for days
     * @param myFile: File for date
     * @throws IOException 
     */
    public static void changeDate(ArrayList<Events> event,
            ArrayList<String> months, int[] days, File myFile)
            throws IOException {
        FileWriter fWriter = new FileWriter(myFile, false);
        PrintWriter pWriter = new PrintWriter(fWriter);
        
        Scanner scanS = new Scanner(System.in);
        Scanner scanN = new Scanner(System.in);
        String month;
        int day;
        
        System.out.println("What month is it for you?");
        month = scanS.nextLine();
        month = monthCheck(months, month);
        System.out.println("What day of the month is it?");
        day = scanN.nextInt();
        day = dayCheck(months, days, month, day);
        pWriter.println(month + "," + day);
        pWriter.close();
        fWriter = new FileWriter(myFile, true);
        System.out.println("The date has been set.");
    }
    
    /**
     * Method Name: monthCheck
     * Method Description: Checks user input to see if month exists
     * @param months: ArrayList for months
     * @param month: Month name that user input
     * @return - months
     */
    public static String monthCheck(ArrayList<String> months, String month) {
        Scanner scanS = new Scanner(System.in);
        
        while (!months.contains(month)) {//if month doesnt exist runs loop
            System.out.println("Please give a correct month.");
            month = scanS.nextLine();
        }
        return month;
    }
    
    /**
     * Method Name: dayCheck
     * Method Description: Checks if user input for day is correct
     * @param months: ArrayList for months
     * @param days: Array for days
     * @param month: Month entered from user input
     * @param day: Day entered from user input
     * @return - day
     */
    public static int dayCheck(ArrayList<String> months, int[] days,
            String month, int day) {
        Scanner scanN = new Scanner(System.in);
        
        for (int i = 0; i < days.length; i++) {//if day doesnt exist runs loop
            while (month.equalsIgnoreCase(months.get(i)) && day > days[i]
                    || day < 1) {
                System.out.println("Please enter a day that exists.");
                day = scanN.nextInt();
            }
        }
        return day;
    }
    
    /**
     * Method Name: fillList
     * Method Description: Fills event ArrayList with info from file
     * @param event: ArrayList for events
     * @param myFile1: File for events
     * @throws IOException 
     */
    public static void fillList(ArrayList<Events> event, File myFile1)
            throws IOException {
        Scanner readFile1 = new Scanner(myFile1);
        int count = 0;
        int count2 = 0;
        
        while (readFile1.hasNext()) {
            String line = readFile1.nextLine();
            count++;
        }
        readFile1.close();
        readFile1 = new Scanner(myFile1);
        
        //creates arrays according to size of count
        String[] eventNames = new String[count];
        String[] months = new String[count];
        int[] days = new int[count];
        
        while (readFile1.hasNext()) {
            String line = readFile1.nextLine();
            String token[] = line.split(",");
            count2++;
            eventNames[count2 - 1] = token[0];
            months[count2 - 1] = token[1];
            days[count2 - 1] = Integer.parseInt(token[2]);
        }
        for (int i = 0; i < count2; i++) {
            event.add(new Events(eventNames[i], months[i], days[i]));
        }
    }
}
