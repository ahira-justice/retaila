package com.ahirajustice.retail.mappings.client;

import com.ahirajustice.retail.entities.Client;
import com.ahirajustice.retail.requests.client.ClientCreateRequest;
import com.ahirajustice.retail.viewmodels.client.ClientViewModel;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMappings {
    
    ClientViewModel clientToClientViewModel(Client client);

    Client clientCreateRequestToClient(ClientCreateRequest clientCreateRequest);

}
