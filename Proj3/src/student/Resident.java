package student;
import java.text.DecimalFormat;

/**
This class is the superclass Resident and contains the profile and
the credit hours that the student is taking and also has an operation 
for financial aid. 
@author Stephen Juan, David Halim
*/
public class Resident extends Student
{
    
    float financialAid = 0;
    boolean tookFinancialAid = false;
    
    /**
    This is a constructor for a resident that passes on values for the student's most recent
    payment and the date of the most recent payment.
    @param profile         - profile of the student (from Student)
    @param creditHours     - number of credits that the student is taking (from Student)
    @throws Exception if the credit hours are invalid.
    */
    public Resident(Profile profile, int creditHours)  throws Exception
    {
        super(profile, creditHours);
        tuitionDue();
        
    }
    
    /**
    Using the number of credits the student of taking, it returns the total cost of tuition. 
    @return total cost of tuition as a float
    */
    public float getTotalCost()
    {
        float residentTuition = 12536;
        float residentCreditHour = 404;
        float totalAmount = 0; 
        
        if (getCreditHours() >= minFulltime) // fulltime students
        {
            totalAmount += residentTuition; 
            if (this.getCreditHours() - fulltimeThreshold > 0) // extra credit over 16 fee 
            {
                totalAmount += (residentCreditHour * (getCreditHours() - fulltimeThreshold)); 
            }
            totalAmount += universityFee; // university fee
        }
        else
        {
            totalAmount += (this.getCreditHours() * residentCreditHour); // cost of tuition
            totalAmount += (0.8 * universityFee); 
        }
        return totalAmount; 
    }
    
    /**
    This method gives the student financial aid to fulltime students onlyone time.
    @param aid to be given to student.
    @return true if the aid is given,
            false if the aid is an invalid number
    @throws Exception if student cannot receive financial aid.
    */
    public boolean setFinancialAid(float aid) throws Exception
    {
        if (getCreditHours() < 12)
            throw new Exception("Parttime student doesn't qualify for the award.");
        if (tookFinancialAid)
            throw new Exception("Awarded once already.");
        
    	if (isValidFinancial(aid))
        {
        	financialAid = aid;
        	tookFinancialAid = true;
        	tuitionDue();
        	return true;
        }
        else
        	return false;
    }
    
    /**
    This method returns checks the validity of the financial aid amount.
    @param aid to be given to student.
    @return true if financial aid is positive and less than 10000, false if otherwise
    */
    public boolean isValidFinancial(float aid)
    {
        if (aid >= 0 && aid <= 10000)
            return true;
        else
            return false;
    }
    
    /**
    This method calculates the tuition due by subtracting the total cost of tuition minus the 
    money the student has already paid. 
    */
    @Override
    public void tuitionDue()
    {
        float tuitionDue = getTotalCost() - getTotalPayment();
        if (financialAid > 0)
            tuitionDue -= financialAid;
        setTuitionDue(tuitionDue);
    }
    
    /**
    This method returns a string representation of the student in the format
    "name:major:credit hours:tuition due: last payment:payment date:resident".
    @return string representation of a student as "name:major:credit hours:tuition due: last payment:payment date:resident"
    */
    @Override
    public String toString()
    {
        if(tookFinancialAid)
        {
        	DecimalFormat df = new DecimalFormat(",##0.00");
        	return super.toString() + ":resident:financial aid $" + df.format(financialAid);
        }
        else
    	    return super.toString() + ":resident";
    }
}