package calendar;
 

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
 
public class Main extends Application {
 
    private Stage stage;
    private ArrayList<appt> appts = new ArrayList<appt>(); 
    private ArrayList<Contact> contacts = new ArrayList<Contact>();
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }
 
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        appts = readFile.read("appts.csv");
        contacts = readFile.read2("contacts.csv");
        stage.setTitle("Digital Diary");
        initUI2();
        stage.show();
    }
    private void initUI2() {
    	
        VBox vbox = new VBox(20);
        vbox.setStyle("-fx-padding: 10;");
        Scene scene = new Scene(vbox, 500, 1000);
        scene.getStylesheets().add("Apollo3.css");
        stage.setScene(scene);

        Label upcoming = new Label("Upcoming Appointments/Projects");
    	vbox.getChildren().add(upcoming);
        
    	
        ListView<appt> listView = new ListView<appt>();
        Collections.sort(appts, (x, y) -> x.startDate.compareTo(y.startDate));
        for (int i = 0; i < appts.size(); i++) {
        	listView.getItems().add(appts.get(i));
        	 if (appts.get(i).startDate == LocalDate.now()) {
        		// listView.getItems().
        	 }
        }
        listView.setCellFactory(new Callback<ListView<appt>, ListCell<appt>>() {
            @Override
            public ListCell<appt> call(ListView<appt> param) {
                return new ColoredCell();
            }
        });
        listView.getStyleClass().add("textfield");
        
       
    	vbox.getChildren().add(listView);
    	listView.setOnMouseClicked(new EventHandler<MouseEvent>(){
    		 
            @Override
            public void handle(MouseEvent arg0) {
               
                editAppt(listView.getSelectionModel().getSelectedItem());
            }
   
        });
    	
       
    	Button addA = new Button("Make Appointment");
    	addA.setOnMouseClicked(e ->
    	{
    		createAppt();
    		
    	});
    	vbox.getChildren().add(addA);
    	
    	//do the same thing but for contacts

        Label cts = new Label("Contacts:");
      	vbox.getChildren().add(cts);
          
    	 ListView<Contact> listViewC = new ListView<Contact>();
    	 Collections.sort(contacts, (x, y) -> x.lastName.compareTo(y.lastName));
         for (int i = 0; i < contacts.size(); i++)
         	listViewC.getItems().add(contacts.get(i));
         listViewC.getStyleClass().add("textfield");
     	vbox.getChildren().add(listViewC);
     	listViewC.setOnMouseClicked(new EventHandler<MouseEvent>(){
     		 
             @Override
             public void handle(MouseEvent arg0) {
                
                 editContact(listViewC.getSelectionModel().getSelectedItem());
             }
    
         });
     	
         
     	Button addB = new Button("Add New Contact");
     	addB.setOnMouseClicked(e ->
     	{
     		initUICreateContact();
     		
     	});
     	vbox.getChildren().add(addB);
    	
    }
    static class ColoredCell extends ListCell<appt>{
        @Override
        public void updateItem(appt ap, boolean empty) {
            super.updateItem(ap, empty);
         
            if (ap == null) {
            	this.setTextFill(Color.BLACK);
            }
            else if (ap.startDate.equals(LocalDate.now())){
            	this.setText(ap.toString());
                this.setTextFill(Color.RED);
            }
            else if(ap.endDate != null && ap.endDate.equals(LocalDate.now())){
            	this.setText(ap.toString());
                this.setTextFill(Color.RED);
            }
            else{
            	this.setText(ap.toString());
                this.setTextFill(Color.BLACK);
            }
            
        }
    }
    public void createAppt() {
    	initUICreate();
    }
    private void initUICreate() {
    	Stage window = new Stage();
		window.setTitle("Make a New Appointment");
		
        VBox vbox = new VBox(20);
        vbox.setStyle("-fx-padding: 10;");
        Scene scene = new Scene(vbox, 400, 400);
        scene.getStylesheets().add("Apollo3.css");
        window.setScene(scene);
        
        TextField desc = new TextField();
        desc.setPromptText("Describe your event");
        desc.getStyleClass().add("textfield");
        vbox.getChildren().add(desc);
    
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Enter the date of the event");
        datePicker.setPrefWidth(400);
        datePicker.getStyleClass().add("textfield");
        vbox.getChildren().add(datePicker);
        datePicker.setOnAction(e -> 
        {     
        	 LocalDate date = datePicker.getValue();
             System.err.println("Selected date: " + date);
        });
        
        TextField time = new TextField();
        time.setPromptText("Enter the time of the event");
        time.getStyleClass().add("textfield");
        vbox.getChildren().add(time);
         
        DatePicker datePicker2 = new DatePicker();
        datePicker2.getStyleClass().add("textfield");
        datePicker2.setPromptText("Enter the end date if applicable");
        datePicker2.setPrefWidth(400);
        vbox.getChildren().add(datePicker2);
        datePicker2.setOnAction(e -> 
        {     
        	 LocalDate date = datePicker.getValue();
             System.err.println("Selected date: " + date);
        });
        
        TextField time2 = new TextField();
        time2.setPromptText("Enter the end time if applicable");
        time2.getStyleClass().add("textfield");
        vbox.getChildren().add(time2);
        
        Button closeButton = new Button("Cancel New Appointment");
        Button applyButton = new Button("Make New Appointment");
        closeButton.setOnAction(e-> window.close());
        applyButton.setOnAction(e->{
                if (hasValue(time.getText())  && hasValue(desc.getText()) && hasValue(time2.getText())){
         
                    appts.add(new appt(datePicker.getValue(), datePicker2.getValue(), desc.getText(), time.getText(), time2.getText()));
                    readFile.save(appts);
	              	  window.close();
	              	  initUI2();
                }
                else if(hasValue(time.getText()) && hasValue(desc.getText())) {
                	  appts.add(new appt(datePicker.getValue(),  desc.getText(), time.getText()));
                	  readFile.save(appts);
                	  window.close();
                	  initUI2();
                }
                else{
                	window.close();
                }
            });
        
        vbox.getChildren().add(applyButton);
        vbox.getChildren().add(closeButton);
        window.show();
    }
    public void editAppt(appt appt) {
    	Stage window = new Stage();
		window.setTitle("Make a New Appointment");
		
        VBox vbox = new VBox(20);
        vbox.setStyle("-fx-padding: 10;");
        Scene scene = new Scene(vbox, 400, 400);
        scene.getStylesheets().add("Apollo3.css");
        window.setScene(scene);
        
        TextField desc = new TextField(appt.desc);
        desc.getStyleClass().add("textfield");
        vbox.getChildren().add(desc);
    
        DatePicker datePicker = new DatePicker(appt.startDate);
        datePicker.setPrefWidth(400);
        datePicker.getStyleClass().add("textfield");
        vbox.getChildren().add(datePicker);
        datePicker.setOnAction(e -> 
        {     
        	 LocalDate date = datePicker.getValue();
             System.err.println("Selected date: " + date);
        });
        
        TextField time = new TextField(appt.timeStart);
        time.getStyleClass().add("textfield");
        vbox.getChildren().add(time);
         
        DatePicker datePicker2 = new DatePicker();
        if (appt.endDate == null) {
        	datePicker2.setPromptText("Enter the end date if applicable");
        }
        else {
        	datePicker2.setValue(appt.endDate);
        }
        datePicker2.getStyleClass().add("textfield");
        datePicker2.setPrefWidth(400);
        vbox.getChildren().add(datePicker2);
        datePicker2.setOnAction(e -> 
        {     
        	 LocalDate date = datePicker.getValue();
             System.err.println("Selected date: " + date);
        });
        
        TextField time2 = new TextField();
        if (appt.timeEnd == null) {
        	time2.setPromptText("Enter the start time if applicable");
        }
        else {
        	 time2.setText(appt.timeEnd);
        }
        time2.getStyleClass().add("textfield");
        vbox.getChildren().add(time2);
        
        Button closeButton = new Button("Delete Appointment");
        Button applyButton = new Button("Done");
        applyButton.setOnAction(e->{
                if (hasValue(time.getText())  && hasValue(desc.getText()) && hasValue(time2.getText())){
                	
                	appts.remove(appt);
                    appts.add(new appt(datePicker.getValue(), datePicker2.getValue(), desc.getText(), time.getText(), time2.getText()));
                    readFile.save(appts);
	              	  window.close();
	              	  initUI2();
                }
                else if(hasValue(time.getText()) && hasValue(desc.getText())) {
                	  appts.remove(appt);
                	  appts.add(new appt(datePicker.getValue(),  desc.getText(), time.getText()));
                	  readFile.save(appts);
                	  window.close();
                	  initUI2();
                }
                else{
                	window.close();
                }
            });
        closeButton.setOnAction(e->{
        	appts.remove(appt);
        	readFile.save(appts);
        	initUI2();
        	window.close();
        });
        vbox.getChildren().add(applyButton);
        vbox.getChildren().add(closeButton);
        window.show();
    	
    }
    public void initUICreateContact() {
    	Stage window = new Stage();
		window.setTitle("Make a New Contact");
		
        VBox vbox = new VBox(20);
        vbox.setStyle("-fx-padding: 10;");
        Scene scene = new Scene(vbox, 400, 400);
        scene.getStylesheets().add("Apollo3.css");
        window.setScene(scene);
       
        TextField fn = new TextField();
        fn.setPromptText("First Name");
        fn.getStyleClass().add("textfield");
        vbox.getChildren().add(fn);
        
        TextField ln = new TextField();
        ln.setPromptText("Last Name");
        ln.getStyleClass().add("textfield");
        vbox.getChildren().add(ln);
        
        TextField cm = new TextField();
        cm.setPromptText("Company Name");
        cm.getStyleClass().add("textfield");
        vbox.getChildren().add(cm);
    
        TextField num = new TextField();
        num.setPromptText("Phone Number");
        num.getStyleClass().add("textfield");
        vbox.getChildren().add(num);
        
        TextField email = new TextField();
        email.setPromptText("Email");
        email.getStyleClass().add("textfield");
        vbox.getChildren().add(email);
       
        Button closeButton = new Button("Cancel");
        Button applyButton = new Button("Done");
        closeButton.setOnAction(e-> window.close());
        applyButton.setOnAction(e->{
                if (hasValue(fn.getText()) && hasValue(ln.getText()) && hasValue(cm.getText()) && hasValue(email.getText()) && hasValue(num.getText())){
                	
                    contacts.add(new Contact(fn.getText(), ln.getText(), cm.getText(), num.getText(), email.getText()));
                    readFile.save2(contacts);
	              	  window.close();
	              	  initUI2();
                }
               
                else{
                	window.close();
                }
            });
        vbox.getChildren().add(applyButton);
        vbox.getChildren().add(closeButton);
        window.show();
    	
    }
    public void editContact(Contact ct) {
    	Stage window = new Stage();
		window.setTitle("Make a New Contact");
		
        VBox vbox = new VBox(20);
        vbox.setStyle("-fx-padding: 10;");
        Scene scene = new Scene(vbox, 400, 400);
        scene.getStylesheets().add("Apollo3.css");
        window.setScene(scene);
       
        TextField fn = new TextField(ct.firstName);
        fn.getStyleClass().add("textfield");
        vbox.getChildren().add(fn);
        
        TextField ln = new TextField(ct.lastName);
        ln.getStyleClass().add("textfield");
        vbox.getChildren().add(ln);
        
        TextField cm = new TextField(ct.company);
        cm.getStyleClass().add("textfield");
        vbox.getChildren().add(cm);
    
        TextField num = new TextField(ct.phone);
        num.getStyleClass().add("textfield");
        vbox.getChildren().add(num);
        
        TextField email = new TextField(ct.email);
        email.getStyleClass().add("textfield");
        vbox.getChildren().add(email);
       
        Button closeButton = new Button("Delete Contact");
        Button applyButton = new Button("Done");
        closeButton.setOnAction(e-> window.close());
        applyButton.setOnAction(e->{
                if (hasValue(fn.getText()) && hasValue(ln.getText()) && hasValue(cm.getText()) && hasValue(email.getText()) && hasValue(num.getText())){
                	
                	contacts.remove(ct);
                    contacts.add(new Contact(fn.getText(), ln.getText(), cm.getText(), num.getText(), email.getText()));
                    readFile.save2(contacts);
	              	  window.close();
	              	  initUI2();
                }
               
                else{
                	window.close();
                }
            });
        closeButton.setOnAction(e->{
        	contacts.remove(ct);
        	readFile.save2(contacts);
        	initUI2();
        	window.close();
        });
        vbox.getChildren().add(applyButton);
        vbox.getChildren().add(closeButton);
        window.show();
    	
    	
    }
    public boolean hasValue(String str){
        if (str.length() > 0)
            return true;
        else
            return false;
    }
}  