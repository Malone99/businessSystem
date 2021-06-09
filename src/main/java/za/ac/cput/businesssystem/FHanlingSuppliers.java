/*
 *  Farai Malone Chawora 
    Student number 220145547
    my FilehandlingSupliers class 

 */
package za.ac.cput.businesssystem;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author FARAI SKOOL
 */
public class FHanlingSuppliers {
           FileWriter printWriter;

    FileInputStream fis;
    ObjectInputStream ois;
    
    public void openFile()
    {
        try
        {
              printWriter = new FileWriter(new File("suplierOutFile.txt"));
            
            
            
        } catch (IOException ioe)
        {
            System.out.println("file failed to open because"+ioe.getMessage());
            System.exit(1);
        }
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
                Object object = ois.readObject();
                
                if (object instanceof Supplier)
                {
                    suppliers.add((Supplier) object);
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
            
            Collections.sort(suppliers, (Supplier s1, Supplier s2) -> s1.getName().compareTo(s2.getName())
            );
        }
        
        return suppliers;
    }
     
       private void writeSupplierOutFile()
    {
        
        try
        {
            printWriter.write("----------------------------------------------- Supliers--------------------------------------------------------------------\n");
            printWriter.write( "ID\t Name\t\t Prod Type\t Description\n");
            
            printWriter.write("----------------------------------------------------------------------------------------------------------\n");
            for (int i = 0; i < suppliersList().size(); i++)
            {
                printWriter.write(
                         
                        suppliersList().get(i).getStHolderId()+"\t"+
                        suppliersList().get(i).getName()+"\t"+
                        suppliersList().get(i).getProductType()+"\t\t"+
                        suppliersList().get(i).getProductDescription()+"\n"
                );
            }
            
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
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
        FHanlingSuppliers FHS = new FHanlingSuppliers ();
        
        //FH.openFile();
        //FH.writeCustomerOutfile();
        //FH.closeFile();
        FHS.openFile();
        FHS.writeSupplierOutFile();
        FHS.closeFile();
    }
}
