package com.dovydasvenckus.budget.client;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @RequestMapping(method = GET)
    public List<Client> getClients() {
        return new ArrayList<>();
    }
}
