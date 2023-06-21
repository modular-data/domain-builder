package uk.gov.justice.digital.controller

import io.micronaut.http.MediaType.APPLICATION_JSON
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import uk.gov.justice.digital.model.Domain
import uk.gov.justice.digital.model.Status
import uk.gov.justice.digital.service.DomainService
import java.util.*

@Controller("/domain")
class DomainController(private val service: DomainService) {

    @Get("{?name,status}", produces = [APPLICATION_JSON])
    fun getDomains(name: String?, status: Status?): List<Domain> {
        return service.getDomains(name, status)
    }

    @Get("/{id}", produces = [APPLICATION_JSON])
    fun getDomain(id: UUID): Domain? {
        return service.getDomain(id)
    }

}