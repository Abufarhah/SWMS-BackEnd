package edu.birzeit.swms.services;

import edu.birzeit.swms.dtos.NotificationRequestDto;
import edu.birzeit.swms.dtos.SubscriptionRequestDto;

public interface NotificationService {

    String sendPnsToTopic(NotificationRequestDto notificationRequestDto);

    void subscribeToTopic(SubscriptionRequestDto subscriptionRequestDto);

    void unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto);

}
