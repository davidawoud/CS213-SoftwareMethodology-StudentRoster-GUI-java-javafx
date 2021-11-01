package student;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import java.time.LocalDate;

import javafx.event.ActionEvent;

/**
This class is the client driver class that takes in user-inputted information
and does functions with them using the Roster class
@author David Halim, Stephen Juan
*/
public class TuitionManagerController 
{
	private Roster studentRoster;
	
	// >>>>>FIRST TAB<<<<<
	/*
	 * Everything in status is greyed out except for [Resident] and [NonResident]
	 * After user clicks one of [Resident] or [NonResident], [Tristate] and [International] becomes able to be clicked
	 * If the user clicks [Tristate], [New York] and [Connecticut] becomes able to be clicked and [International] becomes greyed out
	 * If the user clicks [International], [Tristate] becomes greyed out and [Study Abroad] becomes able to be clicked. 
	 * 
	 * The user will drag the slider to set the number of credits that will range from to 24
	 * If the student is international, any credits under 12 will display a message that will say invalid 
	 * If the student is study abroad, an credits over 12 will display a message that will say invalid
	 * 
	 * [Add Student] should only add a student if all properties are valid, otherwise display a message in the text box
	 * [Remove Student] should only remove a student if all properties are valid, otherwise display a message in the text box
	 * [Tuition Due] should only print something if the student exists
	 * 
	 * The giant rectable text box should display error messages, successful add/remove students
	 */
	@FXML
	private TextField nameTextBox; // for inputting name
	@FXML
	private ToggleGroup majorCheckBox; // for inputting major {radio buttons}
	@FXML
	private RadioButton csButton, eeButton, meButton, itButton, baButton;
	@FXML
	private ToggleGroup isResident; // for inputting if the student is a resident
	@FXML
	private RadioButton residentButton, nonResidentButton; 
	@FXML
	private RadioButton tristateBox; // for checking if the check box is open
	@FXML
	private ToggleGroup stateCheckBox; // for inputting state {radio buttons} 
	@FXML
	private RadioButton nyButton, ctButton; 
	@FXML
	private RadioButton internationalCheckBox; // for inputting an international student
	@FXML
	private ToggleGroup nonResidentType; // tristate or international
	@FXML
	private CheckBox studyAbroadBox; // for setting an international student to studyAbroad
	@FXML
	private Slider creditSlider; // for setting a students credit amount
	@FXML
	private Button addButton; // for adding a student
	@FXML
	private Button removeButton; // for removing a student
	@FXML
	private TextArea tuitionBox; // for displaying how msuch tuition a student owes
	@FXML
	private TextArea messageBox; // for displaying text messages

	/**
	This method automatically runs when the GUI is opened. It disables certain fields and creates
	an instance of Roster.
	*/
	public void initialize()
	{
		// Set [Tristate] [New York] [Connecticut] [International] [Study Abroad] to disable
		tristateBox.setDisable(true);
		internationalCheckBox.setDisable(true);
		studyAbroadBox.setDisable(true);
		setStateStatus(stateCheckBox, true, false); 
		// set [PAYMENT DATE] to 1-1-2021 default value
		LocalDate date = LocalDate.of(2021, 1, 1);
		paymentDate.setValue(date); 
		// disable [paymentButton], [financialAid], [setButton], [tristateDiscountBox], [tristateSetButton]
		studentRoster = new Roster();
	}
	
	/**
	This method is a helper method that either disables/enables, selects/deselects the radio buttons
	in NY or CT.
	@param stateCheckBox - the toggle group that contains NY or CT
	@param disable - boolean that is passed to disable/enable radio buttons
	@param selected - boolean that is passed to select/deselect radio buttons
	*/
	private void setStateStatus(ToggleGroup stateCheckBox, boolean disable, boolean selected)
	{
		stateCheckBox.getToggles().forEach(toggle -> 
		{
			Node node = (Node) toggle; 
			node.setDisable(disable);
		});
		stateCheckBox.getToggles().forEach(toggle -> 
		{
			Node node = (Node) toggle; 
			((RadioButton) node).setSelected(selected); 
		});
	}
	
