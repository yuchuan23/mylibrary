package mylib.jackson.beans;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private Integer no;
    private String name;
    private List<Position> positions;

    @Data
    public static class Position {
        private String position;
        // 守備率
        private String fpct;
    }
}
