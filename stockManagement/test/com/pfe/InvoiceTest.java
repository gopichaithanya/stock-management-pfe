package com.pfe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.dozer.DozerBeanMapper;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pfe.server.dao.producttype.ProductTypeDao;
import com.pfe.server.dao.producttype.ProductTypeDaoImpl;
import com.pfe.server.dao.supplier.SupplierDao;
import com.pfe.server.dao.supplier.SupplierDaoImpl;
import com.pfe.server.model.ProductType;
import com.pfe.server.model.Supplier;
import com.pfe.server.service.InvoiceServiceImpl;
import com.pfe.server.service.SupplierServiceImpl;
import com.pfe.shared.BusinessException;
import com.pfe.shared.dto.InvoiceDTO;
import com.pfe.shared.dto.ProductTypeDTO;
import com.pfe.shared.dto.ShipmentDTO;
import com.pfe.shared.dto.SupplierDTO;

public class InvoiceTest {
	
	private ProductTypeDao productTypeDao;
	private SupplierDao supplierDao;
	private InvoiceServiceImpl invoiceService;
	private DozerBeanMapper dozerMapper;

	
	/**
	 * Create invoice with ONSALE payment type. The invoice contains 1 shipment
	 * so you should have at least one product type and one supplier in the
	 * database.
	 * 
	 */
	@Test
	public void createOnSaleInvoiceTest() {
		
		// Database connection
		ApplicationContext context = new ClassPathXmlApplicationContext("db-config.xml");
		SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		
		invoiceService = new InvoiceServiceImpl();
		dozerMapper = new DozerBeanMapper();

		// Invoice attributes
		InvoiceDTO i = new InvoiceDTO();
		Date date = Calendar.getInstance().getTime();
		String paymentType = InvoiceDTO.ONSALE_PAY;
		i.setCode(202);
		i.setCreated(date);
		i.setPaymentType(paymentType);
		
		//Supplier
		supplierDao = new SupplierDaoImpl(sessionFactory);
		Supplier supplier = (supplierDao.search(0, 1, null)).get(0);
		i.setSupplier(dozerMapper.map(supplier, SupplierDTO.class, SupplierServiceImpl.MINI_SUPPLIER_MAPPING));

		// Shipments
		ArrayList<ShipmentDTO> shipments = new ArrayList<ShipmentDTO>();
		int qty = 1000;
		ShipmentDTO s1 = new ShipmentDTO();
		s1.setCreated(date);
		s1.setCurrentQuantity(qty);
		s1.setInitialQuantity(qty);
		s1.setPaid(false);
		s1.setUnitPrice(new Double(20));
		
		// Shipment type
		productTypeDao = new ProductTypeDaoImpl(sessionFactory);
		ProductType type = (productTypeDao.search(0, 1, null)).get(0);
		s1.setProductType(dozerMapper.map(type, ProductTypeDTO.class));
	
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
