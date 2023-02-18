package com.example.HibernatePostgreSecurityJWT.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * session de hibernate para las llamdas sql y hql para usarlas de forma personalizada
 */
public class HibernateUtil {
    private static StandardServiceRegistry registry;

    private static SessionFactory sessionFactory;

    /**
     * generar una session para las llamadas sql y hql hacia la base de datos
     * @return mediante el patron singelton para generar un solo objeto
     */
    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null){
            try{
                //crear registry
                registry = new StandardServiceRegistryBuilder().configure().build();
                //crear metadarasources
                MetadataSources sources = new MetadataSources(registry);
                //crear Metadata
                Metadata metadata = sources.getMetadataBuilder().build();
                //crear SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            }catch (Exception e){
                e.printStackTrace();
                //si falla al crear sessionfactory destruye para evitar fallos
                if(registry !=null){ StandardServiceRegistryBuilder.destroy(registry);}
            }

        }
        return sessionFactory;

    }

    /**
     * detructor de la session
     */
    public static void shutdown(){
        if(registry != null){
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}
