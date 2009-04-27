package dynamo.core.template;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.util.Assert;

/**
 * JasperTemplateManager.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public final class JasperTemplateManager
			implements TemplateManager {

	/**
	 * @see dynamo.core.template.TemplateManager#generateOutput(dynamo.core.template.TemplateInfo)
	 */
	public Object generateOutput(TemplateInfo input) throws TemplateException {
		Assert.notNull(input, "TemplateInfo must not be null");
		Object report = null;
		
		try {
			// Initialize a bean collection datasource type
			Collection<Object> beans = input.getAllBeans();
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(beans);
			
			// Fill report with the given data
			InputStream sourceStream = input.getSourceFromClasspath();
			Map<String, Object> params = input.getParamsMap();
			report = JasperFillManager.fillReport(sourceStream, params, dataSource);
		}
		catch (JRException ex) {
			throw new TemplateException("Could not fill template", ex);
		}
		catch (Exception ex) {
			throw new TemplateException("Could not generate output using JasperReports", ex);
		}
		return report;
	}

	/**
	 * Exports report to PDF:
	 * 
	 * @param report to export
	 * @return the PDF report
	 * @throws TemplateException when the report could not be exported
	 */
	public static byte[] exportReportToPdf(JasperPrint report) throws TemplateException {
		Assert.notNull(report, "JasperPrint report must not be null");
		byte[] pdfReport = null;
		try {
			pdfReport = JasperExportManager.exportReportToPdf(report);
		}
		catch (JRException ex) {
			throw new TemplateException("Could not generate PDF report using JasperReports engine", ex);
		}
		return pdfReport;
	}

}
