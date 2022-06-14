package com.ahirajustice.retaila.mappings.client;

import com.ahirajustice.retaila.entities.Client;
import com.ahirajustice.retaila.requests.client.ClientCreateRequest;
import com.ahirajustice.retaila.viewmodels.client.ClientViewModel;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMappings {
    
    ClientViewModel clientToClientViewModel(Client client);

    Client clientCreateRequestToClient(ClientCreateRequest clientCreateRequest);

}
