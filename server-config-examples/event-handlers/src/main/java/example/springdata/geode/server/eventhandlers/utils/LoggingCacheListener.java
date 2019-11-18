package example.springdata.geode.server.eventhandlers.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.util.CacheListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class LoggingCacheListener<K, V> extends CacheListenerAdapter<K, V> {

	private Log logger = LogFactory.getLog(LoggingCacheListener.class);

	@Override
	public void afterCreate(EntryEvent<K, V> event) {
		logger.info("In region [" + event.getRegion().getName() + "] created key [" + event.getKey() + "] value [" + event.getNewValue() + "]");
	}

	@Override
	public void afterDestroy(EntryEvent<K, V> event) {
		logger.info("In region [" + event.getRegion().getName() + "] destroyed key [" + event.getKey() + "] ");
	}

	@Override
	public void afterUpdate(EntryEvent<K, V> event) {
		logger.info("In region [" + event.getRegion().getName() + "] updated key [" + event.getNewValue() + "] [oldValue [" + event.getOldValue() + "]] new value [" + event.getNewValue() + "]");
	}
}