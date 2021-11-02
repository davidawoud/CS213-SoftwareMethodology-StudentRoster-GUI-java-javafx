package student;

/**
This class extends Student and contains operations and data that are specific to nonresidents.
It is extended by tristate and international.
@author Stephen Juan, David Halim
*/

public class NonResident extends Student
{
    public static final int nonResidentTuition = 29737;
    public static final int nonResidentCreditHour = 966;
    
    /**
    This is a constructor for a nonresident that passes on values for the student's most recent
    payment and the date of the most recent payment.
    @param profile         - profile of the student (from Student)
    @param creditHours     - number of credits that the student is taking (from Student)
    @throws Exception if the credit hours are invalid.
    */
    public NonResident(Profile profile, int creditHours)  throws Exception
    {
        super(profile, creditHours);
    }
    
    /**
    Using the number of credits the student of taking, it returns the total cost of tuition. 
    @return total cost of tuition as a float
    */
    public float getTotalCost()
    {
        float totalAmount = 0; 
        if (getCreditHours() >= minFulltime) // fulltime students
        {
            totalAmount += nonResidentTuition; 
            if (this.getCreditHours() - fulltimeThreshold > 0) // extra credit over 16 fee 
            {
                totalAmount += (nonResidentCreditHour * (getCreditHours() - fulltimeThreshold)); 
            }
            totalAmount += universityFee; // university fee
        }
        else
        {
            totalAmount += (getCreditHours() * nonResidentCreditHour); // cost of tuition
            totalAmount += (0.8 * universityFee); 
        }
        return totalAmount; 
    }
    
    /**
    This method calculates the tuition due by subtracting the total cost of tuition minus the 
    money the student has already paid. 
    */
    @Override
    public void tuitionDue()
    {
        float tuitionDue = getTotalCost() - getTotalPayment();
        setTuitionDue(tuitionDue);
    }
    
    /**
    This method returns a string representation of the student in the format
    "name:major:".
    @return string representation of a student as "name:major:credit hours"
    */
    @Override
    public String toString()
    {
        return super.toString() + ":non-resident";
    }
}