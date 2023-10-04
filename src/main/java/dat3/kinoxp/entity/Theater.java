package dat3.kinoxp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder //We will talk about this in the class
@NoArgsConstructor
@Entity
public class Theater {

    @Id
    private int id;

    @Column(name="size", nullable = false)
    private int size;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
    List<Showing> showings;

    public void addShowing(Showing showing){
        if(showings == null){
            showings = new ArrayList<>();
        }
        showings.add(showing);
    }

    public Theater(int id, int size) {
        this.id = id;
        this.size = size;
    }
}
