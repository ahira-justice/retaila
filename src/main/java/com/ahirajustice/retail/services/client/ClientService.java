package com.ahirajustice.retail.services.client;

import com.ahirajustice.retail.queries.SearchClientsQuery;
import com.ahirajustice.retail.requests.client.ClientCreateRequest;
import com.ahirajustice.retail.requests.client.ClientUpdateRequest;
import com.ahirajustice.retail.viewmodels.client.ClientViewModel;
import org.springframework.data.domain.Page;

public interface ClientService {

    Page<ClientViewModel> searchClients(SearchClientsQuery query);

    ClientViewModel getClient(long id);

    ClientViewModel createClient(ClientCreateRequest request);

    ClientViewModel updateClient(ClientUpdateRequest request, long id);

}
