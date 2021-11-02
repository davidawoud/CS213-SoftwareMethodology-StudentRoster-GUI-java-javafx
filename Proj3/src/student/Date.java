package student;

import java.util.Calendar; 

/**
This class represents a date and includes methods that checks if the user-input
date is valid and compares if a current date is before, equal to, or after another
date
@author Stephen Juan, David Halim
*/

public class Date implements Comparable<Date>
{	
    private int year;
    private int month;
    private int day;
        
    public static final int NORMAL_YEAR_FEB = 28;
    public static final int LEAP_YEAR_FEB = 29;
    public static final int THIRTY_DAY_MONTH = 30;
    public static final int THIRTY_ONE_DAY_MONTH = 31;
    public static final int NUMBER_MONTHS = 12;
    public static final int YEAR_21 = 2021;

    /**
    This is a constructor for the Date class that accepts a date as a string and gives values
    for the month, day and year from that string.
    @param Date that is to be processed as a string
    */
    public Date(String Date)
    {
        String[] date = new String[3]; 
        for (int i = 0; i < date.length; i++)
        {
            date[i] = "";		
        }
        int stringCounter = 0; 
        for (int i = 0; i < Date.length(); i++)
        {
            if (Date.charAt(i) == '/')
            {
                stringCounter++; 
                continue;
            }
            date[stringCounter] += Date.charAt(i);
        }
        this.month = Integer.valueOf(date[0]);
        this.day = Integer.valueOf(date[1]);
        this.year = Integer.valueOf(date[2]);
    }
    
    /**
    This is another constructor that gets today's date using the Java Calendar class
    */
    public Date()
    {
        Calendar cal = Calendar.getInstance(); 
        this.month = cal.get(Calendar.MONTH) + 1;
        this.day = cal.get(Calendar.DAY_OF_MONTH);
        this.year = cal.get(Calendar.YEAR);
    }
    
    /**
    This is a copy constructor that copies the Date instance
    @param date - the instance to be copied
    */
    public Date(Date date)
    {
        this.month = date.month;
        this.day = date.day;
        this.year = date.year; 
    }

	/**
	This method checks if the date is a valid date in a calendar, utilizing some private classes 
	for in-depth checks. It returns true if i the date is valid and false if the date is invalid.
    @return true for a valid date, false for an invalid date
    */
    public boolean isValid() 
    {
        int day = this.day; 
        int month = this.month;
        int year = this.year;	

        if (!isValid_negativeOr2021(day, month, year))
        {
        	//System.out.println("hi");
            return false;
        }
        if (!isValid_futureDate(day, month, year))
        {
            return false; 
        }
        if (!isValid_monthLeapYear(day, month, year))
        {
            return false; 
        }

        return true;
    }
	
    /**
    This method checks for a negative day or month, a month with a number over 12 and if the year is 
    below 2021. It returns false for a negative date or month, a month over 12 or a year below 2021. 
    @param day   - the input day to be checked
    @param month - the input month to be checked
    @param year  - the input year to be checked
    @return true if day is positive, month is between 1-12 and year is 2021,
            false if otherwise
    */
    private boolean isValid_negativeOr2021(int day, int month, int year)
    {
        if (day < 1) 
        {
            return false; 
        }

        if (month < 1 || month > NUMBER_MONTHS) 
        { 
            return false; 
        } 

        if (year < YEAR_21) 
        { 
            return false; 
        }
        return true; 
    }
	
    /**
    This method checks if a date is a future date and returns false if it is in the future
    @param day   - day to be compared to todays date
    @param month - month to be compared to todays month
    @param year  - year to be compared to todays year
    @return false if the day is in the future, true if the day is the current day or in the past
    */
    private boolean isValid_futureDate(int day, int month, int year)
    {
        Date todaysDate = new Date();
        Date comparedDate = new Date("" + month + "/" + day + "/" + year); 
		
        int result = comparedDate.compareTo(todaysDate);
        // if compareDate is future == 1
        // if compareDate is same == 0
        // if compareDate is before == -1
        if (result == -1 || result == 0) 
        { 
            return true; 
        }
        else 
        { 
            return false;
        }
    }

    /**
    This method checks if the month has 30/21 days, the days are not over 30/31 days. In the case of 
    February, which has 28 or 29 days, it checks if the current year is a leap year and uses that
    information to check whether the day in Feburary is valid or not. It returns false if the date is
    invalid and true if it is valid. 
    @param day   - the input day to be checked
    @param month - the input month to be checked
    @param year  - the input year to be checked
    @return true if the day is valid, false if the day is not valid
    */
    private boolean isValid_monthLeapYear(int day, int month, int year)
    {
        int jan = 1, mar = 3, apr = 4, may = 5, jun = 6, jul = 7, aug = 8, sep = 9, oct = 10, nov = 11, dec = 12;
        // checks months with 31 days
        if (month == jan || month == mar || month == may || month == jul || month == aug || month == oct || month == dec)
        {
            if (day > THIRTY_ONE_DAY_MONTH)
            { 
                return false;
            }	
        }
        // checks months with 30 days
        else if (month == apr || month == jun || month == sep || month == nov) 
        { 
            if (day > THIRTY_DAY_MONTH)
            {
                return false; 
            }
        }
		// checks for february and leap years
        else
        {
            int quadrennial = 4;
            int centennial = 100;
            int quatercentennial = 400;
        	boolean isLeapYear = false;
            if ((year %= quadrennial) == 0)
            {
                if ((year %= centennial) == 0)
                {
                    if ((year %= quatercentennial) == 0) 
                    { 
                    	isLeapYear = true; 
                    }
                }
                else 
                { 
                	isLeapYear = true; 
                } 
            }

            if (day == LEAP_YEAR_FEB && isLeapYear) 
            { 
                return true;  
            }
            else if (day == LEAP_YEAR_FEB && !isLeapYear) 
            { 
                return false; 
            }
            else if (day > NORMAL_YEAR_FEB) 
            { 
                return false; 
            }
        }
        return true; 
    }
	
    /**
    This method compares a current Date object to another Date object. It returns 1 is the current Date object 
    is more, 0 if it is equal and -1 if it is before. 
    @param date - Date object that is being used as a reference for comparison
    @return 1 the parameter is less than the compared month
            0 if both dates are the same
            -1 if the parameter is greater
    */
    @Override
    public int compareTo(Date date)	
    { 
        int currentDay = this.day;
        int currentMonth = this.month;
        int currentYear = this.year;
        if (currentYear > date.year)
        {
            return 1;
        }
        else if (currentYear == date.year)
        {
            if (currentMonth > date.month) 
            {
                return 1;
            }
            else if (currentMonth == date.month)
            {
                if (currentDay > date.day)
                {
                    return 1; 
                }
                else if (currentDay == date.day)
                {
                    return 0;
                }
                else
                {
                    return -1;	
                }
            }
            else
            {
                return -1;
            }
        }
        else // if currentYear < date.year
        {
            return -1;
        }
    } 
    
    @Override
    public String toString()
    {
    	return "" + month + "/" + day + "/" + year;
    }
}