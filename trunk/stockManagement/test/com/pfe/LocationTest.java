package com.pfe;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pfe.server.model.Location;
import com.pfe.server.model.LocationType;

@SuppressWarnings("unchecked")
public class LocationTest {

	@Test
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void createLocationTest() {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"db-config.xml");

		SessionFactory sf = (SessionFactory) context.getBean("sessionFactory");
		HibernateTemplate ht = new HibernateTemplate(sf);

		List<LocationType> list = ht
				.find("from LocationType lt where lt.description = 'warehouse'");
		LocationType locationType = list.get(0);
		
		Location w = new Location();
		w.setName("Main warehouse");
		w.setType(locationType);
		ht.saveOrUpdate(w);

	}
}
