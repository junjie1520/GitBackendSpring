package git.backend.catan;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CatanApplication}.
 */
@Generated
public class CatanApplication__BeanDefinitions {
  /**
   * Get the bean definition for 'catanApplication'.
   */
  public static BeanDefinition getCatanApplicationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CatanApplication.class);
    beanDefinition.setInstanceSupplier(CatanApplication::new);
    return beanDefinition;
  }
}
