package com.pfe;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.shared.model.LocationType;

public class LocationTypeTest {

	@Test
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void createTypeTest() {
		
		ApplicationContext context=new ClassPathXmlApplicationContext
				("db-config.xml");  
        
        SessionFactory sf=(SessionFactory)context.getBean("sessionFactory");
        HibernateTemplate ht = new HibernateTemplate(sf);
		
		LocationType ltype = new LocationType();
		ltype.setDescription("store");
		ht.saveOrUpdate(ltype);

	}
}
