package git.backend.catan.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link PartidaController}.
 */
@Generated
public class PartidaController__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static PartidaController apply(RegisteredBean registeredBean, PartidaController instance) {
    AutowiredFieldValueResolver.forRequiredField("service").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
