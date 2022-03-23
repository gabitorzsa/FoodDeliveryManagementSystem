package presentation;

import business.DeliveryService;

public interface Observer {
    void update(DeliveryService deliveryService);
}
