package student;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.InputMismatchException;

import javafx.event.ActionEvent;

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
	
	// this method sets the status of the states to either enabled/disabled and selected/unselected
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
	
	@FXML
	void non_Resident_Event(ActionEvent event) // this enables tristate and international
	{
		tristateBox.setDisable(false);
		internationalCheckBox.setDisable(false);
	}
	
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
	}
	
	@FXML
	void tristate_Event(ActionEvent event) // this disables and deselects study abroad and enables states with connecticut as default state
	{
		studyAbroadBox.setDisable(true);
		studyAbroadBox.setSelected(false);
		setStateStatus(stateCheckBox, false, true); 
	}
	
	@FXML
	void international_Event(ActionEvent event) // this enables study abroad and disables+deselects state
	{
		studyAbroadBox.setDisable(false);
		setStateStatus(stateCheckBox, true, false); 
	}
	
	@FXML
	void studyAbroad_Event(ActionEvent event) // this  disables credit hours
	{
		if (creditSlider.isDisabled())
		    creditSlider.setDisable(false);
		else
			creditSlider.setDisable(true);
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
    
    
	// >>>>>THIRD TAB<<<<<
	/*
	 * [Print] gives the user an option on what types of stuff to print
	 */
	@FXML
	private MenuItem printList, printDate, printName; // printing the list of all students
	@FXML
	private TextArea messageBoxTab3; // for printing out list of students
	
    @FXML
    void print_list(ActionEvent event) 
    {
    	messageBoxTab3.appendText(studentRoster.print());
    }

    @FXML
    void print_by_name(ActionEvent event) 
    {
    	messageBoxTab3.appendText(studentRoster.printByStudentNames());
    }
    
    @FXML
    void print_by_date(ActionEvent event) 
    {
    	messageBoxTab3.appendText(studentRoster.printByPaymentDate());
    }
    
    @FXML
    void calculate_all_tuition(ActionEvent event) 
    {
    	studentRoster.calculateTuition();
    	messageBoxTab3.appendText("Calculation completed.\n");
    }
}