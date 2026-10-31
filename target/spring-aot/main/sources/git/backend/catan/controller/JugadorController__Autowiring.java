package git.backend.catan.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link JugadorController}.
 */
@Generated
public class JugadorController__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static JugadorController apply(RegisteredBean registeredBean, JugadorController instance) {
    AutowiredFieldValueResolver.forRequiredField("service").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
