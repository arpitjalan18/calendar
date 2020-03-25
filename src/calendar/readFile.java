package calendar;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.io.*; 
public class readFile
{
    public static ArrayList<appt> read(String file)
    {
        String csvFile = file;
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ",";
        int row = 0;
        ArrayList<appt> appointments = new ArrayList<appt>();
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {              	
                String[] rowsStart = line.split(csvSplitBy);
                LinkedList<String> elements = new LinkedList<String>();
                if (row > -1) {
	                for (int i =0; i < rowsStart.length; i++)
	                    elements.add(rowsStart[i]);
	                LocalDate date = LocalDate.of(Integer.parseInt(elements.get(0)), Integer.parseInt(elements.get(1)), Integer.parseInt(elements.get(2)));
	                if (elements.size() > 5) {
	                	LocalDate date2 = LocalDate.of(Integer.parseInt(elements.get(5)), Integer.parseInt(elements.get(6)), Integer.parseInt(elements.get(7)));
	                	appt Appointment =  new appt(date, date2, elements.get(3), elements.get(4), elements.get(8));
	                	appointments.add(Appointment);
	                }
	                else {
	                	appt Appointment =  new appt(date, elements.get(3), elements.get(4));
	                	appointments.add(Appointment);
	                }
	                
                }
                row++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return appointments;
    }
    public static ArrayList<Contact> read2(String file)
    {
        String csvFile = file;
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ",";
        int row = 0;
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
            	
                String[] rowsStart = line.split(csvSplitBy);
                LinkedList<String> elements = new LinkedList<String>();
                if (row > -1 && line.length()>10) {
	                for (int i =0; i < rowsStart.length; i++)
	                    elements.add(rowsStart[i]);               
	              	Contact ct =  new Contact(elements.get(0), elements.get(1), elements.get(2), elements.get(3), elements.get(4));
	                contacts.add(ct);              
                }
                row++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return contacts;
    }
    public static void save(ArrayList<appt> appts) {
    	FileWriter csvWriter = null;
        try{
            csvWriter = new FileWriter("appts.csv");

            for (appt rowData : appts) {
                csvWriter.append(rowData.toAppend());
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    	
    }
    public static void save2(ArrayList<Contact> contacts) {
    	FileWriter csvWriter = null;
        try{
            csvWriter = new FileWriter("contacts.csv");

            for (Contact rowData : contacts) {
                csvWriter.append(rowData.toAppend());
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    	
    }
    
    public static void delete(){
        File file = new File("C:/Users/arpit/coding/twiet/driverData2.csv");
        file.delete();
    }
}
