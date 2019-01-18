package ua.spro.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    private static final SessionFactory factory = build();
    private static StandardServiceRegistry registry;

    private static SessionFactory build() {
        registry = new StandardServiceRegistryBuilder().
                configure("ua/spro/hibernate/hibernate.cfg.xml").
                build();
        return new MetadataSources(registry).
                buildMetadata().
                buildSessionFactory();
    }

    public static SessionFactory getFactory(){
        return factory;
    }
}
