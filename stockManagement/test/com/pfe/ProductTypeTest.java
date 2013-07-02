package com.pfe;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.server.model.ProductType;

public class ProductTypeTest {

	@Test
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void createTypeTest() {
		
		ApplicationContext context=new ClassPathXmlApplicationContext
				("db-config.xml");  
        SessionFactory sf=(SessionFactory)context.getBean("sessionFactory");
        HibernateTemplate ht = new HibernateTemplate(sf);
		
		ProductType type = new ProductType();
		type.setName("pencil");
		type.setDescription("pencil description");
		ht.saveOrUpdate(type);

	}
}
