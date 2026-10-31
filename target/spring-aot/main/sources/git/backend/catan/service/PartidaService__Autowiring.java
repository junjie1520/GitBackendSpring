package git.backend.catan.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link PartidaService}.
 */
@Generated
public class PartidaService__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static PartidaService apply(RegisteredBean registeredBean, PartidaService instance) {
    AutowiredFieldValueResolver.forRequiredField("pRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("hRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("aRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("vRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("arRepository").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
