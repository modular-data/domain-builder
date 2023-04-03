package uk.gov.justice.digital.command

import jakarta.inject.Singleton
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import uk.gov.justice.digital.service.DomainService

@Singleton
@Command(
    name = "view",
    mixinStandardHelpOptions = true,
    description = ["View details for a specific domain"]
)
class ViewDomain(private val service: DomainService) : CommandBase(), Runnable {

    @Option(
        names = ["-n", "--name"],
        description = ["The name of the domain to view"],
        required = true
    )
    var domainName: String = ""

    override fun run() {
        val domain = service.getDomainWithName(domainName)
        if (domain == null) {
            printlnAnsi("@|red,bold ERROR|@ - no domain with name '@|bold $domainName|@' was found")
        }
        else {
            printlnAnsi("\n@|green,bold Found domain with name: '$domainName'|@\n")

            printlnAnsi("""
               @|bold Name        |@| ${domain.name} 
               @|bold Description |@| ${domain.description}
               @|bold Originator  |@| ${domain.originator}
            """.trimIndent())

            printlnAnsi("\n@|yellow,bold Tables in this domain|@\n")

            domain
                .tables
                .forEach {
                    printlnAnsi("""
                        @|bold Table       |@| ${it.name}
                        @|bold Description |@| ${it.description}
                        @|bold Sources     |@| ${it.transform.sources.joinToString()}
                        @|bold Query       |@| ${it.transform.viewText}
                        
                    """.trimIndent())
                }
        }
    }

}