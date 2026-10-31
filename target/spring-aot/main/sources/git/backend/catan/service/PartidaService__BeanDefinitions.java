package git.backend.catan.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link PartidaService}.
 */
@Generated
public class PartidaService__BeanDefinitions {
  /**
   * Get the bean definition for 'partidaService'.
   */
  public static BeanDefinition getPartidaServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(PartidaService.class);
    InstanceSupplier<PartidaService> instanceSupplier = InstanceSupplier.using(PartidaService::new);
    instanceSupplier = instanceSupplier.andThen(PartidaService__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
