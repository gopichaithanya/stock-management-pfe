package com.pfe;

import java.util.ArrayList;

import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pfe.server.dao.producttype.ProductTypeDao;
import com.pfe.server.dao.supplier.SupplierDao;
import com.pfe.server.model.ProductType;
import com.pfe.server.model.Supplier;
import com.pfe.server.service.InvoiceServiceImpl;
import com.pfe.server.service.SupplierServiceImpl;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.InvoiceDTO;
import com.pfe.shared.dto.ProductTypeDTO;
import com.pfe.shared.dto.ShipmentDTO;
import com.pfe.shared.dto.SupplierDTO;

@ContextConfiguration(locations = {"/stockManagement/war/WEB-INF/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class InvoiceTest {
	
	@Autowired
	private ProductTypeDao productTypeDao;
	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private InvoiceServiceImpl invoiceService;
	@Autowired
	private DozerBeanMapper dozerMapper;

	
	/**
	 * Create invoice with ONSALE payment type. The invoice contains 1 shipment
	 * so you should have at least one product type and one supplier in the
	 * database.
	 * 
	 */
	@Test
	public void createOnSaleInvoiceTest() {

		// Invoice attributes
		InvoiceDTO i = new InvoiceDTO();
		String paymentType = InvoiceDTO.ONSALE_PAY;
		i.setCode(5);
		i.setPaymentType(paymentType);
		
		//Supplier
		Supplier supplier = (supplierDao.search(0, 1, null)).get(0);
		i.setSupplier(dozerMapper.map(supplier, SupplierDTO.class, SupplierServiceImpl.MINI_SUPPLIER_MAPPING));

		// Shipments
		ArrayList<ShipmentDTO> shipments = new ArrayList<ShipmentDTO>();
		int qty = 1000;
		ShipmentDTO s1 = new ShipmentDTO();
		s1.setCurrentQuantity(qty);
		s1.setInitialQuantity(qty);
		s1.setPaid(false);
		s1.setUnitPrice(new Double(20));
		
		// Shipment type
		ProductType type = (productTypeDao.search(0, 1, null)).get(0);
		s1.setProductType(dozerMapper.map(type, ProductTypeDTO.class));
		s1.setInvoice(i);
	
//		s1.setInvoice(i);
		shipments.add(s1);
		i.setShipments(shipments);
		
		try {
			invoiceService.create(i);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
	}

}
