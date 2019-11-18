package example.springdata.geode.client.transactions.server.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.geode.cache.CacheEvent;
import org.apache.geode.cache.TransactionEvent;
import org.apache.geode.cache.util.TransactionListenerAdapter;
import org.apache.geode.internal.cache.TXEntryState;

import java.util.stream.Collectors;

public class CustomerTransactionListener extends TransactionListenerAdapter {
	private Log log = LogFactory.getLog(CustomerTransactionListener.class);

	@Override
	public void afterFailedCommit(TransactionEvent event) {

	}

	@Override
	public void afterRollback(TransactionEvent event) {
		log.info("In afterRollback for entry(s) [" + event.getEvents().stream().map(this::getEventInfo).collect(Collectors.toList()) + "]");
	}

	private String getEventInfo(CacheEvent cacheEvent) {
		if (cacheEvent instanceof TXEntryState.TxEntryEventImpl) {
			return ((TXEntryState.TxEntryEventImpl) cacheEvent).getNewValue().toString();
		} else {
			return cacheEvent.toString();
		}
	}
}