	 /**
	This is an event handler that enables tristate and international when nonresident is selected.
	@param event - when the user clicks nonresident
	*/
	@FXML
	void non_Resident_Event(ActionEvent event) // this enables tristate and international
	{
		tristateBox.setDisable(false);
		internationalCheckBox.setDisable(false);
		creditSlider.setDisable(false);
	}
	
	/**
	This is an event that disables everything that isn't applicable to resident when it is selected.
	@param event - when user clicks on resident
	*/
	@FXML
	void resident_Event(ActionEvent event) // this disables and deselects tristate, international, states
	{
		tristateBox.setDisable(true);
		tristateBox.setSelected(false); 
		internationalCheckBox.setDisable(true);
		internationalCheckBox.setSelected(false); 
		studyAbroadBox.setDisable(true);
		studyAbroadBox.setSelected(false);
		setStateStatus(stateCheckBox, true, false); 
		creditSlider.setDisable(false);
	}
	
	/**
	This is an event handler that enables the state options when tristate is selected.
	@param event - when user clicks on tristate
	*/
	@FXML
	void tristate_Event(ActionEvent event) // this disables and deselects study abroad and enables states with connecticut as default state
	{
		studyAbroadBox.setDisable(true);
		studyAbroadBox.setSelected(false);
		setStateStatus(stateCheckBox, false, true); 
		creditSlider.setDisable(false);
	}
	
	/**
	This is an event handler that enables study abroad for students.
	@param event - when user clicks on international
	*/
	@FXML
	void international_Event(ActionEvent event) // this enables study abroad and disables+deselects state
	{
		studyAbroadBox.setDisable(false);
		setStateStatus(stateCheckBox, true, false); 
	}
	
	/**
	This is an event handler that enables/disable creditSlider according to studyAbroad state.
	@param event - when user clicks on studyAbroad
	*/
	@FXML
	void studyAbroad_Event(ActionEvent event) // this  disables credit hours
	{
		if (studyAbroadBox.isSelected())
		    creditSlider.setDisable(true);
		else
			creditSlider.setDisable(false);
	}
	
	/**
	Gets the name entered by the user in Tab 1
	@return name of the student
	@throws Exception if the no input was provided by the user
	*/
	String getNameTab1() throws Exception
	{
		String name = nameTextBox.getText();
		if (name == null || name.equals(""))
			throw new Exception("Name is missing.\n");
		else
			return name;
	}
	
	/**
	Gets the major selected by the user in Tab 1
	@return major of the student
	@throws Exception if the no input was provided by the user
	*/
	Major getMajorTab1() throws Exception // gets the major of student
	{
		Major major;
		if (csButton.isSelected())
		{
			major = Major.CS;
			return major;
		} 
		else if (eeButton.isSelected())
		{
			major = Major.EE;
			return major;
		} 
		else if (meButton.isSelected())
		{
			major = Major.ME;
			return major;
		} 
		else if (itButton.isSelected())
		{
			major = Major.IT;
			return major;
		} 
		else if (baButton.isSelected())
		{
			major = Major.BA;
			return major;
		} 
		else
			throw new Exception("Major is missing.\n");
	}
	
