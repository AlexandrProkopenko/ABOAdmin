package ua.spro.util.test;

import ua.spro.entity.client.Client;
import ua.spro.service.ClientService;
import ua.spro.service.hibernate.ClientServiceImpl;

public class HibernateTest {
    public static void main(String[] args) {
        ClientService service = new ClientServiceImpl();
        Client client = new Client(
                "Vasiya",
                10.2,
                "Masha",
                "+38 093 365 45 78",
                "Pechersk",
                1,
                1
        );
        service.save(client);
    }
}
