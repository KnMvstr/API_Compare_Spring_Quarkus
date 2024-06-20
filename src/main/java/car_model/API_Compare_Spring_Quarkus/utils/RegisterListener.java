package car_model.API_Compare_Spring_Quarkus.utils;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegisterListener {
    private final EntityManagerFactory emf;

    private final SluggerEventListener sluggerEventListener;

    @PostConstruct
    private void init() {
        SessionFactoryImplementor sessionFactory = this.emf.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        if (registry != null) {
            registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(sluggerEventListener);
            registry.getEventListenerGroup(EventType.PRE_UPDATE).appendListener(sluggerEventListener);
        }
    }
}