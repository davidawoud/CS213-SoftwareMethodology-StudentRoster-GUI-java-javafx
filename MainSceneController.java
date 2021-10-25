package application;

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

import javafx.event.ActionEvent;

public class MainSceneController 
{
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
		System.out.println("Start Program");
		// Set [Tristate] [New York] [Connecticut] [International] [Study Abroad] to disable
		tristateBox.setDisable(true);
		internationalCheckBox.setDisable(true);
		studyAbroadBox.setDisable(true);
		setStateStatus(stateCheckBox, true, false); 
		// set [PAYMENT DATE] to 1-1-2021 default value
		LocalDate date = LocalDate.of(2021, 1, 1);
		paymentDate.setValue(date); 
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
	
	Major getMajor() // gets the major of student
	{
		Major major = Major.CS; 
		if (csButton.isSelected()) 
			return Major.CS; 
		else if (eeButton.isSelected())
			return Major.EE; 
		else if (meButton.isSelected())
			return Major.ME; 
		else if (itButton.isSelected())
			return Major.IT; 
		else
			return Major.BA;
	}
	
	@FXML
    void add_a_student(ActionEvent event) // this is the event handler for adding a student
	{
		System.out.println("Add a student");
		// We take the input of nameTextBox, majorCheckBox, isResident, stateCheckBox, internationCheckBox, studyAbroadBox and creditSlider
		String name = nameTextBox.getText();
		
		Major major = getMajor(); 
		
		int creditHours = (int) creditSlider.getValue();
		// EXCEPTION HANDLING HERE FOR INTERNATIONAL STUDENTS MIN = 12
		//                             STUDY ABROAD STUDENTS MAX = 12
		
		if (residentButton.isSelected()) // student is a resident
		{
			
		}
		else // student is a nonresident
		{
			if (tristateBox.isSelected()) // student is a tristate student
			{
				if (nyButton.isSelected()) // student is from new york
				{
					
				}
				else // student is from connecticut
				{
					
				}
			}
			if (internationalCheckBox.isSelected()) // student is an international
			{
				if (studyAbroadBox.isSelected()) // student is study abroad
				{
					// SET STUDENT AS STUDY ABROAD
				}
			}
		}
		// Put the input into backend code
		// IF THERE ARE PROBLEMS WITH ANY OF THE VALUES, DUPLICATES, WE PRINT AN ERROR MESSAGE IN MESSAGEBOX
		// OTHERWISE, WE PRINT A MESSAGE THAT THE STUDENT IS SUCCESSFULLY ADDED
    }
	
    @FXML
    void remove_a_student(ActionEvent event) // this is the event handler for removing a student
    {
    	System.out.println("Remove a student");
    	
    	String name = nameTextBox.getText();
    	
		Major major = getMajor(); 

    	// DO BACKEND HERE
		// IF THERE ARE PROBLEMS WITH ANY OF THE VALUES, DUPLICATES, WE PRINT AN ERROR MESSAGE IN MESSAGEBOX
		// OTHERWISE, WE PRINT A MESSAGE THAT THE STUDENT IS SUCCESSFULLY REMOVED
    }
	
    @FXML
    void get_tuition_due(ActionEvent event) // this is the event handler for displaying the tuition due for a student
    {
    	// PRINTS OUT THE TUITION DUE IN [tuitionBox]
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
	private CheckBox tristateDiscountBox; // for setting tristate discount 
	@FXML
	private TextArea messageBoxTab2; // for printing out error and success messages
	
    @FXML
    void pay_tuition(ActionEvent event) // this is for paying tuition 
    {
    	String name = nameTab2.getText(); // name of the student

    	Major major = Major.CS; // student's major
		if (csButton2.isSelected()) 
			major = Major.CS;
		else if (eeButton2.isSelected())
			major = Major.EE;
		else if (meButton2.isSelected())
			major = Major.ME;
		else if (itButton2.isSelected())
			major = Major.IT;
		else
			major = Major.BA;
	
		String payment = paymentAmount.getText(); // payment amount
		// DO EXCEPTION HANDLING HERE TO MAKE SURE THAT IT IS A FLOAT, ROUND TO 2 DECIMALS AND NON-NEGATIVE

		int month = paymentDate.getValue().getMonthValue();
		int day = paymentDate.getValue().getDayOfMonth();
		int year = paymentDate.getValue().getYear();
		String date = month + "/" + day + "/" + year; // date of the student 
		
		// CALL THE METHOD TO PAY TUITION
		// IF TUITION WAS PAID 
		//		PRINT "{Name}, {Major} successfully paid ${payment} on {date} and owes ${tuition due}."
		// IF TUITION PAID > TUITION CODE
		//		PRINT "{Name}, {Major} owes $0.00."
    }
	
    @FXML
    void set_financial_aid(ActionEvent event) // this is for setting a student's financial aid amount
    {
    	String payment = paymentAmount.getText(); // payment amount
		// DO EXCEPTION HANDLING HERE TO MAKE SURE THAT IT IS A FLOAT, ROUND TO 2 DECIMALS AND NON-NEGATIVE
    	// ALSO EXCEPTION HANDLING IF THE STUDENT IS A RESIDENT, <10K, TRISTATE DISCOUNTS
    	// IF FINAINCIAL AID WAS SUCCESSFULL SET
    	//		PRINT "${aid} of financial aid was successfully awarded to {Name}, {Major}" IN [messageBoxTab2]
    	// OTHERWISE
    	//		PRINT "{Name}, {Major} is unable to receive financial aid." IN [messageBoxTab2]
    }
    
    @FXML
    void set_tristate_discount(ActionEvent event) // this is for setting a students tristate discount
    {
    	if (tristateDiscountBox.isSelected())
    	{
    		// CALL THE BACKEND METHOD THAT GIVES THEM A DISCOUNT
    		// IF DISCOUND WAS SUCCESSFULLY APPLIED
    		//		PRINT "A ${discount} discount was successfully applied to {Name}, {Major} IN [messageBoxTab2]
    		// IF STUDENT HAS A DISCOUNT ALREADY
    		//		PRINT "{Name}, {Major} already has a discount" IN [messageBoxTab2]
    		// IF THE STUDENT IS NOT A TRISTATE
    		//		PRINT "{Name}, {Major} is unable to receive the tristate discount" IN [messageBoxTab2]
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
	private TextField feedbackBox; // feedback box
	@FXML
	private Button sendButton; // end button for feedback box
	
    @FXML
    void print_list(ActionEvent event) 
    {
    	// PRINT STUDENT LIST IN [messageBoxTab3]
    }

    @FXML
    void print_by_name(ActionEvent event) 
    {
    	// PRINT STUDENT LIST BY NAME [messageBoxTab3]
    }
    
    @FXML
    void print_by_date(ActionEvent event) 
    {
    	// PRINT STUDENT DATE BY NAME [messageBoxTab3]
    }
    
    @FXML
    void calculate_all_tuition(ActionEvent event) 
    {
    	// CALCULATE ALL TUITION
    	// PRINT "Calculated all tuition." IN [messageBoxTab3]
    }
    
    @FXML
    void send_feedback(ActionEvent event)
    {
    	// we will clear the feedback box because we dont care what the user says
    	feedbackBox.clear();
    	// PRINT "Thank you for your feedback" IN [[messageBoxTab3]
    }
}
