package student;

/**
Container class defines the array list data structure to hold the student roster.
@author David Halim, Stephen Juan.
*/
public class Roster
{
    private static final int DEFAULT_SIZE = 10;
	private Student[] students;
    private int size; //number of students currently in the roster
    
    /**
    Default constructor initializes size of students array to DEFAULT_SIZE.
    DEFAULT_SIZE is a static final int = 10.
    Calls the Overloaded constructor passing the value DEFAULT_SIZE.
    Initializes size = 0 (empty roster).
    */
    public Roster()
    {
        this(DEFAULT_SIZE);
    }
    
    /**
    Overloaded constructor initializes size of students array and size.
    Initializes size of students array to an accepted value studentsSize.
    Initializes size = 0 (empty roster).
    @param studentsSize accepted value for size of students array.
    */
    public Roster(int studentsSize)
    {
        students = new Student[studentsSize];
        size = 0;
    }
    
    /**
    find the student index, or return (-1) NOT_FOUND.
    @param student to be found.
    @return index if found, returns -1 otherwise. 
    */
    private int find(Student student)
    {
        for (int i = 0; i < size; i++)
        {
            if (student.equals(students[i]))
                return i;
        }
        
        return -1;
    }
    
    /**
    increase the capacity of the array list by 4
    */
    private void grow()
    {
        int growValue = 4;
    	Student[] students = new Student[this.students.length + growValue];
        
        for (int i = 0; i < this.students.length; i++)
        {
            students[i] = this.students[i];
        }
        
        this.students = students;
    }
    
    /**
    Adds an student to the roster.
    Calls find(student) to make sure the new does not
    exist in the array to eliminate duplicates.
    @param student to add to the roster
    @return true if the add is successful, 
            false if the student already exists in the roster.
     */
    public boolean add(Student student)
    {
        int studentIndex = find(student);
        
        if (studentIndex != -1)
            return false;
        
        if (size >= students.length)
            grow();
        
        students[size] = student;
        size++;
        return true;
    }
    
    /**
    Removes an student from the roster.
    Calls find(student) to get the index to remove.
    Maintains the current sequence of students
    in the array after the deletion.
    @param student to remove from the roster.
    @return true if the remove is successful,
            false if the student does not exist in the class.
    */
    public boolean remove(Student student)
    {
        int studentIndex = find(student);
        
        if (studentIndex == -1)
            return false;
        
        for (int i = studentIndex; i < size - 1; i++)
        {
            students[i] = students[i + 1];
        }
        
        students[size - 1] = null;
        
        size = size - 1;
        return true;
    }
    
    /**
    Prints out the list without specifying the order.
    @return print the string to print
    */
    public String print()
    {
        String print = "";
    	if (size == 0)
            print += "Student roster is empty!\n";
    	else
    	{
    		print += "* list of students in the roster **\n";
    		for (int i = 0; i < size; i++)
            {
                print += students[i].toString() + "\n";
            }
    		print += "* end of roster **\n";
    	}
        
        return print;
    }
    
    /**
    Merges 2 sublists and sort them in the order of student names.
    @param first - index of the first element of the right sublist to be merged
    @param middlePoint - index of the last element of the right sublist to be merged
    @param last- index of the last element of the left sublist to be merged
    */
    private void mergeByStudentNames(int first, int middlePoint, int last)
    {
        int leftArraySize = middlePoint - first + 1;
        int rightArraySize = last - middlePoint;
        
        Student[] leftArray = new Student[leftArraySize];
        Student[] rightArray = new Student[rightArraySize];
  
        /*Copy data to temp arrays*/
        for (int i = 0; i < leftArraySize; ++i)
            leftArray[i] = students[first + i];
        
        for (int j = 0; j < rightArraySize; ++j)
            rightArray[j] = students[middlePoint + 1 + j];
  
        /* Merge the temp arrays */
        
        int i = 0, j = 0;
        int k = first;
        while (i < leftArraySize && j < rightArraySize) {
            if (leftArray[i].getProfile().getName().compareTo(rightArray[j].getProfile().getName()) <= 0)
            {
                students[k] = leftArray[i];
                i++;
            }
            else
            {
            	students[k] = rightArray[j];
                j++;
            }
            k++;
        }
        
        /* Copy remaining elements if any */
        
        while (i < leftArraySize) {
        	students[k] = leftArray[i];
            i++;
            k++;
        }
        
        while (j < rightArraySize) {
        	students[k] = rightArray[j];
            j++;
            k++;
        }
    }
    
    /**
    Merges 2 sublists and sort them in the order of Payment Date.
    @param first - index of the first element of the right sublist to be merged
    @param middlePoint - index of the last element of the right sublist to be merged
    @param last- index of the last element of the left sublist to be merged
    */
    private void mergeByPaymentDate(int first, int middlePoint, int last)
    {
        int leftArraySize = middlePoint - first + 1;
        int rightArraySize = last - middlePoint;
        
        Student[] leftArray = new Student[leftArraySize];
        Student[] rightArray = new Student[rightArraySize];
  
        /*Copy data to temp arrays*/
        for (int i = 0; i < leftArraySize; ++i)
            leftArray[i] = students[first + i];
        
        for (int j = 0; j < rightArraySize; ++j)
            rightArray[j] = students[middlePoint + 1 + j];
  
        /* Merge the temp arrays */
        
        int i = 0, j = 0;
        int k = first;
        while (i < leftArraySize && j < rightArraySize) {
            if (leftArray[i].getLastPaymentDate().compareTo(rightArray[j].getLastPaymentDate()) <= 0)
            {
                students[k] = leftArray[i];
                i++;
            }
            else
            {
            	students[k] = rightArray[j];
                j++;
            }
            k++;
        }
        
        /* Copy remaining elements if any */
        
        while (i < leftArraySize) {
        	students[k] = leftArray[i];
            i++;
            k++;
        }
        
        while (j < rightArraySize) {
        	students[k] = rightArray[j];
            j++;
            k++;
        }
    }
    
