package reports

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.OutputStream
import java.sql.Connection
import javax.inject.Inject
import javax.inject.Singleton
import net.sf.jasperreports.engine.JasperPrint
import net.sf.jasperreports.engine.export.JRPdfExporter
import net.sf.jasperreports.export.SimpleExporterInput
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput
import play.api.mvc.Action
import play.api.mvc.Controller
import java.sql.DriverManager
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperFillManager
import java.util.UUID
import play.api.Play
import net.sf.jasperreports.engine.design.JasperDesign
import net.sf.jasperreports.engine.xml.JRXmlLoader

object ReportBuilder {
    
    private var reportCache:scala.collection.Map[String,Boolean] = new scala.collection.mutable.HashMap[String,Boolean].empty
  
    def generateCompileFileName(jrxml:String): String = "/tmp/report_" + jrxml + "_.jasper"
  
    def compile(jrxml:String){
      if(reportCache.get(jrxml).getOrElse(true)){
        val design:JasperDesign = JRXmlLoader.load( Play.resourceAsStream("/public/reports/" + jrxml)(Play.current).get )
        JasperCompileManager.compileReportToFile(design, generateCompileFileName(jrxml))
        reportCache  += (jrxml -> false)
      } 
    }
  
    def toPdf(jrxml:String):ByteArrayInputStream = {
      try {
          val os:OutputStream = new ByteArrayOutputStream()
          val reportParams:java.util.Map[String,Object] = new java.util.HashMap()
            
          val con:Connection = DriverManager.getConnection("jdbc:mysql://localhost/RWS_DB?user=root&password=&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC")
          
          compile(jrxml)
          val jrprint:JasperPrint  = JasperFillManager.fillReport(generateCompileFileName(jrxml), reportParams, con)
          
          val exporter:JRPdfExporter  = new JRPdfExporter()
          exporter.setExporterInput(new SimpleExporterInput(jrprint));
          exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));      
          exporter.exportReport()

          new ByteArrayInputStream((os.asInstanceOf[ByteArrayOutputStream]).toByteArray())
          
      }catch {
        case e:Exception => throw new RuntimeException(e)
        null
      }   
   }
  
}