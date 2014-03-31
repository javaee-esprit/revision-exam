package helpers;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import ds.DataSource;

public class XmlHelper {
    
    public static XmlHelper instance;
    
    
    private  JAXBContext context;
    private  Marshaller marshaller;
    private  Unmarshaller unmarshaller;

    private XmlHelper() {
        
        try {
            context = JAXBContext.newInstance(DataSource.class);
            unmarshaller = context.createUnmarshaller();
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException ex) {
            Logger.getLogger(XmlHelper.class.getName()).log(Level.SEVERE, "xml failure", ex);
        }
        
    }

    public static XmlHelper getInstance() {
        if(instance == null){
            instance = new XmlHelper();
        }
        return instance;
    }
    
    
    
    
    
    public  DataSource convertXmlToDataSource(String filePath){
        DataSource dataSource = null;
        File file = new File(filePath);
        try {
            dataSource = (DataSource) unmarshaller.unmarshal(file);
        } catch (JAXBException ex) {
            Logger.getLogger(XmlHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dataSource;
    }
    
    public  void convertDataSourceToXml(DataSource dataSource, String filePath){
          File file = new File(filePath);
          try { 
            marshaller.marshal(dataSource, file);
        } catch (JAXBException ex) {
            Logger.getLogger(XmlHelper.class.getName()).log(Level.SEVERE, "xml failure", ex);
        }
        
    }
    
}
