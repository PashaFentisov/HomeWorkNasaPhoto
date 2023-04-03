package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Camera {
    private int id;
    private String name;
    private int rover_id;
    private String full_name;
}
