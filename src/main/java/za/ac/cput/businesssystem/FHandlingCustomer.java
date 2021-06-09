/*
 *  Farai Malone Chawora 
    Student number 220145547
    my FilehandlingCustomer  class 

 */
package za.ac.cput.businesssystem;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FARAI SKOOL
 */
public class FHandlingCustomer {
       FileWriter printWriter;
    
 
    FileInputStream fis;
    ObjectInputStream ois;
    
    public void openFile()
    {
        try
        {
              printWriter = new FileWriter(new File("customerOutFile.txt"));
            
            
            
        } catch (IOException ioe)
        {
            System.out.println("file failed to open because"+ioe.getMessage());
            System.exit(1);
        }
    }
    
     private ArrayList<Customer> customersList()
    {
        ArrayList<Customer> customers = new ArrayList<>();
        
        try
        {
            fis = new FileInputStream("stakeholder.ser");
            ois = new ObjectInputStream(fis);
            
            
            while (true)
            {
                Object object = ois.readObject();
                
                if (object instanceof Customer)
                {
                    customers.add((Customer) object);
                }
            }
            
        } catch (EOFException eofe)
        {
            System.out.println(eofe.getMessage());
        } catch (IOException | ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
           System.exit(1);
            
        } finally
        {
            
            try {
                fis.close();
                ois.close();
                System.out.println("File Closed");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(FHandlingCustomer.class.getName()).log(Level.SEVERE, null, ex);
            }
                
           
        }
       
        if (!customers.isEmpty())
        {
            //sorting the arrray
            Collections.sort(customers,(Customer c1, Customer c2) -> c1.getStHolderId().compareTo(c2.getStHolderId())
            );
        }
        
        return customers;
    }
         private int canRentCar()
    {
        int canRent = 0;
        
        for (int i = 0; i < customersList().size(); i++)
        {
            if (customersList().get(i).getCanRent())
            {
                canRent += 1;
            }
        }
        
        return canRent;
    }
          
    private int canNotRentCar()
    {
        int canNotRent = 0;
        
        for (int i = 0; i < customersList().size(); i++)
        {
            
            if (!customersList().get(i).getCanRent())
            {
                canNotRent += 1;
            }
        }
        return canNotRent;
    }
        private void writeCustomerOutfile()
    {
        
        
        try
        {   
            String print="------------------------ CUSTOMERS -------------------------------------------------";
            String prin="ID\t Name\t Surname\t   Date Of Birth\t  Age\t ";
             String line="--------------------------------------------------------------------------------";
             printWriter.write(print);
             printWriter.write("\n");
             printWriter.write(prin);
             printWriter.write("\n");
             printWriter.write(line);
             printWriter.write("\n");
            for (int i = 0; i < customersList().size(); i++)
            {   
                printWriter.write(
                       
                        customersList().get(i).getStHolderId()+"\t"+
                        customersList().get(i).getFirstName()+"\t"+
                        customersList().get(i).getSurName()+"\t"+
                        reFormatDate(customersList().get(i).getDateOfBirth())+"\t"+
                        calculateAge(customersList().get(i).getDateOfBirth())+"\n"
                );
            }
            
            printWriter.write(
                    "\nNumber of customers who can rent:  "+ 
                    canRentCar());
            
            printWriter.write(
                    "\nNumber of customers who cannot rent:  "+ 
                    canNotRentCar());
            
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
        
        private String reFormatDate(String dob)
    {
       
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        LocalDate changeDob = LocalDate.parse(dob); 
        return changeDob.format(dtf);
    }
    
    private int calculateAge(String dob)
    {
        LocalDate changeDob = LocalDate.parse(dob); 
        int dobYear  = changeDob.getYear();
        
        ZonedDateTime currentDate = ZonedDateTime.now();
        int currentYear = currentDate.getYear();
        
       
        return currentYear - dobYear;
    }
    
         public void closeFile()
    {
        try
        {
            
            printWriter.close();
           

        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
          public static void main(String[] args)
    {
        FHandlingCustomer FH = new FHandlingCustomer();
        
        FH.openFile();
        FH.writeCustomerOutfile();
        FH.closeFile();
       // FH.openFile("supplierOutFile.txt");
        //FH.writeSupplierOutFile();
       // FH.closeFile("supplierOutFile.txt");
    }
}
