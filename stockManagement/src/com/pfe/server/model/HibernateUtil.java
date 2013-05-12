package com.pfe.server.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();
	private static Connection genericConnection;

	private static Properties configurationProperties;

	private static SessionFactory buildSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		try {
			configurationProperties = configuration.getProperties();
			genericConnection = openConnection();
		} catch (Exception e) {
		}
		ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder().applySettings(configuration.getProperties());
		return configuration.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
	}

	private static Connection openConnection() throws Exception {
		String driverClass = configurationProperties.getProperty("hibernate.connection.driver_class");
		String connectionUrl = configurationProperties.getProperty("hibernate.connection.url");
		String username = configurationProperties.getProperty("hibernate.connection.username");
		String password = configurationProperties.getProperty("hibernate.connection.password");
		Class.forName(driverClass);
		return DriverManager.getConnection(connectionUrl + "?user=" + username + "&password=" + password);
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