    /**
    Recursive sort method that sorts the list using merge sort.
    Splits the every list/sublist into 2 halves until the lists reach size 1.
    It merges in the order of Student Name or LastPaymentDate according to int sortingType.
    @param first - index of the first element of the list/sublist to be sorted
    @param last - index of the last element of the list/sublist to be sorted
    @param sortingType represents the type of  merge sorting;
           0 = mergeByStudentNames
           1 = mergeByPaymentDate.
    */
    private void sort(int first, int last, int sortingType)
    {
        if (first < last)
        {
            int middlePoint = first + (last - first) / 2;
            
            // Sort first and second halves
            sort(first, middlePoint, sortingType);
            sort(middlePoint + 1, last, sortingType);
            
            // Merge the sorted halves
            if (sortingType == 0)
            	mergeByStudentNames(first, middlePoint, last);
            else
                mergeByPaymentDate(first, middlePoint, last);
        }
    }
    
    /**
    Prints out the list in the order of Student Names.
    @return print the string to print
    */
    public String printByStudentNames()
    {
    	String print = "";
    	if (size == 0)
            print += "Student roster is empty!\n";
    	else
    	{
    		sort(0, size - 1, 0);
    		print += "* list of students ordered by name **\n";
    		for (int i = 0; i < size; i++)
            {
                print += students[i].toString() + "\n";
            }
    		print += "* end of roster **\n";
    	}
        
        return print;
    }
    
    /**
    Prints out the list in the order of Payment Date.
    @return print the string to print
    */
    public String printByPaymentDate()
    {
    	String print = "";
    	if (size == 0)
            print += "Student roster is empty!\n";
    	else
    	{
    		sort(0, size - 1, 1);
    		print += "* list of students made payments ordered by payment date **\n";
    		for (int i = 0; i < size; i++)
            {
    			if (students[i].getLastPaymentDate().equals("--/--/--"))
                    continue;
    			print += students[i].toString() + "\n";
            }
    		print += "* end of roster **\n";
    	}
        
        return print;
    }
    
    /**
    Calculates tuition due for all students in the roster.
    */
    public void calculateTuition()
    {
        for (int i = 0; i < size; i++)
        {
        	students[i].tuitionDue();
        }
    }
    
    /**
    Allows a student in the roster to make a payment.
    Calls find(student) to get the index to make the payment.
    @param student to pay tuition.
    @param paymentDate to specify the payment date.
    @param payment to specify the payment amount.
    @throws Exception if Student is not in the roster,
                         Amount is greater than amount due.
    */
    public void payTuition(Student student, Date paymentDate, float payment) throws Exception
    {
        int studentIndex = find(student);
        
        if (studentIndex == -1)
            throw new Exception("Student is not in the roster.");
        
        if (payment > students[studentIndex].getTuitionDue())
            throw new Exception("Amount is greater than amount due.");
        
        if (!paymentDate.isValid())
        	throw new Exception("Invalid payment date.");
        
        students[studentIndex].setLastPaymentDateAmount(paymentDate, payment);
    }
    
    /**
    Allows an International student in the roster to be in the Study Abroad  program.
    Calls find(student) to get the index of the student.
    @param student to be in the Study Abroad  program.
    @param studyAbroad value of study abroad (true or false).
    @return true if the setStudyAbroad is successful,
            false if the student does not exist in the class. 
    @throws Exception if Student is not international.
    */
    public boolean setStudyAbroad(Student student, boolean studyAbroad) throws Exception
    {
        int studentIndex = find(student);
        
        if (studentIndex == -1)
            return false;
        if (students[studentIndex] instanceof International)
        {
        	International interStudent = (International) students[studentIndex];
        	interStudent.setStudyAbroad();
        }
        else
            throw new Exception("Student is not international");
        
        return true;
    }
    
    /**
    Set the financial aid amount for a resident student.
    Calls find(student) to get the index of the student.
    @param student to get financial aid.
    @param aid value to be given.
    @return true if the setFinancialAid is successful,
            false if the student does not exist in the class. 
    @throws Exception if Financial aid amount is invalid,
                         Student is not a resident student.
    */
    public boolean setFinancialAid(Student student, float aid) throws Exception
    {
        int studentIndex = find(student);
        
        if (studentIndex == -1)
            return false;
        if (students[studentIndex] instanceof Resident)
        {
        	Resident resStudent = (Resident) students[studentIndex];
        	if (!resStudent.setFinancialAid(aid))
        		throw new Exception("Invalid amount.");
        }
        else
            throw new Exception("Not a resident student.");
        
        return true;
    }
}