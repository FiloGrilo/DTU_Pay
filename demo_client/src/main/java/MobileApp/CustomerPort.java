package MobileApp;


import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import Entities.*;
import jakarta.ws.rs.core.Response;

/***
 * Author: Tiago Machado s222963
 */
public class CustomerPort {
    WebTarget baseUrl;
    Client client;

    public CustomerPort() {
        client = ClientBuilder.newClient();
        baseUrl = client.target("http://localhost:8080/customers");
    }

    public String registerCustomer(DTUPayUser customer)
            throws Exception  {
        System.out.println("REGISTER CUSTOMER CLIENT");
        System.out.println(customer.get_bankID());
            Response response = baseUrl.path("register").request().post(Entity.entity(customer, MediaType.APPLICATION_JSON));
        if (response.getStatus() != 200) {
            throw new Exception(response.readEntity(String.class));
        }
        String customerId = response.readEntity(String.class);

        response.close();
        return customerId;
    }
    public Boolean unregisterCustomer(String customerId)
            throws Exception {
        Response response = baseUrl.path("unregister").request()
                .post(Entity.entity(customerId, MediaType.APPLICATION_JSON));

        if (response.getStatus() != 201) {
            throw new Exception(response.readEntity(String.class));
        }
        boolean success = response.readEntity(Boolean.class);

        response.close();
        return success;
    }
    public List<String> requestTokensCustomer(String customerId, int amount) throws Exception {
        System.out.println("SENDING TOKEN REQUEST");
        TokenRequest request_info = new TokenRequest(customerId, amount);
        Response response = baseUrl
                .path("getTokens")
                .request()
                .post(Entity.entity(request_info, MediaType.APPLICATION_JSON));


        if (response.getStatus() != 200) {
            throw new Exception(response.readEntity(String.class));
        }

        List<String> newTokens = response.readEntity(new GenericType<>() {});
        response.close();
        return newTokens;
    }

    public List<PaymentReport> getCustomerReport(String customerID) throws Exception {

        Response response = baseUrl.path("report").request().post(Entity.entity(customerID, MediaType.APPLICATION_JSON));

        if (response.getStatus() != 200) {
            String error = response.readEntity(String.class);
            throw new Exception(error);
        }

        List<PaymentReport> report= response.readEntity(new GenericType<>() {});
        response.close();
        return report;
    }

    public void close() {
        client.close();
    }

}