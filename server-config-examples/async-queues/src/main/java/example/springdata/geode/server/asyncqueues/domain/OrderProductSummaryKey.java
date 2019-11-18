package example.springdata.geode.server.asyncqueues.domain;

import java.io.Serializable;

public final class OrderProductSummaryKey implements Serializable {

    private final Long productId;

    private final long timebucketStart;

    public OrderProductSummaryKey(Long productId, long timebucketStart) {
        this.productId = productId;
        this.timebucketStart = timebucketStart;
    }

    public int hashCode() {
        return this.productId.hashCode();
    }

    public boolean equals(Object other) {
        if (this != other) {
            if (other instanceof OrderProductSummaryKey) {
                OrderProductSummaryKey otherKey = (OrderProductSummaryKey)other;
                return this.productId.equals(otherKey.productId) && this.timebucketStart == otherKey.timebucketStart;
            }
            return false;
        } else {
            return true;
        }
    }
}
