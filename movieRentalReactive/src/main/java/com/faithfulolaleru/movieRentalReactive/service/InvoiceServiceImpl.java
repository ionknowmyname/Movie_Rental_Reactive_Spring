package com.faithfulolaleru.movieRentalReactive.service;

import com.faithfulolaleru.movieRentalReactive.dto.InvoiceRequest;
import com.faithfulolaleru.movieRentalReactive.exception.ErrorResponse;
import com.faithfulolaleru.movieRentalReactive.exception.GeneralException;
import com.faithfulolaleru.movieRentalReactive.models.Invoice;
import com.faithfulolaleru.movieRentalReactive.models.Movie;
import com.faithfulolaleru.movieRentalReactive.repository.InvoiceRepository;
import com.faithfulolaleru.movieRentalReactive.response.AppResponse;
import com.faithfulolaleru.movieRentalReactive.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;



    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<AppResponse> createInvoice(InvoiceRequest request) {
        // get user
        return invoiceRepository.findInvoiceByUserAndMovie(user, request.getMovie())
                .map(invoice -> throwErrorIfExist(invoice))
                .switchIfEmpty(invoiceRepository.save(Invoice.builder()
                        .movie(request.getMovie())
                        .rentStartDate(request.getRentStartDate())
                        .rentDays(request.getRentDays())
                        .rentCost(request.getRentCost())
                        .build()))
                .map(invoice -> AppUtils.entityToDto2(invoice))
                .flatMap(o -> AppUtils.buildAppResponse(o, "Created Successfully"));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<AppResponse> getInvoiceById(String id) {
        return invoiceRepository.findById(id)
                .map(invoice -> AppUtils.entityToDto2(invoice))
                .flatMap(o -> AppUtils.buildAppResponse(o, "Successful"))
                .switchIfEmpty(Mono.error(new GeneralException(HttpStatus.NOT_FOUND,
                        ErrorResponse.ERROR_INVOICE_NOT_EXIST,
                        "Rent Invoice with id doesn't exist")));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<AppResponse> getAllInvoices() {
        return invoiceRepository.findAll()
                .map(i -> AppUtils.entityToDto2(i))
                .collectList()
                .flatMap(o -> AppUtils.buildAppResponse(o, "Successful"))
                .switchIfEmpty(Mono.empty());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<AppResponse> updateInvoiceById(InvoiceRequest request, String id) {
        return null;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Void> deleteInvoiceById(String id) {
        return null;
    }


    private Invoice throwErrorIfExist(Invoice invoice) {
        String message = "Movie with title '" + movie.getTitle() + "' already exists";
        throw new GeneralException(HttpStatus.CONFLICT, ErrorResponse.ERROR_INVOICE_ALREADY_EXIST, message);
    }
    private Invoice throwErrorIfNotExist(Invoice invoice) {
        String message = "Movie with title '" + movie.getTitle() + "' does not exist";
        throw new GeneralException(HttpStatus.NOT_FOUND, ErrorResponse.ERROR_MOVIE_NOT_EXIST, message);
    }
}
