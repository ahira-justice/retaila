package com.ahirajustice.retail.services.client.impl;

import com.ahirajustice.retail.common.CommonHelper;
import com.ahirajustice.retail.entities.Client;
import com.ahirajustice.retail.exceptions.BadRequestException;
import com.ahirajustice.retail.exceptions.ForbiddenException;
import com.ahirajustice.retail.exceptions.NotFoundException;
import com.ahirajustice.retail.mappings.client.ClientMappings;
import com.ahirajustice.retail.properties.AppProperties;
import com.ahirajustice.retail.queries.SearchClientsQuery;
import com.ahirajustice.retail.repositories.ClientRepository;
import com.ahirajustice.retail.requests.client.ClientCreateRequest;
import com.ahirajustice.retail.requests.client.ClientUpdateRequest;
import com.ahirajustice.retail.security.PermissionsProvider;
import com.ahirajustice.retail.services.client.ClientService;
import com.ahirajustice.retail.services.email.straegy.EmailGenerationStrategy;
import com.ahirajustice.retail.services.email.EmailService;
import com.ahirajustice.retail.services.permission.PermissionValidatorService;
import com.ahirajustice.retail.validators.ValidatorUtils;
import com.ahirajustice.retail.validators.client.ClientCreateRequestValidator;
import com.ahirajustice.retail.validators.client.ClientUpdateRequestValidator;
import com.ahirajustice.retail.viewmodels.client.ClientViewModel;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final AppProperties appProperties;
    private final ClientRepository clientRepository;
    private final PermissionValidatorService permissionValidatorService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final List<EmailGenerationStrategy> emailGenerationStrategies;

    private final ClientMappings mappings = Mappers.getMapper(ClientMappings.class);


    @Override
    public Page<ClientViewModel> searchClients(SearchClientsQuery query) {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_SEARCH_CLIENTS)) {
            throw new ForbiddenException();
        }

        return clientRepository.findAll(query.getPredicate(), query.getPageable()).map(mappings::clientToClientViewModel);
    }

    @Override
    public ClientViewModel getClient(long id) {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_VIEW_CLIENT)) {
            throw new ForbiddenException();
        }

        Optional<Client> clientExists = clientRepository.findById(id);

        if (!clientExists.isPresent()) {
            throw new NotFoundException(String.format("Client with id: '%d' does not exist", id));
        }

        return mappings.clientToClientViewModel(clientExists.get());
    }

    @Override
    public ClientViewModel createClient(ClientCreateRequest request) {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_CREATE_CLIENT)) {
            throw new ForbiddenException();
        }

        ValidatorUtils<ClientCreateRequest> validator = new ValidatorUtils<>();
        validator.validate(new ClientCreateRequestValidator(), request);

        if (clientRepository.existsByName(request.getName())) {
            throw new BadRequestException(String.format("Client with name: '%s' already exists", request.getName()));
        }

        Client client = mappings.clientCreateRequestToClient(request);

        String identifier = String.format("%s_%s", request.getName(), CommonHelper.generateRandomString(appProperties.getClientIdentifierLength(), appProperties.getClientIdentifierKeyspace()));
        String secret = CommonHelper.generateRandomString(appProperties.getClientSecretLength(), appProperties.getClientSecretKeyspace());

        client.setIdentifier(identifier);
        client.setSecret(passwordEncoder.encode(secret));
        client.setActive(true);

        emailGenerationStrategies.stream()
                .filter(x -> x.canApply())
                .forEach(x -> emailService.sendEmail(x.generateEmail(client, secret)));

        return mappings.clientToClientViewModel(clientRepository.save(client));
    }

    @Override
    public ClientViewModel updateClient(ClientUpdateRequest request, long id) {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_UPDATE_CLIENT)) {
            throw new ForbiddenException();
        }

        ValidatorUtils<ClientUpdateRequest> validator = new ValidatorUtils<>();
        validator.validate(new ClientUpdateRequestValidator(), request);

        Optional<Client> clientExists = clientRepository.findById(id);

        if (!clientExists.isPresent()) {
            throw new NotFoundException(String.format("Client with id: '%d' does not exist", id));
        }

        if (clientRepository.existsByName(request.getName())) {
            throw new BadRequestException(String.format("Client with name: '%s' already exists", request.getName()));
        }

        Client client = clientExists.get();

        client.setName(request.getName());
        client.setAdminEmail(request.getAdminEmail());

        return mappings.clientToClientViewModel(clientRepository.save(client));
    }

}
