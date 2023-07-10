package miu.edu.onlineRetailSystem.controller;

import miu.edu.onlineRetailSystem.contract.*;
import miu.edu.onlineRetailSystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")           // only admin allowed to see all customers.
    public ResponseEntity<?> findAll(Pageable pageable) {
        return new ResponseEntity<>(customerService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCustomer(@PathVariable int customerId) {
        CustomerResponse customerResponse = customerService.getCustomer(customerId);

        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> save(@RequestBody CustomerResponse customerResponse) {
        CustomerResponse savedCustomerResponse = customerService.save(customerResponse);

        return new ResponseEntity<>(savedCustomerResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@PathVariable int customerId, @RequestBody CustomerResponse customerResponse) {
        CustomerResponse modifiedCustomerResponse = customerService.update(customerId, customerResponse);

        return new ResponseEntity<>(modifiedCustomerResponse, HttpStatus.OK);
    }

    @GetMapping("/{customerId}/addresses")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCustomerAddresses(@PathVariable int customerId) {
        return new ResponseEntity<>(customerService.getCustomerAddresses(customerId), HttpStatus.OK);
    }

    @PostMapping("/{customerId}/addresses")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveCustomerAddress(@PathVariable int customerId, @RequestBody AddressResponse addressResponse) {
        return new ResponseEntity<>(customerService.saveCustomerAddress(customerId, addressResponse),
                HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}/addresses/{addressId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateCustomerAddress(@PathVariable int customerId,
                                                   @PathVariable int addressId,
                                                   @RequestBody AddressResponse addressResponse) {
        return new ResponseEntity<>(customerService.updateCustomerAddress(customerId, addressId, addressResponse),
                HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}/addresses/{addressId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteCustomerAddress(@PathVariable int customerId, int addressId) {
        customerService.deleteCustomerAddress(customerId, addressId);

         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{customerId}/addresses/{addressId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCustomerAddress(@PathVariable int customerId, int addressId) {
        return new ResponseEntity<>(customerService.getCustomerAddress(customerId, addressId),
                HttpStatus.OK);
    }

    @GetMapping("/{customerId}/billing-addresses")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCustomerBillingAddresses(@PathVariable int customerId) {
        // todo: to implement later
        return null;
    }

    @GetMapping("/{customerId}/shipping-addresses")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCustomerShippingAddresses(@PathVariable int customerId) {

        return new ResponseEntity<>(customerService.getCustomerShippingAddresses(customerId),
                HttpStatus.OK);
    }

    @GetMapping("/{customerId}/credit-cards")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCustomerCreditCards(@PathVariable int customerId) {
        return new ResponseEntity<>(customerService.getCustomerCreditCards(customerId), HttpStatus.OK);
    }

    @PostMapping("/{customerId}/credit-cards")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveCustomerCreditCard(@PathVariable int customerId, @RequestBody CreditCardResponse creditCardResponse) {
        return new ResponseEntity<>(customerService.saveCustomerCreditCard(customerId, creditCardResponse),
                HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}/credit-cards/{creditCardId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCustomerCreditCard(@PathVariable int customerId, @PathVariable int creditCardId) {
        return new ResponseEntity<>(customerService.getCustomerCreditCard(customerId, creditCardId),
                HttpStatus.OK);
    }

    @PutMapping("/{customerId}/credit-cards/{creditCardId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateCustomerCreditCard(@PathVariable int customerId,
                                                      @PathVariable int creditCardId,
                                                      @RequestBody CreditCardResponse creditCardResponse) {
       return new ResponseEntity<>(customerService.updateCustomerCreditCard(customerId,
               creditCardId, creditCardResponse), HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}/credit-cards/{creditCardId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteCustomerCreditCard(@PathVariable int customerId,
                                                      @PathVariable int creditCard) {
        return new ResponseEntity<>(customerService.deleteCustomerCreditCard(customerId, creditCard),
                HttpStatus.OK);
    }

    @GetMapping("/{customerId}/orders")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCustomerOrders(@PathVariable int customerId, Pageable pageable) {
        return new ResponseEntity<>(customerService.getCustomerOrders(customerId, pageable),
                HttpStatus.OK);
    }

    @PostMapping("/{customerId}/orders")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveCustomerOrder(@PathVariable int customerId,
                                               @RequestBody OrderResponse orderResponse) {
        return new ResponseEntity<>(customerService.saveCustomerOrder(customerId, orderResponse),
                HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}/orders/{orderId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCustomerOrder(@PathVariable int customerId, @PathVariable int orderId) {
        return new ResponseEntity<>(customerService.getCustomerOrder(customerId, orderId),
                HttpStatus.OK);
    }

    @PutMapping("/{customerId}/orders/{orderId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateCustomerOrder(@PathVariable int customerId,
                                                 @PathVariable int orderId,
                                                 @RequestBody OrderResponse orderResponse) {
        return new ResponseEntity<>(customerService.updateCustomerOrder(customerId, orderId, orderResponse),
                HttpStatus.OK);
    }

    @GetMapping("/{customerId}/orders/{orderId}/return")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> returnCustomerOrder(@PathVariable int customerId,
                                                 @PathVariable int orderId) {
        return new ResponseEntity<>(customerService.returnCustomerOrder(customerId, orderId),
                HttpStatus.OK);
    }

    @GetMapping("/{customerId}/orders/{orderId}/place")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> placeCustomerOrder(@PathVariable int customerId,
                                                @PathVariable int orderId) {
        return new ResponseEntity<>(customerService.placeCustomerOrder(customerId, orderId),
                HttpStatus.OK);
    }

    @GetMapping("/{customerId}/orders/{orderId}/order-lines")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCustomerOrderLines(@PathVariable int customerId,
                                                   @PathVariable int orderId) {
        return new ResponseEntity<>(customerService.getCustomerOrderLines(customerId, orderId),
                HttpStatus.OK);
    }

    @PostMapping("/{customerId}/orders/{orderId}/order-lines")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveCustomerOrderLine(@PathVariable int customerId,
                                                   @PathVariable int orderId,
                                                   @RequestBody OrderLineResponse orderLineResponse) {
        return new ResponseEntity<>(customerService.saveCustomerOrderLine(customerId, orderId, orderLineResponse),
                HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}/orders/{orderId}/order-lines/{orderLineId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCustomerOrderLine(@PathVariable int customerId,
                                                  @PathVariable int orderId,
                                                  @PathVariable int orderLineId) {
        return new ResponseEntity<>(customerService.getCustomerOrderLine(customerId, orderId, orderLineId),
                HttpStatus.OK);
    }

    @PutMapping("/{customerId}/orders/{orderId}/order-lines/{orderLineId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateCustomerOrderLine(@PathVariable int customerId,
                                                     @PathVariable int orderId,
                                                     @PathVariable int orderLineId,
                                                     @RequestBody OrderLineResponse orderLineResponse) {
        return new ResponseEntity<>(customerService.updateCustomerOrderLine(customerId, orderId, orderLineId, orderLineResponse),
                HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}/orders/{orderId}/order-lines/{orderLineId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteCustomerOrderLine(@PathVariable int customerId,
                                                     @PathVariable int orderId,
                                                     @PathVariable int orderLineId) {
        return new ResponseEntity<>(customerService.deleteCustomerOrderLine(customerId, orderId, orderLineId),
                HttpStatus.OK);
    }

    @GetMapping("/{customerId}/orders/{orderId}/review/{reviewId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCustomerOrderReview(@PathVariable int customerId,
                                                    @PathVariable int orderId,
                                                    @PathVariable int reviewId) {
        return new ResponseEntity<>(customerService.getCustomerOrderReview(customerId, orderId, reviewId),
                HttpStatus.OK);
    }

    @PostMapping("/{customerId}/orders/{orderId}/item/{itemId}/review")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveCustomerOrderItemReview(@PathVariable int customerId,
                                                     @PathVariable int orderId,
                                                     @PathVariable int itemId,
                                                     @RequestBody ReviewResponse reviewResponse) {
        return new ResponseEntity<>(customerService.saveCustomerOrderItemReview(customerId, orderId, itemId, reviewResponse),
                HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}/orders/{orderId}/item/{itemId}/review/{reviewId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateCustomerOrderReview(@PathVariable int customerId,
                                                       @PathVariable int orderId,
                                                       @PathVariable int reviewId,
                                                       @PathVariable int itemId,
                                                       @RequestBody ReviewResponse reviewResponse) {
        return new ResponseEntity<>(customerService.updateCustomerOrderReview(customerId, orderId, itemId, reviewId, reviewResponse),
                HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}/orders/{orderId}/review/{reviewId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteCustomerOrderReview(@PathVariable int customerId,
                                                       @PathVariable int orderId,
                                                       @PathVariable int reviewId) {
        return new ResponseEntity<>(customerService.deleteCustomerOrderReview(customerId, orderId, reviewId),
                HttpStatus.OK);
    }
}
