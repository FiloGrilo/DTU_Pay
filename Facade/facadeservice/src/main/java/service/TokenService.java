package service;

import messaging.MessageQueue;
import messaging.Event;
import utils.CorrelationID;
import utils.EventTypes;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class TokenService {
    private MessageQueue queue;
    private Map<CorrelationID, CompletableFuture<List<String>>> correlations = new ConcurrentHashMap<>();

    public TokenService(MessageQueue q) {
        this.queue = q;
        this.queue.addHandler(EventTypes.REQUEST_TOKEN_SUCCESS, this::handleRequestTokensSuccess);
        this.queue.addHandler(EventTypes.REQUEST_TOKEN_FAILED, this::handleRequestTokensFail);
    }

    public List<String> customerTokensRequest(String cid, int amount){
        System.out.println("CustomerToken request for " + amount);
        var correlationID = CorrelationID.randomID();
        correlations.put(correlationID, new CompletableFuture<>());
        Event event = new Event(EventTypes.REQUEST_TOKEN, new Object[] { cid, correlationID, amount });
        queue.publish(event);
        return correlations.get(correlationID).join();
    }

    public void handleRequestTokensSuccess(Event e){
        System.out.println("REQUEST TOKEN SUCCESS");
        var customerTokens = e.getArgument(0, List.class);
        var correlationId = e.getArgument(1, CorrelationID.class);
        correlations.get(correlationId).complete(customerTokens);
    }

    public void handleRequestTokensFail(Event e){
        System.out.println("REQUEST TOKEN FAILED");
        var customerTokens = e.getArgument(0, List.class);
        var correlationId = e.getArgument(1, CorrelationID.class);
        correlations.get(correlationId).complete(customerTokens);
    }

}
