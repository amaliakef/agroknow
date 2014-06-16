package gr.agroknow.metadata.harvester;



import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
import java.util.List;


import gr.agroknow.metadata.harvester.Record;
import org.ariadne.util.IOUtilsv2;
import org.ariadne.util.JDomUtils;
import org.ariadne.util.OaiUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;


import uiuc.oai.OAIException;
import uiuc.oai.OAIRecord;
import uiuc.oai.OAIRecordList;
import uiuc.oai.OAIRepository;


public class HarvestAllProcess{
    	
    
     
        public static void main(String[] args) throws OAIException, IOException, JDOMException {
           
            
            
            if (args.length != 3) {
                System.err.println("Usage: java HarvestProcess param1(target) param2(foldername) param3(metadataPrefix), e.g");                
                System.exit(1);
            } 
//            else{ throw new IOException("ERRROR");}            
            
           listRecords(args[0],args[1],args[2]);           
                  

        //   listRecords("http://jme.collections.natural-europe.eu/oai/","C:/testSet","oai_dc","");
        }

       



	public static void listRecords(String target, String folderName, String metadataPrefix) throws OAIException,IOException, JDOMException {

            File f = new File("D:/metadata_harvested.txt");
            FileWriter fw= new FileWriter(f);

		OAIRepository repos = new OAIRepository();
		File file = new File(folderName);
                String identifier = "";
		file.mkdirs();
               
                int counter = 0;

              repos.setBaseURL(target);
 
              OAIRecordList records;

		//OAIRecordList records = repos.listRecords("ese","9999-12-31","2000-12-31","");
              
               records = repos.listRecords(metadataPrefix);   
		
               
		//		records.moveNext();
		while (records.moreItems()) {
			OAIRecord item = records.getCurrentItem();

			/*get the lom metadata : item.getMetadata();
			 * this return a Node which contains the lom metadata.
			 */
			if(!item.deleted()) {
				Element metadata = item.getMetadata();
				if(metadata != null) {
					System.out.println(item.getIdentifier());
					Record rec = new Record();
					rec.setOaiRecord(item);
					rec.setMetadata(item.getMetadata());
					rec.setOaiIdentifier(item.getIdentifier());
                                        identifier = item.getIdentifier().replaceAll(":", "_");
                                        identifier = identifier.replaceAll("/",".");
					IOUtilsv2.writeStringToFileInEncodingUTF8(OaiUtils.parseLom2Xmlstring(metadata), folderName + "/" + identifier +".xml");
                                        System.out.println(item.getIdentifier());
                                        fw.write(counter + " " + item.getIdentifier()+ " complete \r\n");
                                        counter++;
				}
				else {
					System.out.println(item.getIdentifier() + " deleted");
                                        fw.write(counter + " " + item.getIdentifier()+ " deleted \r\n");
                                        counter++;
				}
			}
                        else {
					System.out.println(item.getIdentifier() + " deleted");
                                        fw.write(counter + " " + item.getIdentifier()+ " deleted \r\n");
                                        counter++;
				}
			records.moveNext();
		}
            fw.close();
	}


}