	/**
	Gets the student to be processed in Tab 1
	@return student to be processed
	*/
	Student getStudentTab1()
	{
		String name;
		Major major;
		int creditHours;
		
		try
		{
			name = getNameTab1();
			major = getMajorTab1();
			creditHours = (int) creditSlider.getValue();
		}
		catch(Exception e)
		{
			messageBox.appendText(e.getMessage());
			return null;
		}
		
		Student student = null;
		
		try
		{
			if (residentButton.isSelected()) // student is a resident
			{
			    student = new Resident(new Profile(name,major.toString()),creditHours);
			}
			else if (nonResidentButton.isSelected())
			{
				if (tristateBox.isSelected()) // student is a tristate student
				{
					if (nyButton.isSelected()) // student is from new york
					{
						student = new Tristate(new Profile(name,major.toString()),
								                                creditHours,"NY");
					}
					else // student is from connecticut
					{
						student = new Tristate(new Profile(name,major.toString()),
								                                creditHours,"CT");
					}
						
				}
				else if (internationalCheckBox.isSelected()) // student is an international
				{
					boolean studyAbroad = false;
					if (studyAbroadBox.isSelected()) // student is study abroad
					{
						studyAbroad = true;
						creditHours = 12;
						messageBox.appendText("Study Abroad Student automatically gets 12 credits.\n");
					}
					student = new International(new Profile(name,major.toString()),
							                              creditHours,studyAbroad);	
				}
				else
				{
					student = new NonResident(new Profile(name,major.toString()),creditHours);
				}
			}
			else
			{
				messageBox.appendText("Student type is missing.\n");
				return null;
			}
		}
		catch (Exception e)
		{
			messageBox.appendText(e.getMessage() + "\n");
			return null;
		}
		
		return student;
	}
	
	/**
	This button creates an instance of student with the selected attributes. Also checks exceptions for
	invalid/missing data.
	@param event - when user clicks Add Student
	*/
	@FXML
    void add_a_student(ActionEvent event) // this is the event handler for adding a student
	{
		Student student = getStudentTab1();
		
		if (student != null)
		{
			if (studentRoster.add(student)) 
            	messageBox.appendText("Student added.\n"); 
            else
            	messageBox.appendText("Student is already in the roster.\n");
		}
    }
	
	/**
	This button removes an instance of student with the selected attributes. Also checks exceptions for
	invalid/missing data and if the student exists or not.
	@param event - when user clicks Remove Student
	*/
    @FXML
    void remove_a_student(ActionEvent event) // this is the event handler for removing a student
    {	
    	String name;
		Major major;
		
		try
		{
			name = getNameTab1();
			major = getMajorTab1();
		}
		catch(Exception e)
		{
			messageBox.appendText(e.getMessage());
			return;
		}
		
		Student student = null;
		
		try
		{
			student = new Student(new Profile(name,major.toString()));
		}
		catch (Exception e)
		{
			messageBox.appendText(e.getMessage() + "\n");
			return;
		}
		
		if (student != null)
		{
			if (studentRoster.remove(student)) 
            	messageBox.appendText("Student removed from the roster.\n"); 
            else
            	messageBox.appendText("Student is not in the roster.\n");
		}
    }
	
    /**
    This method gets the tuition due for a student.
    @param event - when user clicks on Tuition Due
    */
    @FXML
    void get_tuition_due(ActionEvent event) // this is the event handler for displaying the tuition due for a student
    {
    	Student student = getStudentTab1();
    	if (student != null)
		{
    		String tuition = String.valueOf(student.getTuitionDue());
    		tuitionBox.setText(tuition);
		}
    }
	
	
	// >>>>>SECOND TAB<<<<<
	/*
	 * [Pay] should only register if:
	 * 		The payment is non-negative and is less than the total amount due
	 * 		The date is in the year 2021 and not in the future
	 * Otherwise display an error message
	 * 
	 * [Set] should only set if the amount is valid and the student is a resident, otherwise display error message
	 * 
	 * The message box will print out error messages and success messages for student payments and financial aid
	 */
	@FXML
	private TextField nameTab2; // for inputting name in the second tab
	@FXML
	private ToggleGroup paymentsMajorCheck; // for choosing the student major in the second tab
	@FXML
	private RadioButton csButton2, eeButton2, meButton2, itButton2, baButton2; // majors
	@FXML
	private Button studyAbroad; // for setting studyAbroad
	@FXML
	private TextField paymentAmount; // for inputting payment amount
	@FXML
	private DatePicker paymentDate; // for setting the payment date
	@FXML
	private Button paymentButton; // for paying tuition
	@FXML
	private TextField financialAid; // for inputting financial aid
	@FXML
	private Button setButton; // for setting the financial aid
	@FXML
	private TextArea messageBoxTab2; // for printing out error and success messages
	
