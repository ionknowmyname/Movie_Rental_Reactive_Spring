package com.faithfulolaleru.movieRentalReactive.controllers;

import com.faithfulolaleru.movieRentalReactive.dto.InvoiceRequest;
import com.faithfulolaleru.movieRentalReactive.response.AppResponse;
import com.faithfulolaleru.movieRentalReactive.service.InvoiceService;
import com.faithfulolaleru.movieRentalReactive.utils.AppUtils;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/invoices")
@Data
public class InvoiceController {

    private final InvoiceService invoiceService;


    @GetMapping("/")
    public Mono<AppResponse> getAllInvoices(){

        return invoiceService.getAllInvoices();
    }
    @PostMapping("/")
    public Mono<AppResponse> createInvoice(@RequestBody InvoiceRequest request) {

        return invoiceService.createInvoice(request);
    }

    @GetMapping("/{id}")
    public Mono<AppResponse> getInvoiceById(@PathVariable("id") String id){

        return invoiceService.getInvoiceById(id);
    }

    @PutMapping("/{id}")
    public Mono<AppResponse> updateInvoiceById(@PathVariable("id") String id,
                                             @RequestBody InvoiceRequest request) {

        return invoiceService.updateInvoiceById(request, id);
    }

    @DeleteMapping("/{id}")
    public Mono<AppResponse> deleteInvoiceById(@PathVariable("id") String id) {

        Mono<Void> voidMono = invoiceService.deleteInvoiceById(id);

        return AppUtils.buildAppResponse("Successfully Deleted Invoice");
    }


}
