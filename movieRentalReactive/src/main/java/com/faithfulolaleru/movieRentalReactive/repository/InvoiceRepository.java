package com.faithfulolaleru.movieRentalReactive.repository;

import com.faithfulolaleru.movieRentalReactive.models.Invoice;
import com.faithfulolaleru.movieRentalReactive.models.Movie;
import com.faithfulolaleru.movieRentalReactive.models.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InvoiceRepository extends ReactiveMongoRepository<Invoice, String> {

    Flux<Invoice> findInvoicesByUser(User user);

    Mono<Invoice> findInvoiceByUserAndMovie(User user, Movie movie);

}
