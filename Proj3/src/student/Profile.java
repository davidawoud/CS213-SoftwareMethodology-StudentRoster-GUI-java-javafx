package student;

/**
This class defines a Profile that is to be used by Student. It identifies a unique
student with a name and a major.
@author Stephen Juan, David Halim
*/
public class Profile 
{
    private String name;
    private Major major; //5 majors and 2-charater each: CS, IT, BA, EE, ME
    
    public Profile(String name, String major) throws Exception
    {
        this.name = name;
        if (major.equals("CS") || major.equals("cS") || major.equals("Cs") || major.equals("cs"))
            this.major = Major.CS;
        else if (major.equals("IT") || major.equals("iT") || major.equals("It") || major.equals("it"))
            this.major = Major.IT;
        else if (major.equals("BA") || major.equals("bA") || major.equals("Ba") || major.equals("ba"))
            this.major = Major.BA;
        else if (major.equals("EE") || major.equals("eE") || major.equals("Ee") || major.equals("ee"))
            this.major = Major.EE;
        else if (major.equals("ME") || major.equals("mE") || major.equals("Me") || major.equals("me"))
            this.major = Major.ME;
        else
            throw new Exception("'" + major + "' is not a valid major."); 
    }
    
    /**
    This method gets the name of the Profile and returns it.
    @return name - name of the student
    */
    public String getName()
    {
        return this.name;
    }
    
    /**
    This method gets the major of the Profile and returns it.
    @return major - major of the student
    */
    public Major getMajor()
    {
        return this.major; 
    }
    
    /**
    This method compares two students and checks if they are the same. If they are, return 
    true, otherwise return false
    @return true if the name and majors are the same, false if otherwise
    */
    @Override 
    public boolean equals(Object obj)
    {
        Student student = (Student) obj;
        String studentName = student.getProfile().getName();
        Major studentMajor = student.getProfile().getMajor();
        
        if (this.name.equals(studentName) && this.major == studentMajor)
        {
            return true; 
        }
        return false;
    }
    
    /**
    This method returns a string of the Profile in the format name:major:
    @return string in the format "name:major"
    */
    @Override
    public String toString()
    {
        String majorName = "";
        if (this.major == Major.CS)
            majorName = "CS";
        else if (this.major == Major.IT)
            majorName = "IT";
        else if (this.major == Major.BA)
            majorName = "BA";
        else if (this.major == Major.EE)
            majorName = "EE";
        else
            majorName = "ME";
        return this.name + ":" + majorName;
    }
}