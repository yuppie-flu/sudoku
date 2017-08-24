package com.github.yuppieflu

import com.google.common.base.Charsets
import com.google.common.io.Resources
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class AcceptanceTest extends Specification {

    @Shared
    def boardsContentFile = 'boardsContent.txt'
    @Shared
    def expectedResultsFile = 'expectedResults.txt'

    @Subject
    TableValidator tableValidator = new TableValidator()

    @Unroll
    def "Valid boards acceptance test"(String table) {
        expect:
        tableValidator.validate(table)

        where:
        table << readBoards('valid-boards')
    }

    @Unroll
    def "Invalid boards acceptance test"(String table) {
        expect:
        !tableValidator.validate(table)

        where:
        table << readBoards('invalid-boards')
    }

    List<String> readBoards(String directory) {
        new File(getClass().getResource("/${directory}").toURI())
                .listFiles()
                .collect({ Resources.getResource("${directory}/${it.name}") })
                .collect({ Resources.toString(it, Charsets.US_ASCII) })
                .toList()
    }
}