	/**
	Gets the name entered by the user in Tab 2
	@return name of the student
	@throws Exception if the no input was provided by the user
	*/
	String getNameTab2() throws Exception
	{
		String name = nameTab2.getText();
		if (name == null || name.equals(""))
			throw new Exception("Name is missing.\n");
		else
			return name;
	}
	
	/**
	Gets the major selected by the user in Tab 2
	@return major of the student
	@throws Exception if the no input was provided by the user
	*/
	Major getMajorTab2() throws Exception // gets the major of student
	{
		Major major;
		if (csButton2.isSelected())
		{
			major = Major.CS;
			return major;
		} 
		else if (eeButton2.isSelected())
		{
			major = Major.EE;
			return major;
		} 
		else if (meButton2.isSelected())
		{
			major = Major.ME;
			return major;
		} 
		else if (itButton2.isSelected())
		{
			major = Major.IT;
			return major;
		} 
		else if (baButton2.isSelected())
		{
			major = Major.BA;
			return major;
		} 
		else
			throw new Exception("Major is missing.\n");
	}
	
	/**
	Gets the payment entered by the user in Tab 2
	@return payment of the student
	@throws Exception if the no input was provided by the user
	*/
	float getPaymentTab2() throws Exception
	{
		String paymentStr = paymentAmount.getText();
		if (paymentStr == null || paymentStr.equals(""))
			throw new Exception("Payment is missing.\n");
		else
		{
			float payment = Float.parseFloat(paymentStr);
			return payment;
		}
	}
	
	/**
	Gets the financial aid entered by the user in Tab 2
	@return financialAidf of the student
	@throws Exception if the no input was provided by the user
	*/
	float getFinancialAidTab2() throws Exception
	{
		String financialAidStr = financialAid.getText();
		if (financialAidStr == null || financialAidStr.equals(""))
			throw new Exception("Financial Aid is missing.\n");
		else
		{
			float financialAidf = Float.parseFloat(financialAidStr);
			return financialAidf;
		}
	}
	
	/**
	This button takes in the student name/major and get the payment and date to pay their tuition. It
	also checks for invalid dates and amounts.
	@param event - when user clicks on Pay
	*/
    @FXML
    void pay_tuition(ActionEvent event) // this is for paying tuition 
    {
	
    	String name;
		Major major;
		float payment;
		
		try
		{
			name = getNameTab2();
			major = getMajorTab2();
			payment = getPaymentTab2();
		}
		catch(NumberFormatException e)
		{
			messageBoxTab2.appendText("Invalid payment, please enter a float.\n");
			return;
		}
		catch(Exception e)
		{
			messageBoxTab2.appendText(e.getMessage());
			return;
		}
		
		if (payment <= 0)
		{
			messageBoxTab2.appendText("Invalid amount.\n");
			return;
		}
		
		int month = paymentDate.getValue().getMonthValue();
		int day = paymentDate.getValue().getDayOfMonth();
		int year = paymentDate.getValue().getYear();
		String date = month + "/" + day + "/" + year; // date of the student 
		Date paymentDate = null;
		
        Student student = null;
		
		try
		{
			student = new Student(new Profile(name,major.toString()));
			paymentDate = new Date(date);
		}
		catch (Exception e)
		{
			messageBoxTab2.appendText(e.getMessage() + "\n");
			return;
		}
		
		
		try
		{
			studentRoster.payTuition(student,paymentDate,payment);
			messageBoxTab2.appendText("Payment applied.\n");
		}
		catch (Exception e)
		{
			messageBoxTab2.appendText(e.getMessage() + "\n");
			return;
		}
    }
	
