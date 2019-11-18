package example.springdata.geode.client.transactions.server.utils;

import org.apache.geode.cache.TransactionEvent;
import org.apache.geode.cache.TransactionWriter;
import org.apache.geode.cache.TransactionWriterException;
import org.apache.geode.internal.cache.TXEntryState;
import org.apache.geode.internal.cache.TXEvent;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomerTransactionWriter implements TransactionWriter {

	@Override
	public void beforeCommit(TransactionEvent transactionEvent) throws TransactionWriterException {
		AtomicBoolean six_found = new AtomicBoolean(false);
		((TXEvent)transactionEvent).getEvents().forEach(event -> {
			if(event instanceof TXEntryState.TxEntryEventImpl && ((TXEntryState.TxEntryEventImpl)event).getKey().equals(6L)) {
				six_found.set(true);
			}
		});

		if (six_found.get()) {
			throw new TransactionWriterException("Customer for Key: 6 is being changed. Failing transaction");
		}
	}
}