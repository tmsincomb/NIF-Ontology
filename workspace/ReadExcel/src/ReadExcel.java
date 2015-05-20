/*
 * This is a simple program reads an excel document that is generated from an OWL file
 * with extra spaces and blanks (using excel's XML import). Finally, this program
 * writes an output excel document without the unnecessary blanks and spaces.
 * This can be used for the NIF project where we need to generate excel/csv data
 * extracted from a set of OWL classes.
 * @Fahim Imam (August 14, 2013)
 */

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Iterator;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.util.HSSFColor;
 
public class ReadExcel 
{
    static String excelCellData = new String();
    public static void main( String [] args ) 
    {
        //Note: Only works with xls file, not xslx.
	    String excelFileName = "sample2.xls"; // Change the file name here.
											  // Make sure to keep the excel file at the same directory of this program.
		String outputOWLFileName = "sample2output.xls";  // Make sure to have .owl extension for the file name.
		
		loadExcelDocument(excelFileName);
		writeExcelDocument(outputOWLFileName);
        
    } // End of main.
    
    // Reads the excel document.
    public static void loadExcelDocument(String fileName)
    {
    	try 
        {
            InputStream input = new BufferedInputStream(
                        		new FileInputStream(fileName));
            POIFSFileSystem fs = new POIFSFileSystem(input);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            
			String data; 
			excelCellData = "";
			int prevColIndex = 0;
            Iterator rows = sheet.rowIterator();
            
            while(rows.hasNext()) 
            {  
                HSSFRow row = (HSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                
                while(cells.hasNext()) 
                {	
                	HSSFCell cell = (HSSFCell) cells.next();
                	
                	if (cell.getColumnIndex() == 0)
                	  {
                	  	//System.out.print("\n new row.\n");
                		excelCellData += "\n";
                	  	prevColIndex = -1; 
                	  }

            	  	if (cell.getColumnIndex() == prevColIndex) // if the cell has the same column index
            	  	   {  									   // as the previous then append a comma instead of a tab.
            	  											   // Example: a class with two/multiple parents in the excel file will be displayed in the same cell with a comma in between.	
            	  		excelCellData += ", " + cell.getStringCellValue();
            	  		prevColIndex--;
            	  	   }
               	  	else
               	  		excelCellData += "\t" + cell.getStringCellValue() ;
            	  		
                	 // System.out.print( " COL["+ cell.getColumnIndex() + "]" );
                	 // System.out.print("(Prev COL:" + prevColIndex + ")");
                	 
                	 prevColIndex += 1;
                  		
                } //End of while block 2.                
                
            } //End of while block 1.
            
          System.out.print(excelCellData);     
        } // End of try block.
        
        catch ( IOException ex ) 
        {
            ex.printStackTrace();
        } // End of catch.
    }
    
      
   public static void writeExcelDocument(String fileName)
    {
	  try
	  {
	    // Create file 
	    FileWriter fstream = new FileWriter(fileName);
	    BufferedWriter output = new BufferedWriter(fstream);
	    
	    
	    output.write(excelCellData);
	    System.out.println("\n\nSUCCESS! File named " + fileName + " has been written sucessfully.");
	    //Close the output stream
	    output.close();
	    }
	  catch (Exception e)
	  {//Catch exception if any
	      System.err.println("Error: " + e.getMessage());
	  }
    } // End of method writeExcelDocument.
   
   //Example manipulation of excel data.
   /* public static void writeExcelDocument1()
   {
   	 try 
        {
			FileOutputStream fileOut = new FileOutputStream("poi-test.xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("POI Worksheet");
           
			// index from 0,0... cell A1 is cell(0,0)
			
			HSSFRow row1 = worksheet.createRow((short) 0);

			HSSFCell cellA1 = row1.createCell((short) 0);
			cellA1.setCellValue("Hello");
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellA1.setCellStyle(cellStyle);

			HSSFCell cellB1 = row1.createCell((short) 1);
			cellB1.setCellValue("Goodbye");
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellB1.setCellStyle(cellStyle);

			HSSFCell cellC1 = row1.createCell((short) 2);
			cellC1.setCellValue(true);

			HSSFCell cellD1 = row1.createCell((short) 3);
			cellD1.setCellValue(new Date(12,12,12));
			cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(HSSFDataFormat
					.getBuiltinFormat("m/d/yy h:mm"));
			cellD1.setCellStyle(cellStyle);

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			
			

		} 
        
        catch (FileNotFoundException e) 
        {
			e.printStackTrace();
		} 
        
        catch (IOException e) 
		{
			e.printStackTrace();
		}
   	
   } */

} //End of file.