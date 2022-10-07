package com.faithfulolaleru.movieRentalReactive.service;

import com.faithfulolaleru.movieRentalReactive.dto.InvoiceRequest;
import com.faithfulolaleru.movieRentalReactive.response.AppResponse;
import reactor.core.publisher.Mono;

public interface InvoiceService {

    public Mono<AppResponse> createInvoice(InvoiceRequest request);

    public Mono<AppResponse> getInvoiceById(String id);

    public Mono<AppResponse> getAllInvoices();

    public Mono<AppResponse> updateInvoiceById(InvoiceRequest request, String id);

    public Mono<Void> deleteInvoiceById(String id);
}
