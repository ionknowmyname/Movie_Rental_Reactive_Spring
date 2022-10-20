package com.faithfulolaleru.movieRentalReactive.service;

import com.faithfulolaleru.movieRentalReactive.dto.InvoiceRequest;
import com.faithfulolaleru.movieRentalReactive.response.AppResponse;
import reactor.core.publisher.Mono;

public interface InvoiceService {

    Mono<AppResponse> createInvoice(InvoiceRequest request);

    Mono<AppResponse> getInvoiceById(String id);

    Mono<AppResponse> getAllInvoices();

    Mono<AppResponse> updateInvoiceById(InvoiceRequest request, String id);

    Mono<Void> deleteInvoiceById(String id);
}
