package student;

/**
This class contains information about international students
and if they are study abroad or not. Contains updated tuition amount.
@author Stephen Juan, David Halim
*/

public class International extends NonResident
{
    private boolean studyAbroad; 
    
    /**
    This is a constructor for the international students that is the same as NonResident except it included 
    whether or not the student is in the study abroad program or not. 
    @param profile         - profile of the student (from Student)
    @param creditHours     - number of credits that the student is taking (from Student)
    @param studyAbroad     - if the student is in the study abroad program or not
    @throws Exception if the credit hours are invalid.
    */
    public International(Profile profile, int creditHours, boolean studyAbroad) throws Exception
    {
        super(profile, creditHours);
        this.studyAbroad = studyAbroad; 
        if (studyAbroad)
            setStudyAbroad();
    }
    
    /**
    This method overrides the isValidCreditHours from Student and includes extra restrictions for study abroad
    students. If they are study abroad, they are only allowed to take a maximum of 12 credits. 
    @throws Exception if the credit hours are invalid.
    */
    @Override
    public void isValidCreditHours() throws Exception
    {
       if (getCreditHours() < 0)
           throw new Exception("Credit hours cannot be negative.");
       if (getCreditHours() >= 0 && getCreditHours() < 3)
           throw new Exception("Minimum credit hours is 3.");
       if (getCreditHours() > 24)
           throw new Exception("Credit hours exceed the maximum 24.");
       if ( getCreditHours() < 12)
           throw new Exception("International students must enroll at least 12 credits.");   
       if (studyAbroad && getCreditHours() > 12)
    	   throw new Exception("Credit hours exceed the maximum 12.");
    }
    
    public void setStudyAbroad()
    {
        // Set study abroad status to true for an international student.
        studyAbroad = true;
        // If the number of credit hours is greater than 12, set it to 12;
        if (getCreditHours() > minFulltime)
            setCreditStudAbroad();
        //set the payment to 0, clear the payment date
        resetPaymentandDate();
        // recalculate the tuition due
        tuitionDue(); 
    }
    
    /**
    This method gets the tuition for international students.
    @return the total cost of tuition for international students
    */
    @Override
    public float getTotalCost()
    {
    	float internationalTuition = 29737;
        float additionalFee = 2650;
        float totalAmount = 0; 
        
        if (studyAbroad)
            totalAmount = universityFee + additionalFee;
        else
        {
            totalAmount += internationalTuition; 
            if (this.getCreditHours() - fulltimeThreshold > 0) // extra credit over 16 fee 
            {
                totalAmount += (nonResidentCreditHour * (getCreditHours() - fulltimeThreshold)); 
            }
            totalAmount += (universityFee + additionalFee); // university fee
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
    "name:major:credit hours:tuition due: last payment:payment date:non-resident:Internatiional".
    @return string representation of a student.
    */
    @Override
    public String toString()
    {
        String finalString = super.toString() + ":international";
        if (studyAbroad)
            return finalString + ":study abroad";
        else 
            return finalString; 
    }
}