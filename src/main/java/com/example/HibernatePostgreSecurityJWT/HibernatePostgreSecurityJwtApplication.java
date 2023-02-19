package com.example.HibernatePostgreSecurityJWT;

import com.example.HibernatePostgreSecurityJWT.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HibernatePostgreSecurityJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(HibernatePostgreSecurityJwtApplication.class, args);
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		sessionFactory.close();
		session.close();
	}

}
