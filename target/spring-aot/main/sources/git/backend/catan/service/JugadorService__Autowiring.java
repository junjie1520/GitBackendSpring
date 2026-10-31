package git.backend.catan.service;

import java.lang.String;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.aot.AutowiredMethodArgumentsResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link JugadorService}.
 */
@Generated
public class JugadorService__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static JugadorService apply(RegisteredBean registeredBean, JugadorService instance) {
    AutowiredFieldValueResolver.forRequiredField("carreteraRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("poblatRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("assentamentRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("jugadorRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("partidaRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("arestaRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("vertexRepository").resolveAndSet(registeredBean, instance);
    AutowiredMethodArgumentsResolver.forRequiredMethod("construir", int.class, String.class, int.class).resolve(registeredBean, args -> instance.construir(args.get(0), args.get(1), args.get(2)));
    return instance;
  }
}
