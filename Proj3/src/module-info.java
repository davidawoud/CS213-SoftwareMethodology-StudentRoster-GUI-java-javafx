module Proj3 {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens student to javafx.graphics, javafx.fxml;
}
