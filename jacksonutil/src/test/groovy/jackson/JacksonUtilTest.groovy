package mylib.jackson

import spock.lang.Specification

import mylib.jackson.beans.Player
import mylib.jackson.beans.Player.Position

class JacksonUtilTest extends Specification {
    def "test jsonToObject"() {
        given:
            String json = this.getClass().getResource("/player.json").text
            
        when:
            Player p = JacksonUtil.jsonToObject(json, Player.class)
            println "jsonToObject: $p"

        then:
            p.no == 17
            p.name == "Shohei Ohtani" 
            p.positions[0].position == "P"
            p.positions[0].fpct == "1.000"
            p.positions[1].position == "DH"
            p.positions[1].fpct == null
    }

    def "test objectToJson"() {
        given:
            Player p = new Player()
            p.no = 9
            p.name = "Yi-Chuan Lin"
            p.positions = [new Position()]
            p.positions[0].position = "1B"

        when:
            String json = JacksonUtil.objectToJson(p)
            println "objectToJson: $json"

        then:
            noExceptionThrown()
    }
}

