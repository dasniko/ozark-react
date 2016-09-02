package dasniko.ozark.react;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.script.ScriptEngine;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
@ApplicationScoped
public class NashornPool {

    private GenericObjectPool<ScriptEngine> pool;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMinIdle(1);

        pool = new GenericObjectPool<>(new NashornPoolFactory(), config);
    }

    @Produces
    public ObjectPool<ScriptEngine> getObjectPool() {
        return this.pool;
    }


}
