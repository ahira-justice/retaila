package com.ahirajustice.retaila.services.client;

import com.ahirajustice.retaila.queries.SearchClientsQuery;
import com.ahirajustice.retaila.requests.client.ClientCreateRequest;
import com.ahirajustice.retaila.requests.client.ClientUpdateRequest;
import com.ahirajustice.retaila.viewmodels.client.ClientViewModel;
import org.springframework.data.domain.Page;

public interface ClientService {

    Page<ClientViewModel> searchClients(SearchClientsQuery query);

    ClientViewModel getClient(long id);

    ClientViewModel createClient(ClientCreateRequest request);

    ClientViewModel updateClient(ClientUpdateRequest request, long id);

}
