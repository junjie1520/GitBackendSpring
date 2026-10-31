package git.backend.catan.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link PartidaController}.
 */
@Generated
public class PartidaController__BeanDefinitions {
  /**
   * Get the bean definition for 'partidaController'.
   */
  public static BeanDefinition getPartidaControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(PartidaController.class);
    InstanceSupplier<PartidaController> instanceSupplier = InstanceSupplier.using(PartidaController::new);
    instanceSupplier = instanceSupplier.andThen(PartidaController__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
