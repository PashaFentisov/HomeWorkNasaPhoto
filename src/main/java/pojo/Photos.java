package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Photos {
    private List<Photo> photos;

    public List<Photo> getPhotos() {
        return photos;
    }

}