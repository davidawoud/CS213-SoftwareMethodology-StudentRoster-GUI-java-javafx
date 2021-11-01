package student;

/**
This class contains information about tri-state students including 
the state that they live in (NY or CT) and gives them a discounted 
tuition rate. 
@author Stephen Juan, David Halim
*/
public class Tristate extends NonResident
{
    private String state; 
    
    /**
    This is the constructor for tristate that is the same as NonResident, except it passes
    the value of the state that the student is in.
    @param profile         - profile of the student (from Student)
    @param creditHours     - number of credits that the student is taking (from Student)
    @param state           - the state that the student lives in
    @throws Exception if the credit hours are invalid.
    */
    
    public Tristate(Profile profile, int creditHours, String state)  throws Exception
    {
        super(profile, creditHours);
        if (state.equals("NY") || state.equals("nY") || state.equals("Ny") || state.equals("ny"))
            this.state = "NY";
        else if (state.equals("CT") || state.equals("cT") || state.equals("Ct") || state.equals("ct"))
            this.state = "CT";
        else
            throw new Exception("Not part of the tri-state area.");
        tuitionDue();
    }
    
    
    /**
    This method calculates the tuition due by subtracting the total cost of tuition minus the 
    money the student has already paid. Only full time students get a discount. 
    */
    @Override
    public void tuitionDue()
    {
    	int NYDiscount = 4000;
        int CTDiscount = 5000; 
        
        float tuitionDue = getTotalCost() - getTotalPayment();
        if (getCreditHours() >= 12)
        {
            if (state.equals("NY"))
                tuitionDue -= NYDiscount;
            else // CT
                tuitionDue -= CTDiscount;
        }
        setTuitionDue(tuitionDue);
    }
    
    /**
    This method returns a string representation of the student in the format
    "name:major:credit hours:tuition due: last payment:payment date:non-resident (tri-state):state".
    @return string representation of a student as "name:major:credit hours:tuition due: last payment:payment 
            date:non-resident (tri-state):state"
    */
    @Override
    public String toString()
    {
        return super.toString() + "(tri-state):" + state;
    }
}