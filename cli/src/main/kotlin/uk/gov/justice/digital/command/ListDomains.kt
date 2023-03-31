package uk.gov.justice.digital.command

import jakarta.inject.Inject
import jakarta.inject.Singleton
import picocli.CommandLine.Command
import picocli.CommandLine.Help.Ansi
import uk.gov.justice.digital.service.DomainService

@Singleton
@Command(
    name = "list",
    description = ["List all available domains"]
)
class ListDomains: Runnable {

    private val NAME_WIDTH = 20
    private val DESCRIPTION_WIDTH = 40
    private val PADDING = 2

    @Inject
    private lateinit var service: DomainService

    override fun run() {
        fetchAndDisplayDomains()
    }

    private fun fetchAndDisplayDomains() {

        val result = service.getAllDomains()

        if (result.isNotEmpty()) {

            println("Found ${result.size} domains\n")

            // Table headings
            val tableRowLine = tableRowDelimiter(NAME_WIDTH, DESCRIPTION_WIDTH)

            println(tableRowLine)
            printAnsiString(String.format("| @|bold %-20s|@ | @|bold %-40s|@ |", "Name", "Description"))
            println(tableRowLine)

            // Table rows
            result.forEach {
                println(String.format("| %-20s | %-40s |", it.name, it.description))
                println(tableRowLine)
            }
        }
        else printAnsiString("@|red,bold ERROR|@ No domains were found")
    }

    private fun tableRowDelimiter(vararg columnWidth: Int): String {
        return columnWidth
            .joinToString(separator = "+", prefix = "+", postfix = "+") { "-".repeat(it + PADDING) }
    }

    private fun printAnsiString(s: String) {
        println(Ansi.AUTO.string(s))
    }

}
