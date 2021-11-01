package student;
import java.text.DecimalFormat;

/**
This class is the superclass Student and contains the profile and
the credit hours that the student is taking. It is extended by 
NonResident and Resident. 
@author Stephen Juan, David Halim
*/
public class Student 
{
    private Profile profile;
    private int creditHours; 
    
    public static final int universityFee = 3268;
    public static final int minFulltime = 12;
    public static final int fulltimeThreshold = 16; 
    
    private String lastPaymentDate = "--/--/--";
    private float totalPayment =  0; 
    private float tuitionDue = 0; 
    
    // WE WILL NEED TO CHECK FOR SOMETHING WHEN PAYMENT > COST OF TUITION
    
    /**
    This is a default constructor that sets the a default profile
    with name 'Default Name' and a default major of CS and zero credit hours.
    It calls the overloaded constructor to pass on the values.  
    @throws Exception if the credit hours are invalid.
    */
    public Student() throws Exception
    {
        this(new Profile("Default Name","CS"), 12);
    }
    
    /**
    This is an overloaded constructor that initializes the profile and 
    the number of credit the student is taking. 
    @param profile     - profile (including name and major) of the student
    @param creditHours - number of credits the student is taking 
    @throws Exception if the credit hours are invalid.
    */
    public Student(Profile profile, int creditHours) throws Exception
    {
        this.profile = profile;
        this.creditHours = creditHours;
        isValidCreditHours();
    }
    
    /**
    This is a constructor with only the profile of the student as a parameter. 
    @param profile - profile of the student
    @throws Exception if the credit hours are invalid.
    */
    public Student(Profile profile) throws Exception
    {
        this.profile = profile;
        this.creditHours = 12;
        isValidCreditHours();
    }
    
    /**
    This method is a method that is called when anything is being paid. 
    It adds the total payment to the last payment amount and saves the last payment date. 
    @param input   - the date object 
    @param payment - last payment amount
    */
    public void setLastPaymentDateAmount(Date input, float payment)
    {
        lastPaymentDate = input.toString();
        totalPayment += payment;
        tuitionDue -= payment;
    }
    
    /**
    This method returns the total payment that the student has made.
    @return total payment the student made
    */
    public float getTotalPayment() // use this method in the sub classes
    {
        return totalPayment; 
    }
    
    /**
    This method sets the remaining tuition due to the parameter that it takes in. 
    It is called once in every one of the subclasses. 
    @param amount - amount of money that is due
    */
    public void setTuitionDue(float amount)
    {
        tuitionDue = amount;
    }
    
    /**
    This method is for the international class for study abroad. If they have more than
    12 credits, then the credit amount is set to 12. 
    */
    public void setCreditStudAbroad()
    {
        creditHours = minFulltime; 
    }
    
    /**
    This method is for the international class for study abroad, sets payment to zero and clears
    payment date
    */
    public void resetPaymentandDate()
    {
        totalPayment = 0; 
        lastPaymentDate = "--/--/--"; 
    }
    
    /**
    This method returns the tuition due. It is used in JUnit Test classes.
    @return the tuition due
    */
    public float getTuitionDue()
    {
        return tuitionDue; 
    }
    
    /**
    This is do-nothing method that is to be overloaded in the classes that
    extend the student class. 
    */
    public void tuitionDue() // <- total cost of tuition minus total payment
    {
    }
    
    /**
    This method returns the number of credits the student is taking. 
    @return number of credits the student is taking
    */
    public int getCreditHours()
    {
        return creditHours; 
    }
    
    /**
    This method returns the date of last payment.
    @return date of last payment
    */
    public String getLastPaymentDate()
    {
        return lastPaymentDate;
    }
    
    /**
    This method returns the profile of the student. It's used in the compare
    method of Profile.
    @return profile of the student. 
    */
    public Profile getProfile()
    {
        return profile;
    }
    
    /**
    Checks the validity of credit hours (between 3 and 24 inclusive).
    @throws Exception if the credit hours are invalid.
    */
    public void isValidCreditHours() throws Exception
    {
        if (creditHours < 0)
            throw new Exception("Credit hours cannot be negative.");
        if (creditHours >= 0 && creditHours < 3)
            throw new Exception("Minimum credit hours is 3.");
        if (creditHours > 24)
            throw new Exception("Credit hours exceed the maximum 24.");
    }
    
    /**
    This method returns a string representation of the student in the format
    "name:major:".
    @return string representation of a student as "name:major:credit hours"
    */
    @Override
    public String toString()
    {
        DecimalFormat df = new DecimalFormat(",##0.00");
        return profile.toString() + ":" + getCreditHours() + " credit hours:tuition due:" + df.format(tuitionDue) + 
               ":total payment:" + df.format(totalPayment) + ":last payment date: " + lastPaymentDate;
    }
    
    /**
    This method returns true if both student profiles are equal, false if not.
    @return true, if both students are the same, false if otherwise
    */
    @Override
    public boolean equals(Object obj)
    {
        return this.getProfile().equals(obj);
    }
}