package mylib.config

import spock.lang.Specification

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

class ConfigUtilTest extends Specification {
    def "test getWithDefault"() {
        given:
            Config config = ConfigFactory.load("default.conf")
            
        when:
            int number = ConfigUtil.getWithDefault(config, "config.number", 0)
            int number2 = ConfigUtil.getWithDefault(config, "config.number2", 0)

        then:
            number == 20
            number2 == 0
    }
}
