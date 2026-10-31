package git.backend.catan.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link JugadorService}.
 */
@Generated
public class JugadorService__BeanDefinitions {
  /**
   * Get the bean definition for 'jugadorService'.
   */
  public static BeanDefinition getJugadorServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(JugadorService.class);
    InstanceSupplier<JugadorService> instanceSupplier = InstanceSupplier.using(JugadorService::new);
    instanceSupplier = instanceSupplier.andThen(JugadorService__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
