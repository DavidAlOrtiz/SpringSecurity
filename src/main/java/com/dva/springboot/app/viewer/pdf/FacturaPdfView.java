package com.dva.springboot.app.viewer.pdf;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.dva.springboot.app.models.entity.Factura;
import com.dva.springboot.app.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.persistence.Table;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView {
	
	@Autowired
	private MessageSource mesageSourse;
	
	@Autowired
	private LocaleResolver localResolve;

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		MessageSourceAccessor mensajes = getMessageSourceAccessor();
		Locale  local = localResolve.resolveLocale(request);
		Factura factura = (Factura) model.get("factura");
		PdfPCell cell = null;
		PdfPTable tabla = new PdfPTable(1);
		tabla.setSpacingAfter(20);
		cell  = new PdfPCell(new Phrase(mesageSourse.getMessage("text.cliente.nombre", null, local)));
		cell.setBackgroundColor(new Color(123,123,134));
		cell.setPadding(10);
		tabla.addCell(cell);
		tabla.addCell(factura.getClient().getNombre().concat(" ") + factura.getClient().getApellido());
		tabla.addCell(factura.getClient().getEmail());
		
		
		PdfPTable tabla2 = new PdfPTable(1);
		tabla2.setSpacingAfter(20);
		cell = new PdfPCell(new Phrase(mensajes.getMessage("text.cliente.titulo")));
		cell.setBackgroundColor(new Color(22, 253, 252));
		cell.setPadding(10);
		tabla2.addCell(cell);
		tabla2.addCell("Folio "+ factura.getId());
		tabla2.addCell("Descripcion" + factura.getDescripcion());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm   dd/MM/YYYY");
		tabla2.addCell("fecha " + simpleDateFormat.format(factura.getCreateAt()));
		
		PdfPTable tabla3 = new PdfPTable(4);
		tabla3.setWidths(new float[] {3.5f, 1, 1, 1});
		tabla3.addCell("Producto");
		tabla3.addCell("Precio");
		tabla3.addCell("Cantidad");
		tabla3.addCell("Total");
		
		for(ItemFactura items : factura.getItemsFactura()) {
			tabla3.addCell(items.getProducto().getNombre());
			tabla3.addCell(items.getProducto().getPrecio().toString());
			cell = new PdfPCell(new Phrase(""+items.getCantidad()));
			cell.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
			tabla3.addCell(cell);
			tabla3.addCell(items.calcularImporte().toString());
		}
		
		cell = new PdfPCell(new Phrase("Total :"));
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		tabla3.addCell(cell);
		tabla3.addCell(""+ new BigDecimal(factura.getTotal()));
		
		document.add(tabla);
		document.add(tabla2);
		document.add(tabla3);
	}

}
