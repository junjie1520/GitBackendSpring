package git.backend.catan.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link JugadorController}.
 */
@Generated
public class JugadorController__BeanDefinitions {
  /**
   * Get the bean definition for 'jugadorController'.
   */
  public static BeanDefinition getJugadorControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(JugadorController.class);
    InstanceSupplier<JugadorController> instanceSupplier = InstanceSupplier.using(JugadorController::new);
    instanceSupplier = instanceSupplier.andThen(JugadorController__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