    /**
    This method sets the financial aid for resident students. It checks if the amount is valid
    and if they have received financial aid before.
    @param event - when user clicks on Set
    */
    @FXML
    void set_financial_aid(ActionEvent event) // this is for setting a student's financial aid amount
    {
    	String name;
		Major major;
		float financialAidf;
		
		try
		{
			name = getNameTab2();
			major = getMajorTab2();
			financialAidf = getFinancialAidTab2();
		}
		catch(NumberFormatException e)
		{
			messageBoxTab2.appendText("Invalid financial aid, please enter a float.\n");
			return;
		}
		catch(Exception e)
		{
			messageBoxTab2.appendText(e.getMessage());
			e.printStackTrace();
			return;
		}
		
		Student student = null;
		
		try
		{
			student = new Student(new Profile(name,major.toString()));
		}
		catch (Exception e)
		{
			messageBoxTab2.appendText(e.getMessage() + "\n");
			return;
		}
		
		try
		{
			if (studentRoster.setFinancialAid(student, financialAidf))
				messageBoxTab2.appendText("Tuition updated.\n"); 
            else 
            	messageBoxTab2.appendText("Student not in the roster.\n");
		}
		catch (Exception e)
		{
			messageBoxTab2.appendText(e.getMessage() + "\n");
			return;
		}
    }
    
    /**
	This button takes allows international student to enroll in the Study Abroad program
	@param event - when user clicks on Enroll in Study Abroad Program
	*/
    @FXML
    void setStudyAbroad(ActionEvent event)
    {
	
    	String name;
		Major major;
		
		try
		{
			name = getNameTab2();
			major = getMajorTab2();
		}
		catch(NumberFormatException e)
		{
			messageBoxTab2.appendText("Invalid financial aid, please enter a float.\n");
			return;
		}
		catch(Exception e)
		{
			messageBoxTab2.appendText(e.getMessage());
			e.printStackTrace();
			return;
		}
		
        Student student = null;
		
		try
		{
			student = new Student(new Profile(name,major.toString()));
		}
		catch (Exception e)
		{
			messageBoxTab2.appendText(e.getMessage() + "\n");
			return;
		}
		
		try
		{
			if (studentRoster.setStudyAbroad(student, true))
				messageBoxTab2.appendText("Tuition updated.\n"); 
            else 
            	messageBoxTab2.appendText("Couldn't find the international student.\n");
		}
		catch (Exception e)
		{
			messageBoxTab2.appendText(e.getMessage() + "\n");
			return;
		}
    }
    
    
	// >>>>>THIRD TAB<<<<<
	/*
	 * [Print] gives the user an option on what types of stuff to print
	 */
	@FXML
	private MenuItem printList, printDate, printName; // printing the list of all students
	@FXML
	private TextArea messageBoxTab3; // for printing out list of students
	
	/**
	This method prints the list of students in any order.
	@param event - when user clicks on Print List
	*/
    @FXML
    void print_list(ActionEvent event) 
    {
    	messageBoxTab3.appendText(studentRoster.print());
    }
    
    /**
    This method prints the list of students by name in alphabetical order.
    @param event - when user clicks on Print by Name
    */
    @FXML
    void print_by_name(ActionEvent event) 
    {
    	messageBoxTab3.appendText(studentRoster.printByStudentNames());
    }
    
    /**
    This method prints the list of students by payment date.
    @param event - when user clicks on Print by Date
    */
    @FXML
    void print_by_date(ActionEvent event) 
    {
    	messageBoxTab3.appendText(studentRoster.printByPaymentDate());
    }
    
    /**
    This method calculates tuition dues for all students.
    @param event - when user clicks on Calculate Tuition
    */
    @FXML
    void calculate_all_tuition(ActionEvent event) 
    {
    	studentRoster.calculateTuition();
    	messageBoxTab3.appendText("Calculation completed.\n");
    }
}