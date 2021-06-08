/*
 *  Farai Malone Chawora 
    Student number 220145547
    my Filehandling class 

 */
package za.ac.cput.businesssystem;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
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
public class FileHandling {
       
    FileWriter fileWte;
    PrintWriter printWriter;
 
    FileInputStream fis;
    ObjectInputStream ois;
    
    public void openFile(String fileName)
    {
        try
        {
            fileWte = new FileWriter(new File(fileName));
            printWriter = new PrintWriter(fileWte);
            
            
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
                Logger.getLogger(FileHandling.class.getName()).log(Level.SEVERE, null, ex);
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
    
   
    private ArrayList<Supplier> suppliersList()
    {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        
        try
        {
            fis = new FileInputStream("stakeholder.ser");
            ois = new ObjectInputStream(fis);
            
           
            while (true)
            {
                Object obj = ois.readObject();
                
                if (obj instanceof Supplier)
                {
                    suppliers.add((Supplier) obj);
                }
            }
            
        } catch (EOFException eofe)
        {
            
        } catch (IOException | ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
            
        } finally
        {
            try
            {
                fis.close();
                ois.close();
                
            } catch (IOException e)
            {
                System.out.println( e.getMessage());
            }
        }
        
        
        if (!suppliers.isEmpty())
        {
            
            Collections.sort(
                    suppliers, 
                    (Supplier s1, Supplier s2) -> 
                            s1.getName().compareTo(s2.getName())
            );
        }
        
        return suppliers;
    }
    
      private void writeCustomerOutFile()
    {
        
        
        try
        {   
            printWriter.print("------------------------ CUSTOMERS -------------------------------------------------\n");
            printWriter.printf("%s\t%-10s\t%-10s\t%-20s\t%-10s\n","ID", "Name", "Surname", 
                    "Date Of Birth", "Age");
            printWriter.print("--------------------------------------------------------------------------------\n");
            
            for (int i = 0; i < customersList().size(); i++)
            {   
                printWriter.printf(
                       "%s\t%-10s\t%-10s\t%-20s\t%-10s\n",
                        customersList().get(i).getStHolderId(),
                        customersList().get(i).getFirstName(),
                        customersList().get(i).getSurName(),
                        reFormatDate(customersList().get(i).getDateOfBirth()),
                        calculateAge(customersList().get(i).getDateOfBirth())
                );
            }
            
            printWriter.print(
                    "\nNumber of customers who can rent:  "+ 
                    canRentCar());
            
            printWriter.printf(
                    "\nNumber of customers who cannot rent:  "+ 
                    canNotRentCar());
            
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    
    private void writeSupplierOutFile()
    {
        
        try
        {
            printWriter.print("-------------------------- Supliers----------------------\n");
            printWriter.printf("%s\t%-20s\t%-10s\t%-10s\n", "ID", "Name", "Prod Type",
                "Description");
            printWriter.print("------------------------------------------------------\n");
            for (int i = 0; i < suppliersList().size(); i++)
            {
                printWriter.printf(
                         "%s\t%-20s\t%-10s\t%-10s\n",
                        suppliersList().get(i).getStHolderId(),
                        suppliersList().get(i).getName(),
                        suppliersList().get(i).getProductType(),
                        suppliersList().get(i).getProductDescription()
                );
            }
            
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public void closeFile(String filename)
    {
        try
        {
            fileWte.close();
            printWriter.close();
           

        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }//end of class
    
  
    public static void main(String[] args)
    {
        FileHandling FH = new FileHandling();
        
        FH.openFile("customerOutFile.txt");
        FH.writeCustomerOutFile();
        FH.closeFile("customerOutFile.txt");
        FH.openFile("supplierOutFile.txt");
        FH.writeSupplierOutFile();
        FH.closeFile("supplierOutFile.txt");
    }
}//end of mainmethod
